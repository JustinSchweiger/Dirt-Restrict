package net.dirtcraft.plugins.dirtrestrict.data;

import net.dirtcraft.plugins.dirtrestrict.database.DatabaseOperation;
import net.dirtcraft.plugins.dirtrestrict.utils.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class RestrictedItemManager {
	private static List<RestrictedItem> restrictedItems;
	private static List<RestrictedMod> restrictedMods;
	private static List<UUID> bypassingPlayers;

	public static void init() {
		DatabaseOperation.getAllRestrictions((itemRestrictions, modRestrictions) -> {
			if (Utilities.config.general.debug) {
				Utilities.log(Level.WARNING, "Found " + itemRestrictions.size() + " item restrictions.");
				Utilities.log(Level.WARNING, "Found " + modRestrictions.size() + " mod restrictions.");
			}
			restrictedItems = new ArrayList<>();
			restrictedItems.addAll(itemRestrictions);
			restrictedItems.sort(Comparator.comparing(RestrictedItem::getDisplayName));

			restrictedMods = new ArrayList<>();
			restrictedMods.addAll(modRestrictions);
			restrictedMods.sort(Comparator.comparing(RestrictedMod::getDisplayName));

			bypassingPlayers = new ArrayList<>();
		});
	}

	public static List<RestrictedItem> getRestrictedItems() {
		restrictedItems.sort(Comparator.comparing(RestrictedItem::getDisplayName));
		return restrictedItems;
	}

	public static List<RestrictedMod> getRestrictedMods() {
		restrictedMods.sort(Comparator.comparing(RestrictedMod::getDisplayName));
		return restrictedMods;
	}

	public static List<String> getRestrictedItemsAsString() {
		List<String> names = new ArrayList<>();
		for (RestrictedItem item : restrictedItems) {
			names.add(item.getItem().toString());
		}
		return names;
	}

	public static boolean doesRestrictionExist(NamespacedKey itemToCheck) {
		for (RestrictedItem restrictedItem : restrictedItems) {
			if (restrictedItem.getItem().equals(itemToCheck)) {
				return true;
			}
		}
		return false;
	}

	public static void addRestrictionForItem(RestrictedItem restrictedItem) {
		if (!doesRestrictionExist(restrictedItem.getItem())) {
			restrictedItems.add(restrictedItem);
			restrictedItems.sort(Comparator.comparing(RestrictedItem::getDisplayName));
		}
	}

	public static void removeRestriction(NamespacedKey itemToRemove) {
		restrictedItems.removeIf(restrictedItem -> restrictedItem.getItem().equals(itemToRemove));
	}

	public static RestrictedItem getRestrictedItem(NamespacedKey item) {
		for (RestrictedItem restrictedItem : restrictedItems) {
			if (restrictedItem.getItem().equals(item)) {
				return restrictedItem;
			}
		}
		return null;
	}

	public static void updateDisplayName(String item, String displayName, boolean isMod) {
		if (isMod) {
			for (RestrictedMod restrictedMod : restrictedMods) {
				if (restrictedMod.getNamespace().equals(item)) {
					restrictedMod.setDisplayName(displayName);
					return;
				}
			}
		} else {
			for (RestrictedItem restrictedItem : restrictedItems) {
				if (restrictedItem.getItem().toString().equals(item)) {
					restrictedItem.setDisplayName(displayName);
					return;
				}
			}
		}
	}


	public static void updateAlternative(String item, String alternative, boolean isMod) {
		if (isMod) {
			for (RestrictedMod restrictedMod : restrictedMods) {
				if (restrictedMod.getNamespace().equals(item)) {
					restrictedMod.setAlternative(alternative);
					return;
				}
			}
		} else {
			for (RestrictedItem restrictedItem : restrictedItems) {
				if (restrictedItem.getItem().toString().equals(item)) {
					restrictedItem.setAlternative(alternative);
					return;
				}
			}
		}
	}

	public static void updateReason(String item, String reason, boolean isMod) {
		if (isMod) {
			for (RestrictedMod restrictedMod : restrictedMods) {
				if (restrictedMod.getNamespace().equals(item)) {
					restrictedMod.setReason(reason);
					return;
				}
			}
		} else {
			for (RestrictedItem restrictedItem : restrictedItems) {
				if (restrictedItem.getItem().toString().equals(item)) {
					restrictedItem.setReason(reason);
					return;
				}
			}
		}
	}

	public static void updateClickBanned(String item, boolean clickBanned, boolean isMod) {
		if (isMod) {
			for (RestrictedMod restrictedMod : restrictedMods) {
				if (restrictedMod.getNamespace().equals(item)) {
					restrictedMod.setInventoryClickBanned(clickBanned);
					return;
				}
			}
		} else {
			for (RestrictedItem restrictedItem : restrictedItems) {
				if (restrictedItem.getItem().toString().equals(item)) {
					restrictedItem.setInventoryClickBanned(clickBanned);
					return;
				}
			}
		}
	}

	public static void updatePickupBanned(String item, boolean pickupBanned, boolean isMod) {
		if (isMod) {
			for (RestrictedMod restrictedMod : restrictedMods) {
				if (restrictedMod.getNamespace().equals(item)) {
					restrictedMod.setPickupBanned(pickupBanned);
					return;
				}
			}
		} else {
			for (RestrictedItem restrictedItem : restrictedItems) {
				if (restrictedItem.getItem().toString().equals(item)) {
					restrictedItem.setPickupBanned(pickupBanned);
					return;
				}
			}
		}
	}

	public static void updatePlaceBanned(String item, boolean placeBanned, boolean isMod) {
		if (isMod) {
			for (RestrictedMod restrictedMod : restrictedMods) {
				if (restrictedMod.getNamespace().equals(item)) {
					restrictedMod.setPlaceBanned(placeBanned);
					return;
				}
			}
		} else {
			for (RestrictedItem restrictedItem : restrictedItems) {
				if (restrictedItem.getItem().toString().equals(item)) {
					restrictedItem.setPlaceBanned(placeBanned);
					return;
				}
			}
		}
	}

	public static void updateBreakBanned(String item, boolean breakBanned, boolean isMod) {
		if (isMod) {
			for (RestrictedMod restrictedMod : restrictedMods) {
				if (restrictedMod.getNamespace().equals(item)) {
					restrictedMod.setBreakBanned(breakBanned);
					return;
				}
			}
		} else {
			for (RestrictedItem restrictedItem : restrictedItems) {
				if (restrictedItem.getItem().toString().equals(item)) {
					restrictedItem.setBreakBanned(breakBanned);
					return;
				}
			}
		}
	}

	public static void addAllowedWorld(String item, String world, boolean isMod) {
		if (isMod) {
			for (RestrictedMod restrictedMod : restrictedMods) {
				if (restrictedMod.getNamespace().equals(item)) {
					restrictedMod.addWhitelistedWorld(world);
					return;
				}
			}
		} else {
			for (RestrictedItem restrictedItem : restrictedItems) {
				if (restrictedItem.getItem().toString().equals(item)) {
					restrictedItem.addWhitelistedWorld(world);
					return;
				}
			}
		}
	}

	public static void removeAllowedWorld(String value, String world, boolean isMod) {
		if (isMod) {
			for (RestrictedMod restrictedMod : restrictedMods) {
				if (restrictedMod.getNamespace().equals(value)) {
					restrictedMod.removeWhitelistedWorld(world);
					return;
				}
			}
		} else {
			for (RestrictedItem restrictedItem : restrictedItems) {
				if (restrictedItem.getItem().toString().equals(value)) {
					restrictedItem.removeWhitelistedWorld(world);
					return;
				}
			}
		}
	}

	public static List<String> getRestrictedModsAsString() {
		List<String> names = new ArrayList<>();
		for (RestrictedMod restrictedMod : restrictedMods) {
			names.add(restrictedMod.getNamespace());
		}
		return names;
	}

	public static boolean doesModRestrictionExist(String namespaceToCheck) {
		for (RestrictedMod restrictedMod : restrictedMods) {
			if (restrictedMod.getNamespace().equals(namespaceToCheck)) {
				return true;
			}
		}
		return false;
	}

	public static void removeModRestriction(String namespaceToRemove) {
		restrictedMods.removeIf(restrictedMod -> restrictedMod.getNamespace().equals(namespaceToRemove));
	}

	public static RestrictedMod getRestrictedMod(String namespace) {
		for (RestrictedMod restrictedMod : restrictedMods) {
			if (restrictedMod.getNamespace().equals(namespace)) {
				return restrictedMod;
			}
		}
		return null;
	}

	public static void addRestrictionForMod(RestrictedMod restrictedMod) {
		if (!doesModRestrictionExist(restrictedMod.getNamespace())) {
			restrictedMods.add(restrictedMod);
			restrictedMods.sort(Comparator.comparing(RestrictedMod::getDisplayName));
		}
	}

	public static boolean isWorldWhitelisted(String world, String value, boolean isMod) {
		if (isMod) {
			for (RestrictedMod restrictedMod : restrictedMods) {
				if (restrictedMod.getNamespace().equals(value)) {
					for (String whitelistedWorld : restrictedMod.getWhitelistedWorlds()) {
						if (whitelistedWorld.equals(world)) {
							return true;
						}
					}
				}
			}
		} else {
			for (RestrictedItem restrictedItem : restrictedItems) {
				if (restrictedItem.getItem().toString().equals(value)) {
					for (String whitelistedWorld : restrictedItem.getWhitelistedWorlds()) {
						if (whitelistedWorld.equals(world)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public static List<String> getNotWhitelistedWorlds(String value, boolean isMod) {
		List<String> notWhitelistedWorlds = new ArrayList<>();
		if (isMod) {
			for (RestrictedMod restrictedMod : restrictedMods) {
				if (restrictedMod.getNamespace().equals(value)) {
					for (String world : Bukkit.getWorlds().stream().map(World::getName).collect(Collectors.toList())) {
						if (!restrictedMod.getWhitelistedWorlds().contains(world)) {
							notWhitelistedWorlds.add(world);
						}
					}
					return notWhitelistedWorlds;
				}
			}
		} else {
			for (RestrictedItem restrictedItem : restrictedItems) {
				if (restrictedItem.getItem().toString().equals(value)) {
					for (String world : Bukkit.getWorlds().stream().map(World::getName).collect(Collectors.toList())) {
						if (!restrictedItem.getWhitelistedWorlds().contains(world)) {
							notWhitelistedWorlds.add(world);
						}
					}
					return notWhitelistedWorlds;
				}
			}
		}
		return notWhitelistedWorlds;
	}

	public static List<String> getAllWhitelistedWorlds(String value, boolean isMod) {
		List<String> whitelistedWorlds = new ArrayList<>();
		if (isMod) {
			for (RestrictedMod restrictedMod : restrictedMods) {
				if (restrictedMod.getNamespace().equals(value)) {
					whitelistedWorlds.addAll(restrictedMod.getWhitelistedWorlds());
					return whitelistedWorlds;
				}
			}
		} else {
			for (RestrictedItem restrictedItem : restrictedItems) {
				if (restrictedItem.getItem().toString().equals(value)) {
					whitelistedWorlds.addAll(restrictedItem.getWhitelistedWorlds());
					return whitelistedWorlds;
				}
			}
		}
		return whitelistedWorlds;
	}

	public static boolean isBreakBanned(String namespace) {
		for (RestrictedMod restrictedMod : restrictedMods) {
			if (restrictedMod.getNamespace().equals(namespace)) {
				return restrictedMod.isBreakBanned();
			}
		}
		return false;
	}

	public static boolean isBreakBanned(NamespacedKey item) {
		for (RestrictedItem restrictedItem : restrictedItems) {
			if (restrictedItem.getItem().equals(item)) {
				return restrictedItem.isBreakBanned();
			}
		}
		return false;
	}

	public static boolean isPlaceBanned(String namespace) {
		for (RestrictedMod restrictedMod : restrictedMods) {
			if (restrictedMod.getNamespace().equals(namespace)) {
				return restrictedMod.isPlaceBanned();
			}
		}
		return false;
	}

	public static boolean isPlaceBanned(NamespacedKey item) {
		for (RestrictedItem restrictedItem : restrictedItems) {
			if (restrictedItem.getItem().equals(item)) {
				return restrictedItem.isPlaceBanned();
			}
		}
		return false;
	}

	public static boolean isClickBanned(String namespace) {
		for (RestrictedMod restrictedMod : restrictedMods) {
			if (restrictedMod.getNamespace().equals(namespace)) {
				return restrictedMod.isInventoryClickBanned();
			}
		}
		return false;
	}

	public static boolean isClickBanned(NamespacedKey item) {
		for (RestrictedItem restrictedItem : restrictedItems) {
			if (restrictedItem.getItem().equals(item)) {
				return restrictedItem.isInventoryClickBanned();
			}
		}
		return false;
	}

	public static boolean isPickupBanned(String namespace) {
		for (RestrictedMod restrictedMod : restrictedMods) {
			if (restrictedMod.getNamespace().equals(namespace)) {
				return restrictedMod.isPickupBanned();
			}
		}
		return false;
	}

	public static boolean isPickupBanned(NamespacedKey item) {
		for (RestrictedItem restrictedItem : restrictedItems) {
			if (restrictedItem.getItem().equals(item)) {
				return restrictedItem.isPickupBanned();
			}
		}
		return false;
	}

	public static boolean isWhitelistedInWorld(NamespacedKey block, String name) {
		for (RestrictedItem restrictedItem : restrictedItems) {
			if (restrictedItem.getItem().equals(block)) {
				return restrictedItem.getWhitelistedWorlds().contains(name);
			}
		}
		return false;
	}

	public static boolean isWhitelistedInWorld(String namespace, String name) {
		for (RestrictedMod restrictedMod : restrictedMods) {
			if (restrictedMod.getNamespace().equals(namespace)) {
				return restrictedMod.getWhitelistedWorlds().contains(name);
			}
		}
		return false;
	}

	public static void addBypass(UUID uuid) {
		if (!bypassingPlayers.contains(uuid)) {
			bypassingPlayers.add(uuid);
		}
	}

	public static void removeBypass(UUID uuid) {
		bypassingPlayers.remove(uuid);
	}

	public static boolean isBypassing(UUID uuid) {
		return bypassingPlayers.contains(uuid);
	}
}
