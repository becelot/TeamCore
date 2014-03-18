package com.becelot.teamcore.block;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;

public class UnbreakableGlass {
	public static final Block glass = (new BlockGlass(501, Material.glass, false)).setBlockUnbreakable().setResistance(6000000.0F).setStepSound(Block.soundGlassFootstep).setUnlocalizedName("glass").setTextureName("glass");

	public static void register() {
		GameRegistry.registerBlock(glass, "unbreakableGlass");
		LanguageRegistry.addName(glass, "Unbreakable Glass");
	}
}
