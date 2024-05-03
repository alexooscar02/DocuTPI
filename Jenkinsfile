pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Paso para clonar el repositorio desde GitHub
                git 'https://github.com/alexooscar02/DocuTPI.git'
            }
        }
        
        stage('Integration Test') {
            steps {
                // Paso para ejecutar las pruebas de integración
                sh 'mvn verify -P integracion'
            }
        }
        
        stage('Deploy') {
            steps {
                // Paso opcional para desplegar la aplicación
                // Puedes añadir aquí los comandos necesarios para desplegar tu aplicación si es necesario
            }
        }
    }
}
