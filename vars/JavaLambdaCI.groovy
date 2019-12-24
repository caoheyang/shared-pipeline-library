def call(body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()
  
  pipeline {
    agent {
      label master
    }
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
}