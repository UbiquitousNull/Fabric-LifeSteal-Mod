package lifesteal_mod_fabric_package.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import lifesteal_mod_fabric_package.util.HealthUtils;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.command.argument.EntityArgumentType.getPlayer;
import static net.minecraft.command.argument.EntityArgumentType.player;

public class SetMaxHealth {
	public static void register() {
		CommandRegistrationCallback.EVENT.register(
				(CommandDispatcher<ServerCommandSource> dispatcher,
				 CommandRegistryAccess registryAccess,
				 CommandManager.RegistrationEnvironment env) -> dispatcher.register(
						CommandManager.literal("setmaxhealth")
								.requires(src -> src.hasPermissionLevel(2))
								.then(CommandManager.argument("player", player())
										.then(CommandManager.argument("health", IntegerArgumentType.integer(1))
												.executes(ctx -> {
													try {
														ServerPlayerEntity target = getPlayer(ctx, "player");
														int health = IntegerArgumentType.getInteger(ctx, "health");
														HealthUtils.setMaxHealth(target, health);
														ctx.getSource().sendFeedback(
																() -> Text.literal("Set max health of ")
																		.append(target.getDisplayName())
																		.append(Text.literal(" to " + health)),
																true
														);
														return 1;
													} catch (Exception e) {
														ctx.getSource().sendError(Text.literal("§cError: " + e.getMessage()));
														return 0;
													}
												})
										)
								)
				)
		);
	}
}
