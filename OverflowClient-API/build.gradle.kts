plugins {
    id("java")
    id("fabric-loom")
}

group = "com.nikoverflow"
version = project.property("api_version")!!

dependencies {
    minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${project.property("minecraft_version")}:${project.property("parchment_version")}@zip")
    })
}

publishing.publications.create<MavenPublication>("maven") {
    from(components["java"])
}