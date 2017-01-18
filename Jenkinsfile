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
    /* dir ('brein-api-library/brein-api-library-java') { */
        app.inside ('-v /var/lib/jenkins/workspace/Brein-Api-Library-Java'){
                /* sh 'ant 03-wrap-up' */
                sh 'startCompile.sh'
            }
    /*    } */
    }

    /* stage ('Test') {} */

    /* stage ('Publish') {} */

}