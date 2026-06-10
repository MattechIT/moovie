import com.android.build.api.dsl.ApplicationExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

// Read local.properties dynamically
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { localProperties.load(it) }
}
val tmdbApiKey = localProperties.getProperty("TMDB_API_KEY")?.let { "\"$it\"" } ?: "\"\""
val supabaseUrl = localProperties.getProperty("SUPABASE_URL")?.let { "\"$it\"" } ?: "\"\""
val supabaseKey = localProperties.getProperty("SUPABASE_KEY")?.let { "\"$it\"" } ?: "\"\""
val mapsApiKey = localProperties.getProperty("MAPS_API_KEY") ?: (project.findProperty("MAPS_API_KEY") as? String) ?: ""

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

extensions.configure<ApplicationExtension> {
    namespace = "com.example.moovie"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.moovie"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "TMDB_API_KEY", tmdbApiKey)
        buildConfigField("String", "SUPABASE_URL", supabaseUrl)
        buildConfigField("String", "SUPABASE_KEY", supabaseKey)
        manifestPlaceholders["MAPS_API_KEY"] = mapsApiKey
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
        buildConfig = true // Enable BuildConfig class generation
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

configurations.all {
    resolutionStrategy {
        force("androidx.concurrent:concurrent-futures:1.2.0")
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.koin.androidx.compose)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // Biometric App Lock
    implementation(libs.androidx.biometric)

    // Ktor Client
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)

    // Coil Image Loader
    implementation(libs.coil.compose)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Google Maps & Location Services
    implementation(libs.play.services.maps)
    implementation(libs.maps.compose)
    implementation(libs.play.services.location)
    // Supabase
    implementation(libs.supabase.auth)
    implementation(libs.supabase.postgrest)
    implementation(libs.supabase.storage)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}