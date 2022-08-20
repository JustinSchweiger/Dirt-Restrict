package net.dirtcraft.plugins.dirtrestrict.commands;

import net.dirtcraft.plugins.dirtrestrict.data.RestrictedItemManager;
import net.dirtcraft.plugins.dirtrestrict.utils.Permissions;
import net.dirtcraft.plugins.dirtrestrict.utils.Strings;
import net.dirtcraft.plugins.dirtrestrict.utils.Utilities;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BaseCommand implements CommandExecutor, TabCompleter {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission(Permissions.BASE)) {
			sender.sendMessage(Strings.NO_PERMISSION);
			return true;
		}

		if (args.length == 0) {
			List<TextComponent> listings = getListings(sender);
			sender.sendMessage(Strings.BAR_TOP);
			sender.sendMessage("");
			for (TextComponent listing : listings) {
				sender.spigot().sendMessage(listing);
			}
			sender.sendMessage("");
			sender.sendMessage(Strings.BAR_BOTTOM);

			return true;
		}

		if (args[0].equalsIgnoreCase("list")) {
			int page = 1;
			if (args.length == 2 && Utilities.isInteger(args[1])) {
				page = Integer.parseInt(args[1]);
			}

			BannedItemsCommand.sendBannedItemsList(sender, page);
			return true;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(Strings.NO_CONSOLE);
			return false;
		}

		switch (args[0].toLowerCase()) {
			case "add":
				return AddCommand.run(sender, args);
			case "addmod":
				return AddCommand.runMod(sender, args);
			case "remove":
				return RemoveCommand.run(sender, args);
			case "removemod":
				return RemoveCommand.runMod(sender, args);
			case "edit":
				return EditCommand.run(sender, args);
			case "editmod":
				return EditCommand.runMod(sender, args);
			case "bypass":
				return BypassCommand.run(sender);
			case "reload":
				return ReloadCommand.run(sender, args);
			default:
				sender.sendMessage(Strings.UNKNOWN_COMMAND);
				return false;
		}
	}

	private List<TextComponent> getListings(CommandSender sender) {
		List<TextComponent> listings = new ArrayList<>();

		if (sender.hasPermission(Permissions.ADD)) {
			TextComponent add = new TextComponent(ChatColor.GOLD + "  /dirtrestrict " + ChatColor.YELLOW + "add <display-name>");
			add.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Ban the item you are currently holding in your hand.")));
			add.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dirtrestrict add <display-name>"));
			listings.add(add);

			TextComponent addMod = new TextComponent(ChatColor.GOLD + "  /dirtrestrict " + ChatColor.YELLOW + "addMod <namespace> <display-name>");
			addMod.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Bans all items from the given namespace.")));
			addMod.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dirtrestrict addMod <namespace> <display-name>"));
			listings.add(addMod);
		}

		if (sender.hasPermission(Permissions.REMOVE)) {
			TextComponent remove = new TextComponent(ChatColor.GOLD + "  /dirtrestrict " + ChatColor.YELLOW + "remove <restricted-item>");
			remove.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Removes the item from the list of restricted items.")));
			remove.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dirtrestrict remove <restricted-item>"));
			listings.add(remove);

			TextComponent removeMod = new TextComponent(ChatColor.GOLD + "  /dirtrestrict " + ChatColor.YELLOW + "removeMod <namespace>");
			removeMod.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Removes the mod from the list of restricted items.")));
			removeMod.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dirtrestrict removeMod <namespace>"));
			listings.add(removeMod);
		}

		if (sender.hasPermission(Permissions.EDIT)) {
			TextComponent edit = new TextComponent(ChatColor.GOLD + "  /dirtrestrict " + ChatColor.YELLOW + "edit <restricted-item> <option> <value>");
			edit.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Edits a configurable option of a restricted item.")));
			edit.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dirtrestrict edit <restricted-item> <option> <value>"));
			listings.add(edit);

			TextComponent editMod = new TextComponent(ChatColor.GOLD + "  /dirtrestrict " + ChatColor.YELLOW + "editMod <namespace> <option> <value>");
			editMod.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Edits a configurable option of a restricted mod.")));
			editMod.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dirtrestrict editMod <namespace> <option> <value>"));
			listings.add(editMod);
		}

		if (sender.hasPermission(Permissions.LIST)) {
			TextComponent list = new TextComponent(ChatColor.GOLD + "  /dirtrestrict " + ChatColor.YELLOW + "list [page]");
			list.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Lists all restricted items.")));
			list.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dirtrestrict list [page]"));
			listings.add(list);
		}

		if (sender.hasPermission(Permissions.BYPASS)) {
			TextComponent bypass = new TextComponent(ChatColor.GOLD + "  /dirtrestrict " + ChatColor.YELLOW + "bypass");
			bypass.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Bypasses the restrictions.")));
			bypass.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dirtrestrict bypass"));
			listings.add(bypass);
		}

		if (sender.hasPermission(Permissions.RELOAD)) {
			TextComponent reload = new TextComponent(ChatColor.GOLD + "  /dirtrestrict " + ChatColor.YELLOW + "reload");
			reload.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Reloads the config.")));
			reload.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dirtrestrict reload"));
			listings.add(reload);
		}

		return listings;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> arguments = new ArrayList<>();

		if (args.length == 1) {
			if (sender.hasPermission(Permissions.ADD)) {
				arguments.add("add");
				arguments.add("addMod");
			}

			if (sender.hasPermission(Permissions.LIST)) {
				arguments.add("list");
			}

			if (sender.hasPermission(Permissions.BYPASS)) {
				arguments.add("bypass");
			}

			if (sender.hasPermission(Permissions.REMOVE)) {
				arguments.add("remove");
				arguments.add("removeMod");
			}

			if (sender.hasPermission(Permissions.EDIT)) {
				arguments.add("edit");
				arguments.add("editMod");
			}

			if (sender.hasPermission(Permissions.RELOAD)) {
				arguments.add("reload");
			}
		} else if (args.length >= 2 && args[0].equalsIgnoreCase("add") && sender.hasPermission(Permissions.ADD)) {
			arguments.add("<display-name>");
		} else if (args.length == 2 && args[0].equalsIgnoreCase("addMod") && sender.hasPermission(Permissions.ADD)) {
			arguments.addAll(Utilities.getAllNamespaces());
		} else if (args.length >= 3 && args[0].equalsIgnoreCase("addMod") && sender.hasPermission(Permissions.ADD)) {
			arguments.add("<display-name>");
		} else if (args.length == 2 && args[0].equalsIgnoreCase("list") && sender.hasPermission(Permissions.LIST)) {
			arguments.add("[page]");
		} else if (args.length == 2 && args[0].equalsIgnoreCase("edit") && sender.hasPermission(Permissions.EDIT)) {
			arguments.addAll(RestrictedItemManager.getRestrictedItemsAsString());
		} else if (args.length == 3 && args[0].equalsIgnoreCase("edit") && sender.hasPermission(Permissions.EDIT)) {
			arguments.addAll(EditCommand.getOptions());
		} else if (args.length > 3 && args[0].equalsIgnoreCase("edit") && args[2].equalsIgnoreCase("setDisplayName") && sender.hasPermission(Permissions.EDIT)) {
			arguments.add("<display-name>");
		} else if (args.length > 3 && args[0].equalsIgnoreCase("edit") && args[2].equalsIgnoreCase("addAllowedWorld") && sender.hasPermission(Permissions.EDIT)) {
			arguments.addAll(RestrictedItemManager.getNotWhitelistedWorlds(args[1], false));
		} else if (args.length > 3 && args[0].equalsIgnoreCase("edit") && args[2].equalsIgnoreCase("removeAllowedWorld") && sender.hasPermission(Permissions.EDIT)) {
			arguments.addAll(RestrictedItemManager.getAllWhitelistedWorlds(args[1], false));
		} else if (args.length > 3 && args[0].equalsIgnoreCase("edit") && (args[2].equalsIgnoreCase("setBreakBanned") || args[3].equalsIgnoreCase("setPlaceBanned") || args[3].equalsIgnoreCase("setPickupBanned") || args[3].equalsIgnoreCase("setClickBanned")) && sender.hasPermission(Permissions.EDIT)) {
			arguments.add("true");
			arguments.add("false");
		} else if (args.length > 3 && args[0].equalsIgnoreCase("edit") && args[2].equalsIgnoreCase("setReason") && sender.hasPermission(Permissions.EDIT)) {
			arguments.add("<reason>");
		} else if (args.length > 3 && args[0].equalsIgnoreCase("edit") && args[2].equalsIgnoreCase("setAlternative") && sender.hasPermission(Permissions.EDIT)) {
			arguments.add("<alternative>");
		} else if (args.length == 2 && args[0].equalsIgnoreCase("editMod") && sender.hasPermission(Permissions.EDIT)) {
			arguments.addAll(RestrictedItemManager.getRestrictedModsAsString());
		} else if (args.length == 3 && args[0].equalsIgnoreCase("editMod") && sender.hasPermission(Permissions.EDIT)) {
			arguments.addAll(EditCommand.getOptions());
		} else if (args.length > 3 && args[0].equalsIgnoreCase("editMod") && args[2].equalsIgnoreCase("setDisplayName") && sender.hasPermission(Permissions.EDIT)) {
			arguments.add("<display-name>");
		} else if (args.length > 3 && args[0].equalsIgnoreCase("editMod") && args[2].equalsIgnoreCase("addAllowedWorld") && sender.hasPermission(Permissions.EDIT)) {
			arguments.addAll(RestrictedItemManager.getNotWhitelistedWorlds(args[1], true));
		} else if (args.length > 3 && args[0].equalsIgnoreCase("editMod") && args[2].equalsIgnoreCase("removeAllowedWorld") && sender.hasPermission(Permissions.EDIT)) {
			arguments.addAll(RestrictedItemManager.getAllWhitelistedWorlds(args[1], true));
		} else if (args.length > 3 && args[0].equalsIgnoreCase("editMod") && (args[2].equalsIgnoreCase("setBreakBanned") || args[3].equalsIgnoreCase("setPlaceBanned") || args[3].equalsIgnoreCase("setPickupBanned") || args[3].equalsIgnoreCase("setClickBanned")) && sender.hasPermission(Permissions.EDIT)) {
			arguments.add("true");
			arguments.add("false");
		} else if (args.length > 3 && args[0].equalsIgnoreCase("editMod") && args[2].equalsIgnoreCase("setReason") && sender.hasPermission(Permissions.EDIT)) {
			arguments.add("<reason>");
		} else if (args.length > 3 && args[0].equalsIgnoreCase("editMod") && args[2].equalsIgnoreCase("setAlternative") && sender.hasPermission(Permissions.EDIT)) {
			arguments.add("<alternative>");
		} else if (args.length == 2 && args[0].equalsIgnoreCase("remove") && sender.hasPermission(Permissions.REMOVE)) {
			arguments.addAll(RestrictedItemManager.getRestrictedItemsAsString());
		} else if (args.length == 2 && args[0].equalsIgnoreCase("removeMod") && sender.hasPermission(Permissions.REMOVE)) {
			arguments.addAll(RestrictedItemManager.getRestrictedModsAsString());
		}

		List<String> tabResults = new ArrayList<>();
		for (String argument : arguments) {
			if (argument.contains("display-name") || argument.contains("page") || argument.contains("restricted-items") || argument.contains("option") || argument.contains("value") || argument.contains("reason") || argument.contains("alternative")) {
				tabResults.add(argument);
				continue;
			}

			if (argument.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
				tabResults.add(argument);
			}
		}

		return tabResults;
	}
}
