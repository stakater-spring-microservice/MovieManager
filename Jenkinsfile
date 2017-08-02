#!/usr/bin/env groovy
@Library('github.com/fabric8io/fabric8-pipeline-library@master')

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

    }
}
