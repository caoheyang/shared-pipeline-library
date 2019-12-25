def call(body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()
  
  def project = config.project
  
  def jenkins_node = 'master'
 
  pipeline {
    node {
      stage('chekcout source code') {
        checkout scm
      }
      stage('compile') {
        sh('mvn clean package')
      }
      stage('udpate lambda function') {
        def file_path=pwd()
        def update_lambda_code = libraryResource 'update_lambda.py'
        writeFile file: 'update_lambda.py', text: update_lambda_code
        sh('python3 update_lambda.py -lambda_function tiny-url-lambda -file_path '+file_path+'/target/'+project+'-lambda-package.zip -profile global -region us-east-1')
      }
    }
  }
}
