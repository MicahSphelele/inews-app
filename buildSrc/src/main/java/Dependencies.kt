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
    const val material = "1.2.1"
    const val retrofit = "2.9.0"
    const val androidxLifeCycleExt ="2.2.0"
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

    //Material
    const val MATERIAL = "com.google.android.material:material:${Versions.material}"

    //Retrofit
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val RETROFIT_GSON_CONVERTER = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"


    /*
   ''*/

}