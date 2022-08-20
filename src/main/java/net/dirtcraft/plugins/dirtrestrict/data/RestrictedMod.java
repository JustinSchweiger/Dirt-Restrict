package net.dirtcraft.plugins.dirtrestrict.data;

import org.bukkit.NamespacedKey;

import java.util.List;

public class RestrictedMod {
	private final String namespace;
	private String displayName;
	private String reason;
	private String alternative;
	private final List<String> whitelistedWorlds;
	private boolean breakBanned;
	private boolean placeBanned;
	private boolean pickupBanned;
	private boolean inventoryClickBanned;

	public RestrictedMod(String namespace, String displayName, String reason, String alternative, List<String> whitelistedWorlds, boolean breakBanned, boolean placeBanned, boolean pickupBanned, boolean inventoryClickBanned) {
		this.namespace = namespace;
		this.displayName = displayName;
		this.reason = reason;
		this.alternative = alternative;
		this.whitelistedWorlds = whitelistedWorlds;
		this.breakBanned = breakBanned;
		this.placeBanned = placeBanned;
		this.pickupBanned = pickupBanned;
		this.inventoryClickBanned = inventoryClickBanned;
	}

	public String getNamespace() {
		return namespace;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAlternative() {
		return alternative;
	}

	public void setAlternative(String alternative) {
		this.alternative = alternative;
	}

	public List<String> getWhitelistedWorlds() {
		return whitelistedWorlds;
	}

	public void addWhitelistedWorld(String world) {
		this.whitelistedWorlds.add(world);
	}

	public void removeWhitelistedWorld(String world) {
		this.whitelistedWorlds.remove(world);
	}

	public boolean isBreakBanned() {
		return breakBanned;
	}

	public void setBreakBanned(boolean breakBanned) {
		this.breakBanned = breakBanned;
	}

	public boolean isPlaceBanned() {
		return placeBanned;
	}

	public void setPlaceBanned(boolean placeBanned) {
		this.placeBanned = placeBanned;
	}

	public boolean isPickupBanned() {
		return pickupBanned;
	}

	public void setPickupBanned(boolean pickupBanned) {
		this.pickupBanned = pickupBanned;
	}

	public boolean isInventoryClickBanned() {
		return inventoryClickBanned;
	}

	public void setInventoryClickBanned(boolean inventoryClickBanned) {
		this.inventoryClickBanned = inventoryClickBanned;
	}
}
