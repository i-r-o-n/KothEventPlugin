package me.iron.kotheventplugin.commands

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class Kit : CommandExecutor, Listener {

    private val invName = "Kit Selector"


    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.view.title != invName) {
            return
        }
        val player = event.whoClicked as Player
        val slot = event.slot
        val currentPlayerInventory = player.inventory
        event.isCancelled = true

        when (slot) {
            11 -> {
                combatItems.forEach { currentPlayerInventory.addItem(it) }
                currentPlayerInventory.clear() }
            13 -> {
                utilityItems.forEach { currentPlayerInventory.addItem(it) }
                currentPlayerInventory.clear() }
            15 -> {
                gathererItems.forEach { currentPlayerInventory.addItem(it) }
                currentPlayerInventory.clear() }
        }

    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) {
            sender.sendMessage("Only players can run this command.")
            return true
        }

        val inv = Bukkit.createInventory(sender, 9 * 3, invName)
        inv.setItem(11, getItem(ItemStack(Material.IRON_SWORD), "&9Combat", "&aClick to Join!", "&aPreview Items on Click"))
        inv.setItem(13, getItem(ItemStack(Material.FISHING_ROD), "&9Utility", "&aClick to Join!", "&aPreview Items on Click"))
        inv.setItem(15, getItem(ItemStack(Material.IRON_PICKAXE), "&9Gatherer", "&aClick to Join!", "&aPreview Items on Click"))
        sender.openInventory(inv)
        return true
    }

    private fun getItem(item: ItemStack, name: String, vararg lore: String): ItemStack? {
        val meta = item.itemMeta
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name))
        val lores: MutableList<String> = ArrayList()
        for (s in lore) {
            lores.add(ChatColor.translateAlternateColorCodes('&', s))
        }
        meta.lore = lores
        item.itemMeta = meta
        return item
    }

    private val combatItems= arrayListOf<ItemStack>(
        ItemStack(Material.CROSSBOW),
        ItemStack(Material.LEATHER_HELMET),
        ItemStack(Material.CHAINMAIL_CHESTPLATE),
        ItemStack(Material.LEATHER_LEGGINGS),
        ItemStack(Material.CHAINMAIL_BOOTS),
        ItemStack(Material.IRON_SWORD),
        ItemStack(Material.SHIELD),
        ItemStack(Material.ARROW, 12),
        ItemStack(Material.FLINT_AND_STEEL),
        ItemStack(Material.ENDER_PEARL, 2),
        ItemStack(Material.GOLDEN_APPLE, 2),
    )

    private val gathererItems = arrayListOf<ItemStack>(
        ItemStack(Material.IRON_PICKAXE),
        ItemStack(Material.TURTLE_HELMET),
        ItemStack(Material.STONE_AXE),
        ItemStack(Material.STONE_SHOVEL),
        ItemStack(Material.STONE_HOE),
        ItemStack(Material.SHEARS),
        // ItemStack(Material.POTION, 1, 0, 10),
    )

    private val utilityItems = arrayListOf<ItemStack>(
        ItemStack(Material.FISHING_ROD),
        ItemStack(Material.LEATHER_HELMET),
        ItemStack(Material.LEATHER_CHESTPLATE),
        ItemStack(Material.CREEPER_SPAWN_EGG, 8),
        ItemStack(Material.TNT, 8),
        // ItemStack(Material.GOAT_HORN, 1, 0, 0),
        ItemStack(Material.WHEAT_SEEDS, 8),
        ItemStack(Material.OAK_SAPLING, 2),
        ItemStack(Material.BIRCH_SAPLING, 2),
        ItemStack(Material.BONE_MEAL, 32)
    )

}