pipeline {
    agent any
    stages {
        stage("git"){
            steps{
                git credentialsId: 'b18a8ae1-7f6f-4804-be00-d40a45b0da3c', url: 'https://github.com/thanasismoutos/StatisticsClient1.git'
                echo "StatisticsClient.java"           
            }
        }
        stage ("build"){
            sh '/src/main/java/StatisticsClient.java'
        }
    }
    
}
