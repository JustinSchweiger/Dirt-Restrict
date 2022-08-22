package net.dirtcraft.plugins.dirtrestrict.database;

import net.dirtcraft.plugins.dirtrestrict.DirtRestrict;
import net.dirtcraft.plugins.dirtrestrict.data.RestrictedItem;
import net.dirtcraft.plugins.dirtrestrict.data.RestrictedMod;
import net.dirtcraft.plugins.dirtrestrict.database.callbacks.AddRestrictionCallback;
import net.dirtcraft.plugins.dirtrestrict.database.callbacks.EditCallback;
import net.dirtcraft.plugins.dirtrestrict.database.callbacks.GetRestrictionCallback;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseOperation {

	public static void addRestrictionForItem(final NamespacedKey itemToRestrict, final String displayName, final AddRestrictionCallback addRestrictionCallback) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtRestrict.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement statement = connection.prepareStatement("INSERT INTO ITEM VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
				statement.setString(1, itemToRestrict.toString());
				statement.setString(2, displayName);
				statement.setString(3, "");
				statement.setString(4, "");
				statement.setString(5, null);
				statement.setBoolean(6, true);
				statement.setBoolean(7, true);
				statement.setBoolean(8, true);
				statement.setBoolean(9, true);
				statement.setBoolean(10, true);
				statement.setBoolean(11, true);
				statement.execute();

				Bukkit.getScheduler().runTask(DirtRestrict.getPlugin(), addRestrictionCallback::onSuccess);
			} catch (SQLException ignored) {}
		});
	}

	public static void getAllRestrictions(final GetRestrictionCallback getRestrictionCallback) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtRestrict.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement getItems = connection.prepareStatement("SELECT * FROM ITEM");
				 PreparedStatement getMods = connection.prepareStatement("SELECT * FROM MODS")) {
				ResultSet items = getItems.executeQuery();
				ResultSet mods = getMods.executeQuery();
				List<RestrictedItem> restrictedItems = new ArrayList<>();
				List<RestrictedMod> restrictedMods = new ArrayList<>();
				while (items.next()) {
					List<String> whitelistedWorlds = new ArrayList<>();
					if (items.getString("item_whitelistedWorlds") != null) {
						whitelistedWorlds = Arrays.asList(items.getString("item_whitelistedWorlds").split(","));
					}

					restrictedItems.add(
							new RestrictedItem(
									NamespacedKey.fromString(items.getString("item_block")),
									items.getString("item_displayName"),
									items.getString("item_reason"),
									items.getString("item_alternative"),
									whitelistedWorlds,
									items.getBoolean("item_breakBanned"),
									items.getBoolean("item_placeBanned"),
									items.getBoolean("item_pickupBanned"),
									items.getBoolean("item_inventoryClickBanned"),
									items.getBoolean("item_holdBanned"),
									items.getBoolean("item_useBanned")
							)
					);
				}

				while (mods.next()) {
					List<String> whitelistedWorlds = new ArrayList<>();
					if (mods.getString("mods_whitelistedWorlds") != null) {
						whitelistedWorlds = Arrays.asList(mods.getString("mods_whitelistedWorlds").split(","));
					}
					restrictedMods.add(
							new RestrictedMod(
									mods.getString("mods_namespace"),
									mods.getString("mods_displayName"),
									mods.getString("mods_reason"),
									mods.getString("mods_alternative"),
									whitelistedWorlds,
									mods.getBoolean("mods_breakBanned"),
									mods.getBoolean("mods_placeBanned"),
									mods.getBoolean("mods_pickupBanned"),
									mods.getBoolean("mods_inventoryClickBanned"),
									mods.getBoolean("mods_holdBanned"),
									mods.getBoolean("mods_useBanned")
							)
					);
				}

				Bukkit.getScheduler().runTask(DirtRestrict.getPlugin(), () -> getRestrictionCallback.onSuccess(restrictedItems, restrictedMods));
			} catch (SQLException e) {e.printStackTrace();}
		});
	}

	public static void removeRestriction(final NamespacedKey itemToRemove) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtRestrict.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement statement = connection.prepareStatement("DELETE FROM ITEM WHERE item_block = ?")) {
				statement.setString(1, itemToRemove.toString());
				statement.execute();
			} catch (SQLException ignored) {}
		});
	}

	public static void updateDisplayName(final String value, final String displayName, final boolean isMod, final EditCallback editCallback) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtRestrict.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement items = connection.prepareStatement("UPDATE ITEM SET item_displayName = ? WHERE item_block = ?");
				 PreparedStatement mods = connection.prepareStatement("UPDATE MODS SET mods_displayName = ? WHERE mods_namespace = ?")) {
				if (isMod) {
					mods.setString(1, displayName);
					mods.setString(2, value);
					mods.execute();
				} else {
					items.setString(1, displayName);
					items.setString(2, value);
					items.execute();
				}
				Bukkit.getScheduler().runTask(DirtRestrict.getPlugin(), editCallback::onSuccess);
			} catch (SQLException ignored) {}
		});
	}

	public static void updateAlternative(final String value, final String alternative, final boolean isMod, final EditCallback editCallback) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtRestrict.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement items = connection.prepareStatement("UPDATE ITEM SET item_alternative = ? WHERE item_block = ?");
				 PreparedStatement mods = connection.prepareStatement("UPDATE MODS SET mods_alternative = ? WHERE mods_namespace = ?")) {
				if (isMod) {
					mods.setString(1, alternative);
					mods.setString(2, value);
					mods.execute();
				} else {
					items.setString(1, alternative);
					items.setString(2, value);
					items.execute();
				}
				Bukkit.getScheduler().runTask(DirtRestrict.getPlugin(), editCallback::onSuccess);
			} catch (SQLException ignored) {}
		});
	}

	public static void updateReason(final String value, final String reason, final boolean isMod, final EditCallback editCallback) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtRestrict.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement items = connection.prepareStatement("UPDATE ITEM SET item_reason = ? WHERE item_block = ?");
				 PreparedStatement mods = connection.prepareStatement("UPDATE MODS SET mods_reason = ? WHERE mods_namespace = ?")) {
				if (isMod) {
					mods.setString(1, reason);
					mods.setString(2, value);
					mods.execute();
				} else {
					items.setString(1, reason);
					items.setString(2, value);
					items.execute();
				}
				Bukkit.getScheduler().runTask(DirtRestrict.getPlugin(), editCallback::onSuccess);
			} catch (SQLException ignored) {}
		});
	}

	public static void updateClickBanned(final String value, final boolean clickBanned, final boolean isMod, final EditCallback editCallback) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtRestrict.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement items = connection.prepareStatement("UPDATE ITEM SET item_inventoryClickBanned = ? WHERE item_block = ?");
				 PreparedStatement mods = connection.prepareStatement("UPDATE MODS SET mods_inventoryClickBanned = ? WHERE mods_namespace = ?")) {
				if (isMod) {
					mods.setBoolean(1, clickBanned);
					mods.setString(2, value);
					mods.execute();
				} else {
					items.setBoolean(1, clickBanned);
					items.setString(2, value);
					items.execute();
				}
				Bukkit.getScheduler().runTask(DirtRestrict.getPlugin(), editCallback::onSuccess);
			} catch (SQLException ignored) {}
		});
	}

	public static void updatePickupBanned(final String value, final boolean pickupBanned, final boolean isMod, final EditCallback editCallback) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtRestrict.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement items = connection.prepareStatement("UPDATE ITEM SET item_pickupBanned = ? WHERE item_block = ?");
				 PreparedStatement mods = connection.prepareStatement("UPDATE MODS SET mods_pickupBanned = ? WHERE mods_namespace = ?")) {
				if (isMod) {
					mods.setBoolean(1, pickupBanned);
					mods.setString(2, value);
					mods.execute();
				} else {
					items.setBoolean(1, pickupBanned);
					items.setString(2, value);
					items.execute();
				}
				Bukkit.getScheduler().runTask(DirtRestrict.getPlugin(), editCallback::onSuccess);
			} catch (SQLException ignored) {}
		});
	}

	public static void updateHoldBanned(final String value, final boolean holdBanned, final boolean isMod, final EditCallback editCallback) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtRestrict.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement items = connection.prepareStatement("UPDATE ITEM SET item_holdBanned = ? WHERE item_block = ?");
			     PreparedStatement mods = connection.prepareStatement("UPDATE MODS SET mods_holdBanned = ? WHERE mods_namespace = ?")) {
				if (isMod) {
					mods.setBoolean(1, holdBanned);
					mods.setString(2, value);
					mods.execute();
				} else {
					items.setBoolean(1, holdBanned);
					items.setString(2, value);
					items.execute();
				}
				Bukkit.getScheduler().runTask(DirtRestrict.getPlugin(), editCallback::onSuccess);
			} catch (SQLException ignored) {}
		});
	}

	public static void updateUseBanned(final String value, final boolean useBanned, final boolean isMod, final EditCallback editCallback) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtRestrict.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement items = connection.prepareStatement("UPDATE ITEM SET item_useBanned = ? WHERE item_block = ?");
			     PreparedStatement mods = connection.prepareStatement("UPDATE MODS SET mods_useBanned = ? WHERE mods_namespace = ?")) {
				if (isMod) {
					mods.setBoolean(1, useBanned);
					mods.setString(2, value);
					mods.execute();
				} else {
					items.setBoolean(1, useBanned);
					items.setString(2, value);
					items.execute();
				}
				Bukkit.getScheduler().runTask(DirtRestrict.getPlugin(), editCallback::onSuccess);
			} catch (SQLException ignored) {}
		});
	}

	public static void updatePlaceBanned(final String value, final boolean placeBanned, final boolean isMod, final EditCallback editCallback) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtRestrict.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement items = connection.prepareStatement("UPDATE ITEM SET item_placeBanned = ? WHERE item_block = ?");
				 PreparedStatement mods = connection.prepareStatement("UPDATE MODS SET mods_placeBanned = ? WHERE mods_namespace = ?")) {
				if (isMod) {
					mods.setBoolean(1, placeBanned);
					mods.setString(2, value);
					mods.execute();
				} else {
					items.setBoolean(1, placeBanned);
					items.setString(2, value);
					items.execute();
				}
				Bukkit.getScheduler().runTask(DirtRestrict.getPlugin(), editCallback::onSuccess);
			} catch (SQLException ignored) {}
		});
	}

	public static void updateBreakBanned(final String value, final boolean breakBanned, final boolean isMod, final EditCallback editCallback) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtRestrict.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement items = connection.prepareStatement("UPDATE ITEM SET item_breakBanned = ? WHERE item_block = ?");
				 PreparedStatement mods = connection.prepareStatement("UPDATE MODS SET mods_breakBanned = ? WHERE mods_namespace = ?")) {
				if (isMod) {
					mods.setBoolean(1, breakBanned);
					mods.setString(2, value);
					mods.execute();
				} else {
					items.setBoolean(1, breakBanned);
					items.setString(2, value);
					items.execute();
				}
				Bukkit.getScheduler().runTask(DirtRestrict.getPlugin(), editCallback::onSuccess);
			} catch (SQLException ignored) {}
		});
	}

	public static void addAllowedWorld(final String value, final String world, final boolean isMod, final EditCallback editCallback) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtRestrict.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement getWorldsForItem = connection.prepareStatement("SELECT item_whitelistedWorlds FROM ITEM WHERE item_block = ?");
			     PreparedStatement updateWorldsForItem = connection.prepareStatement("UPDATE ITEM SET item_whitelistedWorlds = ? WHERE item_block = ?");
				 PreparedStatement getWorldsForMod = connection.prepareStatement("SELECT mods_whitelistedWorlds FROM MODS WHERE mods_namespace = ?");
				 PreparedStatement updateWorldsForMod = connection.prepareStatement("UPDATE MODS SET mods_whitelistedWorlds = ? WHERE mods_namespace = ?")) {
				if (isMod) {
					getWorldsForMod.setString(1, value);
					ResultSet worldResult = getWorldsForMod.executeQuery();
					if (worldResult.next()) {
						String worldsString = worldResult.getString("mods_whitelistedWorlds");
						if (worldsString == null) {
							worldsString = world;
						}
						List<String> worlds = new ArrayList<>(Arrays.asList(worldsString.split(",")));
						worlds.add(world);
						updateWorldsForMod.setString(1, String.join(",", worlds));
						updateWorldsForMod.setString(2, value);
						updateWorldsForMod.execute();
					}
				} else {
					getWorldsForItem.setString(1, value);
					ResultSet worldsResult = getWorldsForItem.executeQuery();
					if (worldsResult.next()) {
						String worldsString = worldsResult.getString("item_whitelistedWorlds");
						if (worldsString == null) {
							worldsString = world;
						}
						List<String> worlds = new ArrayList<>(Arrays.asList(worldsString.split(",")));
						worlds.add(world);
						updateWorldsForItem.setString(1, String.join(",", worlds));
						updateWorldsForItem.setString(2, value);
						updateWorldsForItem.execute();
					}
				}

				Bukkit.getScheduler().runTask(DirtRestrict.getPlugin(), editCallback::onSuccess);
			} catch (SQLException ignored) {}
		});
	}

	public static void removeAllowedWorld(final String value, final String world, final boolean isMod, final EditCallback editCallback) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtRestrict.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement getWorldsForItem = connection.prepareStatement("SELECT item_whitelistedWorlds FROM ITEM WHERE item_block = ?");
			     PreparedStatement updateWorldsForItem = connection.prepareStatement("UPDATE ITEM SET item_whitelistedWorlds = ? WHERE item_block = ?");
			     PreparedStatement getWorldsForMod = connection.prepareStatement("SELECT mods_whitelistedWorlds FROM MODS WHERE mods_namespace = ?");
			     PreparedStatement updateWorldsForMod = connection.prepareStatement("UPDATE MODS SET mods_whitelistedWorlds = ? WHERE mods_namespace = ?")) {
				if (isMod) {
					getWorldsForMod.setString(1, value);
					ResultSet worldResult = getWorldsForMod.executeQuery();
					if (worldResult.next()) {
						String worldsString = worldResult.getString("mods_whitelistedWorlds");
						if (worldsString == null) {
							Bukkit.getScheduler().runTask(DirtRestrict.getPlugin(), editCallback::onSuccess);
							return;
						}
						List<String> worlds = new ArrayList<>(Arrays.asList(worldsString.split(",")));
						worlds.remove(world);
						updateWorldsForMod.setString(1, String.join(",", worlds));
						updateWorldsForMod.setString(2, value);
						updateWorldsForMod.execute();
					}
				} else {
					getWorldsForItem.setString(1, value);
					ResultSet worldsResult = getWorldsForItem.executeQuery();
					if (worldsResult.next()) {
						String worldsString = worldsResult.getString("item_whitelistedWorlds");
						if (worldsString == null) {
							Bukkit.getScheduler().runTask(DirtRestrict.getPlugin(), editCallback::onSuccess);
							return;
						}
						List<String> worlds = new ArrayList<>(Arrays.asList(worldsString.split(",")));
						worlds.remove(world);
						updateWorldsForItem.setString(1, String.join(",", worlds));
						updateWorldsForItem.setString(2, value);
						updateWorldsForItem.execute();
					}
				}

				Bukkit.getScheduler().runTask(DirtRestrict.getPlugin(), editCallback::onSuccess);
			} catch (SQLException ignored) {}
		});
	}

	public static void removeModRestriction(final String namespaceToRemove) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtRestrict.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement statement = connection.prepareStatement("DELETE FROM MODS WHERE MODS.mods_namespace = ?")) {
				statement.setString(1, namespaceToRemove);
				statement.execute();
			} catch (SQLException ignored) {}
		});
	}

	public static void addRestrictionForMod(final String namespace, final String displayName, final AddRestrictionCallback addRestrictionCallback) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtRestrict.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement statement = connection.prepareStatement("INSERT INTO MODS VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
				statement.setString(1, namespace);
				statement.setString(2, displayName);
				statement.setString(3, "");
				statement.setString(4, "");
				statement.setString(5, null);
				statement.setBoolean(6, true);
				statement.setBoolean(7, true);
				statement.setBoolean(8, true);
				statement.setBoolean(9, true);
				statement.setBoolean(10, true);
				statement.setBoolean(11, true);
				statement.execute();

				Bukkit.getScheduler().runTask(DirtRestrict.getPlugin(), addRestrictionCallback::onSuccess);
			} catch (SQLException ignored) {}
		});
	}
}
