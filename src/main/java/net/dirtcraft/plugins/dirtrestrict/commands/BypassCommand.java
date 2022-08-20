package net.dirtcraft.plugins.dirtrestrict.commands;

import net.dirtcraft.plugins.dirtrestrict.data.RestrictedItemManager;
import net.dirtcraft.plugins.dirtrestrict.utils.Permissions;
import net.dirtcraft.plugins.dirtrestrict.utils.Strings;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BypassCommand {
	public static boolean run(CommandSender sender) {
		if (!sender.hasPermission(Permissions.BYPASS)) {
			sender.sendMessage(Strings.NO_PERMISSION);
			return true;
		}

		Player player = (Player) sender;

		if (RestrictedItemManager.isBypassing(player.getUniqueId())) {
			RestrictedItemManager.removeBypass(player.getUniqueId());
			sender.sendMessage(Strings.BYPASS_DISABLED);
		} else {
			RestrictedItemManager.addBypass(player.getUniqueId());
			sender.sendMessage(Strings.BYPASS_ENABLED);
		}

		return true;
	}
}
