node() {

    stage 'Checkout'

    // checkout workspace
    dir('../brein-workspace') {

    // git@github.com:MarcoRecchioni/reponame.git

       //  git url: 'https://github.com/Breinify/brein-workspace.git'
        git url: 'git@github.com:MarcoRecchioni/Breinify/brein-workspace.git'
    }

    // checkout
    dir ('brein-api-library-java') {
        git url: 'https://github.com/Breinify/brein-api-library-java.git'
    }

    stage 'Build'

    // compile
    sh('ant 03-wrap-up')

    stage 'Test'

    stage 'Upload'

}