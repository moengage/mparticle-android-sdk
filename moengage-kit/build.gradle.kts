@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(moengageInternal.plugins.plugin.android.lib)
    alias(moengageInternal.plugins.plugin.kotlin.android)
    id("mvn-publish")
}

val libVersionName = project.findProperty("VERSION_NAME") as String
android {
    namespace = "com.moengage.mparticle.kits"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

        buildConfigField("String", "MOENGAGE_KIT_VERSION", "\"$libVersionName\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    compileOnly(libs.mParticleAndroidKitBase)
    compileOnly(moengage.core) {
        exclude("com.moengage", "core")
    }
    // Todo: Remove this Snapshot build dependency
    compileOnly("com.moengage:core:6.8.1-SNAPSHOT")

    testImplementation(moengage.core) {
        exclude("com.moengage", "core")
    }
    // Todo: Remove this Snapshot build dependency
    testImplementation("com.moengage:core:6.8.1-SNAPSHOT")
    testImplementation(libs.mParticleAndroidKitBase)
    testImplementation(moengageInternal.bundles.junitBundle)
    testImplementation(libs.mockito)
}