pipeline {
    agent any
    environment {
        APP_NAME = "geladeira-magica"
        IMAGE_NAME = "geladeira-magica:latest"
        CONTAINER_NAME = "geladeira-magica"
        PORT = "38080"
        DOCKER_BUILDKIT = "1"
    }
    options {
        timestamps()
    }
    stages {
        stage('Clone Repository') {
            steps {
                git 'https://github.com/kokatk/repo-geladeira-magica.git'
            }
        }
        stage('Env Check') {
            steps {
                sh '''
                  set -e
                  command -v docker
                  docker version
                  test -S /var/run/docker.sock
                '''
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
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Verify JAR') {
            steps {
                sh 'ls -l target/*.jar'
            }
        }
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE_NAME .'
            }
        }
        stage('Deploy Container') {
            steps {
                sh '''
                  set -e
                  docker rm -f $CONTAINER_NAME || true
                  if command -v ss >/dev/null 2>&1; then ss -ltn | awk '{print $4}' | grep -q ":$PORT$" && echo "Porta ocupada: $PORT" || true; fi
                  OPENAI_FLAG=""
                  if [ -n "$OPENAI_API_KEY" ]; then OPENAI_FLAG="-e OPENAI_API_KEY=$OPENAI_API_KEY"; fi
                  docker run -d --name $CONTAINER_NAME -p $PORT:8080 $OPENAI_FLAG $IMAGE_NAME
                '''
            }
        }
    }
}
