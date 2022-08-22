package net.dirtcraft.plugins.dirtrestrict.data;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import java.util.List;

public class RestrictedItem {
	private final NamespacedKey item;
	private String displayName;
	private String reason;
	private String alternative;
	private final List<String> whitelistedWorlds;
	private boolean breakBanned;
	private boolean placeBanned;
	private boolean pickupBanned;
	private boolean inventoryClickBanned;
	private boolean holdBanned;
	private boolean useBanned;

	public RestrictedItem(NamespacedKey item, String displayName, String reason, String alternative, List<String> whitelistedWorlds, boolean breakBanned, boolean placeBanned, boolean pickupBanned, boolean inventoryClickBanned, boolean holdBanned, boolean useBanned) {
		this.item = item;
		this.displayName = displayName;
		this.reason = reason;
		this.alternative = alternative;
		this.whitelistedWorlds = whitelistedWorlds;
		this.breakBanned = breakBanned;
		this.placeBanned = placeBanned;
		this.pickupBanned = pickupBanned;
		this.inventoryClickBanned = inventoryClickBanned;
		this.holdBanned = holdBanned;
		this.useBanned = useBanned;
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

	public void addWhitelistedWorld(String world) {
		this.whitelistedWorlds.add(world);
	}

	public void removeWhitelistedWorld(String world) {
		this.whitelistedWorlds.remove(world);
	}

	public List<String> getWhitelistedWorlds() {
		return whitelistedWorlds;
	}

	public NamespacedKey getItem() {
		return item;
	}

	public boolean isHoldBanned() {
		return holdBanned;
	}

	public void setHoldBanned(boolean holdBanned) {
		this.holdBanned = holdBanned;
	}

	public boolean isUseBanned() {
		return useBanned;
	}

	public void setUseBanned(boolean useBanned) {
		this.useBanned = useBanned;
	}
}
