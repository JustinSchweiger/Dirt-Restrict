package net.dirtcraft.plugins.dirtrestrict.commands;

import net.dirtcraft.plugins.dirtrestrict.data.RestrictedItemManager;
import net.dirtcraft.plugins.dirtrestrict.database.DatabaseOperation;
import net.dirtcraft.plugins.dirtrestrict.utils.Permissions;
import net.dirtcraft.plugins.dirtrestrict.utils.Strings;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;

public class RemoveCommand {
	public static boolean run(CommandSender sender, String[] args) {
		if (!sender.hasPermission(Permissions.REMOVE)) {
			sender.sendMessage(Strings.NO_PERMISSION);
			return true;
		}

		if (args.length != 2) {
			sender.sendMessage(Strings.INVALID_ARGUMENTS_USAGE + "/dirtrestrict remove <restricted-item>");
			return true;
		}

		NamespacedKey itemToRemove = NamespacedKey.fromString(args[1]);

		if (!RestrictedItemManager.doesRestrictionExist(itemToRemove)) {
			sender.sendMessage(Strings.RESTRICTION_DOESNT_EXISTS.replace("{value}", itemToRemove.toString()));
			return true;
		}

		DatabaseOperation.removeRestriction(itemToRemove);
		RestrictedItemManager.removeRestriction(itemToRemove);

		sender.sendMessage(Strings.ITEM_RESTRICTION_REMOVED.replace("{material}", itemToRemove.toString()));

		return true;
	}

	public static boolean runMod(CommandSender sender, String[] args) {
		if (!sender.hasPermission(Permissions.REMOVE)) {
			sender.sendMessage(Strings.NO_PERMISSION);
			return true;
		}

		if (args.length != 2) {
			sender.sendMessage(Strings.INVALID_ARGUMENTS_USAGE + "/dirtrestrict removeMod <namespace>");
			return true;
		}

		String namespaceToRemove = args[1];

		if (!RestrictedItemManager.doesModRestrictionExist(namespaceToRemove)) {
			sender.sendMessage(Strings.RESTRICTION_DOESNT_EXISTS.replace("{value}", namespaceToRemove));
			return true;
		}

		DatabaseOperation.removeModRestriction(namespaceToRemove);
		RestrictedItemManager.removeModRestriction(namespaceToRemove);

		sender.sendMessage(Strings.MOD_RESTRICTION_REMOVED.replace("{namespace}", namespaceToRemove));

		return true;
	}
}
