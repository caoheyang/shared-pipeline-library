def call(body) {
  def config = [:]
  resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

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
        def build = Thread.currentThread().toString()
        def regexp= ".+?/job/([^/]+)/.*"
        def match = build  =~ regexp
        def jobName = match[0][1]
        print(jobName)
        def file_path=pwd()
        def update_lambda_code = libraryResource 'update_lambda.py'
        writeFile file: 'update_lambda.py', text: update_lambda_code
        sh('python3 update_lambda.py -lambda_function tiny-url-lambda -file_path '+file_path+'/target/tiny-url-1.0-SNAPSHOT-lambda-package.zip -profile global -region us-east-1')
      }
    }
  }
}
