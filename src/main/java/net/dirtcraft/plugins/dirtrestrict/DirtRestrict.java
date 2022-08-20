package net.dirtcraft.plugins.dirtrestrict;

import net.dirtcraft.plugins.dirtrestrict.data.RestrictedItemManager;
import net.dirtcraft.plugins.dirtrestrict.database.Database;
import net.dirtcraft.plugins.dirtrestrict.utils.Utilities;
import org.bukkit.plugin.java.JavaPlugin;

public final class DirtRestrict extends JavaPlugin {

	private static DirtRestrict plugin;
	public static DirtRestrict getPlugin() {
		return plugin;
	}

	@Override
	public void onEnable() {
		plugin = this;
		Utilities.loadConfig();
		Database.initialiseDatabase();
		RestrictedItemManager.init();
		Utilities.registerCommands();
		Utilities.registerListener();
	}

	@Override
	public void onDisable() {
		Database.closeDatabase();
	}
}
