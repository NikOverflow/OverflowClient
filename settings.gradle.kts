rootProject.name = "OverflowClient"
include("OverflowClient-Client")
include("OverflowClient-API")

pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net")
        mavenCentral()
    }
    plugins {
        id("fabric-loom") version "1.10-SNAPSHOT"
    }
}