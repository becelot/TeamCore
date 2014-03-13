package com.becelot.spacerace.setup.state;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import com.becelot.spacerace.SpaceConfig;
import com.becelot.spacerace.dimension.DimensionTeleporter;
import com.becelot.spacerace.setup.FSMTeamBuilderState;

public class FSMIdleState extends FSMTeamBuilderState {
	
	public FSMIdleState() {
		this.requiredCommandNames.add("startrace");
	}

	@Override
	public void interact(EntityPlayer entityPlayer, int team, int x, int y,
			int z) {
		// Nothing here
		
	}

	@Override
	public void commandSend(String command, ICommandSender icommandsender, String[] args) {
		for(Object player : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
			if (player instanceof EntityPlayerMP) {
				EntityPlayerMP playerMP = (EntityPlayerMP)player;
				DimensionTeleporter.transferPlayerToDimension(playerMP, SpaceConfig.dimensionId, 0, 1, 0);
			}
		}
		
		this.switchState(fsmTeamLimit);
	}

}
