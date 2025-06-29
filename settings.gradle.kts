rootProject.name = "OverflowClient"
include("OverflowClient-Client")
include("OverflowClient-API")

pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net")
        mavenCentral()
    }
    plugins {
        id("fabric-loom") version extra.get("fabric_loom_version").toString()
    }
}