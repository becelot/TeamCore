package com.becelot.spacerace.setup;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import com.becelot.spacerace.SpaceConfig;
import com.becelot.spacerace.SpaceraceState;
import com.becelot.spacerace.command.CommandHandler;
import com.becelot.spacerace.dimension.DimensionTeleporter;

public class SpaceraceStartCommand extends CommandHandler {

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		if (SpaceConfig.raceState == SpaceraceState.SR_IDLE) {
			for(Object player : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
				if (player instanceof EntityPlayerMP) {
					EntityPlayerMP playerMP = (EntityPlayerMP)player;
					DimensionTeleporter.transferPlayerToDimension(playerMP, SpaceConfig.dimensionId);
				}
			}
			SpaceConfig.raceState = SpaceraceState.SR_PREPARING;
			SpaceConfig.buildPhase = TeamBuildPhase.TBP_SETUP;
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender) {
		return true;
	}

}
