repositories {
  jcenter()
}

plugins {
  `kotlin-dsl`
}

tasks{
  compileKotlin{
    kotlinOptions{
      allWarningsAsErrors = true
    }
  }
}