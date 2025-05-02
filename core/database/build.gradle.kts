plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.com.google.dagger.hilt.android)
}

android {
    namespace = "${AppVersions.APPLICATION_ID}.core.database"
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
}

dependencies {
    implementation(libs.bundles.layer.data)
    implementation(libs.room.ktx)

    ksp(libs.com.google.dagger.hilt.compiler)
    ksp(libs.room.compiler)
}
