plugins {
    id("java")
    id("maven-publish")
}

group = "com.nikoverflow"

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

subprojects {
    apply(plugin = "maven-publish")
    publishing {
        repositories {
            maven {
                name = "NikOverflowRepositoryReleases"
                url = uri("https://reposilite.nikoverflow.com/releases")
                credentials(PasswordCredentials::class)
                authentication {
                    create<BasicAuthentication>("basic")
                }
            }
            maven {
                name = "NikOverflowRepositorySnapshots"
                url = uri("https://reposilite.nikoverflow.com/snapshots")
                credentials(PasswordCredentials::class)
                authentication {
                    create<BasicAuthentication>("basic")
                }
            }
        }
    }
}