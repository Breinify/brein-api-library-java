node('master') {
    stage 'Checkout'
    /* Checkout the code we are currently running against */

    checkout scm
    def app = docker.image('compilecontainer')

    app.inside {
        dir('../brein-workspace') {
            git url: 'ssh://git@github.com/Breinify/brein-workspace.git'
        }

        // checkout
        dir ('brein-api-library/brein-api-library-java') {
            git url: 'https://github.com/Breinify/brein-api-library-java.git'
        }
    }

    stage 'Build'
    /* Build the Docker image with a Dockerfile, tagging it with the build number */
    app.inside {
          sh 'ant 03-wrap-up'
        }

    stage 'Test'
    /* We can run tests inside our new image */

    stage 'Publish'

}