pipeline {
    agent any

    triggers {
        pollSCM('H/1 * * * *')
    }

    stages {

        stage('Build Maven') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Clean Old Docker Images') {
            steps {
                bat '''
                for /f "tokens=*" %%i in ('docker images -q taskmanagement-demo') do docker rmi -f %%i
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t taskmanagement-demo:%BUILD_NUMBER% .'
            }
        }
    }
}
