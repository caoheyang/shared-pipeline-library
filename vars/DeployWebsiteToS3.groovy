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
      stage('deploy website to S3') {
        sh('aws s3 sync . s3://'+project+'/  --profile global --region us-east-1 --delete --exclude ".git/*"')
      }
    }
  }
}
