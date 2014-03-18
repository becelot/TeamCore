package com.becelot.teamcore.setup.state;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import com.becelot.teamcore.TeamConfig;
import com.becelot.teamcore.dimension.DimensionTeleporter;
import com.becelot.teamcore.setup.FSMTeamBuilderState;

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
				DimensionTeleporter.transferPlayerToDimension(playerMP, TeamConfig.dimensionId, 0, 1, 0);
			}
		}
		
		this.switchState(fsmTeamLimit);
	}

}
