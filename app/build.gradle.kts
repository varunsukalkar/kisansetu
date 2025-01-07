plugins {
    alias(libs.plugins.android.application)

    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("kotlin-kapt")



}


android {
    namespace = "com.varun.myapplication"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.varun.myapplication"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11

    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.core.ktx)
    implementation(libs.firebase.database.ktx)
    implementation(libs.firebase.storage.ktx)
    implementation(libs.play.services.location)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.room.runtime)
    // implementation(libs.coil.compose)
    kapt("androidx.room:room-compiler:2.5.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    implementation(libs.riobottomnavigation)
    implementation("io.coil-kt:coil-compose:2.7.0")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("com.mapbox.maps:android:11.9.0")
    implementation("com.mapbox.extension:maps-compose:11.9.0")
    implementation ("com.google.android.gms:play-services-location:17.0.0")
implementation("androidx.compose.material:material:1.7.6")
    implementation("androidx.navigation:navigation-compose:2.4.0-alpha06")

}

