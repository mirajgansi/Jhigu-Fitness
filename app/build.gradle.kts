plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
<<<<<<< HEAD
}

android {
    namespace = "com.example.jhigu_fitness"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.jhigu_fitness"
        minSdk = 25
=======
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.jhigufitness"
    compileSdk = 35
    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.jhigufitness"
        minSdk = 24
>>>>>>> 0867dbfb7ba5cd9f3800a264292e7f08e0891759
        targetSdk = 34
        versionCode = 1
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
<<<<<<< HEAD
=======
    implementation(libs.firebase.database)
    implementation(libs.firebase.auth)
>>>>>>> 0867dbfb7ba5cd9f3800a264292e7f08e0891759
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}