package com.jenkins
import groovy.json.JsonOutput
/**
 * Provides a choice parameter
 * @param parameterName
 * @param description
 * @param script
 * @return
 */




def activeChoicesParameter(parameterName, description, script) {
  return [
      $class     : 'ChoiceParameter',
      choiceType : 'PT_SINGLE_SELECT',
      description: description,
      filterable : true,
      name       : parameterName,
      script     : [
          $class        : 'GroovyScript',
          fallbackScript: [classpath: [], sandbox: false, script: ''],
          script        : [
              classpath: [],
              sandbox  : false,
              script   : script
          ]
      ]
  ]
}

/**
 * Will build an active choices parameter for the give project and repo
 * @param credentials
 * @param name
 * @param description
 * @return
 */
// def activeChoiceParamWithAvailBranches(credentials, project, repo, name, description) {
//   def activeChoice = new ActiveChoiceUtilities()
//   def script = activeChoice.getBranchesListFromRepo(project, repo, credentials)
//   return [
//       $class     : 'ChoiceParameter',
//       choiceType : 'PT_SINGLE_SELECT',
//       description: description,
//       filterable : true,
//       name       : name,
//       script     : [
//           $class        : 'GroovyScript',
//           fallbackScript: [classpath: [], sandbox: false, script: ''],
//           script        : [
//               classpath: [],
//               sandbox  : false,
//               script   : script
//           ]
//       ]
//   ]
// }

/**
 * Provides a cascade choice parameter
 * @param parameterName
 * @param description
 * @param script
 * @param referencedParameter
 * @return
 */
def cascadeChoicesParameter(parameterName, description, script, referencedParameter = null) {
  return [
      $class     : 'CascadeChoiceParameter',
      choiceType : 'PT_SINGLE_SELECT',
      description: description,
      filterable : true,
      name       : parameterName,
      referencedParameters: referencedParameter,
      script     : [
          $class        : 'GroovyScript',
          fallbackScript: [classpath: [], sandbox: false, script: ''],
          script        : [
              classpath: [],
              sandbox  : false,
              script   : script
          ]
      ]
  ]
}

/**
 * Will setup a new parameter called ENV will all of the env for a given PROJECT
 * requires: parameter PROJECT
 * @param accountName
 * @param region
 * @param paramName - the variable name for the param (default ENV)
 * @return
 */
// def get_env(accountName, region, paramName = 'ENV') {
//   def consulInstanceConfig = new Constants().getConsulConfig()[accountName][region]
//   def consulUrl = consulInstanceConfig.url
//   def consulToken = consulInstanceConfig.token
//   def credentials = com.cloudbees.plugins.credentials.CredentialsProvider.lookupCredentials(
//       org.jenkinsci.plugins.plaincredentials.impl.StringCredentialsImpl.class,
//       jenkins.model.Jenkins.instance
//   )
//   def matchingCredentials = credentials.findResult { it.id == consulToken ? it : null }
//   def token = hudson.util.Secret.toString(matchingCredentials.getSecret())
//   def script = """
//     import groovy.json.JsonSlurper
//     def response = "${consulUrl}/v1/kv/environments/?token=${token}&keys".toURL().getText()
//     def json = new JsonSlurper().parseText(response)""" + '''
//     def env = json.findAll{ env -> env.contains("${PROJECT}/url") && !env.contains("history")}.collect{item -> item.split("/")[1]}
//     return env
//   '''
//   return cascadeChoicesParameter(paramName, 'Application ENV', script, 'PROJECT')
// }

/**
* This method will append default parameters to existing job parameters
* @param jobParams Array current parameters for a job
*
* @return Array default parameters appended to current job parameters
*/
def processParameters(jobParams = []) {
  def defaultParams = new Constants().defaultJobParameters()
  return jobParams + defaultParams
}

/**
* Return a Choice parameter containing default choices for NodeJS Script output log levels
*
* @param name set parameter name for a new choice parameter
* @param description (Optional) set description for a new choice parameter
*
* @return Jenkins choice parameter object
*/
def nodeScriptLogLevelParam(name, description = 'Set the NodeJS Script log level') {
  def constantObj = new Constants()
  choice(choices: constantObj.getNodeScriptLogLevels(), description: 'Set the log level', name: name)
}

/**
* Return a Choice parameter containing default choices for NodeJS Script output log levels
*
* @param values HashMap should have keys: name, description
*
* @return Jenkins choice parameter object
*/
def logLevelParameter(values =[:]) {
  nodeScriptLogLevelParam(values.name, values.description? values.description:"")
}

/**
* @param values HashMap contains various attributes required to build a Jenkins String parameter
*
* @return Jenkins String Parameter
*/
def stringParameter(values = [:]) {
  return string(name: values.name, description: values.description? values.description:"", defaultValue: values.defaultValue, trim: values.trim)
}

/**
* @param values HashMap contains various attributes required to build a Jenkins Password parameter
*
* @return Jenkins Password Parameter
*/
def passwordParameter(values = [:]) {
  return password(name: values.name, description: values.description? values.description:"")
}

/**
* @param values HashMap contains various attributes required to build a Jenkins Boolean parameter
*
* @return Jenkins Boolean Parameter
*/
def booleanParameter(values = [:]) {
  return booleanParam(name: values.name, description: values.description? values.description:"", defaultValue: values.defaultValue)
}

/**
* @param values HashMap contains various attributes required to build a Jenkins Text parameter
*
* @return Jenkins Text Parameter
*/
def textParameter(values = [:]) {
  return text(name: values.name, description: values.description? values.description:"", defaultValue: values.defaultValue)
}

/**
* @param values HashMap contains various attributes required to build a Jenkins Choice(Dropdown) parameter
*
* @return Jenkins Choice(Dropdown) Parameter
*/
def choiceParameter(values = [:]) {
  return choice(choices: values.choices, name: values.name, description: values.description? values.description:"")
}

/**
* @param values HashMap contains various attributes required to build a Jenkins Hidden parameter
*
* @return Jenkins Hidden Parameter
*/
def hiddenParameter(values = [:]) {
  return [
        $class: 'WHideParameterDefinition',
        defaultValue: values.defaultValue,
        description: values.description? values.description:"",
        name: values.name
      ]
}

/**
* For newer Jenkins job implementations please use this method!
* @param values HashMap contains various attributes required to build a Jenkins ActiveChoice CascadeChoiceParameter
*                 Currently used `ActiveChoice choiceType` are 1. PT_SINGLE_SELECT 2. PT_MULTI_SELECT
*                 Other available choiceTypes - 1. PT_CHECKBOX 2. PT_RADIO
*
* @return Jenkins ActiveChoice CascadeChoiceParameter
*/
def activeChoiceParameter(values = [:]) {
  return [
        $class: 'CascadeChoiceParameter',
        choiceType: values.choiceType,
        description: values.description? values.description:"",
        filterable: values.filterable ? values.filterable : true,
        name: values.name,
        referencedParameters: values.referencedParameters,
        script: [
          $class: 'GroovyScript',
          fallbackScript: [classpath: [], sandbox: false, script: ''],
          script: [
            classpath: [],
            sandbox: false,
            script: values.script
          ]
        ]
      ]
}

/**
*
* @param values HashMap should have keys: fileName, additionalMessage, archiveFile, [printUrls=true], [isOptional=true]
*
* @return Jenkins ActiveChoice CascadeChoiceParameter
*/
def inputFileParameter(values = [:]){
  try{
    if (values.meta){
        def hiddenFileParamMetaJson = JsonOutput.toJson(values.meta)
        return hiddenParameter(["name":"fileParamMeta",
          "defaultValue":"""
          ${hiddenFileParamMetaJson.toString()}
          """,
          "description": "File parameters meta info"])
    }else{
      return []
    }
  }catch(Exception ex){
    return []
  }
}

/**
*
* @param values HashMap should have keys: branchParamName, branchParamScmProject, branchParamScmRepo
* @return Jenkins ActiveChoice CascadeChoiceParameters for branch selection
*/
// def branchSelectionParameter(values = [:]){
//   def paramArray= []
//   def activeChoiceUtils = new ActiveChoiceUtilities()

//   if(values.name){
//     paramArray.push(activeChoiceParameter([
//       "paramType": InputParameterType.ACTIVECHOICEPARAMETER,
//       "name": values.name,
//       "choiceType": "PT_SINGLE_SELECT",
//       "description": (values.description? values.description: "Active choice parameter to list branches from desired repositories"),
//       "filterable": "true",
//       "referencedParameters": "",
//       "script" : activeChoiceUtils.getBranchesListFromRepo(values.branchParamScmProject? values.branchParamScmProject: params.scmProject, values.branchParamScmRepo? values.branchParamScmRepo : params.scmRepoName)
//     ]))
//   }
//   return paramArray
// }

/**
*
* @param values HashMap should have keys: name, description, accountName, region, squadronEnvParamName
* @return Jenkins ActiveChoice CascadeChoiceParameters for vertical selection
*/
// def verticalSelectionParameter(values = [:]) {
//   def paramArray = []
//   def activeChoiceUtils = new ActiveChoiceUtilities()
//   if(values.name) {
//     paramArray.push(activeChoiceParameter([
//       "paramType": InputParameterType.ACTIVECHOICEPARAMETER,
//       "name": values.name,
//       "choiceType": values.choiceType,
//       "description": values.description ? values.description : "Selene vertical name",
//       "filterable": true,
//       "referencedParameters": values.squadronEnvParamName,
//       "script" : activeChoiceUtils.getVerticalListFromSelene(values.accountName, values.region, values.squadronEnvParamName)
//     ]))
//   }
//   return paramArray
// }

/**
*
* @param values HashMap should have keys: envParamName, envParamAccount, envParamRegion
* @return Jenkins ActiveChoice CascadeChoiceParameters for environment selection
*/
// def envSelectionParameter(values = [:]){
//   def paramArray= []
//   def pipelineUtils = new PipelineUtilities()

//   if(values.meta instanceof Collection){
//     values.meta.each{
//       if(it.envParamName){
//         paramArray.push(activeChoiceParameter([
//           "paramType": InputParameterType.ACTIVECHOICEPARAMETER,
//           "name": it.envParamName,
//           "choiceType": "PT_SINGLE_SELECT",
//           "description": it.description? it.description: "Active choice parameter to list squadron environments from desired repositories",
//           "filterable": "true",
//           "referencedParameters": "",
//           "script" : pipelineUtils.getEnvironmentlistByAccountAndRegion(it.envParamAccount, it.envParamRegion)
//         ]))
//       }
//     }
//   }
//   return paramArray
// }

/**
* By default files will be uploaded to the s3 bucket. If you want to change this behavior, set `archiveFile` option to `false` in the values object
*
* @param values HashMap should have keys: fileName, additionalMessage, archiveFile, stringPeerName, stringPeerDescription, stringPeerNeedsTextParam
* @return Jenkins Parameter array for string/text and file inputs as specified in input map
*/
def inputFileParameterWithString(values = [:]) {
  def paramArray = []

  try {
    if (values.meta){
        if(values.meta instanceof Collection){
          values.meta.each{
            if(it.stringPeerName){
              if(it.stringPeerNeedsTextParam == true)
                paramArray.push(textParameter(["name": it.stringPeerName, "description": it.stringPeerDescription, "defaultValue": it.defaultValue]))
              else
                paramArray.push(stringParameter(["name": it.stringPeerName, "description": it.stringPeerDescription, "defaultValue": it.defaultValue, "trim": true]))
            }
          }
        }

        // Below hidden parameter is similar to that of `inputFileParameter` written above to be used within pipeline utilities standard wrapper
        paramArray.push(jsonObjectHiddenParameter([
          name: 'fileParamMeta',
          meta: values.meta,
          description: 'File parameters meta info',
        ]))
    }
  }
  catch (Exception ex) {
    error("File input with string peer failed: "+ ex.getMessage())
  }

  return paramArray
}

/**
* @param values HashMap should have keys: scmProject, scmRepoName
*
* @return Jenkins Parameter array for SCM project and SCM repo name
*/
def gitScmParameter(values = [:]){
  def paramArray = []

  try{
    if(values.meta.scmProject){
      paramArray.push(hiddenParameter(["name":"scmProject", "defaultValue": values.meta.scmProject, "description": "SCM project name"]))
    }
    if(values.meta.scmRepoName){
      paramArray.push(hiddenParameter(["name":"scmRepoName", "defaultValue": values.meta.scmRepoName, "description": "SCM repository name"]))
    }
    return paramArray
  }catch(Exception ex){
    error("Could not create SCM parameters:"+ ex.getMessage())
  }
}

/**
*
* @param values HashMap should have keys ->  meta: [[repository, project, branch, checkoutDir]]
* @return Jenkins parameter array with a new Hidden param containing details about Multiple Git Checkouts
*/
def multipleGitCheckoutsParameter(values = [:]) {
  def paramArray = []
  if (! values.meta)
    return paramArray

  paramArray.push(jsonObjectHiddenParameter([
    name: 'gitCheckoutsData',
    meta: values.meta,
    description: 'Multiple git checkouts metadata details'
  ]))

  return paramArray
}

/**
* @param values HashMap should have keys ->  meta: [[meta: <HashMap containing Metadata>, name, description]]
*
* @return New Jenkins Hidden Parameter containing JSON details about provided Metadata
*/
def jsonObjectHiddenParameter(values = [:]) {
  def jsonMetadata = JsonOutput.toJson(values.meta)
  return hiddenParameter([
    name: values.name,
    defaultValue: """${jsonMetadata.toString()}""",
    description: values.description,
  ])
}

return this
