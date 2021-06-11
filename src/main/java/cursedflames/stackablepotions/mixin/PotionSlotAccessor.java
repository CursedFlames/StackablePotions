package cursedflames.stackablepotions.mixin;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(targets = "net.minecraft.screen.BrewingStandScreenHandler$PotionSlot")
public interface PotionSlotAccessor {
    @Invoker("matches")
    static boolean matches(ItemStack item){
        return false;
    }
}
