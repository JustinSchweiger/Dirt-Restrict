package net.dirtcraft.plugins.dirtrestrict.utils;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;

public class Strings {
	// ---------------------------------------------------------- GENERAL ----------------------------------------------------------
	public static final String PREFIX = ChatColor.GRAY + "[" + ChatColor.RED + "Dirt" + ChatColor.DARK_RED + "Restrict" + ChatColor.GRAY + "] ";
	public static final String INTERNAL_PREFIX = ChatColor.GRAY + "[" + ChatColor.RED + "Dirt" + ChatColor.DARK_RED + "Restrict" + ChatColor.GRAY + "] ";
	public static final String NO_PERMISSION = PREFIX + ChatColor.RED + "You do not have permission to use this command.";
	public static final String NO_CONSOLE = PREFIX + ChatColor.RED + "You must be a player to use this command.";
	public static final String INVALID_ARGUMENTS_USAGE = PREFIX + ChatColor.DARK_RED + "Invalid arguments.\n" + ChatColor.GOLD + "Usage" + ChatColor.GRAY + ": " + ChatColor.RED;
	public static final String NO_AIR = PREFIX + ChatColor.RED + "You must hold an item in your hand!";
	public static final String RELOAD_DONE = PREFIX + ChatColor.GREEN + "Config reloaded.";
	public static final String RESTRICTED_BLOCK = ChatColor.RED + "This block is restricted!";
	public static final String RESTRICTED_ITEM = ChatColor.RED + "This item is restricted!";
	public static final String BYPASSING_RESTRICTION = PREFIX + ChatColor.GRAY + "Bypassing restriction on this block/item.";
	public static final String UNKNOWN_COMMAND = PREFIX + ChatColor.RED + "Unknown command. Type " + ChatColor.DARK_RED + "/dirtrestrict" + ChatColor.RED + " for a list of available commands.";
	public static final String BAR_TOP = Utilities.format("&x&f&b&7&9&0&0&m-&x&f&b&7&1&0&0&m-&x&f&a&6&9&0&0&m-&x&f&a&6&1&0&0&m-&x&f&a&5&9&0&0&m-&x&f&a&5&1&0&0&m-&x&f&9&4&9&0&0&m-&x&f&9&4&1&0&0&m-&x&f&9&3&8&0&0&m-&x&f&9&3&0&0&0&m-&x&f&8&2&8&0&0&m-&x&f&8&2&0&0&0&m-&x&f&8&1&8&0&0&m-&x&f&8&1&0&0&0&m-&x&f&7&0&8&0&0&m-&x&f&7&0&0&0&0&m-" + ChatColor.GRAY + "[" + ChatColor.BOLD + " " + ChatColor.RED + "DirtCraft " + ChatColor.DARK_RED + "Restrict" + ChatColor.BOLD + " " + ChatColor.GRAY + "]" + "&x&f&7&0&0&0&0&m-&x&f&7&0&8&0&0&m-&x&f&8&1&0&0&0&m-&x&f&8&1&8&0&0&m-&x&f&8&2&0&0&0&m-&x&f&8&2&8&0&0&m-&x&f&9&3&0&0&0&m-&x&f&9&3&8&0&0&m-&x&f&9&4&1&0&0&m-&x&f&9&4&9&0&0&m-&x&f&a&5&1&0&0&m-&x&f&a&5&9&0&0&m-&x&f&a&6&1&0&0&m-&x&f&a&6&9&0&0&m-&x&f&b&7&1&0&0&m-&x&f&b&7&9&0&0&m-");
	public static final String BAR_BOTTOM = Utilities.format("&x&f&b&7&9&0&0&m-&x&f&b&7&4&0&0&m-&x&f&b&6&f&0&0&m-&x&f&b&6&a&0&0&m-&x&f&a&6&5&0&0&m-&x&f&a&6&0&0&0&m-&x&f&a&5&b&0&0&m-&x&f&a&5&6&0&0&m-&x&f&a&5&1&0&0&m-&x&f&a&4&c&0&0&m-&x&f&9&4&7&0&0&m-&x&f&9&4&2&0&0&m-&x&f&9&3&d&0&0&m-&x&f&9&3&7&0&0&m-&x&f&9&3&2&0&0&m-&x&f&9&2&d&0&0&m-&x&f&8&2&8&0&0&m-&x&f&8&2&3&0&0&m-&x&f&8&1&e&0&0&m-&x&f&8&1&9&0&0&m-&x&f&8&1&4&0&0&m-&x&f&8&0&f&0&0&m-&x&f&7&0&a&0&0&m-&x&f&7&0&5&0&0&m-&x&f&7&0&0&0&0&m-&x&f&7&0&0&0&0&m-&x&f&7&0&5&0&0&m-&x&f&7&0&a&0&0&m-&x&f&8&0&f&0&0&m-&x&f&8&1&4&0&0&m-&x&f&8&1&9&0&0&m-&x&f&8&1&e&0&0&m-&x&f&8&2&3&0&0&m-&x&f&8&2&8&0&0&m-&x&f&9&2&d&0&0&m-&x&f&9&3&2&0&0&m-&x&f&9&3&7&0&0&m-&x&f&9&3&d&0&0&m-&x&f&9&4&2&0&0&m-&x&f&9&4&7&0&0&m-&x&f&a&4&c&0&0&m-&x&f&a&5&1&0&0&m-&x&f&a&5&6&0&0&m-&x&f&a&5&b&0&0&m-&x&f&a&6&0&0&0&m-&x&f&a&6&5&0&0&m-&x&f&b&6&a&0&0&m-&x&f&b&6&f&0&0&m-&x&f&b&7&4&0&0&m-&x&f&b&7&9&0&0&m-");
	public static final String HALF_BAR_ONE = "&x&f&b&7&9&0&0&m-&x&f&b&7&3&0&0&m-&x&f&b&6&c&0&0&m-&x&f&b&6&6&0&0&m-&x&f&b&6&0&0&0&m-&x&f&b&5&9&0&0&m-&x&f&b&5&3&0&0&m-&x&f&b&4&c&0&0&m-&x&f&b&4&6&0&0&m-&x&f&b&4&0&0&0&m-&x&f&b&3&9&0&0&m-&x&f&b&3&3&0&0&m-&x&f&b&2&d&0&0&m-&x&f&b&2&6&0&0&m-&x&f&b&2&0&0&0&m-&x&f&b&1&9&0&0&m-&x&f&b&1&3&0&0&m-&x&f&b&0&d&0&0&m-&x&f&b&0&6&0&0&m-&x&f&b&0&0&0&0&m-";
	public static final String HALF_BAR_TWO = Utilities.format("&x&f&b&0&0&0&0&m-&x&f&b&0&6&0&0&m-&x&f&b&0&d&0&0&m-&x&f&b&1&3&0&0&m-&x&f&b&1&9&0&0&m-&x&f&b&2&0&0&0&m-&x&f&b&2&6&0&0&m-&x&f&b&2&d&0&0&m-&x&f&b&3&3&0&0&m-&x&f&b&3&9&0&0&m-&x&f&b&4&0&0&0&m-&x&f&b&4&6&0&0&m-&x&f&b&4&c&0&0&m-&x&f&b&5&3&0&0&m-&x&f&b&5&9&0&0&m-&x&f&b&6&0&0&0&m-&x&f&b&6&6&0&0&m-&x&f&b&6&c&0&0&m-&x&f&b&7&3&0&0&m-&x&f&b&7&9&0&0&m-");

	// ---------------------------------------------------------- ADD COMMAND ----------------------------------------------------------
	public static final String RESTRICTION_EXISTS = PREFIX + ChatColor.RED + "There is already a restriction set for " + ChatColor.GOLD + "{material}" + ChatColor.RED + ".";
	public static final String ITEM_RESTRICTION_ADDED = PREFIX + ChatColor.GRAY + "Restriction added for " + ChatColor.GOLD + "{material}" + ChatColor.GRAY + ".";
	public static final String MOD_RESTRICTION_ADDED = PREFIX + ChatColor.GRAY + "Restriction added for mod " + ChatColor.GOLD + "{namespace}" + ChatColor.GRAY + ".";
	public static final String INVALID_NAMESPACE = PREFIX + ChatColor.RED + "Invalid namespace " + ChatColor.DARK_AQUA + "{namespace}" + ChatColor.RED + ".";

	// ---------------------------------------------------------- LIST COMMAND ----------------------------------------------------------
	public static final String PAGE_DOES_NOT_EXIST = PREFIX + ChatColor.RED + "This page does not exist!";
	public static final String NO_RESTRICTIONS_YET = PREFIX + ChatColor.RED + "There are no restricted items/mods yet!";

	// ---------------------------------------------------------- LIST COMMAND ----------------------------------------------------------
	public static final String RESTRICTION_DOESNT_EXISTS = PREFIX + ChatColor.RED + "There is no restriction for " + ChatColor.GOLD + "{value}" + ChatColor.RED + ".";

	// ---------------------------------------------------------- EDIT COMMAND ----------------------------------------------------------
	public static final String NOT_A_VALID_WORLD = PREFIX + ChatColor.RED + "This world is not valid! Please use the tab-completion!";
	public static final String DISPLAY_NAME_SET = PREFIX + ChatColor.GRAY + "Display name set to " + ChatColor.GOLD + "{displayName}" + ChatColor.GRAY + ".";
	public static final String ALTERNATIVE_SET = PREFIX + ChatColor.GRAY + "Updated alternative.";
	public static final String REASON_SET = PREFIX + ChatColor.GRAY + "Updated reason.";
	public static final String UPDATED_METHOD = PREFIX + ChatColor.GRAY + "Updated {action} to " + ChatColor.GOLD + "{value}" + ChatColor.GRAY + ".";
	public static final String ADDED_WHITELISTED_WORLD = PREFIX + ChatColor.GRAY + "Added world " + ChatColor.GOLD + "{world}" + ChatColor.GRAY + " to whitelisted worlds.";
	public static final String REMOVED_WHITELISTED_WORLD = PREFIX + ChatColor.GRAY + "Removed world " + ChatColor.GOLD + "{world}" + ChatColor.GRAY + " from whitelisted worlds.";
	public static final String WORLD_ALREADY_WHITELISTED = PREFIX + ChatColor.RED + "World " + ChatColor.GOLD + "{world}" + ChatColor.RED + " is already whitelisted!";
	public static final String WORLD_NOT_WHITELISTED = PREFIX + ChatColor.RED + "World " + ChatColor.GOLD + "{world}" + ChatColor.RED + " is not yet whitelisted!";

	// ---------------------------------------------------------- REMOVE COMMAND ----------------------------------------------------------
	public static final String ITEM_RESTRICTION_REMOVED = PREFIX + ChatColor.GRAY + "Restriction removed for " + ChatColor.GOLD + "{material}" + ChatColor.GRAY + ".";
	public static final String MOD_RESTRICTION_REMOVED = PREFIX + ChatColor.GRAY + "Restriction removed for mod " + ChatColor.GOLD + "{namespace}" + ChatColor.GRAY + ".";

	// ---------------------------------------------------------- BYPASS COMMAND ----------------------------------------------------------
	public static final String BYPASS_DISABLED = PREFIX + ChatColor.GRAY + "Bypass is now " + ChatColor.RED + "OFF" + ChatColor.GRAY + ".";
	public static final String BYPASS_ENABLED = PREFIX + ChatColor.GRAY + "Bypass is now " + ChatColor.GREEN + "ON" + ChatColor.GRAY + ".";
	public static final String BYPASSING = ChatColor.GREEN + "You are bypassing the restriction on this item!";
}
