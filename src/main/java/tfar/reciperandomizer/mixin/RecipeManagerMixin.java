package tfar.reciperandomizer.mixin;

import com.google.gson.JsonObject;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.reciperandomizer.MixinHooks;

import java.util.Map;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {
	@Shadow private Map<RecipeType<?>, Map<Identifier, Recipe<?>>> recipes;

	@Inject(method = "apply",at = @At("RETURN"))
	public void scrambleRecipes(Map<Identifier, JsonObject> splashList, ResourceManager resourceManagerIn, Profiler profilerIn, CallbackInfo ci){
		recipes = MixinHooks.scramble(recipes);
	}
}
