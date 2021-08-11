#!/usr/bin/env groovy

def call(String name = 'Student') {
    echo "Hello, ${name}."
	if(name=='Student'){
		pipeline{
			agent any
			stages{
				stage("learn"){	
					steps{
						echo "Learn any technology"
					}
				}
				stage("Practice"){	
					steps{
						echo "Practice to explore more"
					}
				}
				stage("Apply"){	
					steps{
						echo "Build Some Project "
					}
				}
			}
		}
	}
	else{
		pipeline{
			agent any
			stages{
				stage("1"){	
					steps{
						echo "Do whatever you want "
					}
				}
				stage("2"){	
					steps{
						echo "Do whatever you want"
					}
				}
				stage("3"){	
					steps{
						echo "Do whatever you want "
					}
				}
			}
		}
		
	}	
}
