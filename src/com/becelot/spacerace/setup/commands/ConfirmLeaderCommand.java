package com.becelot.spacerace.setup.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;

import com.becelot.spacerace.SpaceConfig;
import com.becelot.spacerace.command.CommandHandler;
import com.becelot.spacerace.team.Team;
import com.becelot.spacerace.team.TeamManager;
import com.becelot.spacerace.util.Chat;
import com.becelot.spacerace.util.SetupStructureBuilder;

public class ConfirmLeaderCommand extends CommandHandler{

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		Chat.sendToAllPlayersFromRegistry("command.confirmleader.confirmed");
		TeamManager manager = TeamManager.getInstance();
		
		//Teleport all leaders up
		for(Team team : manager.getTeams()) {
			EntityPlayerMP player = team.getTeamLeader();
			player.setPositionAndUpdate(player.posX, player.posY+SetupStructureBuilder.height, player.posZ);
			Chat.sendToPlayer(player, "command.confirmleader.instructions");
		}
		
		
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender) {
		return icommandsender.getCommandSenderName().equals(SpaceConfig.gameMod);
	}
	
}
