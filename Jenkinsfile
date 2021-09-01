def commonsLibrary = library('revision1-src')
def commonsPackage = commonsLibrary.com.jenkins
def Commons = commonsPackage.Commons.new().get()

def inputFile = 'input.txt'

def defaultChoice = "'--Select--'"

def listForChoices = [defaultChoice, "'Pune'", "'Mumbai'", "'Solapur'"]

def ParamTypes = Commons.InputParameterType

properties(
  [
    buildDiscarder(
			logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '20')
		),
    parameters(Commons.Parameters.generateStandardDataInputParams([
        [
        "paramType": Commons.InputParameterType.STRING,
        "name": "Email",
        "description": "Accepts Email",
        "defaultValue": "abc@gmail.com",
        "trim":"true",
        "required":"true"
      ],
      [
        "paramType": Commons.InputParameterType.INPUTFILE_WITH_STRING,
        "name": "file",
        "meta": [
          [
           
            'additionalMessage': "",
            'archiveFile': true,
            'stringPeerName':'File',
            'stringPeerDescription':"""
                Enter File Name to Print File Data
                """,
            'stringPeerNeedsTextParam':false
          ]
        ]
      ],
       [
        "paramType": Commons.InputParameterType.BOOLEAN,
        "name": "RemoveFile",
        "description": "select if you want to remove demo.txt file",
        "defaultValue": false
      ],  
      [
        paramType: ParamTypes.ACTIVECHOICEPARAMETER,
        name: 'city',
        choiceType: 'PT_SINGLE_SELECT',
        description: 'Please select a choice',
        script: "return ${listForChoices}"
      ],
       [
        "paramType": Commons.InputParameterType.INPUTFILE,
        "name": "file1",
        "meta": [
          ['fileName': 'demo1.txt', 
          "additionalMessage": """
            A text file containing a list of urls followed by the corresponding primary video docId, separated by newline, for documents you would like to process as part of this job.
            <br />
            &emsp; Example: https://www.thespruce.com/takeout-copycat-easy-sesame-chicken-4133629,4135314
            <br />
            Note: If you want to remove field then please provide list of urls only.
            <br />
            &emsp; Example: https://www.thespruce.com/takeout-copycat-easy-sesame-chicken-4133629

          """, 'archiveFile': true],
        ],
      ]
    
    ]))
  ]
)

node{
    stage("Email Verification"){
        println("${params.Email}")   
        println("${params.city}")
    }
    stage("Printing Demo File Data"){
        path="${params.File}"
        File myfile=new File("$workspace/${path}")
        println(myfile.text)
        print("${params.RemoveFile}")
    }
     stage("Printing inputfile Data"){
        path="${params.file1}"
        print(path)
        File myfile1=new File("$workspace/${path}")
        println(myfile1.text)
    }
}
