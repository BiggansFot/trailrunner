pipeline {
    agent any
    stages { 
        stage('build'){
           steps{
               sh "mvn compile"
           }
        }
        stage('Test') {
            steps{
                sh "mvn test"
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
                sh script: 'robot C:/Users/ersha/.jenkins/workspace/Pär_Ershag/Selenium/test.robot', returnStatus: true
            }
            post {
                always {
                    robot outputPath: 'C:/Users/ersha/.jenkins/workspace/Pär_Ershag@2', passThreshold: 100.0, unstableThreshold: 70.0, onlyCritical: false
                }
            }
        }
    }
}


