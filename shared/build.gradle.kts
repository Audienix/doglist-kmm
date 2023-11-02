plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.6.0"
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here

                // Serialization
                implementation(deps.serialization.core)
                implementation(deps.serialization.json)

                // Ktor
                implementation(deps.ktor.core)
                implementation(deps.ktor.clientJson)
                implementation(deps.ktor.serializationKotlinXJson)
                implementation(deps.ktor.negotiation)
                implementation(deps.ktor.serialization)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(deps.ktor.okHttp)

                implementation(deps.compose.runtime)
                implementation(platform(deps.compose.bom))
            }
        }
    }
}

android {
    namespace = "com.audienix.doglist"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}
