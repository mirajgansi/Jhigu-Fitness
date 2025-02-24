plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
<<<<<<< HEAD
<<<<<<< HEAD
=======
    alias(libs.plugins.google.gms.google.services)
>>>>>>> rojan
}

android {

    namespace = "com.example.jhigu_fitness"
    compileSdk = 35
    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.jhigu_fitness"
        minSdk = 25
<<<<<<< HEAD
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
=======
        targetSdk = 35
>>>>>>> rojan
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        namespace = "com.example.jhigu_fitness" // Replace with your app's package name
        compileSdkVersion(35)

        defaultConfig {
            applicationId = "com.example.jhigu_fitness" // Replace with your app's package name
            minSdkVersion(21)
            targetSdkVersion(35)

            buildFeatures {
                viewBinding = true
            }
            namespace = "com.example.jhigu_fitness" // Replace with your app's package name
            compileSdkVersion(34)

            defaultConfig {
                applicationId = "com.example.jhigu_fitness" // Replace with your app's package name
                minSdkVersion(23)
                targetSdkVersion(34)

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
            implementation(libs.firebase.auth)
            implementation(libs.androidx.navigation.fragment.ktx)
            implementation(libs.androidx.navigation.ui.ktx)
            implementation(libs.firebase.database)
            testImplementation(libs.junit)
            androidTestImplementation(libs.androidx.junit)
            androidTestImplementation(libs.androidx.espresso.core)
            implementation("com.cloudinary:cloudinary-android:2.1.0")
            implementation("com.squareup.picasso:picasso:2.8")
        }
    }
}


<<<<<<< HEAD
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
=======
>>>>>>> rojan
