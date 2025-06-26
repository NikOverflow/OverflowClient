rootProject.name = "OverflowClient"
include("OverflowClient-Client")
include("OverflowClient-API")

pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net")
        mavenCentral()
    }
    plugins {
        id("fabric-loom") version extra.get("loom_version").toString()
    }
}