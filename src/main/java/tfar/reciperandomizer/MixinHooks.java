package tfar.reciperandomizer;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import tfar.reciperandomizer.mixin.AbstractCookingRecipeAccessor;
import tfar.reciperandomizer.mixin.ShapedRecipeAccessor;
import tfar.reciperandomizer.mixin.ShapelessRecipeAccessor;

import java.util.*;

public class MixinHooks {

	public static Random random = new Random();

	public static Map<RecipeType<?>, Map<Identifier, Recipe<?>>> scramble(Map<RecipeType<?>, Map<Identifier, Recipe<?>>> original){
		Map<RecipeType<?>, Map<Identifier, Recipe<?>>> scrambledRecipes = new HashMap<>();
		List<Recipe<?>> craftingRecipes = new ArrayList<>(original.get(RecipeType.CRAFTING).values());
		List<Recipe<?>> smeltingRecipes = new ArrayList<>(original.get(RecipeType.SMELTING).values());
		List<Recipe<?>> smokingRecipes = new ArrayList<>(original.get(RecipeType.SMOKING).values());
		List<Recipe<?>> blastingRecipes = new ArrayList<>(original.get(RecipeType.BLASTING).values());
		List<Recipe<?>> campfireRecipes = new ArrayList<>(original.get(RecipeType.CAMPFIRE_COOKING).values());
		List<Recipe<?>> outputs = new ArrayList<>();
		outputs.addAll(craftingRecipes);
		outputs.addAll(smeltingRecipes);
		outputs.addAll(smokingRecipes);
		outputs.addAll(blastingRecipes);
		outputs.addAll(campfireRecipes);


		for (Map.Entry<RecipeType<?>,Map<Identifier, Recipe<?>>> entry : original.entrySet()){
			RecipeType<?> recipeType = entry.getKey();
			if (recipeType == RecipeType.CRAFTING ||
							recipeType == RecipeType.SMELTING ||
							recipeType == RecipeType.SMOKING ||
							recipeType == RecipeType.BLASTING ||
			recipeType== RecipeType.CAMPFIRE_COOKING){
				Map<Identifier, Recipe<?>> scrambledCrafting = scrambleRecipes(entry.getValue(),outputs);
				scrambledRecipes.put(entry.getKey(),scrambledCrafting);
			} else {
				scrambledRecipes.put(entry.getKey(),entry.getValue());
			}
		}
		return scrambledRecipes;
	}

	public static Map<Identifier, Recipe<?>> scrambleRecipes(Map<Identifier, Recipe<?>> original, List<Recipe<?>> outputs){
		Map<Identifier, Recipe<?>> scrambledCrafting = new HashMap<>();
		for (Map.Entry<Identifier,Recipe<?>> entry : original.entrySet()){
			Recipe<?> recipe = entry.getValue();
			if (recipe instanceof ShapelessRecipe) {
				ItemStack newResult = outputs.get(random.nextInt(outputs.size())).getOutput().copy();
				if (!newResult.isEmpty()) {
					((ShapelessRecipeAccessor) recipe).setOutput(newResult);
					scrambledCrafting.put(entry.getKey(), recipe);
				}
			} else if (recipe instanceof ShapedRecipe) {
				ItemStack newResult = outputs.get(random.nextInt(outputs.size())).getOutput().copy();
				if (!newResult.isEmpty()) {
					((ShapedRecipeAccessor) recipe).setOutput(newResult);
					scrambledCrafting.put(entry.getKey(), recipe);
				}
			} else if (recipe instanceof AbstractCookingRecipe) {
				ItemStack newResult = outputs.get(random.nextInt(outputs.size())).getOutput().copy();
				if (!newResult.isEmpty()) {
					((AbstractCookingRecipeAccessor) recipe).setOutput(newResult);
					scrambledCrafting.put(entry.getKey(), recipe);
				}
			}
			else {
				scrambledCrafting.put(entry.getKey(),entry.getValue());
			}
		}
		return scrambledCrafting;
	}
}
