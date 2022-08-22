package net.dirtcraft.plugins.dirtrestrict.listeners;

import net.dirtcraft.plugins.dirtrestrict.data.RestrictedItemManager;
import net.dirtcraft.plugins.dirtrestrict.utils.Strings;
import net.dirtcraft.plugins.dirtrestrict.utils.Utilities;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

public class ItemHoldListener implements Listener {
	@EventHandler
	public void onPlayItemHold(PlayerItemHeldEvent event) {
		ItemStack item = event.getPlayer().getInventory().getItem(event.getNewSlot());
		if (item == null) {
			return;
		}
		NamespacedKey block = item.getType().getKey();
		Player player = event.getPlayer();

		if (RestrictedItemManager.doesRestrictionExist(block) || RestrictedItemManager.doesModRestrictionExist(block.getNamespace())) {
			if (RestrictedItemManager.isHoldBanned(block) || RestrictedItemManager.isHoldBanned(block.getNamespace())) {
				if (!RestrictedItemManager.isWhitelistedInWorld(block, player.getWorld().getName()) && !RestrictedItemManager.isWhitelistedInWorld(block.getNamespace(), player.getWorld().getName())) {
					if (player.hasPermission("dirtrestrict.bypass.item." + block.getNamespace() + "." + block.getKey()) || player.hasPermission("dirtrestrict.bypass.mod." + block.getNamespace()) || RestrictedItemManager.isBypassing(player.getUniqueId())) {
						player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Strings.BYPASSING));
						return;
					}

					player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Strings.RESTRICTED_ITEM));
					Utilities.playErrorSound(player);
					player.getInventory().setItem(event.getNewSlot(), null);
					event.setCancelled(true);
				}
			}
		}
	}
}
