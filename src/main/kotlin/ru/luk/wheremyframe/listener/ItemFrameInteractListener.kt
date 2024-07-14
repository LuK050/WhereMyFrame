package ru.luk.wheremyframe.listener
import ru.luk.wheremyframe.damageItemInMainHand
import ru.luk.wheremyframe.WhereMyFrame

import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.*
import org.bukkit.entity.ItemFrame
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.inventory.ItemStack


class ItemFrameInteractListener : Listener {
    @EventHandler
    fun onItemFrameInteraction(event: PlayerInteractEntityEvent) {
        if (event.rightClicked is ItemFrame) {
            val player: Player = event.player
            val itemFrame = event.rightClicked as ItemFrame

            if (player.isSneaking && player.inventory.itemInMainHand.type == Material.SHEARS && itemFrame.isVisible
                && itemFrame.item.type != Material.AIR) {
                itemFrame.world.playSound(itemFrame.location, Sound.ITEM_AXE_STRIP, 1f, 1f)
                event.isCancelled = true
                itemFrame.isVisible = false

                if (WhereMyFrame.damagingShears && player.gameMode != GameMode.CREATIVE) {
                    damageItemInMainHand(player)
                }
            }
        }
    }

    @EventHandler
    fun onItemFrameDamage(event: EntityDamageByEntityEvent) {
        if (event.entity is ItemFrame) {
            val itemFrame = event.entity as ItemFrame

            if (!WhereMyFrame.allowEmptyItemFrame && !itemFrame.isVisible && itemFrame.item.type != Material.AIR) {
                itemFrame.world.playSound(itemFrame.location, Sound.ITEM_AXE_STRIP, 1f, 1f)
                itemFrame.remove()

                if (event.damager is Player && (event.damager as Player).gameMode != GameMode.CREATIVE) {
                    val material = if (event.entity is GlowItemFrame) Material.GLOW_ITEM_FRAME else Material.ITEM_FRAME
                    itemFrame.world.dropItem(itemFrame.location.add(0.0, 0.2, 0.0), itemFrame.item)
                    itemFrame.world.dropItem(itemFrame.location.add(0.0, 0.2, 0.0), ItemStack(material, 1))
                }
            }
        }
    }
}