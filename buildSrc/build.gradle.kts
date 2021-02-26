repositories {
  jcenter()
}

buildscript {

}

plugins {
  `kotlin-dsl`
  id("name.remal.check-updates") version "1.2.2"
  id("de.fayard.buildSrcVersions") version "0.3.2"
}

//buildscript {
//  repositories {
//    maven { url = uri("https://plugins.gradle.org/m2/") }
//  }
//  dependencies {
//    classpath("name.remal:gradle-plugins:1.0.211")
//  }
//}
//
//apply(plugin = "name.remal.check-dependency-updates")

tasks{

  compileKotlin{
    kotlinOptions{
      allWarningsAsErrors = true
    }
  }
}