package cursedflames.stackablepotions.mixin;

import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Items.class)
public abstract class ItemsMixin {
	@ModifyArg(method = "<clinit>",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item$Settings;maxCount(I)Lnet/minecraft/item/Item$Settings;", ordinal = 0),
			slice = @Slice( from = @At(value = "NEW", target = "Lnet/minecraft/item/PotionItem;")))
	private static int onPotion(int old) {
		return 64;
	}
	@ModifyArg(method = "<clinit>",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item$Settings;maxCount(I)Lnet/minecraft/item/Item$Settings;", ordinal = 0),
			slice = @Slice( from = @At(value = "NEW", target = "Lnet/minecraft/item/SplashPotionItem;")))
	private static int onSplashPotion(int old) {
		return 64;
	}
	@ModifyArg(method = "<clinit>",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item$Settings;maxCount(I)Lnet/minecraft/item/Item$Settings;", ordinal = 0),
			slice = @Slice( from = @At(value = "NEW", target = "Lnet/minecraft/item/LingeringPotionItem;")))
	private static int onLingeringPotion(int old) {
		return 64;
	}
}
