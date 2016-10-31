node {
    // uncomment these 2 lines and edit the name 'node-4.6.0' according to what you choose in configuration
    // def nodeHome = tool name: 'node-4.6.0', type: 'jenkins.plugins.nodejs.tools.NodeJSInstallation'
    // env.PATH = "${nodeHome}/bin:${env.PATH}"

//    stage('check tools') {
//        sh "node -v"
//        sh "npm -v"
//        sh "bower -v"
//        sh "gulp -v"
//    }

//    stage('checkout') {
//        checkout scm
//    }

//    stage('npm install') {
//        sh "npm install"
//    }

//    stage('clean') {
//        sh "./mvnw clean"
//    }

//    stage('backend tests') {
//        sh "./mvnw test"
//    }

//    stage('frontend tests') {
//        sh "gulp test"
//    }

//    stage('packaging') {
//        sh "./mvnw package -Pprod -DskipTests"
//    }

    stage('Creation Docker image') {
        sh "docker build -t ersagun/bibliotheque:development ."
    }

    stage('Run Kubernetes') {
        sh "kubectl run bibliotheque --image=ersagun/bibliotheque:development --port=8080"
    }  
}
