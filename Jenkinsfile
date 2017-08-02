#!/usr/bin/env groovy
@Library('github.com/stakater/fabric8-pipeline-library')

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

dockerNode(dockerImage: 'openjdk:8') {
    container(name: 'openjdk8') {
        stage('checkout') {
            checkout scm
        }

        stage('check java') {
            sh "uname -a"
            sh "java -version"
        }

        stage('clean') {
            sh "chmod +x mvnw"
            sh "./mvnw clean"
        }

        stage('install tools') {
            sh "./mvnw com.github.eirslett:frontend-maven-plugin:install-node-and-yarn -DnodeVersion=v6.11.1 -DyarnVersion=v0.27.5"
        }

        stage('yarn install') {
            sh "./mvnw com.github.eirslett:frontend-maven-plugin:yarn"
        }

        stage('backend tests') {
            try {
                sh "./mvnw test"
            } catch(err) {
                throw err
            } finally {
                junit '**/target/surefire-reports/TEST-*.xml'
            }
        }

        stage('frontend tests') {
            try {
                sh "./mvnw com.github.eirslett:frontend-maven-plugin:yarn -Dfrontend.yarn.arguments=test"
            } catch(err) {
                throw err
            } finally {
                junit '**/target/test-results/karma/TESTS-*.xml'
            }
        }

        stage('packaging') {
            sh "./mvnw package -Pprod -DskipTests"
            archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
        }

        stage('quality analysis') {
            withSonarQubeEnv('Sonar') {
                sh "./mvnw sonar:sonar"
            }
        }
    }
}


