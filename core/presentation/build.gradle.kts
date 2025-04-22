plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.com.google.dagger.hilt.android)
}

android {
    namespace = "${AppVersions.APPLICATION_ID}.core.ui"
    compileSdk = AppVersions.COMPILE_SDK

    defaultConfig {
        minSdk = AppVersions.MIN_SDK
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
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material3)
    implementation(libs.bundles.layer.presentation)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)

    ksp(libs.com.google.dagger.hilt.compiler)
}
