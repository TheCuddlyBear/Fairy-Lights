package me.paulf.fairylights.util.crafting;

import me.paulf.fairylights.server.item.FLItems;
import me.paulf.fairylights.server.item.crafting.FLCraftingRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class HangingLightsRecipe extends CustomRecipe {
    private final Supplier<? extends RecipeSerializer<HangingLightsRecipe>> serializer;

    private ItemStack result = ItemStack.EMPTY;

    public HangingLightsRecipe(final Supplier<? extends RecipeSerializer<HangingLightsRecipe>> serializer) {
        super(CraftingBookCategory.MISC);
        this.serializer = serializer;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        if(input.size() >= 3){
            ItemStack first = input.getItem(0);
            ItemStack second = input.getItem(1);
            ItemStack third = input.getItem(2);
            if (first.getItem() == Items.IRON_INGOT && second.getItem() == Items.STRING && third.getItem() == Items.IRON_INGOT) { // Base case
                if(input.size() == 3){
                    this.result = new ItemStack(FLItems.HANGING_LIGHTS.get());
                    return true;
                }
                if(input.size() > 3){
                    for(int i = 4; i < input.size(); i++){
                        ItemStack stack = input.getItem(i);
                        if(!stack.is(FLCraftingRecipes.LIGHTS)){
                            this.result = ItemStack.EMPTY;
                            return false;
                        }
                    }
                    this.result = new ItemStack(FLItems.HANGING_LIGHTS.get());
                    return true;
                }else{
                    this.result = ItemStack.EMPTY;
                    return false;
                }
            }
        }
        this.result = ItemStack.EMPTY;
        return false;
    }

    @Override
    public @NotNull ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        final ItemStack result = this.result;
        return result.isEmpty() ? result : result.copy();
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider registries) {
        return new ItemStack(Items.NAME_TAG);
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return this.serializer.get();
    }
}
