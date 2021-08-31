package com.jenkins
import groovy.json.JsonSlurper
import groovy.json.JsonOutput

public enum InputParameterType {
  STRING('stringParameter'),
  PASSWORD('passwordParameter'),
  BOOLEAN('booleanParameter'),
  TEXT('textParameter'),
  HIDDEN('hiddenParameter'),
  CHOICE('choiceParameter'),
  GITSCM('gitScmParameter'),
  ACTIVECHOICEPARAMETER('activeChoiceParameter'),
  INPUTFILE('inputFileParameter'),
  INPUTFILE_WITH_STRING('inputFileParameterWithString'),
  ENV_SELECTION('envSelectionParameter'),
  BRANCH_SELECTION('branchSelectionParameter'),
  VERTICAL_SELECTION('verticalSelectionParameter'),
  NODEJS_LOGLEVEL('logLevelParameter'),
  MULTI_GIT_CHECKOUTS('multipleGitCheckoutsParameter')

  final String methodToInvoke;

  private InputParameterType(String methodToInvoke) {
      this.methodToInvoke = methodToInvoke;
  }

  String getMethodToInvoke(){
      return methodToInvoke
  }
}





def generateStandardDataInputParams(paramDetails=[]) {
  def paramUtils = new ParameterUtilities()
  def inputParameterType = InputParameterType
  def paramsArray = []

  // Generating standardized input params
  try {
    if(paramDetails instanceof Collection) {
      for (Map param in paramDetails) {
        if (!param.name || !param.paramType) {
          throw new Exception("A parameter should have name and paramType attributes!")
        }
        def methodToInvoke = param.paramType.getMethodToInvoke()
        def currentParameter = paramUtils."$methodToInvoke"(param)

        // Checking for composite parameter / custom parameter
        if (currentParameter instanceof Collection) {
          currentParameter.each { paramsArray.push(it) }
        }
        else
          paramsArray.push(currentParameter)
      }
    }

    paramsArray.push(paramUtils.jsonObjectHiddenParameter([
        name: 'paramDetails',
        meta: paramDetails,
        description: 'Job parameters meta info'
      ]))
  }
  catch (Exception ex) {
    throw ex;
  }
  finally {
    return paramUtils.processParameters(paramsArray);
  }
}





def processParameters(jobParams = []) {
  def defaultParams = defaultJobParameters()
  return jobParams + defaultParams
}




def defaultJobParameters() {
  return [
    booleanParam(name: 'muteSlackNotifications', description: 'Check this parameter to mute slack notifications received at Slackbot')
  ]
}