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
    maven { url = uri("https://libraries.minecraft.net/") }
    maven { url = uri("https://repo.codemc.io/repository/maven-public/") }
    maven { url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/") }
    maven { url = uri("https://nexus.sirblobman.xyz/repository/public/") }
}

dependencies {
    implementation("commons-io:commons-io:2.11.0")
    implementation("com.github.simplix-softworks:SimplixStorage:3.2.3")

    compileOnly("io.papermc.paper:paper-api:1.17-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.10.10")
    compileOnly("com.mojang:authlib:1.5.25")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("com.gmail.filoghost.holographicdisplays:holographicdisplays-api:2.4.9")
    compileOnly("com.github.Archy-X:AureliumSkills:Beta1.1.4")
    compileOnly("com.SirBlobman.combatlogx:CombatLogX-API:10.0.0.0-SNAPSHOT")
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
    }
}