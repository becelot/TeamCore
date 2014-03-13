package com.becelot.spacerace.setup.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.EnumGameType;

import com.becelot.spacerace.SpaceConfig;
import com.becelot.spacerace.SpaceraceState;
import com.becelot.spacerace.command.CommandHandler;
import com.becelot.spacerace.dimension.DimensionTeleporter;
import com.becelot.spacerace.setup.TeamBuildPhase;
import com.becelot.spacerace.util.Chat;
import com.becelot.spacerace.util.SetupStructureBuilder;

public class SpaceraceStartCommand extends CommandHandler {

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		//If the race is not ongoing
		if (SpaceConfig.raceState == SpaceraceState.SR_IDLE) {
			//Transfer every Player into the setup dimension
			for(Object player : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
				if (player instanceof EntityPlayerMP) {
					EntityPlayerMP playerMP = (EntityPlayerMP)player;
					DimensionTeleporter.transferPlayerToDimension(playerMP, SpaceConfig.dimensionId, 0, 1, 0);
				}
			}
			//transistion to new state
			SpaceConfig.raceState = SpaceraceState.SR_PREPARING;
			SpaceConfig.buildPhase = TeamBuildPhase.TBP_SETUP;
		} else if (SpaceConfig.buildPhase == TeamBuildPhase.TBP_DONE) {
			//TODO: Add Countdown, take this functionality, if ready
			//TODO: Register player listener, that prevents one time fall damage!
			//Remove all cages
			for (Object o : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
				EntityPlayerMP player = (EntityPlayerMP)o;
				player.setGameType(EnumGameType.SURVIVAL);
			}
			SetupStructureBuilder.buildWorldCage(MinecraftServer.getServer().worldServerForDimension(0), 0);
			SpaceConfig.raceState = SpaceraceState.SR_STARTED;
			Chat.sendToAllPlayersFromRegistry("command.startrace.start");
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender) {
		return icommandsender.getCommandSenderName().equals(SpaceConfig.gameMod);
	}

}
