def call(body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()
  
  def project = config.project.toLowerCase()

  def jenkins_node = 'master'

  def project = config.project
  pipeline {
    node {
      stage('chekcout source code') {
        checkout scm
      }
      stage('compile') {
        sh('mvn clean package')
      }
      stage('udpate lambda function') {
        print(project)
        def file_path=pwd()
        def update_lambda_code = libraryResource 'update_lambda.py'
        writeFile file: 'update_lambda.py', text: update_lambda_code
        sh('python3 update_lambda.py -lambda_function tiny-url-lambda -file_path '+file_path+'/target/tiny-url-1.0-SNAPSHOT-lambda-package.zip -profile global -region us-east-1')
      }
    }
  }
}
