package lifesteal_mod_fabric_package.util;

import lifesteal_mod_fabric_package.LifeStealModFabric;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class HealthUtils {
	private static final Identifier HEART_MODIFIER_ID = Identifier.of(LifeStealModFabric.MOD_ID, "extra_hearts");

	// extraHearts: number of additional hearts (negative to subtract)
	public static void applyHeartModifier(PlayerEntity player, int extraHearts) {

		double amount = extraHearts * 2.0D;

		EntityAttributeInstance healthAttr =
				player.getAttributeInstance(EntityAttributes.MAX_HEALTH);
		if (healthAttr == null) { return; }

		EntityAttributeModifier old = healthAttr.getModifier(HEART_MODIFIER_ID);
		double newTotal;

		if (old != null) {
			newTotal = old.value() + amount;
			healthAttr.removeModifier(old);
		}else {
			newTotal = amount;
		}

		if (newTotal != 0.0D) {
			EntityAttributeModifier modifier = new EntityAttributeModifier(
					HEART_MODIFIER_ID,
					newTotal,
					EntityAttributeModifier.Operation.ADD_VALUE
			);
			healthAttr.addPersistentModifier(modifier);
		}

		double max = healthAttr.getValue();
		if (player.getHealth() > max) {
			player.setHealth((float) max);
		}
	}

	public static void setMaxHearts(PlayerEntity player, int hearts) {
		int clampedHearts = Math.max(1, hearts);
		double newMaxHp = clampedHearts * 2.0D;

		EntityAttributeInstance hp = player
				.getAttributeInstance(EntityAttributes.MAX_HEALTH);
		if (hp == null) return;

		// Remove any old modifier we applied
		hp.removeModifier(HEART_MODIFIER_ID);

		// Calculate how much to add on top of the current base
		double delta = newMaxHp - hp.getBaseValue();
		if (delta != 0.0D) {
			// This single call will be synced to the client
			hp.addPersistentModifier(new EntityAttributeModifier(
					HEART_MODIFIER_ID,
					delta,
					EntityAttributeModifier.Operation.ADD_VALUE
			));
		}

		// Clamp current health
		double max = hp.getValue();
		if (player.getHealth() > max) {
			player.setHealth((float) max);
		}
	}
}
