plugins {
    kotlin("jvm")
}

java {
    sourceCompatibility = AppVersions.javaVersion
    targetCompatibility = AppVersions.javaVersion
}
kotlin {
    jvmToolchain(AppVersions.JVM_TARGET.toInt())
}

dependencies {
    implementation(libs.bundles.layer.domain)

    testImplementation(libs.bundles.test.unit)
}
