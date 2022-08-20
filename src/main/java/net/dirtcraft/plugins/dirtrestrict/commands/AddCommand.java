package net.dirtcraft.plugins.dirtrestrict.commands;

import net.dirtcraft.plugins.dirtrestrict.data.RestrictedItem;
import net.dirtcraft.plugins.dirtrestrict.data.RestrictedItemManager;
import net.dirtcraft.plugins.dirtrestrict.data.RestrictedMod;
import net.dirtcraft.plugins.dirtrestrict.database.DatabaseOperation;
import net.dirtcraft.plugins.dirtrestrict.utils.Permissions;
import net.dirtcraft.plugins.dirtrestrict.utils.Strings;
import net.dirtcraft.plugins.dirtrestrict.utils.Utilities;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;

public class AddCommand {

	public static boolean run(CommandSender sender, String[] args) {
		if (!sender.hasPermission(Permissions.ADD)) {
			sender.sendMessage(Strings.NO_PERMISSION);
			return true;
		}

		Player player = (Player) sender;
		if (args.length < 2) {
			sender.sendMessage(Strings.INVALID_ARGUMENTS_USAGE + ChatColor.RED + "/dirtrestrict add <display-name>");
			return true;
		}

		StringBuilder builder = new StringBuilder();
		for (int i = 1; i < args.length; i++) {
			if (i == args.length - 1) {
				builder.append(ChatColor.stripColor(args[i]));
			} else {
				builder.append(ChatColor.stripColor(args[i])).append(" ");
			}
		}
		String displayName = builder.toString();
		NamespacedKey itemToRestrict = player.getInventory().getItemInMainHand().getType().getKey();

		if (itemToRestrict == NamespacedKey.fromString("minecraft:air")) {
			sender.sendMessage(Strings.NO_AIR);
			return true;
		}

		if (RestrictedItemManager.doesRestrictionExist(itemToRestrict)) {
			sender.sendMessage(Strings.RESTRICTION_EXISTS.replace("{material}", itemToRestrict.toString()));
			return true;
		}

		addRestrictionForItem(sender, itemToRestrict, displayName);

		return true;
	}

	public static boolean runMod(CommandSender sender, String[] args) {
		if (!sender.hasPermission(Permissions.ADD)) {
			sender.sendMessage(Strings.NO_PERMISSION);
			return true;
		}

		if (args.length < 3) {
			sender.sendMessage(Strings.INVALID_ARGUMENTS_USAGE + ChatColor.RED + "/dirtrestrict addMod <namespace> <display-name>");
			return true;
		}

		if (!Utilities.getAllNamespaces().contains(args[1])) {
			sender.sendMessage(Strings.INVALID_NAMESPACE.replace("{namespace}", args[1]));
			return true;
		}

		StringBuilder builder = new StringBuilder();
		for (int i = 2; i < args.length; i++) {
			if (i == args.length - 1) {
				builder.append(ChatColor.stripColor(args[i]));
			} else {
				builder.append(ChatColor.stripColor(args[i])).append(" ");
			}
		}
		String displayName = builder.toString();

		addRestrictionForMod(sender, args[1], displayName);

		return true;
	}

	private static void addRestrictionForItem(CommandSender sender, NamespacedKey itemToRestrict, String displayName) {
		RestrictedItemManager.addRestrictionForItem(new RestrictedItem(itemToRestrict, displayName, "", "", new ArrayList<>(), true, true, true, true));
		DatabaseOperation.addRestrictionForItem(itemToRestrict, displayName, () -> {
			sender.sendMessage(Strings.ITEM_RESTRICTION_ADDED.replace("{material}", itemToRestrict.toString()));
		});
	}

	private static void addRestrictionForMod(CommandSender sender, String namespace, String displayName) {
		RestrictedItemManager.addRestrictionForMod(new RestrictedMod(namespace, displayName, "", "", new ArrayList<>(), true, true, true, true));
		DatabaseOperation.addRestrictionForMod(namespace, displayName, () -> {
			sender.sendMessage(Strings.MOD_RESTRICTION_ADDED.replace("{namespace}", namespace));
		});
	}
}
