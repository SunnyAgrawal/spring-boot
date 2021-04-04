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
                sh 'mvn clean package sonar:sonar -s settings.xml -Dmaven.repo.local=$WORKSPACE/.m2 -Dsonar.host.url=$SONAR_URL -Dsonar.login=$SONARQUBE_USR -Dsonar.password=$SONARQUBE_PSW'
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    sh '/usr/local/bin/docker build -t $DOCKER_REGISTRY/spring-boot:$BUILD_NUMBER'
                }
            }
        }
        stage('Publish Docker Image') {
            steps {
                script {
                    sh '/usr/local/bin/docker login -u $NEXUS_CRED_USR -p $NEXUS_CRED_PSW $DOCKER_REGISTRY'
                    sh '/usr/local/bin/docker push $DOCKER_REGISTRY/spring-boot:$BUILD_NUMBER'
                }
            }
        }
        stage('Deploy Docker image') {
            steps {
                script {
                    sh '/usr/local/bin/docker run -d -p 8083:8083 $DOCKER_REGISTRY/spring-boot:$BUILD_NUMBER'
                }
            }
        }
        stage('Run Automation suite') {
            steps {
                echo "4"
            }
        }
    }
}