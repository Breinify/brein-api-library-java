package BreinApiLibraryJava.buildTypes

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.AntBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.AntBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ant
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.vcs

object BreinApiLibraryJava_Build : BuildType({
    uuid = "9f6b84f6-a4e8-4f79-a48e-86098d4197c3"
    extId = "BreinApiLibraryJava_Build"
    name = "Build"

    vcs {
        root(BreinApiLibraryJava.vcsRoots.BreinApiLibraryJava_HttpsGithubComBreinifyBreinApiLibraryJavaRefsHeadsMaster)

    }

    steps {
        ant {
            mode = antFile {
            }
            targets = "03-wrap-up"
        }
    }

    triggers {
        vcs {
        }
    }
})
