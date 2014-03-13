package com.becelot.spacerace;

import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;

import com.becelot.spacerace.block.TeamSelectionBlock;
import com.becelot.spacerace.block.UnbreakableGlass;
import com.becelot.spacerace.command.GenericCommand;
import com.becelot.spacerace.dimension.SpaceProvider;
import com.becelot.spacerace.player.PlayerEvent;
import com.becelot.spacerace.player.PlayerTracker;
import com.becelot.spacerace.setup.SetupCommand;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "spaceracemod", name="SpaceRace", version="0.0.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class SpaceRaceMod {
	
	public static final int dimensionId = 5;
	
	@Instance(value= "spaceracemod")
	public static SpaceRaceMod instance;

	
	@EventHandler
	public void preLoad(FMLPreInitializationEvent event) {
		SpaceConfig.loadConfig(event);
		
		DimensionManager.registerProviderType(SpaceConfig.dimensionId, SpaceProvider.class, false);
		DimensionManager.registerDimension(SpaceConfig.dimensionId, SpaceConfig.dimensionId);
		
		TeamSelectionBlock.register();
		UnbreakableGlass.register();

		GameRegistry.registerPlayerTracker(new PlayerTracker());
		
		MinecraftForge.EVENT_BUS.register(new PlayerEvent());
	}
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		GenericCommand.registerCommands(event);
		SetupCommand.registerCommands(event);
	}

}
