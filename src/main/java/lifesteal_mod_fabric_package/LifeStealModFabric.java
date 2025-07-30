package lifesteal_mod_fabric_package;

import lifesteal_mod_fabric_package.command.SetMaxHealth;
import lifesteal_mod_fabric_package.event.PlayerDeathHandler;
import lifesteal_mod_fabric_package.item.LifeStealModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LifeStealModFabric implements ModInitializer {
	public static final String MOD_ID = "lifesteal_mod_fabric";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LifeStealModItems.initializeItems();
		PlayerDeathHandler.register();
		SetMaxHealth.register();
	}
}