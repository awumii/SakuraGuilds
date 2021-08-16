plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "me.xneox"
version = "4.0.0"

repositories {
    mavenCentral()
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://repo.codemc.io/repository/maven-public/") }
    maven { url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/") }
    maven { url = uri("https://nexus.sirblobman.xyz/repository/public/") }
    maven { url = uri("https://repo.aikar.co/content/groups/aikar/") }
    maven { url = uri("https://maven.enginehub.org/repo/") }
}

dependencies {
    implementation("commons-io:commons-io:2.11.0")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("com.zaxxer:HikariCP:5.0.0")
    implementation("co.aikar:idb-core:1.0.0-SNAPSHOT")

    compileOnly("io.papermc.paper:paper-api:1.17-R0.1-SNAPSHOT")
    compileOnly("fr.minuskube.inv:smart-invs:1.2.7")
    compileOnly("me.clip:placeholderapi:2.10.10")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7.1")
    compileOnly("com.gmail.filoghost.holographicdisplays:holographicdisplays-api:2.4.9")
    compileOnly("com.github.Archy-X:AureliumSkills:Beta1.2.0")
    compileOnly("com.SirBlobman.combatlogx:CombatLogX-API:10.0.0.0-SNAPSHOT")
    compileOnly("fr.xephi:authme:5.6.0-SNAPSHOT")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.3.0-SNAPSHOT")
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
        minimize()

        relocate("org.apache.commons", "lib")
        relocate("com.zaxxer", "lib")
        relocate("co.aikar", "lib")
    }
}