node('master') {

    def master = load '../brein-workspace/brein-intellij-workspace/common-libs/jenkins/jenkins.master.groovy'
    master.checkOutBreinRepo()

    master.buildAndTest('brein-api-library/brein-api-library-java')

    master.deploy('brein-api-library/brein-api-library-java')
}