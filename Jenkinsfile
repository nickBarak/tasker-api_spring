pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                echo '=== CLONE REPOSITORY ==='
                sh 'sudo rm -rf tasker-api'
                sh 'sudo git clone https://github.com/nickBarak/tasker-api_spring.git tasker-api'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo '=== BUILD DOCKER IMAGE ==='
                sh 'sudo docker rmi -f nickbarak/tasker-api-spring'
                sh 'sudo docker build -t nickbarak/tasker-api-spring ./tasker-api'
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
                sh 'sudo docker restart api'
            }
        }
    }
}