@Suppress("unused")
object Versions {
    const val junit = "4.13"
    const val kotlin = "1.4.0"
    const val android = "4.0.1"
    const val androidXAppcompat = "1.2.0"
    const val androidXCoreKTX = "1.3.1"
    const val androidXConstraints = "2.0.1"
    const val androidxLegacySupport = "1.0.0"
    const val androidxRecyclerView = "1.1.0"
    const val androidxCardView = "1.0.0"
    const val androidxBrowser = "1.2.0"
    const val androidxTestExt = "1.1.2"
    const val androidxEspresso = "3.3.0"
    const val androidxTestRules = "1.3.0"
    const val androidxLifeCycleExt ="2.2.0"
    const val androidxRoomRxJava = "2.2.5"
    const val androidxLifeCycleReactiveStreams ="2.2.0"
    const val androidxNavigation = "2.3.0"
    const val rxJavaAndroid = "2.1.1"
    const val rxJava = "2.2.10"
    const val material = "1.2.1"
    const val retrofit = "2.9.0"
    const val dagger = "2.27"
    const val glide = "4.11.0"
    const val dataBinding ="3.1.4"
    const val customYoutubePlayer = "10.0.5"
    const val customShimmer = "0.5.0"
    const val buildToolsVersion = "29.0.3"
    const val minSdkVersion = 21
    const val maxSdkVersion = 29

}

@Suppress("unused")
object Deps {

    const val ANDROID_TOOLS_GRADLE = "com.android.tools.build:gradle:${Versions.android}"

    //Kotlin
    const val KOTLIN_GRADLE = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val KOTLIN_STD_LIB = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    //AndroidX Test
    const val JUNIT = "junit:junit:${Versions.junit}"
    const val ANDROIDX_TEST_EXT = "androidx.test.ext:junit:${Versions.androidxTestExt}"
    const val ANDROIDX_ESPRESSO = "androidx.test.espresso:espresso-core:${Versions.androidxEspresso}"
    const val ANDROIDX_TEST_RULES = "androidx.test:rules:${Versions.androidxTestRules}"


    //AndroidX
    const val ANDROIDX_APPCOMPAT = "androidx.appcompat:appcompat:${Versions.androidXAppcompat}"
    const val ANDROIDX_CORE_KTX = "androidx.core:core-ktx:${Versions.androidXCoreKTX}"
    const val ANDROIDX_CONSTRAINT = "androidx.constraintlayout:constraintlayout:${Versions.androidXConstraints}"
    const val ANDROIDX_LEGACY = "androidx.legacy:legacy-support-v4:${Versions.androidxLegacySupport}"
    const val ANDROIDX_RECYCLER_VIEW = "androidx.recyclerview:recyclerview:${Versions.androidxRecyclerView}"
    const val ANDROIDX_CARD_VIEW = "androidx.cardview:cardview:${Versions.androidxCardView}"
    const val ANDROIDX_BROWSER = "androidx.browser:browser:${Versions.androidxBrowser}"
    const val ANDROIDX_LIFECYCLE_EXT = "androidx.lifecycle:lifecycle-extensions:${Versions.androidxLifeCycleExt}"
    const val ANDROIDX_ROOM_RXJAVA2 = "androidx.room:room-rxjava2:${Versions.androidxRoomRxJava}"
    const val ANDROIDX_ROOM_COMPILER = "androidx.room:room-compiler:${Versions.androidxRoomRxJava}"
    const val ANDROIDX_LIFECYCLE_STREAM_EXT = "androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.androidxLifeCycleReactiveStreams}"
    const val ANDROIDX_NAV_FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:${Versions.androidxNavigation}"
    const val ANDROIDX_NAV_UI_KTX = "androidx.navigation:navigation-ui-ktx:${Versions.androidxNavigation}"
    //Material
    const val MATERIAL = "com.google.android.material:material:${Versions.material}"

    //Retrofit
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val RETROFIT_GSON_CONVERTER = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val RETROFIT_RX_ADAPTER = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"

    //Dagger 2
    const val DAGGER = "com.google.dagger:dagger:${Versions.dagger}"
    const val DAGGER_COMPILER = "com.google.dagger:dagger-compiler:${Versions.dagger}"

    //Dagger Android
    const val DAGGER_ANDROID = "com.google.dagger:dagger-android:${Versions.dagger}"
    const val DAGGER_SUPPORT = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    const val DAGGER_PROCESSOR = "com.google.dagger:dagger-android-processor:${Versions.dagger}"

    //RxAndroid
    const val RX_ANDROID = "io.reactivex.rxjava2:rxandroid:${Versions.rxJavaAndroid}"
    const val RX_JAVA = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"

    //Glide
    const val GLIDE = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:${Versions.glide}"

    //Data binding
    const val DATA_BINDING_COMPILER = "com.android.databinding:compiler:${Versions.dataBinding}"

    //Custom lib
    const val CUSTOM_YOUTUBE_PLAYER = "com.pierfrancescosoffritti.androidyoutubeplayer:core:${Versions.customYoutubePlayer}"
    const val CUSTOM_SHIMMER = "com.facebook.shimmer:shimmer:${Versions.customShimmer}"

}