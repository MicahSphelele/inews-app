@file:Suppress("ImplicitThis")

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
    compileSdk = Versions.maxSdkVersion
    //buildToolsVersion Versions.buildToolsVersion

    defaultConfig {
        applicationId = "sphe.inews"
        minSdk = Versions.minSdkVersion
        targetSdk  = Versions.maxSdkVersion
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
    testImplementation("junit:junit:${Versions.junit}")
    //noinspection GradleDependency
    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.androidxEspresso}")
    //noinspection GradleDependency
    androidTestImplementation("androidx.test.espresso:espresso-contrib:${Versions.androidxEspresso}") {
        //exclude group = "org.checkerframework", module = "checker"
        exclude(group = "org.checkerframework", module = "checker")
    }
    //noinspection GradleDependency
    androidTestImplementation("androidx.test.espresso:espresso-intents:${Versions.androidxEspresso}")
    //noinspection GradleDependency
    debugImplementation("androidx.fragment:fragment-testing:${Versions.fragmentTesting}")
    androidTestImplementation("androidx.test:rules:${Versions.androidxTestRules}")

    androidTestImplementation("androidx.test:core-ktx:${Versions.androidxTestRules}")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.3")
    //noinspection GradleDependency
    androidTestImplementation("androidx.test.espresso:espresso-idling-resource:${Versions.androidxEspresso}") {
        exclude(group = "org.checkerframework", module = "checker")
    }
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.2") //1.2.1
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation("com.google.truth:truth:1.1.3")

    //Nav Controller Testing
    androidTestImplementation("androidx.navigation:navigation-testing:2.5.0")
    //Hilt Testing
    androidTestImplementation("com.google.dagger:hilt-android-testing:${Versions.hiltVersion}")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}")

    implementation(kotlin("stdlib-jdk8", "1.6.21"))
    //implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.0")
    implementation("androidx.appcompat:appcompat:${Versions.androidXAppcompat}")
    implementation("androidx.core:core-ktx:${Versions.androidXCoreKTX}")
    implementation("androidx.constraintlayout:constraintlayout:${Versions.androidXConstraints}")
    implementation("androidx.legacy:legacy-support-v4:${Versions.androidxLegacySupport}")
    implementation("androidx.recyclerview:recyclerview:${Versions.androidxRecyclerView}")
    implementation("androidx.cardview:cardview:${Versions.androidxCardView}")
    implementation("androidx.browser:browser:${Versions.androidxBrowser}")
    implementation("androidx.multidex:multidex:2.0.1")

    //AndroidX support design
    implementation("com.google.android.material:material:${Versions.material}")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:${Versions.retrofit}")
    implementation("com.squareup.retrofit2:converter-gson:${Versions.retrofit}")
    implementation("com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    //ViewModel and LiveData
    implementation("androidx.lifecycle:lifecycle-extensions:${Versions.androidxLifeCycleExt}")

    //Room Database
    //implementation("androidx.room:room-rxjava2:${Versions.androidxRoomRxJava}")
    implementation("androidx.room:room-ktx:${Versions.androidxRoom}")
    kapt("androidx.room:room-compiler:${Versions.androidxRoomRxJava}")
    kapt("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.5.0")

    //RxAndroid
    implementation("io.reactivex.rxjava3:rxandroid:${Versions.rxJavaAndroid}")

    //RxJava
    implementation("io.reactivex.rxjava3:rxjava:${Versions.rxJava}")

    // Reactive Streams (convert Observable to LiveData)
    implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.androidxLifeCycleReactiveStreams}")

    // Glide
    implementation("com.github.bumptech.glide:glide:${Versions.glide}")
    kapt("com.github.bumptech.glide:compiler:${Versions.glide}")

    //Data Binding compiler
    kapt("com.android.databinding:compiler:${Versions.dataBinding}")

    // Architecture comp. navigation
    implementation("androidx.navigation:navigation-fragment-ktx:${Versions.androidxNavigation}")
    implementation("androidx.navigation:navigation-ui-ktx:${Versions.androidxNavigation}")

    //Custom Libraries
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:${Versions.customYoutubePlayer}")
    implementation("com.facebook.shimmer:shimmer:0.5.0")
    implementation("com.google.code.gson:gson:${Versions.gson}")
    implementation("com.airbnb.android:lottie:${Versions.lottie}")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.6")

    //Hilt
    implementation("com.google.dagger:hilt-android:${Versions.hiltVersion}")
    kapt("com.google.dagger:hilt-android-compiler:${Versions.hiltCompilerVersion}")
    //implementation "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltLifecycleViewModelVersion}"
    kapt("androidx.hilt:hilt-compiler:${Versions.hiltLifecycleViewModelVersion}")

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