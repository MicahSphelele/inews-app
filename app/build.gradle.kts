@file:Suppress("ImplicitThis", "UnstableApiUsage")

import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    kotlin("android")
    //kotlin("android.extensions")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin") apply true
    //id("com.google.devtools.ksp") version "1.7.10-1.0.6"
    kotlin("kapt")
}

val gradleProps = rootProject.file("./app.properties")
val gradleProperties = Properties()
gradleProperties.load(FileInputStream(gradleProps))

android {

    compileSdk = 34
    //buildToolsVersion Versions.buildToolsVersion

    defaultConfig {
        applicationId = "sphe.inews"
        minSdk = 21
        targetSdk = 33
        versionCode = 4
        versionName = "v1.2.3"
        testInstrumentationRunner = "sphe.inews.HiltTestRunner"
        // buildConfigField "String", "TEST_STRING", gradleProperties.getProperty('TEST_STRING')
        //multiDexEnabled = true
    }

    signingConfigs {
        create("release") {
            storeFile = file(gradleProperties.getProperty("KEY_STORE"))
            storePassword = gradleProperties.getProperty("KEY_STORE_PASS")
            keyPassword = gradleProperties.getProperty("KEY_STORE_PASS")
            keyAlias = gradleProperties.getProperty("KEY_STORE_ALIAS")
        }
    }

    buildFeatures {

        dataBinding = true
        // for view binding:
        viewBinding = true
    }

    kotlinOptions {
        jvmTarget = "11"
        //useIR = true
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }


    packagingOptions {

        resources.excludes.add("**/attach_hotspot_windows.dll")
        //exclude("**/attach_hotspot_windows.dll")
        resources.excludes.add("META-INF/licenses/**")
        //exclude("META-INF/licenses/**")
        resources.excludes.add("META-INF/AL2.0")
        //exclude("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
        //exclude("META-INF/LGPL2.1")
    }
}

//afterEvaluate {
//    android.applicationVariants.all { variant ->
//        System.err.println("applicationVariant : " + variant.name)
//    }
//}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    testImplementation("junit:junit:4.13.2")
    //noinspection GradleDependency
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    //noinspection GradleDependency
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.4.0") {
        //exclude group = "org.checkerframework", module = "checker"
        exclude(group = "org.checkerframework", module = "checker")
    }
    //noinspection GradleDependency
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.4.0")
    //noinspection GradleDependency
    debugImplementation("androidx.fragment:fragment-testing:1.4.1")
    androidTestImplementation("androidx.test:rules:1.4.0")

    androidTestImplementation("androidx.test:core-ktx:1.4.0")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.3")
    //noinspection GradleDependency
    androidTestImplementation("androidx.test.espresso:espresso-idling-resource:3.4.0") {
        exclude(group = "org.checkerframework", module = "checker")
    }
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.2") //1.2.1
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation("com.google.truth:truth:1.1.3")

    //Nav Controller Testing
    androidTestImplementation("androidx.navigation:navigation-testing:2.5.0")
    //Hilt Testing
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.42")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.42")

    implementation(kotlin("stdlib-jdk8", "1.6.21"))
    //implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.browser:browser:1.4.0")
    implementation("androidx.multidex:multidex:2.0.1")

    //AndroidX support design
    implementation("com.google.android.material:material:1.4.0")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    //ViewModel and LiveData
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    //Room Database
    //implementation("androidx.room:room-rxjava2:${Versions.androidxRoomRxJava}")
    implementation("androidx.room:room-ktx:2.4.2")
    kapt("androidx.room:room-compiler:2.4.2")
    kapt("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.5.0")

    //RxAndroid
    implementation("io.reactivex.rxjava3:rxandroid:3.0.0")

    //RxJava
    implementation("io.reactivex.rxjava3:rxjava:3.1.5")

    // Reactive Streams (convert Observable to LiveData)
    implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:2.4.0")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.13.2")
    kapt("com.github.bumptech.glide:compiler:4.13.2")

    //Data Binding compiler
    kapt("com.android.databinding:compiler:3.1.4")

    // Architecture comp. navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.0")

    //Custom Libraries
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:11.0.1")
    implementation("com.facebook.shimmer:shimmer:0.5.0")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("com.airbnb.android:lottie:5.2.0")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.6")

    //Hilt
    implementation("com.google.dagger:hilt-android:2.42")
    kapt("com.google.dagger:hilt-android-compiler:2.42")
    //implementation "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltLifecycleViewModelVersion}"
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    implementation("com.google.android.gms:play-services-location:20.0.0")
}

kapt {
    correctErrorTypes = true
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(11))
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

//hilt {
//    enableTransformForLocalTests = true
//}