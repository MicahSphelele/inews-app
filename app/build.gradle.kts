@file:Suppress("ImplicitThis", "UnstableApiUsage")

import java.util.Properties
import java.io.FileInputStream

plugins {
    /*id("com.android.application")
    kotlin("android")
    //kotlin("android.extensions")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin") apply true
    id("com.google.devtools.ksp") version "1.6.21-1.0.5"

    kotlin("kapt")*/
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
//    alias(libs.plugins.kotlin.kapt)
    kotlin("kapt")
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlin.symbol.processor)
}

val gradleProperties = Properties().apply {
    load(FileInputStream(rootProject.file("./app.properties")))
}

android {
    namespace = "sphe.inews"
    compileSdk = 34
    //buildToolsVersion Versions.buildToolsVersion

    defaultConfig {
        applicationId = "sphe.inews"
        minSdk = 21
        targetSdk = 34
        versionCode = 4
        versionName = "v1.2.3"
        testInstrumentationRunner = "sphe.inews.HiltTestRunner"
        // buildConfigField "String", "TEST_STRING", gradleProperties.getProperty('TEST_STRING')
        multiDexEnabled = true
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


    packaging {

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
    testImplementation(libs.junit)
    //noinspection GradleDependency
    //androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation(libs.androidx.espresso.core)
    //noinspection GradleDependency
//    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.4.0") {
//        exclude(group = "org.checkerframework", module = "checker")
//    }
    androidTestImplementation(libs.androidx.espresso.contrib) {
        exclude(group = "org.checkerframework", module = "checker")
    }
    //noinspection GradleDependency
//    androidTestImplementation("androidx.test.espresso:espresso-intents:3.4.0")
    androidTestImplementation(libs.androidx.espresso.intents)
    //noinspection GradleDependency
//    debugImplementation("androidx.fragment:fragment-testing:1.4.1")
    debugImplementation(libs.androidx.fragment.testing)
//    androidTestImplementation("androidx.test:rules:1.4.0")
    androidTestImplementation(libs.androidx.test.rules)

//    androidTestImplementation("androidx.test:core-ktx:1.4.0")
    androidTestImplementation(libs.androidx.test.core.ktx)
//    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.3")
    androidTestImplementation(libs.androidx.junit.ktx)
    //noinspection GradleDependency
//    androidTestImplementation("androidx.test.espresso:espresso-idling-resource:3.4.0") {
//        exclude(group = "org.checkerframework", module = "checker")
//    }
    androidTestImplementation(libs.androidx.espresso.idling.resource) {
        exclude(group = "org.checkerframework", module = "checker")
    }
//    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.2") //1.2.1
    androidTestImplementation(libs.kotlinx.coroutines.test)
//    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation(libs.androidx.core.testing)
//    androidTestImplementation("com.google.truth:truth:1.1.3")
    androidTestImplementation(libs.google.truth)
    //Nav Controller Testing
//    androidTestImplementation("androidx.navigation:navigation-testing:2.5.0")
    androidTestImplementation(libs.androidx.navigation.testing)
    //Hilt Testing
//    androidTestImplementation("com.google.dagger:hilt-android-testing:2.42")
    androidTestImplementation(libs.google.hilt.android.testing)
//    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.42")
    kspAndroidTest(libs.google.hilt.android.compiler)
 //   implementation(kotlin("stdlib-jdk8", "1.6.21"))
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.0")
//    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation(libs.androidx.appcompat)
//    implementation("androidx.core:core-ktx:1.7.0")
    implementation(libs.androidx.core.ktx)
//    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(libs.androidx.constraintlayout)
//    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation(libs.androidx.legacy.support)
//    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation(libs.androidx.recyclerview)
//    implementation("androidx.cardview:cardview:1.0.0")
    implementation(libs.androidx.cardview)
//    implementation("androidx.browser:browser:1.4.0")
    implementation(libs.androidx.browser)
//    implementation("androidx.multidex:multidex:2.0.1")
    implementation(libs.androidx.multidex)

    //AndroidX support design
//    implementation("com.google.android.material:material:1.4.0")
    implementation(libs.google.android.material)

    //Retrofit
//    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation(libs.retrofit2)
//    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation(libs.retrofit2.converter.gson)
//    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
    implementation(libs.retrofit2.adapter.rxjava2)
//    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    implementation(libs.okhttp3.logging.interceptor)

    //ViewModel and LiveData
//    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation(libs.androidx.lifecycle.extensions)
    //Room Database
//    implementation("androidx.room:room-ktx:2.4.2")
    implementation(libs.androidx.room.ktx)
//    kapt("androidx.room:room-compiler:2.4.2")
    ksp(libs.androidx.room.compiler)
//    kapt("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.5.0")
    ksp(libs.kotlinx.metadata.jvm)

    //RxAndroid
//    implementation("io.reactivex.rxjava3:rxandroid:3.0.0")
    implementation(libs.io.reactivex.rxandroid)

    //RxJava
//    implementation("io.reactivex.rxjava3:rxjava:3.1.5")
    implementation(libs.io.reactivex.rxjava)

    // Reactive Streams (convert Observable to LiveData)
//    implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:2.4.0")
    implementation(libs.androidx.lifecycle.reactivestreams.ktx)

    // Glide
//    implementation("com.github.bumptech.glide:glide:4.13.2")
    implementation(libs.glide)
//    kapt("com.github.bumptech.glide:compiler:4.13.2")
    ksp(libs.glide.compiler)

    //Data Binding compiler
//    kapt("com.android.databinding:compiler:3.1.4")
    kapt(libs.android.databinding.compiler)

    // Architecture comp. navigation
//    implementation("androidx.navigation:navigation-fragment-ktx:2.4.0")
    implementation(libs.androidx.navigation.fragment.ktx)
//    implementation("androidx.navigation:navigation-ui-ktx:2.4.0")
    implementation(libs.androidx.navigation.ui.ktx)

    //Custom Libraries
//    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:11.0.1")
    implementation(libs.youtube.player.core)
//    implementation("com.facebook.shimmer:shimmer:0.5.0")
    implementation(libs.facebook.shimmer)
//    implementation("com.google.code.gson:gson:2.9.0")
    implementation(libs.google.gson)
//    implementation("com.airbnb.android:lottie:5.2.0")
    implementation(libs.airbnb.lottie)

//    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.6")
    coreLibraryDesugaring(libs.android.desugar)

    //Hilt
//    implementation("com.google.dagger:hilt-android:2.42")
    implementation(libs.google.hilt.android)
//    kapt("com.google.dagger:hilt-android-compiler:2.42")
    ksp(libs.google.hilt.android.compiler)
    //implementation "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltLifecycleViewModelVersion}"
    ksp(libs.androidx.hilt.compiler)

    implementation(libs.google.play.services.location)
}

kapt {
    correctErrorTypes = true
}

//java {
//    toolchain.languageVersion.set(JavaLanguageVersion.of(11))
//    sourceCompatibility = JavaVersion.VERSION_11
//    targetCompatibility = JavaVersion.VERSION_11
//}

//hilt {
//    enableTransformForLocalTests = true
//}