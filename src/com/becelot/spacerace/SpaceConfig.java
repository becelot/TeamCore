package com.becelot.spacerace;

import com.becelot.spacerace.setup.TeamBuildPhase;

public final class SpaceConfig {
	public static int teamSelectionId = 500;
	public static int unbreakableGlassId = 501;
	public static String gameMod = "onlinefreak";
	
	public static final int dimensionId = 5;
	public static final int maxTeams = 7;
	
	public static SpaceraceState raceState = SpaceraceState.SR_IDLE;
	public static TeamBuildPhase buildPhase = TeamBuildPhase.TBP_IDLE;
	
	public static int teamCount = 4;
	public static int minMemberCount = 2;
	public static int maxMemberCount = 3;
	
}
