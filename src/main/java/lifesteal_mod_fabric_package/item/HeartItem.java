package lifesteal_mod_fabric_package.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import static lifesteal_mod_fabric_package.util.HealthUtils.applyHeartModifier;

public class HeartItem extends Item {
	private final int health;

	public HeartItem(Settings settings, int health) {
		super(settings);
		this.health = health;
	}

	@Override
	public ActionResult use(World world, PlayerEntity user, Hand hand) {
		if (!world.isClient) {
			((ServerWorld) world).spawnParticles(
					ParticleTypes.TOTEM_OF_UNDYING,
					user.getX(), user.getY() + 1.0D, user.getZ(),
					20, .5, .5, .5, 0);
		} else {
			world.addParticleClient(
					ParticleTypes.TOTEM_OF_UNDYING,
					user.getX(), user.getY() + 1.0D, user.getZ(),
					0, 0, 0);
		}

		user.getStackInHand(hand).decrement(1);
		applyHeartModifier(user, health);
		user.playSound(SoundEvents.ITEM_TOTEM_USE, 1F, 1F);
		return ActionResult.SUCCESS;
	}
}
