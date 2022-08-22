package net.dirtcraft.plugins.dirtrestrict.commands;

import net.dirtcraft.plugins.dirtrestrict.DirtRestrict;
import net.dirtcraft.plugins.dirtrestrict.data.RestrictedItem;
import net.dirtcraft.plugins.dirtrestrict.data.RestrictedItemManager;
import net.dirtcraft.plugins.dirtrestrict.data.RestrictedMod;
import net.dirtcraft.plugins.dirtrestrict.utils.Permissions;
import net.dirtcraft.plugins.dirtrestrict.utils.Strings;
import net.dirtcraft.plugins.dirtrestrict.utils.Utilities;
import net.dirtcraft.plugins.dirtrestrict.utils.gradient.GradientHandler;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SearchCommand {
	private static HashMap<Location, Material> foundBlocks;

	public static boolean run(CommandSender sender, String[] args) {
		if (!sender.hasPermission(Permissions.SEARCH)) {
			sender.sendMessage(Strings.NO_PERMISSION);
			return true;
		}

		if (args.length == 2 && args[1].equalsIgnoreCase("run")) {
			foundBlocks = null;
			runSearch(sender);
		} else if (args.length == 2 && !Utilities.isInteger(args[1])) {
			sender.sendMessage(Strings.INVALID_ARGUMENTS_USAGE + "/dirtrestrict search [page]");
			return true;
		}

		if (args.length == 2 && Utilities.isInteger(args[1])) {
			if (foundBlocks == null) {
				sender.sendMessage(Strings.NO_SEARCH_IN_PROGRESS);
				return true;
			}

			int page = Integer.parseInt(args[1]);
			showResults(sender, page);
		} else if (args.length == 1) {
			showResults(sender, 1);
		}

		return true;
	}

	private static void runSearch(CommandSender sender) {
		Player player = (Player) sender;
		final List<Chunk> loadedChunks = new ArrayList<>(Arrays.asList(player.getWorld().getLoadedChunks()));
		final List<RestrictedItem> restrictedItems = new ArrayList<>(RestrictedItemManager.getRestrictedItems());
		final List<RestrictedMod> restrictedMods = new ArrayList<>(RestrictedItemManager.getRestrictedMods());

		sender.sendMessage(Strings.SEARCH_STARTED);
		Bukkit.getScheduler().runTaskAsynchronously(DirtRestrict.getPlugin(), () -> {
			final List<Material> bannedBlocks = new ArrayList<>();
			final HashMap<Location, Material> found = new HashMap<>();

			for (RestrictedMod mod : restrictedMods) {
				for (Material item : Material.values()) {
					if (item.getKey().getNamespace().equalsIgnoreCase(mod.getNamespace())) {
						if (item.isBlock()) {
							bannedBlocks.add(item);
						}
					}
				}
			}

			for (RestrictedItem restrictedItem : restrictedItems) {
				for (Material item : Material.values()) {
					if (item.getKey().toString().equalsIgnoreCase(restrictedItem.getItem().toString())) {
						if (item.isBlock()) {
							bannedBlocks.add(item);
						}
					}
				}
			}

			for (Chunk chunk : loadedChunks) {
				for (int x = 0; x < 16; x++) {
					for (int z = 0; z < 16; z++) {
						int maxY = chunk.getWorld().getHighestBlockYAt(x, z);
						for (int y = 0; y <= maxY; y++) {
							Material material = chunk.getBlock(x, y, z).getType();
							if (bannedBlocks.contains(material)) {
								found.put(chunk.getBlock(x, y, z).getLocation(), material);
							}
						}
					}
				}
			}

			if (found.isEmpty()) {
				sender.sendMessage(Strings.SEARCH_NO_RESULTS);
				return;
			}

			Bukkit.getScheduler().runTask(DirtRestrict.getPlugin(), () -> {
				foundBlocks = found;
				sender.sendMessage(Strings.SEARCH_COMPLETE.replace("{count}", String.valueOf(found.size())));
			});
		});
	}

	private static void showResults(CommandSender sender, int page) {
		if (foundBlocks == null) {
			sender.sendMessage(Strings.NO_SEARCH_IN_PROGRESS);
			return;
		}

		int maxPage = (int) Math.ceil((double) foundBlocks.size() / (double) Utilities.config.general.listEntries);
		if (page > maxPage) {
			sender.sendMessage(Strings.PAGE_DOES_NOT_EXIST);
			return;
		}

		int start = (page - 1) * Utilities.config.general.listEntries;
		int end = page * Utilities.config.general.listEntries;
		if (end > foundBlocks.size()) {
			end = foundBlocks.size();
		}

		sender.sendMessage(Strings.BAR_TOP);
		sender.sendMessage("");

		for (int i = start; i < end; i++) {
			Location loc = foundBlocks.keySet().toArray(new Location[0])[i];
			Material material = foundBlocks.get(loc);
			BaseComponent[] tpToBlock = new ComponentBuilder("")
					.append(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "TP" + ChatColor.DARK_GRAY + "]")
					.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/minecraft:tp %s %s %s", loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())))
					.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.BLUE + "Teleport to this block."))).create();

			BaseComponent[] block = new ComponentBuilder(ChatColor.GRAY + material.getKey().toString()).create();

			BaseComponent[] entry = new ComponentBuilder("").append(tpToBlock).append(ChatColor.GRAY + " - ").event((HoverEvent) null).event((ClickEvent) null).append(block).create();
			sender.spigot().sendMessage(entry);
		}

		TextComponent bottomBar = new TextComponent(TextComponent.fromLegacyText(GradientHandler.hsvGradient("-----------------------", new java.awt.Color(251, 121, 0), new java.awt.Color(247, 0, 0), GradientHandler::linear, net.md_5.bungee.api.ChatColor.STRIKETHROUGH)));
		TextComponent pagePrev;
		if (page == 1) {
			pagePrev = new TextComponent(ChatColor.GRAY + "  \u25C0 ");
			pagePrev.setBold(true);
			pagePrev.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.RED + "You are already on the first page!")));
		} else {
			pagePrev = new TextComponent(ChatColor.GREEN + "  \u25C0 ");
			pagePrev.setBold(true);
			pagePrev.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GREEN + "Previous page")));
			pagePrev.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict search " + (page - 1)));
		}
		bottomBar.addExtra(pagePrev);
		TextComponent pageNext;
		if (page == maxPage) {
			pageNext = new TextComponent(ChatColor.GRAY + " \u25B6  ");
			pageNext.setBold(true);
			pageNext.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.RED + "You are already on the last page!")));
		} else {
			pageNext = new TextComponent(ChatColor.GREEN + " \u25B6  ");
			pageNext.setBold(true);
			pageNext.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GREEN + "Next page")));
			pageNext.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict search " + (page + 1)));
		}
		bottomBar.addExtra(pageNext);
		bottomBar.addExtra(new TextComponent(TextComponent.fromLegacyText(GradientHandler.hsvGradient("-----------------------", new java.awt.Color(247, 0, 0), new java.awt.Color(251, 121, 0), GradientHandler::linear, net.md_5.bungee.api.ChatColor.STRIKETHROUGH))));
		sender.sendMessage("");
		sender.spigot().sendMessage(bottomBar);
	}
}
