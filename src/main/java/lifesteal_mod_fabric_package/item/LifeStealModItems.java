package lifesteal_mod_fabric_package.item;

import lifesteal_mod_fabric_package.LifeStealModFabric;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public final class LifeStealModItems {
	private LifeStealModItems()	{}


	public static HeartItem COMMON_HEART_ITEM;
	public static HeartItem UNCOMMON_HEART_ITEM;
	public static HeartItem RARE_HEART_ITEM;
	public static HeartItem EPIC_HEART_ITEM;
	public static HeartItem LEGENDARY_HEART_ITEM;

	public static void initializeItems() {
		COMMON_HEART_ITEM = registerHeartItem("common_heart_item", 2, new Item.Settings().maxCount(10).rarity(Rarity.COMMON));
		UNCOMMON_HEART_ITEM = registerHeartItem("uncommon_heart_item", 4, new Item.Settings().maxCount(5).rarity(Rarity.UNCOMMON));
		RARE_HEART_ITEM = registerHeartItem("rare_heart_item", 10, new Item.Settings().maxCount(2).rarity(Rarity.RARE));
		EPIC_HEART_ITEM = registerHeartItem("epic_heart_item", 20, new Item.Settings().maxCount(1).rarity(Rarity.EPIC));
		LEGENDARY_HEART_ITEM = registerLegendaryHeartItem("legendary_heart_item", 40, new Item.Settings().maxCount(1).rarity(Rarity.COMMON));

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.OPERATOR)
				.register(entries -> {
					entries.add(COMMON_HEART_ITEM);
					entries.add(UNCOMMON_HEART_ITEM);
					entries.add(RARE_HEART_ITEM);
					entries.add(EPIC_HEART_ITEM);
					entries.add(LEGENDARY_HEART_ITEM);
				});
	}

	private static HeartItem registerHeartItem(String path, int health, Item.Settings settings) {
		Identifier id = Identifier.of(LifeStealModFabric.MOD_ID, path);
		RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);
		settings = settings.registryKey(key);
		HeartItem item = new HeartItem(settings, health);
		return Registry.register(Registries.ITEM, id, item);
	}

	private static LegendaryHeartItem registerLegendaryHeartItem(String path, int health, Item.Settings settings) {
		Identifier id = Identifier.of(LifeStealModFabric.MOD_ID, path);
		RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);
		settings = settings.registryKey(key);
		LegendaryHeartItem item = new LegendaryHeartItem(settings, health);
		return Registry.register(Registries.ITEM, id, item);
	}

//	private static <T extends Item> T registerItem(String path,
//	                                               Function<Item.Settings, T> factory,
//	                                               Item.Settings settings) {
//		RegistryKey<Item> key = RegistryKey.of(
//				RegistryKeys.ITEM,
//				Identifier.of(LifeStealModFabric.MOD_ID, path)
//		);
//		return Items.register(key, factory, settings);
//	}


}
