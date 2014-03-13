package com.becelot.spacerace.setup;

import net.minecraft.entity.player.EntityPlayer;

/*
 * Interface for PlayerInteractEvent loclization
 */
public interface ITeamBlockInteractionEvent {
	public void interact(EntityPlayer entityPlayer, int team, int x, int y, int z);
}
