package com.becelot.teamcore.block;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class TeamSelectionItemBlock extends ItemBlock {

	public TeamSelectionItemBlock(int par1) {
		super(par1);
		this.setHasSubtypes(true);
	}
	
	public String getUnlocalizedName(ItemStack itemstack)
	{
	       return "teamSelection" + itemstack.getItemDamage();
	}
	
	public int getMetadata(int meta) {
		return meta;
	}

}
