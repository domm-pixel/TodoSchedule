plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")

    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.dom.todo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dom.todo"
        minSdk = 28
        targetSdk = 34
        versionCode = 4
        versionName = "0.1.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }
    dataBinding {
        enable = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    // use stable version
    implementation("androidx.core:core-ktx:1.13.1")

    // dagger hilt
    val dagger_hilt_version = "2.48"
    implementation("com.google.dagger:hilt-android:$dagger_hilt_version")
    kapt("com.google.dagger:hilt-compiler:$dagger_hilt_version")
    kapt("com.google.dagger:hilt-android-compiler:$dagger_hilt_version")

//    implementation ("androidx.hilt:hilt-common:1.1.0")
//    implementation ("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
//    kapt ("androidx.hilt:hilt-compiler:1.1.0")
//    implementation ("androidx.hilt:hilt-work:1.1.0")


    // navigation
    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.6")

    // The view calendar library
    val calendar_version = "2.4.1"
    implementation ("com.kizitonwose.calendar:view:$calendar_version")

    // room
    val room_version = "2.4.0"
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation ("androidx.room:room-ktx:$room_version")

//    kapt("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.5.0")

}