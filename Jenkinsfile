pipeline {
    agent any

    stages {
        stage('Free Memory') {
            steps {
                echo '=== FREE MEMORY ==='
                sh 'sudo docker-compose down'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo '=== BUILD DOCKER IMAGE ==='
                sh 'sudo docker build -t nickbarak/tasker-api-spring .'
            }
        }

        stage('Push Docker Image to Remote Repository') {
            steps {
                echo '=== PUSH DOCKER IMAGE TO REMOTE REPOSITORY ==='
                sh 'sudo docker push nickbarak/tasker-api-spring'
            }
        }
        
        stage('Restart Container with Latest Image') {
            steps {
                echo '=== RESTART CONTAINER WITH LATEST IMAGE ==='
                cd '../../../../../home/opc'
                sh 'sudo docker-compose up -d'
            }
        }
    }
}
