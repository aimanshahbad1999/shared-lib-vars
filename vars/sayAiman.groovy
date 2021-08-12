#!/usr/bin/env groovy

def call(String name = 'Student') {
    echo "Hello, ${name}."
	if(name=="Aiman"){
		node{
			stage("First"){
				print("first")
				echo "Aiman"
				sh "mkdir aiman"
				
			}
			stage("Second Stage"){
				print("Second")
				
			}
			stage("Third Stage"){
				print("Third")
			}
	}else{
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
	
}
