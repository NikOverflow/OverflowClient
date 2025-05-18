plugins {
    id("fabric-loom")
}

group = "com.nikoverflow"
version = project.property("client_version")!!

dependencies {
    minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${project.property("minecraft_version")}:${project.property("parchment_version")}@zip")
    })
    modImplementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")

    include(implementation(project(path = ":OverflowClient-API", configuration = "namedElements"))!!)
}

tasks {
    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
            expand("version" to inputs.properties["version"])
        }
    }
    jar {
        from(rootProject.file("LICENSE")) {
            rename { "${it}_OverflowClient" }
        }
        from(rootProject.projectDir) {
            include("LICENSE_*")
        }
    }
    remapJar {
        archiveBaseName.set("OverflowClient")
    }
}

publishing.publications.create<MavenPublication>("maven") {
    from(components["java"])
}