pipeline {
    agent any

    stages {
        stage ('Compile Stage') {

            steps {
                withMaven(maven : 'maven_3_9_2') {
                    bat 'mvn clean compile'
                }
            }
        }

        stage ('Testing Stage') {

            steps {
                withMaven(maven : 'maven_3_9_2') {
                    bat 'mvn test'
                }
            }
        }

        stage ('Deployment Stage') {

            steps {
                withMaven(maven : 'maven_3_9_2') {
                    bat 'mvn deploy'
                }
            }
        }
    }
}