package cursedflames.stackablepotions.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.BrewingStandScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

@Mixin(BrewingStandScreenHandler.class)
public abstract class BrewingStandScreenHandlerMixin extends ScreenHandler {
	private BrewingStandScreenHandlerMixin(ScreenHandlerType<?> type, int syncId) {
		super(type, syncId);
	}
	
	@Inject(method = "transferSlot",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/screen/BrewingStandScreenHandler$PotionSlot;matches(Lnet/minecraft/item/ItemStack;)Z"),
			locals = LocalCapture.CAPTURE_FAILSOFT,
			cancellable = true)
	private void onTransferSlot(PlayerEntity player, int index, CallbackInfoReturnable<ItemStack> info,
			ItemStack itemStack, Slot slot, ItemStack itemStack2) {
		// Replace vanilla shift-click behavior for potions with our own, to prevent getting more than one in a slot
		if (PotionSlotAccessor.matches(itemStack)) {
			boolean movedItems = false;
			for (int i = 0; i < 3; i++) {
				Slot slot2 = this.getSlot(i);
				if (slot2.getStack().isEmpty() && slot.canInsert(itemStack2)) {
					if (itemStack2.getCount() > slot2.getMaxItemCount()) {
						slot2.setStack(itemStack2.split(slot2.getMaxItemCount()));
					} else {
						slot2.setStack(itemStack2.split(itemStack2.getCount()));
					}
					movedItems = true;
					slot2.markDirty();
					// We loop through all the slots without breaking on a successful transfer,
					// so that you can shift-click into all potion slots at once
					if (itemStack2.isEmpty()) break;
				}
			}
			// Vanilla behavior seems to be this, although I'm not sure why.
			if (!movedItems) {
                info.setReturnValue(ItemStack.EMPTY);
            }
		}
	}
	
	@Redirect(method = "transferSlot",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/screen/BrewingStandScreenHandler$PotionSlot;matches(Lnet/minecraft/item/ItemStack;)Z"))
	private boolean onTransferSlotRedirect(ItemStack stack, PlayerEntity player, int index) {
		// Block the default shift-clicking into potion slots so we can do it ourselves.
		// Unfortunately because we're cancelling the vanilla `if`, the else ifs run, meaning the player can
		// shift-click into potion slots with more than 3 potions and the rest of the stack will also get moved around
		// kinda annoying but whatever, don't know of a clean solution for it.
		return false;
	}
}
