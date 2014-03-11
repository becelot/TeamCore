package com.becelot.spacerace.setup;

import net.minecraft.entity.player.EntityPlayer;

public interface ITeamBlockInteractionEvent {
	public void interact(EntityPlayer entityPlayer, int team, double x, double y, double z);
}
