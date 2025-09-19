pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "splitty-server:latest"
        DOCKER_CONTAINER = "splitty-server"
    }

    stages {
        stage('Checkout') {
            steps {
                echo "Checking out source code."
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo "Creating application-prod.yml"
                 withCredentials([file(credentialsId: 'prod-config', variable: 'PROD_CONFIG')]) {
                     sh "cp $PROD_CONFIG src/main/resources/application-prod.yml"
                 }
                
                echo "Building the project with Gradle."
                sh "./gradlew clean build -x test"
            }
        }

        // stage('Test') {
        //     steps {
        //         echo "Running tests."
        //         sh "./gradlew test"
        //     }
        // }

        stage('Docker Build') {
            steps {
                echo "Building Docker image: $DOCKER_IMAGE"
                sh "docker build -t $DOCKER_IMAGE ."
            }
        }

        stage('Docker Push') {
            steps {
                echo "Pushing Docker image: $DOCKER_IMAGE"
                withCredentials([usernamePassword(credentialsId: 'docker-hub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh "echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin"
                    sh "docker tag $DOCKER_IMAGE $DOCKER_USER/$DOCKER_IMAGE"
                    sh "docker push $DOCKER_USER/$DOCKER_IMAGE"

                    sh """
                        docker rmi $DOCKER_IMAGE || true
                        docker rmi $DOCKER_USER/$DOCKER_IMAGE || true
                        docker image prune -f || true
                    """
                }
            }
        }

        stage('Deploy to EC2') {
            steps {
                echo "Deploying Docker image to EC2"
                withCredentials([usernamePassword(credentialsId: 'docker-hub', 
                                                 usernameVariable: 'DOCKER_USER', 
                                                 passwordVariable: 'DOCKER_PASS'),
                                 string(credentialsId: 'ec2-ip', variable: 'EC2_IP')])  {
                    sshagent(['ec2-ssh']) {
                        sh """
                            ssh -o StrictHostKeyChecking=no ubuntu@$EC2_IP '
                                cd ~/ &&
                                docker stop $DOCKER_CONTAINER || true &&
                                docker rm $DOCKER_CONTAINER -f || true &&
                                docker rmi $DOCKER_USER/$DOCKER_IMAGE || true &&
                                docker pull $DOCKER_USER/$DOCKER_IMAGE &&
                                docker-compose up -d &&
                                docker image prune -f
                            '
                        """
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Cleaning up workspace.'
            cleanWs()
        }
        success {
            echo 'Pipeline succeeded!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
