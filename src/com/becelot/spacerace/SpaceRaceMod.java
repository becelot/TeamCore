package com.becelot.spacerace;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;

import com.becelot.spacerace.block.TeamSelectionBlock;
import com.becelot.spacerace.block.TeamSelectionItemBlock;
import com.becelot.spacerace.dimension.SpaceEnterCommand;
import com.becelot.spacerace.dimension.SpaceProvider;
import com.becelot.spacerace.player.PlayerEvent;
import com.becelot.spacerace.player.PlayerTracker;
import com.becelot.spacerace.team.TeamRegisterCommand;
import com.becelot.spacerace.team.TeamSendCommand;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "spaceracemod", name="SpaceRace", version="0.0.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class SpaceRaceMod {
	
	public static final int dimensionId = 5;
	
	@Instance(value= "spaceracemod")
	public static SpaceRaceMod instance;
	public static Block teamSelectionBlock;
	
	@EventHandler
	public void preLoad(FMLPreInitializationEvent event) {
		DimensionManager.registerProviderType(SpaceConfig.dimensionId, SpaceProvider.class, false);
		DimensionManager.registerDimension(SpaceConfig.dimensionId, SpaceConfig.dimensionId);
		
		teamSelectionBlock = new TeamSelectionBlock(SpaceConfig.teamSelectionId, Material.ground);
		GameRegistry.registerBlock(teamSelectionBlock, TeamSelectionItemBlock.class, "teamSelectionBlock");
		
		
		LanguageRegistry.addName(new ItemStack(teamSelectionBlock, 1, 0), "Team Selection Block");
		LanguageRegistry.addName(new ItemStack(teamSelectionBlock, 1, 1), "Team Red");
		LanguageRegistry.addName(new ItemStack(teamSelectionBlock, 1, 2), "Team Yellow");
		LanguageRegistry.addName(new ItemStack(teamSelectionBlock, 1, 3), "Team Green");
		LanguageRegistry.addName(new ItemStack(teamSelectionBlock, 1, 4), "Team Blue");
		LanguageRegistry.addName(new ItemStack(teamSelectionBlock, 1, 5), "Team Cyan");
		LanguageRegistry.addName(new ItemStack(teamSelectionBlock, 1, 6), "Team Pink");
		GameRegistry.registerPlayerTracker(new PlayerTracker());
		
		MinecraftForge.EVENT_BUS.register(new PlayerEvent());
	}
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		event.registerServerCommand(new SpaceEnterCommand());
		event.registerServerCommand(new TeamSendCommand());
		event.registerServerCommand(new TeamRegisterCommand());
	}

}
