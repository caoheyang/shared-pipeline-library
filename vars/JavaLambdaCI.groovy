pipeline {
  agent { label master } 
  stages {
      stage('checkout code') {
          steps {
                checkout scm
          }
      }
      stage('compile') {
          steps {
              sh 'mvn clean package'
          }
      }
  }
}  