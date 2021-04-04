pipeline {
    agent any
    tools {
        maven '3.6.3'
    }
    environment {
        SONARQUBE = credentials('sonar')
        NEXUS_CRED = credentials('nexuscreds')
        SONAR_URL = "http://192.168.0.22:9000"
        DOCKER_REGISTRY = "192.168.0.22:8082"
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package sonar:sonar -s settings.xml -Dmaven.repo.local=$WORKSPACE/.m2 -Dsonar.host.url=$env.SONAR_URL -Dsonar.login=$SONARQUBE_USR -Dsonar.password=$SONARQUBE_PSW'
            }
        }
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $env.DOCKER_REGISTRY/spring-boot:$BUILD_NUMBER'
            }
        }
        stage('Publish Docker Image') {
            steps {
                sh 'docker login -u $NEXUS_CRED_USR -p $NEXUS_CRED_PSW $env.DOCKER_REGISTRY'
                sh 'docker push $env.DOCKER_REGISTRY/spring-boot:$BUILD_NUMBER'
            }
        }
        stage('Deploy Docker image') {
            steps {
                sh 'docker run -d -p 8083:8083 $env.DOCKER_REGISTRY/spring-boot:$BUILD_NUMBER'
            }
        }
        stage('Run Automation suite') {
            steps {
                echo "4"
            }
        }
    }
}