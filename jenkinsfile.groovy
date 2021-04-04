pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'clean package sonar:sonar -s settings.xml -Dmaven.repo.local=$WORKSPACE/.m2'
            }
        }
        stage('Build Docker Image') {
            steps {
            }
        }
        stage('Publish Docker Image') {
            steps {
            }
        }
        stage('Deploy Docker image') {
            steps {
            }
        }
        stage('Run Automation suite') {
            steps {
            }
        }
    }
}