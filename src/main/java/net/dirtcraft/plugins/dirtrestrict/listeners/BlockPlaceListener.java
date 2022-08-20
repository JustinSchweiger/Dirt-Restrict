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
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		NamespacedKey block = event.getBlock().getType().getKey();
		Player player = event.getPlayer();

		if (RestrictedItemManager.doesRestrictionExist(block) || RestrictedItemManager.doesModRestrictionExist(block.getNamespace())) {
			if (RestrictedItemManager.isPlaceBanned(block) || RestrictedItemManager.isPlaceBanned(block.getNamespace())) {
				if (!RestrictedItemManager.isWhitelistedInWorld(block, player.getWorld().getName()) && !RestrictedItemManager.isWhitelistedInWorld(block.getNamespace(), player.getWorld().getName())) {
					if (player.hasPermission("dirtrestrict.bypass.item." + block.getNamespace() + "." + block.getKey()) || player.hasPermission("dirtrestrict.bypass.mod." + block.getNamespace()) || RestrictedItemManager.isBypassing(player.getUniqueId())) {
						player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Strings.BYPASSING));
						return;
					}

					player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Strings.RESTRICTED_BLOCK));
					Utilities.playErrorSound(player);
					player.getInventory().setItem(player.getInventory().getHeldItemSlot(), null);
					event.setCancelled(true);
				}
			}
		}
	}
}
