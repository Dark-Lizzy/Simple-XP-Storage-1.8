package com.darkliz.simplexpstorage.init;


import com.darkliz.simplexpstorage.Reference;
import com.darkliz.simplexpstorage.items.ManaCrystal;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SXPSItems extends Item{
	
	public static Item mana_crystal_empty;
	public static Item mana_crystal_full;
	
	public static void init()
	{
		
		mana_crystal_empty = new ManaCrystal(true).setUnlocalizedName("mana_crystal_empty");
		register(mana_crystal_empty);
		mana_crystal_full = new ManaCrystal(false).setUnlocalizedName("mana_crystal_full");
		register(mana_crystal_full);
		
	}
	
	
	public static void registerRenders()
	{
		
		registerRender(mana_crystal_empty);
		registerRender(mana_crystal_full);
		
	}
	
	
	
	public static void register(Item item)
	{
		GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
	}
	
	public static void registerRender(Item item)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().
		register(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}


