pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        // Todo: Remove the snapshot build config before merge to master
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
    }
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // Todo: Remove the snapshot build config before merge to master
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
    }

    versionCatalogs {
        create("moengageInternal") {
            from("com.moengage:android-dependency-catalog-internal:1.3.0")
        }
        create("moengage") {
            from("com.moengage:android-dependency-catalog:3.1.0")
        }
    }
}

include(
    ":moengage-kit",
    ":example"
)
includeBuild("publishing-plugin")
rootProject.name = "mparticle-android-integration-moengage"