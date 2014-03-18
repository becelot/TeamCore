package com.becelot.teamcore;

import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;

import com.becelot.teamcore.block.TeamSelectionBlock;
import com.becelot.teamcore.block.UnbreakableGlass;
import com.becelot.teamcore.command.GenericCommand;
import com.becelot.teamcore.dimension.SpaceProvider;
import com.becelot.teamcore.player.PlayerEvent;
import com.becelot.teamcore.player.PlayerTracker;
import com.becelot.teamcore.setup.SetupCommand;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "teamcoremod", name=TeamCoreMod.modName, version="0.0.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class TeamCoreMod {
	
	public static final int dimensionId = 5;
	public static final String modid = "teamcoremod";
	public static final String modName = "TeamCore";
	
	@Instance(value=TeamCoreMod.modid)
	public static TeamCoreMod instance;
	
	public static PlayerEvent eventManager;

	
	@EventHandler
	public void preLoad(FMLPreInitializationEvent event) {
		TeamConfig.loadConfig(event);
		
		DimensionManager.registerProviderType(TeamConfig.dimensionId, SpaceProvider.class, false);
		DimensionManager.registerDimension(TeamConfig.dimensionId, TeamConfig.dimensionId);
		
		TeamSelectionBlock.register();
		UnbreakableGlass.register();

		GameRegistry.registerPlayerTracker(new PlayerTracker());
		
		eventManager = new PlayerEvent();
		MinecraftForge.EVENT_BUS.register(eventManager);
	}
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		GenericCommand.registerCommands(event);
		SetupCommand.registerCommands(event);
	}

}
