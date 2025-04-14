plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.com.google.devtools.ksp)
}

android {
    namespace = AppVersions.APPLICATION_ID
    compileSdk = AppVersions.COMPILE_SDK

    defaultConfig {
        applicationId = AppVersions.APPLICATION_ID
        minSdk = AppVersions.MIN_SDK
        targetSdk = AppVersions.COMPILE_SDK
        versionCode = AppVersions.APP_VERSION_CODE
        versionName = AppVersions.APP_VERSION_NAME
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = AppVersions.javaVersion
        targetCompatibility = AppVersions.javaVersion
    }
    kotlinOptions {
        jvmTarget = AppVersions.JVM_TARGET
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)
    implementation(libs.bundles.layer.presentation)

    ksp(libs.com.google.dagger.hilt.compiler)

    implementation(project(":core:api"))
    implementation(project(":core:presentation"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":presentation:users"))
}
