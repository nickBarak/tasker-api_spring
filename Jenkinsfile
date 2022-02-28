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
pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                echo '=== CLONE REPOSITORY ==='
                sh 'rm -rf tasker-api'
                git clone 'https://github.com/nickBarak/tasker-api_spring.git' tasker-api
            }
        }

        stage('Build Docker Image') {
            steps {
                echo '=== BUILD DOCKER IMAGE ==='
                sh 'docker rmi -f nickbarak/tasker-api-spring'
                sh 'docker build -t nickbarak/tasker-api-spring .'
            }
        }

        stage('Push Docker Image to Remote Repository') {
            steps {
                echo '=== PUSH DOCKER IMAGE TO REMOTE REPOSITORY ==='
                sh 'docker push nickbarak/tasker-api-spring'
            }
        }
        
        stage('Restart Container with Latest Image') {
            steps {
                echo '=== RESTART CONTAINER WITH LATEST IMAGE ==='
                sh 'docker restart api'
            }
        }
    }
}