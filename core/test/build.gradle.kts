plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.com.google.dagger.hilt.android)
}

android {
    namespace = "${AppVersions.APPLICATION_ID}.core.test"
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
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(libs.bundles.layer.data)
    ksp(libs.com.google.dagger.hilt.compiler)
    implementation(libs.bundles.test.compose)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.room.ktx)
    implementation(libs.room.testing)

    implementation(project(":core:api"))
    implementation(project(":core:database"))
    implementation(project(":core:preferences"))

    testImplementation(libs.bundles.test.unit)
}
