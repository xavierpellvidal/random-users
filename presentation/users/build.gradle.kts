plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "${AppVersions.APPLICATION_ID}.users"
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
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
            all {
                it.systemProperties["robolectric.pixelCopyRenderMode"] = "hardware"
            }
        }
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material3)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.layer.presentation)
    implementation(libs.coil.compose)
    implementation(libs.coil.okhttp)
    implementation(libs.androidx.ui.tooling)

    ksp(libs.com.google.dagger.hilt.compiler)

    implementation(project(":core:presentation"))
    implementation(project(":domain"))

    kspTest(libs.com.google.dagger.hilt.compiler)
    testImplementation(project(":data"))
    testImplementation(project(":core:preferences"))
    testImplementation(project(":core:database"))
    testImplementation(project(":core:api"))
    testImplementation(libs.arrow.core.retrofit)
    testImplementation(libs.retrofit)
    testImplementation(libs.retrofit.gson)
    testImplementation(libs.okhttp)
    testImplementation(libs.okhttp.logging.interceptor)
    testImplementation(libs.room.testing)
    testImplementation(libs.bundles.test.unit)
    testImplementation(libs.bundles.test.compose)
}
