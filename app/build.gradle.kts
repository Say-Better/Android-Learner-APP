plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    id("kotlin-kapt")

    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")

    //Hilt 종속성 추가
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
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

    implementation(libs.firebase.database.ktx)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //바텀네비
    implementation ("androidx.navigation:navigation-compose:2.7.6")
    implementation ("androidx.compose.material:material:1.6.1")

    implementation ("androidx.compose.material:material-icons-extended:1.6.1")
    implementation ("androidx.activity:activity-compose:1.8.2")
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
}

kapt {
    correctErrorTypes = true
}