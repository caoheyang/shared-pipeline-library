def call(body) 
  node {
    agent {
      docker {
          image 'maven:3-alpine' 
          args '-v /root/.m2:/root/.m2' 
      }
    }
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