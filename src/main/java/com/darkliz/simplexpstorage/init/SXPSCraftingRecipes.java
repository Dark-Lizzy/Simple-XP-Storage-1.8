package com.darkliz.simplexpstorage.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SXPSCraftingRecipes {

	public static void doCraftingRecipes()
	{
	
	
	GameRegistry.addShapelessRecipe(new ItemStack(SXPSItems.mana_crystal_empty, 2), 
			new Object[] {	Items.quartz, 
							new ItemStack(Items.dye, 1, 4)}); //lapis lazuli
	}
	
}
