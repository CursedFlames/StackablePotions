package cursedflames.stackablepotions.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import cursedflames.stackablepotions.PotionDropsBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.LingeringPotionItem;
import net.minecraft.item.PotionItem;
import net.minecraft.item.SplashPotionItem;

@Mixin(Items.class)
public abstract class ItemsMixin {
	@Redirect(method = "<clinit>",
			at = @At(value = "NEW", target = "Lnet/minecraft/item/PotionItem;"))
	private static PotionItem onPotion(Item.Settings settings) {
		return new PotionDropsBottleItem(settings.maxCount(16));
	}
	@Redirect(method = "<clinit>",
			at = @At(value = "NEW", target = "Lnet/minecraft/item/SplashPotionItem;"))
	private static SplashPotionItem onSplashPotion(Item.Settings settings) {
		return new SplashPotionItem(settings.maxCount(16));	
	}
	@Redirect(method = "<clinit>",
			at = @At(value = "NEW", target = "Lnet/minecraft/item/LingeringPotionItem;"))
	private static LingeringPotionItem onLingeringPotion(Item.Settings settings) {
		return new LingeringPotionItem(settings.maxCount(16));
	}
}
