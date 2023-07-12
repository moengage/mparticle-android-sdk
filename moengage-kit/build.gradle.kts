@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(moengageInternal.plugins.plugin.android.lib)
    alias(moengageInternal.plugins.plugin.kotlin.android)
}

apply(from = "../scripts/gradle/release.gradle")

android {
    namespace = "com.moengage.mparticle.kits"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33
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
    api("com.mparticle:android-kit-base:5.51.3")
    compileOnly(moengage.core)
}