plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    id("kotlin-kapt")

    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")

    //Hilt 종속성 추가
    kotlin("kapt")
    id("com.google.dagger.hilt.android")

    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.0"
}

android {
    namespace = "gdsc.solutionchallenge.saybetter.saybetterlearner"
    compileSdk = 34

    defaultConfig {
        applicationId = "gdsc.solutionchallenge.saybetter.saybetterlearner"
        minSdk = 30
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    implementation(libs.androidx.media3.common)

    implementation (libs.firebase.database.ktx)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.core)


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    //바텀네비
    implementation ("androidx.navigation:navigation-compose:2.7.6")
    implementation ("androidx.compose.material:material:1.6.1")

    implementation ("androidx.compose.material:material-icons-extended:1.6.1")
    implementation ("androidx.activity:activity-compose:1.8.2")
    // Compose Foundation (필요한 종속성 추가)
    implementation ("androidx.compose.foundation:foundation:1.4.0")
    kapt ("com.google.dagger:hilt-compiler:2.46")

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))

    // Add the dependency for the Firebase SDK for Google Analytics
    implementation("com.google.firebase:firebase-analytics-ktx")

    //Hilt 종속성 추가
    implementation("com.google.dagger:hilt-android:2.46")
    kapt("com.google.dagger:hilt-android-compiler:2.46")

    //webRTC 종속성 추가
    implementation ("com.mesibo.api:webrtc:1.0.5")

    //Gson 종속성 추가
    implementation("com.google.code.gson:gson:2.10.1")

    //권한 요청 관련 모듈
    implementation("com.google.accompanist:accompanist-permissions:0.35.1-alpha")

    //Google Play services
    implementation ("com.google.gms:google-services:4.3.15")
    implementation ("com.google.firebase:firebase-auth:22.0.0")
    implementation ("com.google.firebase:firebase-bom:32.0.0")
    implementation ("com.google.android.gms:play-services-auth:20.5.0")

    implementation ("androidx.credentials:credentials:1.2.2")
    implementation ("androidx.credentials:credentials-play-services-auth:1.2.2")
    implementation ("com.google.android.libraries.identity.googleid:googleid:1.1.0")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")

    // OkHttp
    implementation ("com.squareup.okhttp3:okhttp:4.10.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("androidx.camera:camera-camera2:1.4.0-alpha02")
    implementation("androidx.camera:camera-lifecycle:1.4.0-alpha02")
    implementation ("androidx.camera:camera-view:1.4.0-alpha02")

    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("com.kizitonwose.calendar:compose:2.6.0-beta02")
}

kapt {
    correctErrorTypes = true
}