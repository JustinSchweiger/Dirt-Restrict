CREATE TABLE IF NOT EXISTS ITEM
(
    item_block                VARCHAR(60) NOT NULL,
    item_displayName          VARCHAR(50) NOT NULL,
    item_reason               TEXT        NOT NULL,
    item_alternative          TEXT        NOT NULL,
    item_whitelistedWorlds    VARCHAR(100),
    item_breakBanned          BOOLEAN     NOT NULL,
    item_placeBanned          BOOLEAN     NOT NULL,
    item_pickupBanned         BOOLEAN     NOT NULL,
    item_inventoryClickBanned BOOLEAN     NOT NULL,
    primary key (item_block)
);

CREATE TABLE IF NOT EXISTS MODS
(
    mods_namespace            VARCHAR(50) NOT NULL,
    mods_displayName          VARCHAR(50) NOT NULL,
    mods_reason               TEXT        NOT NULL,
    mods_alternative          TEXT        NOT NULL,
    mods_whitelistedWorlds    VARCHAR(100),
    mods_breakBanned          BOOLEAN     NOT NULL,
    mods_placeBanned          BOOLEAN     NOT NULL,
    mods_pickupBanned         BOOLEAN     NOT NULL,
    mods_inventoryClickBanned BOOLEAN     NOT NULL,
    primary key (mods_namespace)
);