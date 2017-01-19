package BreinApiLibraryJava

import BreinApiLibraryJava.buildTypes.*
import BreinApiLibraryJava.vcsRoots.*
import BreinApiLibraryJava.vcsRoots.BreinApiLibraryJava_HttpsGithubComBreinifyBreinApiLibraryJavaRefsHeadsMaster
import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.Project
import jetbrains.buildServer.configs.kotlin.v10.projectFeatures.VersionedSettings
import jetbrains.buildServer.configs.kotlin.v10.projectFeatures.VersionedSettings.*
import jetbrains.buildServer.configs.kotlin.v10.projectFeatures.versionedSettings

object Project : Project({
    uuid = "8a5cb6e9-d9e6-4bae-a6cc-9e465dcc0f67"
    extId = "BreinApiLibraryJava"
    parentId = "_Root"
    name = "Brein Api Library Java"

    vcsRoot(BreinApiLibraryJava_HttpsGithubComBreinifyBreinApiLibraryJavaRefsHeadsMaster)

    buildType(BreinApiLibraryJava_Build)

    features {
        versionedSettings {
            id = "PROJECT_EXT_4"
            mode = VersionedSettings.Mode.ENABLED
            buildSettingsMode = VersionedSettings.BuildSettingsMode.USE_CURRENT_SETTINGS
            rootExtId = BreinApiLibraryJava_HttpsGithubComBreinifyBreinApiLibraryJavaRefsHeadsMaster.extId
            showChanges = false
            settingsFormat = VersionedSettings.Format.KOTLIN
        }
    }
})
