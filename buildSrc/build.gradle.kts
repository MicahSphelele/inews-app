repositories {
    mavenCentral()
    google()
}

//kotlinDslPluginOptions.experimentalWarning.set(false)

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
    implementation("com.android.tools.build:gradle:7.2.1")
    implementation("com.squareup:javapoet:1.13.0")
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.42")
    //implementation("org.jetbrains.dokka:dokka-gradle-plugin:${Plugins.DOKKA}")
    //implementation("org.jetbrains.dokka:dokka-core:${Plugins.DOKKA}")
}

plugins {
    `kotlin-dsl`
    id("com.google.devtools.ksp") version "1.6.21-1.0.5" apply false
}