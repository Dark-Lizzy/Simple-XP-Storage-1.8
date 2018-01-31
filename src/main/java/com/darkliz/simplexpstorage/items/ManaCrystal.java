package com.darkliz.simplexpstorage.items;

import com.darkliz.simplexpstorage.init.SXPSItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// Code by Dark Lizzy //


public class ManaCrystal extends Item {

	private boolean isEmpty;
	
	
	public ManaCrystal(boolean isEmpty)
	{
		this.isEmpty = isEmpty;
		this.setCreativeTab(CreativeTabs.tabMisc);
		
	}
	
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
		
		
		//Prevent transferring of mana while in creative mode
		if(playerIn.capabilities.isCreativeMode)
		{
			return itemStackIn;
		}
		
		
		//System.out.println("experienceTotal: " + playerIn.experienceTotal); //DEBUG MESSAGE
		
		
		// Get the correct amount of current xp from experienceLevel + experience
		// This fixes the problem of xp being calculated incorrectly after using the anvil (anvil removes levels, not xp)
		
		int Level = playerIn.experienceLevel - 1;
		int correctTotal = 0;
		int xpRemainder = Math.round(playerIn.experience * playerIn.xpBarCap());
		while(Level >= 0)
		{
			int xpForLevel;
			if(Level >= 30)
		    {	
				xpForLevel = 112 + (Level - 30) * 9; 
		    }
		    else if(Level >= 15)
		    {   	
		    	xpForLevel = 37 + (Level - 15) * 5;
		    }
		    else
		    {
		    	xpForLevel = 7 + Level * 2;
		    }
			correctTotal = correctTotal + xpForLevel;
			Level--;
		}
		correctTotal = correctTotal + xpRemainder;
		

		//System.out.println("XP Remainder: " + playerIn.experience * playerIn.xpBarCap());//xpRemainder);  //DEBUG MESSAGE
		//System.out.println("XP Remainder Rounded: " + xpRemainder); //DEBUG MESSAGE
		//System.out.println("XP Bar Cap: " + playerIn.xpBarCap()); //DEBUG MESSAGE
		//System.out.println("Correct XP: " + correctTotal); //DEBUG MESSAGE
		
		
		// Continue with original code using correct xp total
		
		Item itemToReturn;
		int amount = 0;
		int xp = correctTotal;//playerIn.experienceTotal;
		
		// Also set experienceTotal to correct amount
		playerIn.experienceTotal = correctTotal;
		
        if(this.isEmpty) //if using an empty mana crystal
        {
        	if(playerIn.isSneaking())
        	{
        		amount = itemStackIn.stackSize;
        		if(xp >= 7 * amount)
            	{
        			//System.out.println("if 1: " + xp);
            		this.removeExperience(playerIn, 7 * amount);
            		itemStackIn.stackSize = 0;
            	}
        		else
        		{
        			if(xp >= 7)
        			{
        				
        				int xpToRemove = 0;
        				amount = 0;
        				while(xp >= 7 && itemStackIn.stackSize > 0)
	        			{
        					xpToRemove = xpToRemove + 7;
        					xp = xp - 7;
                    		itemStackIn.stackSize--;
                    		amount++;
	        			}
        				this.removeExperience(playerIn, xpToRemove);
        				
        			}
        			else
        			{
        				
            			return itemStackIn;
        			}
        		}
        		worldIn.playSoundAtEntity(playerIn, "random.orb", 0.2F, 0.5F * ((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.7F + 1.8F));
        	}
        	else
        	{
        		if(xp >= 7)
            	{
            		this.removeExperience(playerIn, 7);
            		itemStackIn.stackSize--;
            		amount = 1;
            	}
        		else
        		{
        			
        			return itemStackIn;
        		}
        	}
        	itemToReturn = SXPSItems.mana_crystal_full;
        	
        }
        else //If using a full mana crystal
        {
        	if(playerIn.isSneaking())
        	{
        		amount = itemStackIn.stackSize;
        		playerIn.addExperience(7 * amount);
        		itemStackIn.stackSize = 0;
        		worldIn.playSoundAtEntity(playerIn, "random.orb", 0.2F, 0.5F * ((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.7F + 1.8F));
        	}
        	else
        	{
        		playerIn.addExperience(7);
            	itemStackIn.stackSize--;
            	amount = 1;
        	}
        	itemToReturn = SXPSItems.mana_crystal_empty;
        	
        }
        
        if(amount > 0)
    	{
        	if(playerIn.inventory.getFirstEmptyStack() >= 0)
        	{
        		playerIn.inventory.addItemStackToInventory(new ItemStack(itemToReturn, amount));
        	}
        	else
        	{
        		//spawn an item entity on the ground
        		if(!worldIn.isRemote)
    			{
        			playerIn.dropItem(itemToReturn, amount);
        			
    			}
        	}
        	
    	}
        
        //System.out.println("xp after: " + playerIn.experienceTotal); //DEBUG MESSAGE
        
        worldIn.playSoundAtEntity(playerIn, "random.orb", 0.1F, 0.5F * ((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.7F + 1.8F));
        return itemStackIn;
    }
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
	    return !this.isEmpty;
	}
	
	
	public void removeExperience(EntityPlayer playerIn, int amount)
    {
    	
    	if(playerIn.experienceTotal - amount < 0)
    	{
    		amount = playerIn.experienceTotal;
    	}
    	
    	if (amount == 0) return;
    	
    	
        playerIn.experience -= (float)amount / (float)playerIn.xpBarCap();
        
        for (playerIn.experienceTotal -= amount; playerIn.experience < 0.0F; playerIn.experience = 1.0F - playerIn.experience / (float)playerIn.xpBarCap())
        {
        	playerIn.experience = Math.abs(playerIn.experience) * (float)playerIn.xpBarCap();
        	playerIn.removeExperienceLevel(1);
        }
        
        if (playerIn.experienceTotal == 0)
        {
        	playerIn.experienceLevel = 0;
        	playerIn.experience = 0.0F;
        }
    }
	
	
}
