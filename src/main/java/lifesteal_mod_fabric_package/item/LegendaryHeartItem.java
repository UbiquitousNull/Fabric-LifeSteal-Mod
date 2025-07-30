package lifesteal_mod_fabric_package.item;

import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;

public class LegendaryHeartItem extends HeartItem {
	public LegendaryHeartItem(Settings settings, int health) {
		super(settings, health);
	}

	public Rarity getRarity(ItemStack stack) {
		return Rarity.EPIC;
	}

	@Override
	public Text getName(ItemStack stack) {
		String base = Text.translatable(this.getTranslationKey()).getString();
		return Text.literal(base).formatted(Formatting.GOLD);
	}
}
