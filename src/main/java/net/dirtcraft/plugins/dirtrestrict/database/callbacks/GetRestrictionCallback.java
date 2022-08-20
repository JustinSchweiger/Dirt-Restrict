package net.dirtcraft.plugins.dirtrestrict.database.callbacks;

import net.dirtcraft.plugins.dirtrestrict.data.RestrictedItem;
import net.dirtcraft.plugins.dirtrestrict.data.RestrictedMod;

import java.util.List;

public interface GetRestrictionCallback {
	void onSuccess(List<RestrictedItem> restrictedItems, List<RestrictedMod> restrictedMods);
}
