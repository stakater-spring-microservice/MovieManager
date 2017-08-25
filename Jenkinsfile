#!/usr/bin/env groovy
@Library('github.com/stakater/fabric8-pipeline-library@master')

def localItestPattern = ""
try {
    localItestPattern = ITEST_PATTERN
} catch (Throwable e) {
    localItestPattern = "*KT"
}

def localFailIfNoTests = ""
try {
    localFailIfNoTests = ITEST_FAIL_IF_NO_TEST
} catch (Throwable e) {
    localFailIfNoTests = "false"
}

def versionPrefix = ""
try {
    versionPrefix = VERSION_PREFIX
} catch (Throwable e) {
    versionPrefix = "1.0"
}

def canaryVersion = "${versionPrefix}.${env.BUILD_NUMBER}"
def fabric8Console = "${env.FABRIC8_CONSOLE ?: ''}"
def utils = new io.fabric8.Utils()
def label = "buildpod.${env.JOB_NAME}.${env.BUILD_NUMBER}".replace('-', '_').replace('/', '_')
def gitRepo = "git@github.com:stakater-spring-microservice/MovieManager.git"

def envStage = utils.environmentNamespace('stage')
def envProd = utils.environmentNamespace('run')
def stashName = ""
def deploy = false

// TODO # 1 : use different profile dev or prod
// TODO # 2 : use config maps
// TODO # 3 : the code is being checked out multiple times; that should be fixed! must be checked out only once
// TODO # 4 : run performance tests ( gatling ) and save stats
// TODO # 5 : the artifacts should be archived & should be browse'able from Jenkins!

mavenNode(mavenImage: 'openjdk:8') {
    container(name: 'maven') {

        stage("checkout") {
            checkout scm
        }

        stage("clean") {
            sh 'chmod +x mvnw'
            sh './mvnw clean'
        }

        stage('install tools') {
            sh './mvnw com.github.eirslett:frontend-maven-plugin:install-node-and-yarn -DnodeVersion=v6.11.1 -DyarnVersion=v0.27.5'
        }

        stage('yarn install') {
            sh './mvnw com.github.eirslett:frontend-maven-plugin:yarn'
        }

        stage('Canary Release'){
            mavenCanaryRelease {
              version = canaryVersion
            }
        }

        stage('Integration Testing') {
            mavenIntegrationTest {
              environment = 'Test'
              failIfNoTests = localFailIfNoTests
              itestPattern = localItestPattern
            }
        }

        stage('SonarQube analysis') {
          withSonarQubeEnv('sonarqube') {
            sh './mvnw compile'
            // requires SonarQube Scanner for Maven 3.2+
            sh "./mvnw -Dsonar.host.url=${env.SONAR_HOST_URL} org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar"
          }
        }

        stage('Release') {
          timeout(time: 1, unit: 'HOURS') { // Just in case something goes wrong, pipeline will be killed after a timeout
            def qg = waitForQualityGate() // Reuse taskId previously collected by withSonarQubeEnv
            if (qg.status != 'OK') {
              error "Pipeline aborted due to quality gate failure: ${qg.status}"
            }
            else {
              //push release git branch and tag and nexus repo
              gitRelease {
                project = gitRepo
              }
            }
          }
        }

        stage('Rollout to Stage') {
            kubernetesApply(environment: envStage)
            // TODO: figure out why to stash deployments?
            // TODO: from where does the stash command comes?
            //stash deployments
            stashName = label
            stash includes: '**/*.yml', name: stashName
        }
    }
}
