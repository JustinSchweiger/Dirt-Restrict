package net.dirtcraft.plugins.dirtrestrict.commands;

import net.dirtcraft.plugins.dirtrestrict.data.RestrictedItem;
import net.dirtcraft.plugins.dirtrestrict.data.RestrictedItemManager;
import net.dirtcraft.plugins.dirtrestrict.data.RestrictedMod;
import net.dirtcraft.plugins.dirtrestrict.database.DatabaseOperation;
import net.dirtcraft.plugins.dirtrestrict.utils.Permissions;
import net.dirtcraft.plugins.dirtrestrict.utils.Strings;
import net.dirtcraft.plugins.dirtrestrict.utils.Utilities;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class EditCommand {
	private static final List<String> options = Arrays.asList("setDisplayName", "addAllowedWorld", "removeAllowedWorld", "setBreakBanned", "setPlaceBanned", "setPickupBanned", "setClickBanned", "setHoldBanned", "setUseBanned", "setReason", "setAlternative");

	public static List<String> getOptions() {
		return options;
	}

	public static boolean run(CommandSender sender, String[] args) {
		if (!sender.hasPermission(Permissions.EDIT)) {
			sender.sendMessage(Strings.NO_PERMISSION);
			return true;
		}

		if (args.length == 2) {
			NamespacedKey item = NamespacedKey.fromString(args[1]);
			if (item == null) {
				sender.sendMessage(Strings.INVALID_ARGUMENTS_USAGE + ChatColor.RED + "/dirtrestrict edit <id>");
				return true;
			}

			RestrictedItem restrictedItem = RestrictedItemManager.getRestrictedItem(item);
			showEditMenuForItems(sender, restrictedItem);
			return true;
		}

		if (args.length <= 3 || !options.contains(args[2]) || NamespacedKey.fromString(args[1]) == null) {
			sender.sendMessage(Strings.INVALID_ARGUMENTS_USAGE + ChatColor.RED + "/dirtrestrict edit <id> <option> <value>");
			return true;
		}

		if ((args[2].equalsIgnoreCase("addAllowedWorld") || args[2].equalsIgnoreCase("removeAllowedWorld")) && !Utilities.isWorld(args[3])) {
			sender.sendMessage(Strings.NOT_A_VALID_WORLD);
			return true;
		}

		if (args[2].equalsIgnoreCase("setBreakBanned") || args[2].equalsIgnoreCase("setPlaceBanned") || args[2].equalsIgnoreCase("setPickupBanned") || args[2].equalsIgnoreCase("setClickBanned") || args[2].equalsIgnoreCase("setHoldBanned")) {
			if (!args[3].equalsIgnoreCase("true") && !args[3].equalsIgnoreCase("false")) {
				sender.sendMessage(Strings.INVALID_ARGUMENTS_USAGE + ChatColor.RED + "/dirtrestrict edit <id> <option> <value>");
				return true;
			}
		}

		String value = args[1];
		String option = args[2].toLowerCase();
		switch (option) {
			case "setdisplayname":
				setDisplayName(sender, value, args, false);
				break;
			case "addallowedworld":
				addAllowedWorld(sender, value, args, false);
				break;
			case "removeallowedworld":
				removeAllowedWorld(sender, value, args, false);
				break;
			case "setbreakbanned":
				setBreakBanned(sender, value, args, false);
				break;
			case "setplacebanned":
				setPlaceBanned(sender, value, args, false);
				break;
			case "setpickupbanned":
				setPickupBanned(sender, value, args, false);
				break;
			case "setclickbanned":
				setClickBanned(sender, value, args, false);
				break;
			case "setholdbanned":
				setHoldBanned(sender, value, args, false);
				break;
			case "setusebanned":
				setUseBanned(sender, value, args, false);
				break;
			case "setreason":
				setReason(sender, value, args, false);
				break;
			case "setalternative":
				setAlternative(sender, value, args, false);
				break;
		}

		return true;
	}

	public static boolean runMod(CommandSender sender, String[] args) {
		if (!sender.hasPermission(Permissions.EDIT)) {
			sender.sendMessage(Strings.NO_PERMISSION);
			return true;
		}

		if (args.length == 2) {
			String namespace = args[1];
			if (!Utilities.getAllNamespaces().contains(namespace)) {
				sender.sendMessage(Strings.INVALID_ARGUMENTS_USAGE + ChatColor.RED + "/dirtrestrict editMod <namespace>");
				return true;
			}

			RestrictedMod restrictedMod = RestrictedItemManager.getRestrictedMod(namespace);
			showEditMenuForMods(sender, restrictedMod);
			return true;
		}

		if (args.length <= 3 || !options.contains(args[2]) || !Utilities.getAllNamespaces().contains(args[1])) {
			sender.sendMessage(Strings.INVALID_ARGUMENTS_USAGE + ChatColor.RED + "/dirtrestrict editMod <namespace> <option> <value>");
			return true;
		}

		if ((args[2].equalsIgnoreCase("addAllowedWorld") || args[2].equalsIgnoreCase("removeAllowedWorld")) && !Utilities.isWorld(args[3])) {
			sender.sendMessage(Strings.NOT_A_VALID_WORLD);
			return true;
		}

		if (args[2].equalsIgnoreCase("setBreakBanned") || args[2].equalsIgnoreCase("setPlaceBanned") || args[2].equalsIgnoreCase("setPickupBanned") || args[2].equalsIgnoreCase("setClickBanned") || args[2].equalsIgnoreCase("setHoldBanned")) {
			if (!args[3].equalsIgnoreCase("true") && !args[3].equalsIgnoreCase("false")) {
				sender.sendMessage(Strings.INVALID_ARGUMENTS_USAGE + ChatColor.RED + "/dirtrestrict editMod <id> <option> <value>");
				return true;
			}
		}

		String namespace = args[1];
		String option = args[2].toLowerCase();
		switch (option) {
			case "setdisplayname":
				setDisplayName(sender, namespace, args, true);
				break;
			case "addallowedworld":
				addAllowedWorld(sender, namespace, args, true);
				break;
			case "removeallowedworld":
				removeAllowedWorld(sender, namespace, args, true);
				break;
			case "setholdbanned":
				setHoldBanned(sender, namespace, args, true);
				break;
			case "setusebanned":
				setUseBanned(sender, namespace, args, true);
				break;
			case "setbreakbanned":
				setBreakBanned(sender, namespace, args, true);
				break;
			case "setplacebanned":
				setPlaceBanned(sender, namespace, args, true);
				break;
			case "setpickupbanned":
				setPickupBanned(sender, namespace, args, true);
				break;
			case "setclickbanned":
				setClickBanned(sender, namespace, args, true);
				break;
			case "setreason":
				setReason(sender, namespace, args, true);
				break;
			case "setalternative":
				setAlternative(sender, namespace, args, true);
				break;
		}

		return true;
	}

	private static void setAlternative(CommandSender sender, String value, String[] args, boolean isMod) {
		StringBuilder builder = new StringBuilder();
		for (int i = 3; i < args.length; i++) {
			if (i == args.length - 1) {
				builder.append(ChatColor.stripColor(args[i]));
			} else {
				builder.append(ChatColor.stripColor(args[i])).append(" ");
			}
		}
		String alternative = builder.toString();

		DatabaseOperation.updateAlternative(value, alternative, isMod, () -> {
			sender.sendMessage(Strings.ALTERNATIVE_SET);
			RestrictedItemManager.updateAlternative(value, alternative, isMod);
		});
	}

	private static void setReason(CommandSender sender, String value, String[] args, boolean isMod) {
		StringBuilder builder = new StringBuilder();
		for (int i = 3; i < args.length; i++) {
			if (i == args.length - 1) {
				builder.append(ChatColor.stripColor(args[i]));
			} else {
				builder.append(ChatColor.stripColor(args[i])).append(" ");
			}
		}
		String reason = builder.toString();

		DatabaseOperation.updateReason(value, reason, isMod, () -> {
			sender.sendMessage(Strings.REASON_SET);
			RestrictedItemManager.updateReason(value, reason, isMod);
		});
	}

	private static void setClickBanned(CommandSender sender, String value, String[] args, boolean isMod) {
		boolean clickBanned = args[3].equalsIgnoreCase("true");
		DatabaseOperation.updateClickBanned(value, clickBanned, isMod, () -> {
			sender.sendMessage(Strings.UPDATED_METHOD.replace("{action}", "\"click banned\"").replace("{value}", String.valueOf(clickBanned)));
			RestrictedItemManager.updateClickBanned(value, clickBanned, isMod);
		});
	}

	private static void setHoldBanned(CommandSender sender, String value, String[] args, boolean isMod) {
		boolean holdBanned = args[3].equalsIgnoreCase("true");
		DatabaseOperation.updateHoldBanned(value, holdBanned, isMod, () -> {
			sender.sendMessage(Strings.UPDATED_METHOD.replace("{action}", "\"hold banned\"").replace("{value}", String.valueOf(holdBanned)));
			RestrictedItemManager.updateHoldBanned(value, holdBanned, isMod);
		});
	}

	private static void setUseBanned(CommandSender sender, String value, String[] args, boolean isMod) {
		boolean useBanned = args[3].equalsIgnoreCase("true");
		DatabaseOperation.updateUseBanned(value, useBanned, isMod, () -> {
			sender.sendMessage(Strings.UPDATED_METHOD.replace("{action}", "\"use banned\"").replace("{value}", String.valueOf(useBanned)));
			RestrictedItemManager.updateUseBanned(value, useBanned, isMod);
		});
	}

	private static void setPickupBanned(CommandSender sender, String value, String[] args, boolean isMod) {
		boolean pickupBanned = args[3].equalsIgnoreCase("true");
		DatabaseOperation.updatePickupBanned(value, pickupBanned, isMod, () -> {
			sender.sendMessage(Strings.UPDATED_METHOD.replace("{action}", "\"pickup banned\"").replace("{value}", String.valueOf(pickupBanned)));
			RestrictedItemManager.updatePickupBanned(value, pickupBanned, isMod);
		});
	}

	private static void setPlaceBanned(CommandSender sender, String value, String[] args, boolean isMod) {
		boolean placeBanned = args[3].equalsIgnoreCase("true");
		DatabaseOperation.updatePlaceBanned(value, placeBanned, isMod, () -> {
			sender.sendMessage(Strings.UPDATED_METHOD.replace("{action}", "\"place banned\"").replace("{value}", String.valueOf(placeBanned)));
			RestrictedItemManager.updatePlaceBanned(value, placeBanned, isMod);
		});
	}

	private static void setBreakBanned(CommandSender sender, String value, String[] args, boolean isMod) {
		boolean breakBanned = args[3].equalsIgnoreCase("true");
		DatabaseOperation.updateBreakBanned(value, breakBanned, isMod, () -> {
			sender.sendMessage(Strings.UPDATED_METHOD.replace("{action}", "\"break banned\"").replace("{value}", String.valueOf(breakBanned)));
			RestrictedItemManager.updateBreakBanned(value, breakBanned, isMod);
		});
	}

	private static void addAllowedWorld(CommandSender sender, String value, String[] args, boolean isMod) {
		String world = args[3];
		if (RestrictedItemManager.isWorldWhitelisted(world, value, isMod)) {
			sender.sendMessage(Strings.WORLD_ALREADY_WHITELISTED.replace("{world}", world));
			return;
		}

		DatabaseOperation.addAllowedWorld(value, world, isMod, () -> {
			sender.sendMessage(Strings.ADDED_WHITELISTED_WORLD.replace("{world}", world));
			RestrictedItemManager.addAllowedWorld(value, world, isMod);
		});
	}

	private static void removeAllowedWorld(CommandSender sender, String value, String[] args, boolean isMod) {
		String world = args[3];
		if (!RestrictedItemManager.isWorldWhitelisted(world, value, isMod)) {
			sender.sendMessage(Strings.WORLD_NOT_WHITELISTED.replace("{world}", world));
			return;
		}

		DatabaseOperation.removeAllowedWorld(value, world, isMod, () -> {
			sender.sendMessage(Strings.REMOVED_WHITELISTED_WORLD.replace("{world}", world));
			RestrictedItemManager.removeAllowedWorld(value, world, isMod);
		});
	}

	private static void setDisplayName(CommandSender sender, String value, String[] args, boolean isMod) {
		StringBuilder builder = new StringBuilder();
		for (int i = 3; i < args.length; i++) {
			if (i == args.length - 1) {
				builder.append(ChatColor.stripColor(args[i]));
			} else {
				builder.append(ChatColor.stripColor(args[i])).append(" ");
			}
		}
		String displayName = builder.toString();

		DatabaseOperation.updateDisplayName(value, displayName, isMod, () -> {
			sender.sendMessage(Strings.DISPLAY_NAME_SET.replace("{displayName}", displayName));
			RestrictedItemManager.updateDisplayName(value, displayName, isMod);
		});
	}

	public static void showEditMenuForItems(CommandSender sender, RestrictedItem restrictedItem) {
		String whitelistedWorlds = ChatColor.DARK_RED + "None";
		if (restrictedItem.getWhitelistedWorlds().size() > 0) {
			whitelistedWorlds = ChatColor.YELLOW + String.join(", ", restrictedItem.getWhitelistedWorlds());
		}
		String reason = restrictedItem.getReason();
		if (reason.length() == 0) {
			reason = "No reason provided";
		}
		String alternative = restrictedItem.getAlternative();
		if (alternative.length() == 0) {
			alternative = "No alternative provided";
		}

		BaseComponent[] id = new ComponentBuilder("")
				.append(ChatColor.GOLD + "ID" + ChatColor.DARK_GRAY + ": " + ChatColor.RED + restrictedItem.getItem())
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.RED + "The Material of the block/item. This cannot be changed!"))).create();

		BaseComponent[] displayName = new ComponentBuilder("")
				.append(ChatColor.GOLD + "Name" + ChatColor.DARK_GRAY + ": " + ChatColor.GREEN + restrictedItem.getDisplayName())
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GREEN + "Click to change the name of the item!")))
				.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dirtrestrict edit " + restrictedItem.getItem() + " setDisplayName <display-name>")).create();

		ComponentBuilder addAllowedWorld = new ComponentBuilder("")
				.append(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "\u002B" + ChatColor.DARK_GRAY + "]")
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GREEN + "Add a world.")))
				.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dirtrestrict edit " + restrictedItem.getItem() + " addAllowedWorld <world>"));

		ComponentBuilder removeAllowedWorld = new ComponentBuilder("")
				.append(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "\u2212" + ChatColor.DARK_GRAY + "]")
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.RED + "Remove a world.")))
				.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dirtrestrict edit " + restrictedItem.getItem() + " removeAllowedWorld <world>"));

		ComponentBuilder allowedWorlds = new ComponentBuilder("")
				.append(ChatColor.GOLD + "Allowed Worlds" + ChatColor.DARK_GRAY + ": " + whitelistedWorlds)
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.YELLOW + "Click the " + ChatColor.GREEN + "\u002B" + ChatColor.YELLOW + " or " + ChatColor.RED + "\u2212" + ChatColor.YELLOW + " to add/remove worlds.")));

		BaseComponent[] worlds = new ComponentBuilder("")
				.append(addAllowedWorld.create())
				.append(" ")
				.event((ClickEvent) null)
				.event((HoverEvent) null)
				.append(removeAllowedWorld.create())
				.append(" ")
				.event((ClickEvent) null)
				.event((HoverEvent) null)
				.append(allowedWorlds.create())
				.create();

		ComponentBuilder breakBannedComponent = new ComponentBuilder("")
				.append(ChatColor.GOLD + "Break Banned" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + restrictedItem.isBreakBanned())
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Click to toggle whether the item/block can be broken!")));
		if (restrictedItem.isBreakBanned()) {
			breakBannedComponent.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict edit " + restrictedItem.getItem() + " setBreakBanned false"));
		} else {
			breakBannedComponent.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict edit " + restrictedItem.getItem() + " setBreakBanned true"));
		}
		BaseComponent[] breakBanned = breakBannedComponent.create();

		ComponentBuilder placeBannedComponent = new ComponentBuilder("")
				.append(ChatColor.GOLD + "Place Banned" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + restrictedItem.isPlaceBanned())
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Click to toggle whether the item/block can be placed!")));
		if (restrictedItem.isPlaceBanned()) {
			placeBannedComponent.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict edit " + restrictedItem.getItem() + " setPlaceBanned false"));
		} else {
			placeBannedComponent.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict edit " + restrictedItem.getItem() + " setPlaceBanned true"));
		}
		BaseComponent[] placeBanned = placeBannedComponent.create();

		ComponentBuilder pickupBannedComponent = new ComponentBuilder("")
				.append(ChatColor.GOLD + "Pickup Banned" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + restrictedItem.isPickupBanned())
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Click to toggle whether the item/block can be picked up!")));
		if (restrictedItem.isPickupBanned()) {
			pickupBannedComponent.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict edit " + restrictedItem.getItem() + " setPickupBanned false"));
		} else {
			pickupBannedComponent.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict edit " + restrictedItem.getItem() + " setPickupBanned true"));
		}
		BaseComponent[] pickupBanned = pickupBannedComponent.create();

		ComponentBuilder clickBannedComponent = new ComponentBuilder("")
				.append(ChatColor.GOLD + "Click Banned" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + restrictedItem.isInventoryClickBanned())
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Click to toggle whether the item/block can be clicked in an inventory!")));
		if (restrictedItem.isInventoryClickBanned()) {
			clickBannedComponent.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict edit " + restrictedItem.getItem() + " setInventoryClickBanned false"));
		} else {
			clickBannedComponent.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict edit " + restrictedItem.getItem() + " setInventoryClickBanned true"));
		}
		BaseComponent[] clickBanned = clickBannedComponent.create();

		ComponentBuilder useBannedComponent = new ComponentBuilder("")
				.append(ChatColor.GOLD + "Use Banned" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + restrictedItem.isUseBanned())
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Click to toggle whether the item/block can be used!")));
		if (restrictedItem.isUseBanned()) {
			useBannedComponent.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict edit " + restrictedItem.getItem() + " setUseBanned false"));
		} else {
			useBannedComponent.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict edit " + restrictedItem.getItem() + " setUseBanned true"));
		}
		BaseComponent[] useBanned = useBannedComponent.create();

		ComponentBuilder holdBannedComponent = new ComponentBuilder("")
				.append(ChatColor.GOLD + "Hold Banned" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + restrictedItem.isHoldBanned())
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Click to toggle whether the item/block can be held!")));
		if (restrictedItem.isHoldBanned()) {
			holdBannedComponent.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict edit " + restrictedItem.getItem() + " setHoldBanned false"));
		} else {
			holdBannedComponent.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict edit " + restrictedItem.getItem() + " setHoldBanned true"));
		}
		BaseComponent[] holdBanned = holdBannedComponent.create();

		BaseComponent[] reasonComponent = new ComponentBuilder("")
				.append(ChatColor.GOLD + "Reason" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + reason)
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GREEN + "Click to change the reason the item/block is banned!")))
				.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dirtrestrict edit " + restrictedItem.getItem() + " setReason <reason>")).create();

		BaseComponent[] alternativeComponent = new ComponentBuilder("")
				.append(ChatColor.GOLD + "Alternative" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + alternative)
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GREEN + "Click to change the alternative hint for item/block!")))
				.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dirtrestrict edit " + restrictedItem.getItem() + " setAlternative <alternative>")).create();

		sender.sendMessage(Strings.BAR_TOP);
		sender.sendMessage("");
		sender.spigot().sendMessage(id);
		sender.spigot().sendMessage(displayName);
		sender.spigot().sendMessage(worlds);
		sender.sendMessage("");
		sender.spigot().sendMessage(breakBanned);
		sender.spigot().sendMessage(placeBanned);
		sender.spigot().sendMessage(pickupBanned);
		sender.spigot().sendMessage(clickBanned);
		sender.spigot().sendMessage(holdBanned);
		sender.spigot().sendMessage(useBanned);
		sender.sendMessage("");
		sender.spigot().sendMessage(reasonComponent);
		sender.spigot().sendMessage(alternativeComponent);
		sender.sendMessage("");
		sender.sendMessage(Strings.BAR_BOTTOM);
	}

	public static void showEditMenuForMods(CommandSender sender, RestrictedMod restrictedMod) {
		String whitelistedWorlds = ChatColor.DARK_RED + "None";
		if (restrictedMod.getWhitelistedWorlds().size() > 0) {
			whitelistedWorlds = ChatColor.GREEN + String.join(", ", restrictedMod.getWhitelistedWorlds());
		}
		String reason = restrictedMod.getReason();
		if (reason.length() == 0) {
			reason = "No reason provided";
		}
		String alternative = restrictedMod.getAlternative();
		if (alternative.length() == 0) {
			alternative = "No alternative provided";
		}

		BaseComponent[] namespace = new ComponentBuilder("")
				.append(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + "MOD" + ChatColor.DARK_GRAY + "] " + ChatColor.GOLD + "Namespace" + ChatColor.DARK_GRAY + ": " + ChatColor.RED + restrictedMod.getNamespace())
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.RED + "The namespace of the mod. This cannot be changed!"))).create();

		BaseComponent[] displayName = new ComponentBuilder("")
				.append(ChatColor.GOLD + "Name" + ChatColor.DARK_GRAY + ": " + ChatColor.RED + restrictedMod.getDisplayName())
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GREEN + "Click to change the name of the mod!")))
				.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dirtrestrict editMod " + restrictedMod.getNamespace() + " setDisplayName <display-name>")).create();

		ComponentBuilder addAllowedWorld = new ComponentBuilder("")
				.append(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "\u002B" + ChatColor.DARK_GRAY + "]")
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GREEN + "Add a world.")))
				.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dirtrestrict editMod " + restrictedMod.getNamespace() + " addAllowedWorld <world>"));

		ComponentBuilder removeAllowedWorld = new ComponentBuilder("")
				.append(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "\u2212" + ChatColor.DARK_GRAY + "]")
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.RED + "Remove a world.")))
				.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dirtrestrict editMod " + restrictedMod.getNamespace() + " removeAllowedWorld <world>"));

		ComponentBuilder allowedWorlds = new ComponentBuilder("")
				.append(ChatColor.GOLD + "Allowed Worlds" + ChatColor.DARK_GRAY + ": " + whitelistedWorlds)
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.YELLOW + "Click the " + ChatColor.GREEN + "\u002B" + ChatColor.YELLOW + " or " + ChatColor.RED + "\u2212" + ChatColor.YELLOW + " to add/remove worlds.")));

		BaseComponent[] worlds = new ComponentBuilder("")
				.append(addAllowedWorld.create())
				.append(" ")
				.event((ClickEvent) null)
				.event((HoverEvent) null)
				.append(removeAllowedWorld.create())
				.append(" ")
				.event((ClickEvent) null)
				.event((HoverEvent) null)
				.append(allowedWorlds.create())
				.create();

		ComponentBuilder breakBannedComponent = new ComponentBuilder("")
				.append(ChatColor.GOLD + "Break Banned" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + restrictedMod.isBreakBanned())
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Click to toggle whether items/blocks from the mod can be broken!")));
		if (restrictedMod.isBreakBanned()) {
			breakBannedComponent.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict editMod " + restrictedMod.getNamespace() + " setBreakBanned false"));
		} else {
			breakBannedComponent.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict editMod " + restrictedMod.getNamespace() + " setBreakBanned true"));
		}
		BaseComponent[] breakBanned = breakBannedComponent.create();

		ComponentBuilder placeBannedComponent = new ComponentBuilder("")
				.append(ChatColor.GOLD + "Place Banned" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + restrictedMod.isPlaceBanned())
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Click to toggle whether the items/blocks can be placed!")));
		if (restrictedMod.isPlaceBanned()) {
			placeBannedComponent.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict editMod " + restrictedMod.getNamespace() + " setPlaceBanned false"));
		} else {
			placeBannedComponent.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict editMod " + restrictedMod.getNamespace() + " setPlaceBanned true"));
		}
		BaseComponent[] placeBanned = placeBannedComponent.create();

		ComponentBuilder pickupBannedComponent = new ComponentBuilder("")
				.append(ChatColor.GOLD + "Pickup Banned" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + restrictedMod.isPickupBanned())
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Click to toggle whether the items/blocks can be picked up!")));
		if (restrictedMod.isPickupBanned()) {
			pickupBannedComponent.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict editMod " + restrictedMod.getNamespace() + " setPickupBanned false"));
		} else {
			pickupBannedComponent.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict editMod " + restrictedMod.getNamespace() + " setPickupBanned true"));
		}
		BaseComponent[] pickupBanned = pickupBannedComponent.create();

		ComponentBuilder clickBannedComponent = new ComponentBuilder("")
				.append(ChatColor.GOLD + "Click Banned" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + restrictedMod.isInventoryClickBanned())
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Click to toggle whether the items/blocks can be clicked in an inventory!")));
		if (restrictedMod.isInventoryClickBanned()) {
			clickBannedComponent.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict editMod " + restrictedMod.getNamespace() + " setInventoryClickBanned false"));
		} else {
			clickBannedComponent.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict editMod " + restrictedMod.getNamespace() + " setInventoryClickBanned true"));
		}
		BaseComponent[] clickBanned = clickBannedComponent.create();

		ComponentBuilder useBannedComponent = new ComponentBuilder("")
				.append(ChatColor.GOLD + "Use Banned" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + restrictedMod.isUseBanned())
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Click to toggle whether the items/blocks can be used!")));
		if (restrictedMod.isUseBanned()) {
			useBannedComponent.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict editMod " + restrictedMod.getNamespace() + " setUseBanned false"));
		} else {
			useBannedComponent.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict editMod " + restrictedMod.getNamespace() + " setUseBanned true"));
		}
		BaseComponent[] useBanned = useBannedComponent.create();

		ComponentBuilder holdBannedComponent = new ComponentBuilder("")
				.append(ChatColor.GOLD + "Hold Banned" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + restrictedMod.isHoldBanned())
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Click to toggle whether the items/blocks can be held!")));
		if (restrictedMod.isHoldBanned()) {
			holdBannedComponent.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict editMod " + restrictedMod.getNamespace() + " setHoldBanned false"));
		} else {
			holdBannedComponent.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrestrict editMod " + restrictedMod.getNamespace() + " setHoldBanned true"));
		}
		BaseComponent[] holdBanned = holdBannedComponent.create();

		BaseComponent[] reasonComponent = new ComponentBuilder("")
				.append(ChatColor.GOLD + "Reason" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + reason)
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GREEN + "Click to change the reason the mod is banned!")))
				.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dirtrestrict editMod " + restrictedMod.getNamespace() + " setReason <reason>")).create();

		BaseComponent[] alternativeComponent = new ComponentBuilder("")
				.append(ChatColor.GOLD + "Alternative" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + alternative)
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GREEN + "Click to change the alternative hint for the mod!")))
				.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dirtrestrict editMod " + restrictedMod.getNamespace() + " setAlternative <alternative>")).create();

		sender.sendMessage(Strings.BAR_TOP);
		sender.sendMessage("");
		sender.spigot().sendMessage(namespace);
		sender.spigot().sendMessage(displayName);
		sender.spigot().sendMessage(worlds);
		sender.sendMessage("");
		sender.spigot().sendMessage(breakBanned);
		sender.spigot().sendMessage(placeBanned);
		sender.spigot().sendMessage(pickupBanned);
		sender.spigot().sendMessage(clickBanned);
		sender.spigot().sendMessage(holdBanned);
		sender.spigot().sendMessage(useBanned);
		sender.sendMessage("");
		sender.spigot().sendMessage(reasonComponent);
		sender.spigot().sendMessage(alternativeComponent);
		sender.sendMessage("");
		sender.sendMessage(Strings.BAR_BOTTOM);
	}
}
