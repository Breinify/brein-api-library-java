node() {

    stage 'Checkout'

    // checkout workspace
    dir('../brein-workspace') {
        git url: 'https://github.com/Breinify/brein-workspace.git'
    }

    // checkout
    dir ('brein-api-library-java') {
        git url: 'https://github.com/Breinify/brein-api-library-java.git'
    }

    stage 'Build'

    // compile
    sh('ant build')


    stage 'Test'

    stage 'Upload'

}