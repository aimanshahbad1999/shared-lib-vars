#!/usr/bin/env groovy

public class sayAiman{
    def student(){
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

    def other(){
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
    }
}


