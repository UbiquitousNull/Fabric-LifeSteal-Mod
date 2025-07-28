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
	public HeartItem(Settings settings) { super(settings); }

	@Override
	public ActionResult use(World world, PlayerEntity user, Hand hand) {
		if (!world.isClient) {
			ServerWorld serverWorld = (ServerWorld) world;
			serverWorld.spawnParticles(ParticleTypes.TOTEM_OF_UNDYING, user.getX(), user.getY() +1.0D, user.getZ(), 20, 0.5D, 0.5D, 0.5D, 0.0D);
		}else {
			world.addParticleClient(ParticleTypes.TOTEM_OF_UNDYING, user.getX(), user.getY() + 1.0D, user.getZ(), 0.0D, 0.0D, 0.0D);
		}
		user.getStackInHand(hand).decrement(1);
		applyHeartModifier(user, 1);
		user.playSound(SoundEvents.ITEM_TOTEM_USE, 1.0F, 1.0F);

		return  ActionResult.SUCCESS;
	}
}
