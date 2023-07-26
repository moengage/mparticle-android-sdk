plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.moengage.mparticle.sampleapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.moengage.mparticle.sampleapp"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.lifecycle:lifecycle-process:2.5.1")

    // mParticle
    implementation("com.mparticle:android-kit-base:5.51.3")
    implementation("com.mparticle:android-core:5.51.3")
    implementation("com.android.installreferrer:installreferrer:2.2")

    // MoEngage
    implementation(projects.moengageKit)
    implementation(moengage.core) {
        exclude("com.moengage", "core")
    }
    // Todo: Remove this Snapshot build dependency
    implementation("com.moengage:core:6.8.1-SNAPSHOT")
    implementation(moengageInternal.kotlinStdLib)

    // Common
    implementation("com.google.android.gms:play-services-ads-identifier:18.0.1")
    implementation("com.google.firebase:firebase-messaging:23.1.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}