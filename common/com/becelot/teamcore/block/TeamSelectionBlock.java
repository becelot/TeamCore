package com.becelot.teamcore.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import com.becelot.teamcore.TeamConfig;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TeamSelectionBlock extends Block {
	private Icon[] icons;

	public TeamSelectionBlock(int par1, Material par2Material) {
		super(par1, par2Material);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setUnlocalizedName("testBlock");
	}
	
	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT) 
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, @SuppressWarnings("rawtypes") List par3List) {
		for(int i=0; i < TeamConfig.maxTeams; i++) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}
	
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return icons[meta];
	}
	
	public int damageDropped(int par1) {
	    return par1;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		icons = new Icon[TeamConfig.maxTeams];
		
		
		for (int i = 0; i < TeamConfig.maxTeams; i++) {
			icons[i] = par1IconRegister.registerIcon("spaceracemod:teamselection" + i);
		}
	}
	
	/*
	 * Registers all blocks of this type
	 */
	public static void register() {
		TeamSelectionBlock teamSelectionBlock = new TeamSelectionBlock(TeamConfig.teamSelectionId, Material.ground);
		GameRegistry.registerBlock(teamSelectionBlock, TeamSelectionItemBlock.class, "teamSelectionBlock");
		
		
		LanguageRegistry.addName(new ItemStack(teamSelectionBlock, 1, 0), "Team Selection Block");
		LanguageRegistry.addName(new ItemStack(teamSelectionBlock, 1, 1), "Team Red");
		LanguageRegistry.addName(new ItemStack(teamSelectionBlock, 1, 2), "Team Yellow");
		LanguageRegistry.addName(new ItemStack(teamSelectionBlock, 1, 3), "Team Green");
		LanguageRegistry.addName(new ItemStack(teamSelectionBlock, 1, 4), "Team Blue");
		LanguageRegistry.addName(new ItemStack(teamSelectionBlock, 1, 5), "Team Cyan");
		LanguageRegistry.addName(new ItemStack(teamSelectionBlock, 1, 6), "Team Purple");
	}

}
