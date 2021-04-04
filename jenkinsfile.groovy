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
        stage('Build and Publish') {
            steps {
                script{
                    docker.withRegistry('192.168.0.22:8082', 'nexuscreds') {
                        def customImage = docker.build("spring-boot:${env.BUILD_NUMBER}")
                        /* Push the container to the custom Registry */
                        customImage.push()
                        // sh 'docker build -t $DOCKER_REGISTRY/spring-boot:$BUILD_NUMBER'
                    }
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