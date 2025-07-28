package lifesteal_mod_fabric_package.item;

import lifesteal_mod_fabric_package.LifeStealModFabric;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
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

	public static final Item HEART_ITEM = registerItem("heart_item", Item::new, new Item.Settings());

	private static void addItemsToItemTabGroup(FabricItemGroupEntries entries) { entries.add(HEART_ITEM); }

	public static Item registerItem(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
		final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of("lifesteal_mod_fabric", path));
		return Items.register(registryKey, factory, settings);
	}

	public static void registerModBaseItems() {
		LifeStealModFabric.LOGGER.info("Registering Mod Items for: {}", LifeStealModFabric.MOD_ID);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.OPERATOR).register(LifeStealModItems::addItemsToItemTabGroup);
	}


}
