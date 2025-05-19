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
    modImplementation("net.fabricmc:fabric-loader:${project.property("fabric_loader_version")}")
    modImplementation("net.fabricmc.fabric-api:fabric-resource-loader-v0:${project.property("fabric_resource_loader_version")}")

    include(implementation(project(path = ":OverflowClient-API", configuration = "namedElements"))!!)
}

tasks {
    processResources {
        inputs.property("mod_version", project.version)
        inputs.property("minecraft_version", project.property("minecraft_version"))
        inputs.property("fabric_resource_loader_version", project.property("fabric_resource_loader_version"))
        filesMatching("fabric.mod.json") {
            expand(
                "mod_version" to inputs.properties["mod_version"],
                "minecraft_version" to inputs.properties["minecraft_version"],
                "fabric_resource_loader_version" to inputs.properties["fabric_resource_loader_version"]
            )
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