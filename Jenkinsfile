pipeline {
    agent any
    parameters {
        choice(description: 'Select branch.', name: 'branch', choices: 'main\nb1')
    }
    stages {
        
        stage('Build') {
            steps{
                bat "mvn compile"
            }
        }
        stage('Test') {
            steps{
                bat "mvn test"
            }
            post {
                always {
                    jacoco(
                        execPattern: 'target/*.exec',
                        classPattern: 'target/classes',
                        sourcePattern: 'src/main/java',
                        exclusionPattern: 'src/test*'
                    )
                    junit '**/TEST*.xml'
                }
            }
        }
        stage('Run Robot and Post Test') {
            steps{
                bat script: "robot --nostatusrc Selenium/test.robot", returnStatus: true
            }
            post {
                always {
                    robot outputPath: '.', passThreshold: 100.0, unstableThreshold: 70.0, onlyCritical: false
                }
            }
        }
    }
}
