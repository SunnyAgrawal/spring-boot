pipeline {
    agent any
    tools {
        maven '3.6.3'
    }
    environment {
        SONARQUBE = credentials('sonar')
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package sonar:sonar -s settings.xml -Dmaven.repo.local=$WORKSPACE/.m2 -Dsonar.host.url=http://192.168.0.22:9000 -Dsonar.login=$SONARQUBE_USR -Dsonar.password=$SONARQUBE_PSW'
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