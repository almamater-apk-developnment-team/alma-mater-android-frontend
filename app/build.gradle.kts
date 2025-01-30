plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
}

android {
    namespace = "com.nitt"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.nitt"
        minSdk = 26
        targetSdk = 34
        versionCode = 14
        versionName = "1.0"
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // Navigation dependencies
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation (libs.logging.interceptor)
    implementation (libs.androidx.work.runtime.ktx)
    implementation( libs.androidx.core.ktx.v1150)
    // retrofit
    implementation(libs.androidx.navigation.compose.v273)
// Calender-dependency
    implementation(libs.view)
    implementation(libs.calendar.compose)
//  firebase
    implementation(libs.google.firebase.firestore)
//  CloudMessaging
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.analytics)
    //pdf and imaged is play intent
    implementation (libs.glide)
    annotationProcessor (libs.compiler)
    //retrofit
    implementation (libs.squareup.retrofit)
    implementation (libs.squareup.converter.gson)
    //ViewModel
    implementation (libs.lifecycle.viewmodel.compose)
    implementation (libs.androidx.lifecycle.livedata)
    implementation (libs.androidx.lifecycle.viewmodel.compose.v260)
    implementation(libs.androidx.activity.compose.v172)
    implementation(libs.core.ktx.v1120)
    implementation(libs.androidx.documentfile)
    //firebase BoM
    implementation(platform(libs.google.firebase.bom.v3370))
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    implementation (libs.coil.kt.coil.compose)
    implementation (libs.coil.svg)
    implementation(libs.okhttp)
//    for swipe refresh
    implementation(libs.accompanist.swiperefresh)
//   for finding the number of pages in the pdf
    implementation(libs.itext7.core)
//    ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)

}

