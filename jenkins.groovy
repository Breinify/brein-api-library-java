node('master') {

    // we need the new versions, test
    stage ('Checkout') {
        dir('brein-workspace') {
            git credentialsId: 'BREIN-STAGE-ACCESS', url: 'ssh://git@github.com/Breinify/brein-workspace.git'
        }

        dir ('brein-api-library/brein-api-library-java') {
            git url: 'https://github.com/Breinify/brein-api-library-java.git'
        }
    }


    // it is enough to run the test, it will resolve, build and test
    stage ('Test & Build') {
        try {
            dir ('brein-api-library/brein-api-library-java') {
                sh 'ant 06-run-test-suite'
            }
        } catch (err) {

            emailext (
                    to: 'marco@breinify.com',
                    subject: "${env.JOB_NAME} #${env.BUILD_NUMBER} [${currentBuild.result}]",
                    body: "Build URL: ${env.BUILD_URL} failed.\n\n",
                    attachLog: true,
            )

            // emailext body: 'Failure in step: Test & Build', recipientProviders: [[$class: 'CulpritsRecipientProvider']], subject: 'Jenkins Error'
            throw err
        }
    }

    // now we should just publish the new version, it made it through the test
    stage ('Deploy') {
        try {
            dir ('brein-api-library/brein-api-library-java') {
                sh 'ant 04-publish-results'
            }
        } catch (err) {
            emailext body: 'Failure in step: Deploy', recipientProviders: [[$class: 'CulpritsRecipientProvider']], subject: 'Jenkins Error'
            emailextrecipients([[$class: 'CulpritsRecipientProvider']])
            throw err
        }
    }
}