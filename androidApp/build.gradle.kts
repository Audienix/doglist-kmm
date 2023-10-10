plugins {
    androidApplication
    kotlinAndroid
    kotlinKapt
    kotlinParcelize
}

android {
    namespace = "com.forbes.doglist.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.forbes.doglist.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = deps.compose.androidxComposeCompilerVersion
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":shared"))

    // Core
    implementation(deps.androidx.appCompat)
    implementation(deps.androidx.coreKtx)
    implementation(deps.androidx.activityCompose)
    implementation(deps.androidx.composeNavigation)

    // Coroutines
    implementation(deps.coroutines.core)
    implementation(deps.coroutines.android)

    // Image Loading
    implementation(deps.coilCompose)

    // DI
    implementation(deps.koin.core)
    implementation(deps.koin.android)

    // Compose
    implementation(platform(deps.compose.bom))
    implementation(deps.compose.foundation)
    implementation(deps.compose.foundationLayout)
    implementation(deps.compose.materialIconsExtended)
    implementation(deps.compose.material3)
    debugImplementation(deps.compose.uiTooling)
    implementation(deps.compose.uiToolingPreview)
    implementation(deps.compose.runtime)

    //Google Accompanist
    implementation(deps.accompanist)

    // Screen Navigation
    implementation(deps.voyagerNavigator)
}