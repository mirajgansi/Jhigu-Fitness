plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
<<<<<<< HEAD
    namespace = "com.example.jhigu_fitness" // Replace with your app's package name
    compileSdkVersion(35)

    defaultConfig {
        applicationId = "com.example.jhigu_fitness" // Replace with your app's package name
        minSdkVersion(21)
        targetSdkVersion(35)
=======
    buildFeatures{
        viewBinding = true
    }
    namespace = "com.example.jhigu_fitness" // Replace with your app's package name
    compileSdkVersion(34)

    defaultConfig {
        applicationId = "com.example.jhigu_fitness" // Replace with your app's package name
        minSdkVersion(23)
        targetSdkVersion(34)
>>>>>>> subim
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
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.firebase.database)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}

