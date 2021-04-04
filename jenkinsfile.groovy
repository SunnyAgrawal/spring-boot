pipeline {
    agent any
    tools {
        maven '3.6.3'
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package sonar:sonar -s settings.xml -Dmaven.repo.local=$WORKSPACE/.m2'
            }
        }
        stage('Build Docker Image') {
            steps {
                echo "1"
            }
        }
        stage('Publish Docker Image') {
            steps {
                echo "2"
            }
        }
        stage('Deploy Docker image') {
            steps {
                echo "3"
            }
        }
        stage('Run Automation suite') {
            steps {
                echo "4"
            }
        }
    }
}