node('master') {

    stage ('Checkout') {
        dir('brein-workspace') {
            git url: 'ssh://git@github.com/Breinify/brein-workspace.git'
        }

        dir ('brein-api-library/brein-api-library-java') {
            git url: 'https://github.com/Breinify/brein-api-library-java.git'
        }
    }

    stage ('Resolve Dependencies') {
        try {
            dir ('brein-api-library/brein-api-library-java') {
                sh '01-resolve-dependencies'
            }
        } catch (err) {
            emailextrecipients([[$class: 'CulpritsRecipientProvider']])
        }
    }

    stage ('Build') {
        try {
            dir ('brein-api-library/brein-api-library-java') {
                sh 'ant 03-wrap-up'
            }
        } catch (err) {
            emailextrecipients([[$class: 'CulpritsRecipientProvider']])
        }
    }

    stage ('TestSuite') {
        try {
            dir ('brein-api-library/brein-api-library-java') {
                // activate when available
                // sh 'ant 06-run-test-suite'
            }
        } catch (err) {
            emailextrecipients([[$class: 'CulpritsRecipientProvider']])
        }
    }

    /* stage ('Test') {} */
    /* stage ('Publish') {} */
}
