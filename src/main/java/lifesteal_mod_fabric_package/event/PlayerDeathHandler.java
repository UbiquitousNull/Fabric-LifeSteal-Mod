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
		ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
			if (!(entity instanceof ServerPlayerEntity player)) return;

			var hpAttr = Objects.requireNonNull(
					player.getAttributeInstance(EntityAttributes.MAX_HEALTH)
			);
			double oldTotalHp = hpAttr.getValue();

			if (oldTotalHp > 2.0) {
				double newMaxHp = oldTotalHp - 2.0;

				HealthUtils.setMaxHealth(player, (int)newMaxHp);

				ServerWorld world = (ServerWorld) player.getWorld();
				world.spawnEntity(new ItemEntity(
						world,
						player.getX(), player.getY(), player.getZ(),
						new ItemStack(LifeStealModItems.COMMON_HEART_ITEM)
				));
			}
		});

		ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
			if (alive) return;

			var oldAttr = oldPlayer.getAttributeInstance(EntityAttributes.MAX_HEALTH);
			var newAttr = newPlayer.getAttributeInstance(EntityAttributes.MAX_HEALTH);
			if (oldAttr == null || newAttr == null) return;

			double totalMaxHp = oldAttr.getValue();
			newAttr.setBaseValue(totalMaxHp);

			newPlayer.setHealth((float) totalMaxHp);
		});
	}
}
