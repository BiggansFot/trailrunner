pipeline {
    agent any
 
   parameters {
        choice(
            name: 'BRANCH',
            choices: ['main', 'b1'],
            description: 'Select the branch to build'
        )
    }
 
    stages {
 
        stage('checkout') {
            steps {
                script {
                    // Use the selected branch from the parameter
                    def selectedBranch = params.BRANCH ?: 'main'
                    checkout([$class: 'GitSCM', 
                             branches: [[name: "*/${selectedBranch}"]],
                             userRemoteConfigs: [[url: 'https://github.com/BiggansFot/trailrunner.git']]])
                }
            }
       }   
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
                    robot outputPath: 'C:/Users/ersha/.jenkins/workspace/Pär_Ershag', passThreshold: 100.0, unstableThreshold: 70.0, onlyCritical: false
                }
            }
        }
    }
}


