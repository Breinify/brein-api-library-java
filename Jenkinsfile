node('master') {


    def app = docker.image('compilecontainer')

    stage ('Checkout') {
        /* checkout scm */

        app.inside {
            dir('brein-workspace') {
                git url: 'ssh://git@github.com/Breinify/brein-workspace.git'
            }

           dir ('brein-api-library/brein-api-library-java') {
                git url: 'https://github.com/Breinify/brein-api-library-java.git'
            }
        }
    }

    stage ('Build') {
        app.inside {
            dir ('/var/lib/jenkins/workspace/Brein-Api-Library-Java/brein-api-library/brein-api-library-java') {
                sh 'ant 03-wrap-up'
            }
        }
    }

    /* stage ('Test') {} */

    /* stage ('Publish') {} */

}