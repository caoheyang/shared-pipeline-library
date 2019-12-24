def call(body){ 
  pileline{
    agent any
    tools {
        maven 'Maven_3.5.2' 
    }
    stages{
      stage('chekcout source code'){
        checkout scm
      }
      stage('compile'){
        sh('mvn clean package')
      }
      stage('udpate lambda function'){
        def update_lambda_code=libraryResource 'update_lambda.py'
        writeFile file: 'update_lambda.py', text: update_lambda_code
        sh('python update_lambda.py -lambda_function tiny-url-lambda -file_path target/tiny-url-1.0-SNAPSHOT-lambda-package.zip -profile global -region us-east-1')
      }
    }
  }
}