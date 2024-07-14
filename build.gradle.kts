import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    kotlin("jvm") version "1.9.23"

    application
    `java-library`
    id("io.papermc.paperweight.userdev") version "1.7.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "ru.luk.wheremyframe"
version = "1.0"

application {
    mainClass.set("$group.WhereMyFrame")
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.codemc.org/repository/maven-public/")
}

dependencies {
    paperweightDevelopmentBundle("io.papermc.paper:dev-bundle:1.17.1-R0.1-SNAPSHOT")
}

bukkit {
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP
    main = application.mainClass.get()
    apiVersion = "1.17"
    authors = listOf("_LuK__")

    commands.create("wheremyframe") {
        usage = "§cCorrect use: /wheremyframe [info/reload]"
        permission = "op"
    }
}


tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        enabled = true
    }

    withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        @Suppress("deprecation", "RedundantSuppression")
        archiveFileName.set("WhereMyFrame-$version.jar")
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}