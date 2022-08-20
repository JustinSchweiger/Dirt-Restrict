package net.dirtcraft.plugins.dirtrestrict.commands;

import net.dirtcraft.plugins.dirtrestrict.data.RestrictedItem;
import net.dirtcraft.plugins.dirtrestrict.data.RestrictedItemManager;
import net.dirtcraft.plugins.dirtrestrict.data.RestrictedMod;
import net.dirtcraft.plugins.dirtrestrict.utils.Permissions;
import net.dirtcraft.plugins.dirtrestrict.utils.Strings;
import net.dirtcraft.plugins.dirtrestrict.utils.Utilities;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class BannedItemsCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission(Permissions.BASE)) {
			sender.sendMessage(Strings.NO_PERMISSION);
			return true;
		}

		if (args.length == 1) {
			if (Utilities.isInteger(args[0])) {
				int page = Integer.parseInt(args[0]);
				sendBannedItemsList(sender, page);
			} else {
				sender.sendMessage(Strings.INVALID_ARGUMENTS_USAGE + "/banneditems [page]");
			}
		} else {
			sendBannedItemsList(sender, 1);
		}
		return true;
	}

	public static void sendBannedItemsList(CommandSender sender, int page) {
		if (page < 1) {
			sender.sendMessage(Strings.PAGE_DOES_NOT_EXIST);
			return;
		}

		List<RestrictedItem> restrictedItems = RestrictedItemManager.getRestrictedItems();
		List<RestrictedMod> restrictedMods = RestrictedItemManager.getRestrictedMods();
		List<Object> itemsAndMods = new ArrayList<>();
		itemsAndMods.addAll(restrictedItems);
		itemsAndMods.addAll(restrictedMods);

		if (itemsAndMods.size() == 0) {
			sender.sendMessage(Strings.NO_RESTRICTIONS_YET);
			return;
		}

		int maxPage = (int) Math.ceil((double) itemsAndMods.size() / (double) Utilities.config.general.listEntries);
		if (page > maxPage) {
			sender.sendMessage(Strings.PAGE_DOES_NOT_EXIST);
			return;
		}

		int start = (page - 1) * Utilities.config.general.listEntries;
		int end = page * Utilities.config.general.listEntries;
		if (end > itemsAndMods.size()) {
			end = itemsAndMods.size();
		}

		sender.sendMessage(Strings.BAR_TOP);
		sender.sendMessage("");

		for (int i = start; i < end; i++) {
			if (itemsAndMods.get(i) instanceof RestrictedItem) {
				RestrictedItem item = (RestrictedItem) itemsAndMods.get(i);
				BaseComponent[] removeRestriction = new ComponentBuilder("")
						.append(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "\u2715" + ChatColor.DARK_GRAY + "]")
						.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict remove " + item.getItem().toString()))
						.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.RED + "Delete this restriction completely."))).create();

				BaseComponent[] editRestriction = new ComponentBuilder("")
						.append(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "\u270E" + ChatColor.DARK_GRAY + "]")
						.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict edit " + item.getItem().toString()))
						.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GREEN + "Edit this restriction."))).create();

				String whitelistedWorlds = ChatColor.DARK_RED + "None";
				if (item.getWhitelistedWorlds().size() > 0) {
					whitelistedWorlds = ChatColor.GREEN + String.join(", ", item.getWhitelistedWorlds());
				}
				String reason = item.getReason();
				if (reason.length() == 0) {
					reason = "No reason provided";
				}
				String alternative = item.getAlternative();
				if (alternative.length() == 0) {
					alternative = "No alternative provided";
				}

				BaseComponent[] restrictedItem;

				if (sender.hasPermission(Permissions.BYPASS)) {
					restrictedItem = new ComponentBuilder("")
							.append(ChatColor.GOLD + item.getDisplayName() + ChatColor.DARK_AQUA + " - " + ChatColor.GRAY + reason)
							.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(
									ChatColor.DARK_AQUA + "\u2219  " + ChatColor.UNDERLINE + "General Info" + ChatColor.DARK_GRAY + ":" + "\n" +
											ChatColor.GOLD + "ID" + ChatColor.DARK_GRAY + ": " + ChatColor.RED + item.getItem().toString() + "\n" +
											ChatColor.GOLD + "Name" + ChatColor.DARK_GRAY + ": " + ChatColor.GREEN + item.getDisplayName() + "\n" +
											ChatColor.GOLD + "Allowed Worlds" + ChatColor.DARK_GRAY + ": " + whitelistedWorlds + "\n" +
											ChatColor.GOLD + "Bypass Permission" + ChatColor.DARK_GRAY + ": " + ChatColor.BLUE + "dirtrestrict.bypass.item." + item.getItem().getNamespace() + "." + item.getItem().getKey() + "\n" +
											"\n" +
											ChatColor.DARK_AQUA + "\u2219  " + ChatColor.UNDERLINE + "Banned Methods" + ChatColor.DARK_GRAY + ":" + "\n" +
											ChatColor.GOLD + "Break Banned" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + item.isBreakBanned() + "\n" +
											ChatColor.GOLD + "Place Banned" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + item.isPlaceBanned() + "\n" +
											ChatColor.GOLD + "Pickup Banned" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + item.isPickupBanned() + "\n" +
											ChatColor.GOLD + "Click Banned" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + item.isInventoryClickBanned() + "\n" +
											"\n" +
											ChatColor.DARK_AQUA + "\u2219  " + ChatColor.UNDERLINE + "Additional Info" + ChatColor.DARK_GRAY + ":" + "\n" +
											ChatColor.GOLD + "Reason" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + reason + "\n" +
											ChatColor.GOLD + "Alternative" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + alternative
							))).event(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, "dirtrestrict.bypass.item." + item.getItem().getNamespace() + "." + item.getItem().getKey())).create();
				} else {
					restrictedItem = new ComponentBuilder("")
							.append(ChatColor.GOLD + item.getDisplayName() + ChatColor.DARK_AQUA + " - " + ChatColor.GRAY + reason)
							.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(
									ChatColor.DARK_AQUA + "\u2219  " + ChatColor.UNDERLINE + "General Info" + ChatColor.DARK_GRAY + ":" + "\n" +
											ChatColor.GOLD + "ID" + ChatColor.DARK_GRAY + ": " + ChatColor.RED + item.getItem().toString() + "\n" +
											ChatColor.GOLD + "Name" + ChatColor.DARK_GRAY + ": " + ChatColor.GREEN + item.getDisplayName() + "\n" +
											ChatColor.GOLD + "Allowed Worlds" + ChatColor.DARK_GRAY + ": " + whitelistedWorlds + "\n" +
											"\n" +
											ChatColor.DARK_AQUA + "\u2219  " + ChatColor.UNDERLINE + "Banned Methods" + ChatColor.DARK_GRAY + ":" + "\n" +
											ChatColor.GOLD + "Break Banned" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + item.isBreakBanned() + "\n" +
											ChatColor.GOLD + "Place Banned" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + item.isPlaceBanned() + "\n" +
											ChatColor.GOLD + "Pickup Banned" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + item.isPickupBanned() + "\n" +
											ChatColor.GOLD + "Click Banned" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + item.isInventoryClickBanned() + "\n" +
											"\n" +
											ChatColor.DARK_AQUA + "\u2219  " + ChatColor.UNDERLINE + "Additional Info" + ChatColor.DARK_GRAY + ":" + "\n" +
											ChatColor.GOLD + "Reason" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + reason + "\n" +
											ChatColor.GOLD + "Alternative" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + alternative
							))).create();
				}

				ComponentBuilder entry = new ComponentBuilder("");

				if (sender.hasPermission(Permissions.REMOVE)) {
					entry.append(removeRestriction);
					entry.append(" ").event((HoverEvent) null).event((ClickEvent) null);
				}

				if (sender.hasPermission(Permissions.EDIT)) {
					entry.append(editRestriction);
				}

				entry.append(ChatColor.GRAY + " - ").event((HoverEvent) null).event((ClickEvent) null);
				entry.append(restrictedItem);

				sender.spigot().sendMessage(entry.create());
			} else {
				RestrictedMod mod = (RestrictedMod) itemsAndMods.get(i);
				BaseComponent[] removeRestriction = new ComponentBuilder("")
						.append(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "\u2715" + ChatColor.DARK_GRAY + "]")
						.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict removeMod " + mod.getNamespace()))
						.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.RED + "Delete this mods restrictions completely."))).create();

				BaseComponent[] editRestriction = new ComponentBuilder("")
						.append(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "\u270E" + ChatColor.DARK_GRAY + "]")
						.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict editMod " + mod.getNamespace()))
						.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GREEN + "Edit this mods restrictions."))).create();

				String whitelistedWorlds = ChatColor.DARK_RED + "None";
				if (mod.getWhitelistedWorlds().size() > 0) {
					whitelistedWorlds = ChatColor.GREEN + String.join(", ", mod.getWhitelistedWorlds());
				}
				String reason = mod.getReason();
				if (reason.length() == 0) {
					reason = "No reason provided";
				}
				String alternative = mod.getAlternative();
				if (alternative.length() == 0) {
					alternative = "No alternative provided";
				}

				BaseComponent[] restrictedItem;

				if (sender.hasPermission(Permissions.BYPASS)) {
					restrictedItem = new ComponentBuilder("")
							.append(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + "MOD" + ChatColor.DARK_GRAY + "] " + ChatColor.GOLD + mod.getDisplayName() + ChatColor.DARK_AQUA + " - " + ChatColor.GRAY + reason)
							.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(
									ChatColor.DARK_AQUA + "\u2219  " + ChatColor.UNDERLINE + "General Info" + ChatColor.DARK_GRAY + ":" + "\n" +
											ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + "MOD" + ChatColor.DARK_GRAY + "] " + ChatColor.GOLD + "Namespace" + ChatColor.DARK_GRAY + ": " + ChatColor.RED + mod.getNamespace() + "\n" +
											ChatColor.GOLD + "Name" + ChatColor.DARK_GRAY + ": " + ChatColor.GREEN + mod.getDisplayName() + "\n" +
											ChatColor.GOLD + "Allowed Worlds" + ChatColor.DARK_GRAY + ": " + whitelistedWorlds + "\n" +
											ChatColor.GOLD + "Bypass Permission" + ChatColor.DARK_GRAY + ": " + ChatColor.BLUE + "dirtrestrict.bypass.mod." + mod.getNamespace() + "\n" +
											"\n" +
											ChatColor.DARK_AQUA + "\u2219  " + ChatColor.UNDERLINE + "Banned Methods" + ChatColor.DARK_GRAY + ":" + "\n" +
											ChatColor.GOLD + "Break Banned" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + mod.isBreakBanned() + "\n" +
											ChatColor.GOLD + "Place Banned" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + mod.isPlaceBanned() + "\n" +
											ChatColor.GOLD + "Pickup Banned" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + mod.isPickupBanned() + "\n" +
											ChatColor.GOLD + "Click Banned" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + mod.isInventoryClickBanned() + "\n" +
											"\n" +
											ChatColor.DARK_AQUA + "\u2219  " + ChatColor.UNDERLINE + "Additional Info" + ChatColor.DARK_GRAY + ":" + "\n" +
											ChatColor.GOLD + "Reason" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + reason + "\n" +
											ChatColor.GOLD + "Alternative" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + alternative
							))).event(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, "dirtrestrict.bypass.mod." + mod.getNamespace())).create();
				} else {
					restrictedItem = new ComponentBuilder("")
							.append(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + "MOD" + ChatColor.DARK_GRAY + "] " + ChatColor.GOLD + mod.getDisplayName() + ChatColor.DARK_AQUA + " - " + ChatColor.GRAY + reason)
							.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(
									ChatColor.DARK_AQUA + "\u2219  " + ChatColor.UNDERLINE + "General Info" + ChatColor.DARK_GRAY + ":" + "\n" +
											ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + "MOD" + ChatColor.DARK_GRAY + "] " + ChatColor.GOLD + "Namespace" + ChatColor.DARK_GRAY + ": " + ChatColor.RED + mod.getNamespace() + "\n" +
											ChatColor.GOLD + "Name" + ChatColor.DARK_GRAY + ": " + ChatColor.GREEN + mod.getDisplayName() + "\n" +
											ChatColor.GOLD + "Allowed Worlds" + ChatColor.DARK_GRAY + ": " + whitelistedWorlds + "\n" +
											"\n" +
											ChatColor.DARK_AQUA + "\u2219  " + ChatColor.UNDERLINE + "Banned Methods" + ChatColor.DARK_GRAY + ":" + "\n" +
											ChatColor.GOLD + "Break Banned" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + mod.isBreakBanned() + "\n" +
											ChatColor.GOLD + "Place Banned" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + mod.isPlaceBanned() + "\n" +
											ChatColor.GOLD + "Pickup Banned" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + mod.isPickupBanned() + "\n" +
											ChatColor.GOLD + "Click Banned" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + mod.isInventoryClickBanned() + "\n" +
											"\n" +
											ChatColor.DARK_AQUA + "\u2219  " + ChatColor.UNDERLINE + "Additional Info" + ChatColor.DARK_GRAY + ":" + "\n" +
											ChatColor.GOLD + "Reason" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + reason + "\n" +
											ChatColor.GOLD + "Alternative" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + alternative
							))).create();
				}

				ComponentBuilder entry = new ComponentBuilder("");

				if (sender.hasPermission(Permissions.REMOVE)) {
					entry.append(removeRestriction);
					entry.append(" ").event((HoverEvent) null).event((ClickEvent) null);
				}

				if (sender.hasPermission(Permissions.EDIT)) {
					entry.append(editRestriction);
				}

				entry.append(ChatColor.GRAY + " - ").event((HoverEvent) null).event((ClickEvent) null);
				entry.append(restrictedItem);

				sender.spigot().sendMessage(entry.create());
			}
		}

		TextComponent bottomBar = new TextComponent(TextComponent.fromLegacyText(Utilities.format(Strings.HALF_BAR_ONE)));
		TextComponent pagePrev;
		if (page == 1) {
			pagePrev = new TextComponent(ChatColor.BLACK + " \u00AB");
			pagePrev.setBold(true);
			pagePrev.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.RED + "You are already on the first page!")));
		} else {
			pagePrev = new TextComponent(ChatColor.GREEN + " \u00AB");
			pagePrev.setBold(true);
			pagePrev.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GREEN + "Previous page")));
			pagePrev.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/banneditems " + (page - 1)));
		}
		bottomBar.addExtra(pagePrev);
		bottomBar.addExtra(ChatColor.DARK_AQUA + " " + page + ChatColor.GRAY + " / " + ChatColor.DARK_AQUA + maxPage + " ");
		TextComponent pageNext;
		if (page == maxPage) {
			pageNext = new TextComponent(ChatColor.BLACK + "\u00BB ");
			pageNext.setBold(true);
			pageNext.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.RED + "You are already on the last page!")));
		} else {
			pageNext = new TextComponent(ChatColor.GREEN + "\u00BB ");
			pageNext.setBold(true);
			pageNext.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GREEN + "Next page")));
			pageNext.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/banneditems " + (page + 1)));
		}
		bottomBar.addExtra(pageNext);
		bottomBar.addExtra(new TextComponent(TextComponent.fromLegacyText(Utilities.format(Strings.HALF_BAR_TWO))));
		sender.sendMessage("");
		sender.spigot().sendMessage(bottomBar);
	}
}
