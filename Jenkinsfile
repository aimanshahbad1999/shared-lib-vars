def commonsLibrary = library('revision1-src')
def commonsPackage = commonsLibrary.com.jenkins
def Commons = commonsPackage.Commons.new().get()
def inputFile = 'input.txt'
properties(
  [
    
    buildDiscarder(
			logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '20')
		),
    parameters(Commons.Parameters.generateStandardDataInputParams([
       [
        "paramType": Commons.InputParameterType.INPUTFILE,
        "name": "file",
        "meta": [
          ['fileName':'list.txt', "additionalMessage": """
            A text file containing a list of urls followed by the corresponding primary video docId, separated by newline, for documents you would like to process as part of this job.
            <br />
            &emsp; Example: https://www.thespruce.com/takeout-copycat-easy-sesame-chicken-4133629,4135314
            <br />
            Note: If you want to remove field then please provide list of urls only.
            <br />
            &emsp; Example: https://www.thespruce.com/takeout-copycat-easy-sesame-chicken-4133629

          """, 'archiveFile': true],
        ],
      ], 
        
      [
        "paramType": Commons.InputParameterType.INPUTFILE_WITH_STRING,
        "name": "file",
        "meta": [
          [
            'fileName': inputFile,
            'additionalMessage': """
                TXT input file. For example: <br /> <br /> <pre> 4158565</pre> <br />
                Do not include a separate row for the column names! Mention each docId in separate line.
            """,
            'archiveFile': true,
            'stringPeerName':'docId',
            'stringPeerDescription':"""
                Enter single docid only. e.g. 767857
                <br /><b>Note:</b>Either provide docId here or use input file in <b>Input request</b> after you start the build
                """,
            'stringPeerNeedsTextParam':false
          ]
        ],
      ],
      [
        "paramType": Commons.InputParameterType.STRING,
        "name": "toolId",
        "description": "Accepts single toolId value",
        "defaultValue": "",
        "trim":"true",
        "required":"true"
      ],
       
    ]))
  ]
)

node{
    stage("Print File Data"){
        println("Hey hiii")
        sh "pwd"
        println "${params.docId}"
        
        File myfile=new File("$workspace/demo.txt")
      
       println(myfile.text)
        
        
    }
}
