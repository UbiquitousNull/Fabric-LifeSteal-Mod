package lifesteal_mod_fabric_package.item;

import lifesteal_mod_fabric_package.LifeStealModFabric;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public final class LifeStealModItems {
	private LifeStealModItems()
	{
	}

	public static final Item HEART_ITEM = registerItem("heart_item", HeartItem::new, new Item.Settings().maxCount(9));

	private static Item registerItem(String path,
	                             Function<Item.Settings, Item> factory,
	                             Item.Settings settings) {
		final RegistryKey<Item> key = RegistryKey.of(
				RegistryKeys.ITEM,
				Identifier.of(LifeStealModFabric.MOD_ID, path)
		);
		return Items.register(key, factory, settings);
	}

	public static void initializeItems() {}

	static {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.OPERATOR)
				.register((entries) -> entries.add(HEART_ITEM));
	}

//	public static void registerLifeStealModItems() {
//		LifeStealModFabric.LOGGER.info("Registering Mod Items for: {}", LifeStealModFabric.MOD_ID);
//		ItemGroupEvents.modifyEntriesEvent(ItemGroups.OPERATOR).register(LifeStealModItems::addItemsToItemTabGroup);
//		LifeStealModFabric.LOGGER.info("All items registered for: {}", LifeStealModFabric.MOD_ID);
//	}


}
