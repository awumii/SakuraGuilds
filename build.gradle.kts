plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "me.xneox"
version = "1.0-rc1"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://jitpack.io")
    maven("https://repo.codemc.io/repository/maven-public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://nexus.sirblobman.xyz/repository/public/")
    maven("https://mvn.intellectualsites.com/content/groups/public/")
    maven("https://repo.essentialsx.net/snapshots/")
}

dependencies {
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.spongepowered:configurate-hocon:4.1.2")
    implementation("fr.minuskube.inv:smart-invs:1.2.7")
    implementation("com.zaxxer:HikariCP:5.0.1")

    compileOnly("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.2")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7.1")
    compileOnly("com.gmail.filoghost.holographicdisplays:holographicdisplays-api:2.4.9")
    compileOnly("com.fastasyncworldedit:FAWE-Bukkit:1.17-339")
}

tasks {
    processResources {
        filesMatching("plugin.yml") {
            expand("version" to project.version)
        }
    }

    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        relocate("org.apache.commons", "$group.libs.commons")
        relocate("com.zaxxer.hikari", "$group.libs.hikari")
        relocate("com.typesafe.config", "$group.libs.config")
        relocate("org.spongepowered.configurate", "$group.libs.configurate")
        relocate("io.leangen.geantyref", "$group.libs.geantyref")
        relocate("fr.minuskube.inv", "$group.libs.smartinvs")
        minimize()
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}