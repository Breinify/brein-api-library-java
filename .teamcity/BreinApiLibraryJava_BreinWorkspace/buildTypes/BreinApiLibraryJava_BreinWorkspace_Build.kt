package BreinApiLibraryJava_BreinWorkspace.buildTypes

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.vcs

object BreinApiLibraryJava_BreinWorkspace_Build : BuildType({
    uuid = "deadc28c-e214-4401-a490-3b3c3287f4b5"
    extId = "BreinApiLibraryJava_BreinWorkspace_Build"
    name = "Build"

    vcs {
        root(BreinApiLibraryJava_BreinWorkspace.vcsRoots.BreinApiLibraryJava_BreinWorkspace_HttpsGithubComBreinifyBreinWorkspaceRefsHeads)

    }

    triggers {
        vcs {
        }
    }
})
