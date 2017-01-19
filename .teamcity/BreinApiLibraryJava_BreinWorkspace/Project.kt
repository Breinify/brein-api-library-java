package BreinApiLibraryJava_BreinWorkspace

import BreinApiLibraryJava_BreinWorkspace.buildTypes.*
import BreinApiLibraryJava_BreinWorkspace.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.Project

object Project : Project({
    uuid = "b635ec44-3f19-4316-aff2-cf11bb0a872a"
    extId = "BreinApiLibraryJava_BreinWorkspace"
    parentId = "BreinApiLibraryJava"
    name = "Brein Workspace"

    vcsRoot(BreinApiLibraryJava_BreinWorkspace_HttpsGithubComBreinifyBreinWorkspaceRefsHeads)

    buildType(BreinApiLibraryJava_BreinWorkspace_Build)
})
