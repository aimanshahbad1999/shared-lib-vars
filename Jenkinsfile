@Library('revision1-vars@master')
@Library('revision1-src@master')

import com.exm.sayAiman
def obj1=new sayAiman()

node{
    stage("Login"){
        print obj1.login("Aiman")
        
    }
    stage("Student"){
        sayAiman "Student"
        
    }
}



