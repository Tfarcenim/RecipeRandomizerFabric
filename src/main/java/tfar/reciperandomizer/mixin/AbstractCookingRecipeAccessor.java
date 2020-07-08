package tfar.reciperandomizer.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractCookingRecipe.class)
public interface AbstractCookingRecipeAccessor {

	@Accessor void setOutput(ItemStack stack);

}
