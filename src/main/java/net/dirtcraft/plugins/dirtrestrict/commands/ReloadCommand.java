package net.dirtcraft.plugins.dirtrestrict.commands;

import net.dirtcraft.plugins.dirtrestrict.utils.Permissions;
import net.dirtcraft.plugins.dirtrestrict.utils.Strings;
import net.dirtcraft.plugins.dirtrestrict.utils.Utilities;
import org.bukkit.command.CommandSender;

public class ReloadCommand {
	public static boolean run(CommandSender sender, String[] args) {
		if (!sender.hasPermission(Permissions.RELOAD)) {
			sender.sendMessage(Strings.NO_PERMISSION);
			return true;
		}

		sender.sendMessage(Strings.RELOAD_DONE);
		Utilities.loadConfig();
		return true;
	}
}
