pipeline {
    agent any
    stages {
        stage('Clone Repository') {
            steps {
                git 'https://github.com/SEU_USUARIO/geladeira-magica.git'
            }
        }
        stage('Build Maven') {
            agent {
                docker {
                    image 'maven:3.9.9-eclipse-temurin-21'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t geladeira-magica:latest .'
            }
        }
        stage('Deploy Container') {
            steps {
                sh 'docker rm -f geladeira-magica || true'
                sh '''
                docker run -d \
                --name geladeira-magica \
                -p 38080:8080 \
                -e OPENAI_API_KEY=change-me \
                geladeira-magica:latest
                '''
            }
        }
    }
}
