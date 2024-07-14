package ru.luk.wheremyframe
import ru.luk.wheremyframe.command.PluginCommand
import ru.luk.wheremyframe.listener.ItemFrameInteractListener

import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin

import java.io.File
import kotlin.properties.Delegates

class WhereMyFrame : JavaPlugin() {
    companion object {
        lateinit var plugin: WhereMyFrame
        var damagingShears by Delegates.notNull<Boolean>()
        var allowEmptyItemFrame by Delegates.notNull<Boolean>()
    }

    override fun onEnable() {
        plugin = this

        File(dataFolder.toString() + File.separator + "config.yml").apply {
            if (!exists()) {
                config.options().copyDefaults(true)
                saveDefaultConfig()
            }
        }

        damagingShears = config.getBoolean("damagingShears")
        allowEmptyItemFrame = config.getBoolean("allowEmptyItemFrame")

        Bukkit.getPluginManager().registerEvents(ItemFrameInteractListener(), this)

        getCommand("wheremyframe")?.setExecutor(PluginCommand())
    }
}