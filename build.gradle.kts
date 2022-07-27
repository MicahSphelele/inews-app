// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    repositories {
        google()
        jcenter()
        //maven(url = uri("https://plugins.gradle.org/m2/"))
        mavenCentral()
        
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://jitpack.io")
        mavenCentral()
        
    }
}

tasks {
    register("clean", Delete::class.java) {
        delete(rootProject.buildDir)
    }
}