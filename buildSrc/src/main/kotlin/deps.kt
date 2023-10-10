import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.project
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

object deps {
    object androidx {
        const val coreKtx = "androidx.core:core-ktx:1.9.0"
        const val appCompat = "androidx.appcompat:appcompat:1.6.1"
        const val activityCompose = "androidx.activity:activity-compose:1.7.2"
        const val composeNavigation = "androidx.navigation:navigation-compose:2.6.0-alpha03"
    }

    object koin {
        private const val version = "3.5.0"
        const val core = "io.insert-koin:koin-core:$version"
        const val android = "io.insert-koin:koin-android:$version"
    }

    object coroutines {
        private const val version = "1.7.3"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object serialization {
        private const val version = "1.6.0"
        const val json = "org.jetbrains.kotlinx:kotlinx-serialization-json:$version"
        const val core = "org.jetbrains.kotlinx:kotlinx-serialization-core:$version"
    }

    object arrow {
        private const val version = "1.2.1"
        const val core = "io.arrow-kt:arrow-core:$version"
        const val fx = "io.arrow-kt:arrow-fx-coroutines:$version"
    }

    object compose {
        const val androidxComposeCompilerVersion = "1.5.3"
        const val bom = "androidx.compose:compose-bom:2023.09.01"

        const val foundation = "androidx.compose.foundation:foundation"
        const val foundationLayout = "androidx.compose.foundation:foundation-layout"

        const val materialIconsExtended = "androidx.compose.material:material-icons-extended"
        const val material3 = "androidx.compose.material3:material3"

        const val runtime = "androidx.compose.runtime:runtime"

        const val uiTooling = "androidx.compose.ui:ui-tooling"
        const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
    }

    object ktor {
        private const val version = "2.3.4"
        const val core = "io.ktor:ktor-client-core:$version"
        const val clientJson = "io.ktor:ktor-client-json:$version"
        const val okHttp = "io.ktor:ktor-client-okhttp:$version"
        const val darwin = "io.ktor:ktor-client-darwin:$version"
        const val serialization = "io.ktor:ktor-client-serialization:$version"
        const val mock = "io.ktor:ktor-client-mock:$version"
        const val negotiation = "io.ktor:ktor-client-content-negotiation:$version"
        const val serializationKotlinXJson = "io.ktor:ktor-serialization-kotlinx-json:$version"
    }

    const val coilCompose = "io.coil-kt:coil-compose:2.4.0"
    const val voyagerNavigator = "cafe.adriel.voyager:voyager-navigator:1.0.0-rc06"
    // Google accompanist
    const val accompanist = "com.google.accompanist:accompanist-pager-indicators:0.30.1"
}

private typealias PDsS = PluginDependenciesSpec
private typealias PDS = PluginDependencySpec

inline val PDsS.androidApplication: PDS get() = id("com.android.application")
inline val PDsS.androidLib: PDS get() = id("com.android.library")
inline val PDsS.kotlinAndroid: PDS get() = id("kotlin-android")
inline val PDsS.kotlin: PDS get() = id("kotlin")
inline val PDsS.kotlinKapt: PDS get() = id("kotlin-kapt")
inline val PDsS.kotlinParcelize: PDS get() = id("kotlin-parcelize")
