def call(body){ 
  pipeline {
    agent { docker 'maven:3-alpine' } 
    stages {
        stage('Example Build') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
  }  
}