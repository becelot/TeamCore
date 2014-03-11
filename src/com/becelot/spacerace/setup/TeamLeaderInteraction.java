package com.becelot.spacerace.setup;

import net.minecraft.entity.player.EntityPlayer;

public class TeamLeaderInteraction implements ITeamBlockInteractionEvent {

	@Override
	public void interact(EntityPlayer entityPlayer, int team, double x, double y,
			double z) {
		entityPlayer.setPosition(x, y+3, z);
	}



}
