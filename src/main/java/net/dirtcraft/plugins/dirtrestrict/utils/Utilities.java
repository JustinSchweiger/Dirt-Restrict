package net.dirtcraft.plugins.dirtrestrict.utils;

import com.moandjiezana.toml.Toml;
import net.dirtcraft.plugins.dirtrestrict.DirtRestrict;
import net.dirtcraft.plugins.dirtrestrict.commands.BannedItemsCommand;
import net.dirtcraft.plugins.dirtrestrict.commands.BaseCommand;
import net.dirtcraft.plugins.dirtrestrict.config.Config;
import net.dirtcraft.plugins.dirtrestrict.listeners.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Utilities {
	public static Config config;

	public static void loadConfig() {
		if (!DirtRestrict.getPlugin().getDataFolder().exists()) {
			DirtRestrict.getPlugin().getDataFolder().mkdirs();
		}
		File file = new File(DirtRestrict.getPlugin().getDataFolder(), "config.toml");
		if (!file.exists()) {
			try {
				Files.copy(DirtRestrict.getPlugin().getResource("config.toml"), file.toPath());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		config = new Toml(new Toml().read(DirtRestrict.getPlugin().getResource("config.toml"))).read(file).to(Config.class);
	}

	public static void registerListener() {
		DirtRestrict.getPlugin().getServer().getPluginManager().registerEvents(new BlockBreakListener(), DirtRestrict.getPlugin());
		DirtRestrict.getPlugin().getServer().getPluginManager().registerEvents(new BlockPlaceListener(), DirtRestrict.getPlugin());
		DirtRestrict.getPlugin().getServer().getPluginManager().registerEvents(new InventoryClickListener(), DirtRestrict.getPlugin());
		DirtRestrict.getPlugin().getServer().getPluginManager().registerEvents(new PickupListener(), DirtRestrict.getPlugin());
		DirtRestrict.getPlugin().getServer().getPluginManager().registerEvents(new ItemHoldListener(), DirtRestrict.getPlugin());
		DirtRestrict.getPlugin().getServer().getPluginManager().registerEvents(new PlayerInteractListener(), DirtRestrict.getPlugin());
	}

	public static void registerCommands() {
		DirtRestrict.getPlugin().getCommand("dirtrestrict").setExecutor(new BaseCommand());
		DirtRestrict.getPlugin().getCommand("dirtrestrict").setTabCompleter(new BaseCommand());
		DirtRestrict.getPlugin().getCommand("banneditems").setExecutor(new BannedItemsCommand());
	}

	public static void log(Level level, String msg) {
		String consoleMessage;
		if (Level.INFO.equals(level)) {
			consoleMessage = Strings.INTERNAL_PREFIX + ChatColor.WHITE + msg;
		} else if (Level.WARNING.equals(level)) {
			consoleMessage = Strings.INTERNAL_PREFIX + ChatColor.YELLOW + msg;
		} else if (Level.SEVERE.equals(level)) {
			consoleMessage = Strings.INTERNAL_PREFIX + ChatColor.RED + msg;
		} else {
			consoleMessage = Strings.INTERNAL_PREFIX + ChatColor.GRAY + msg;
		}

		if (!config.general.coloredDebug) {
			consoleMessage = ChatColor.stripColor(msg);
		}

		DirtRestrict.getPlugin().getServer().getConsoleSender().sendMessage(consoleMessage);
	}

	public static HashSet<String> getAllNamespaces() {
		HashSet<String> namespaces = new HashSet<>();
		for (Material material : Material.values()) {
			namespaces.add(material.getKey().getNamespace());
		}

		return namespaces;
	}

	public static void disablePlugin() {
		DirtRestrict.getPlugin().getServer().getPluginManager().disablePlugin(DirtRestrict.getPlugin());
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException | NullPointerException e) {
			return false;
		}

		return true;
	}

	public static String format(String message) {
		return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', message);
	}

	public static List<String> getAllWorlds() {
		return DirtRestrict.getPlugin().getServer().getWorlds().stream().map(World::getName).collect(Collectors.toList());
	}

	public static boolean isWorld(String arg) {
		return getAllWorlds().contains(arg);
	}

	public static void playErrorSound(CommandSender sender) {
		if (!(sender instanceof Player)) {
			return;
		}

		Player player = (Player) sender;
		if (Utilities.config.sound.playErrorSound) {
			String sound = Utilities.config.sound.errorSound;
			if (sound == null) {
				sound = "minecraft:entity.creeper.death";
			}
			player.playSound(player.getLocation(), sound, 1, 1);
		}
	}
}
