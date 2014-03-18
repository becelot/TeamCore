package com.becelot.teamcore;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public final class SpaceConfig {
	public static int teamSelectionId = 500;
	public static int unbreakableGlassId = 501;
	public static String gameMod = "onlinefreak";
	
	public static int dimensionId = 5;
	public static int maxTeams = 7;
	
	public static int pvpPreventionMinutes;
	public static boolean pvpFriendlyFireOn;
	
	public static SpaceraceState raceState = SpaceraceState.SR_IDLE;
	
	public static int teamCount = 4;
	public static int minMemberCount = 2;
	public static int maxMemberCount = 3;
	
	public static void loadConfig(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		
		config.load();
		
		teamSelectionId = config.getBlock("teamSelectionBlock", 500).getInt();
		unbreakableGlassId = config.getBlock("unbreakableGlass", 501).getInt();
		
		gameMod = config.get(Configuration.CATEGORY_GENERAL, "gameMod", "onlinefreak").getString();
		dimensionId = config.get(Configuration.CATEGORY_GENERAL, "dimensionId", 5).getInt();
		maxTeams = config.get(Configuration.CATEGORY_GENERAL, "maxTeams", 7).getInt();
		
		pvpPreventionMinutes = config.get(Configuration.CATEGORY_GENERAL, "pvpPreventionMinutes", 30).getInt();
		pvpFriendlyFireOn = config.get("PVP", "pvpFriendlyFireOn", false).getBoolean(false);
		
		if (pvpPreventionMinutes < 0)
			pvpPreventionMinutes = 0;
		
		config.save();
	}
	
}
