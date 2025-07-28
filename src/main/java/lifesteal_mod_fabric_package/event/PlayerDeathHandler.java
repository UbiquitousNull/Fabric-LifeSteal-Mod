package lifesteal_mod_fabric_package.event;

import lifesteal_mod_fabric_package.item.LifeStealModItems;
import lifesteal_mod_fabric_package.util.HealthUtils;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.Objects;


public class PlayerDeathHandler {
	public static void register() {
		// 1) On death, reduce max health & drop a heart
		ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
			if (!(entity instanceof ServerPlayerEntity player)) return;

			// reduce max health by 1 heart if >1, drop heart_item if reduced
			double oldMax = Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.MAX_HEALTH)).getBaseValue();
			if (oldMax > 2.0) {
				HealthUtils.setMaxHearts(player, (int)(oldMax/2 - 1));
				ServerWorld world = (ServerWorld) player.getWorld();
				ItemStack heart = new ItemStack(LifeStealModItems.HEART_ITEM);
				ItemEntity drop = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), heart);
				world.spawnEntity(drop);
			}
		});

		// 2) On respawn after death, carry over the reduced max health
		ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
			// `alive == true` means they are changing dimension; skip that
			if (alive) return;

			// Read the old max‐health and write it into the new player entity
			double persistedMax = Objects.requireNonNull(oldPlayer.getAttributeInstance(EntityAttributes.MAX_HEALTH)).getBaseValue();

			Objects.requireNonNull(newPlayer.getAttributeInstance(EntityAttributes.MAX_HEALTH)).setBaseValue(persistedMax);
		});
	}
}
