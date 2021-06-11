package cursedflames.stackablepotions.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionItem.class)
public abstract class PotionItemMixin {
    @Inject(method = "finishUsing(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/item/ItemStack;", cancellable = true,
        at = @At(value = "INVOKE",target = "Lnet/minecraft/entity/player/PlayerInventory;insertStack(Lnet/minecraft/item/ItemStack;)Z", shift = At.Shift.BEFORE))
    public void finishUsing(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        if (user instanceof PlayerEntity) ((PlayerEntity)user).getInventory().offerOrDrop(Items.GLASS_BOTTLE.getDefaultStack());
        cir.setReturnValue(stack);
    }
}
