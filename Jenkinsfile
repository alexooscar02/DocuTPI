pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                echo "Etapa de construcción"
            }
        }
        stage('Integration Test') {
            steps {
                bat 'mvn verify -P integracion'
            }
        }
        stage('Deploy') {
            steps {
                echo "Etapa de despliegue"
            }
        }
    }
}