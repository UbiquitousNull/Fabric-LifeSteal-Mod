package lifesteal_mod_fabric_package.util;

import lifesteal_mod_fabric_package.LifeStealModFabric;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class HealthUtils {
	private static final Identifier HEART_MODIFIER_ID = Identifier.of(LifeStealModFabric.MOD_ID, "extra_health");

	public static void applyHeartModifier(PlayerEntity player, int extraHealth) {

		EntityAttributeInstance healthAttr =
				player.getAttributeInstance(EntityAttributes.MAX_HEALTH);
		if (healthAttr == null) { return; }

		EntityAttributeModifier old = healthAttr.getModifier(HEART_MODIFIER_ID);
		double newTotal;

		if (old != null) {
			newTotal = old.value() + extraHealth;
			healthAttr.removeModifier(old);
		}else {
			newTotal = extraHealth;
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
			player.setHealth((int) max);
		}
	}

	public static void setMaxHealth(PlayerEntity player, int health) {
		int newMaxHp = Math.max(1, health);

		EntityAttributeInstance hp = player
				.getAttributeInstance(EntityAttributes.MAX_HEALTH);
		if (hp == null) return;
		hp.removeModifier(HEART_MODIFIER_ID);

		double delta = newMaxHp - hp.getBaseValue();
		if (delta != 0.0D) {
			hp.addPersistentModifier(new EntityAttributeModifier(
					HEART_MODIFIER_ID,
					delta,
					EntityAttributeModifier.Operation.ADD_VALUE
			));
		}

		double max = hp.getValue();
		if (player.getHealth() > max) {
			player.setHealth((int) max);
		}
	}
}
