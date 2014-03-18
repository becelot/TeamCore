package com.becelot.teamcore.setup.state;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import com.becelot.teamcore.SpaceConfig;
import com.becelot.teamcore.setup.FSMTeamBuilderState;
import com.becelot.teamcore.team.Team;
import com.becelot.teamcore.team.TeamManager;
import com.becelot.teamcore.util.Chat;
import com.becelot.teamcore.util.SetupStructureBuilder;

public class ConfirmLeaderState extends FSMTeamBuilderState {

	public ConfirmLeaderState() {
		super();
		this.requiredCommandNames.add("resetleader");
		this.requiredCommandNames.add("confirmleader");
	}

	@Override
	public void interact(EntityPlayer entityPlayer, int team, int x, int y,
			int z) {
		// Nothing here

	}
	
	private void resetLeader() {
		for (Object o : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
			EntityPlayer player = (EntityPlayer)o;
			player.setPositionAndUpdate(0, 1, 0);
		}
		SetupStructureBuilder.buildTeamSelection(MinecraftServer.getServer().worldServerForDimension(SpaceConfig.dimensionId));
		Chat.sendToAllPlayersFromRegistry("command.resetleader.reseted");
		
		this.switchState(fsmRegisterTeam);
	}
	
	private void confirmLeader() {
		Chat.sendToAllPlayersFromRegistry("command.confirmleader.confirmed");
		TeamManager manager = TeamManager.getInstance();
		
		//Teleport all leaders up
		for(Team team : manager.getTeams()) {
			EntityPlayerMP player = team.getTeamLeader();
			player.setPositionAndUpdate(player.posX, player.posY+SetupStructureBuilder.height, player.posZ);
			Chat.sendToPlayer(player, "command.confirmleader.instructions");
		}
		
		this.switchState(fsmTeamColorSelection);
	}

	@Override
	public void commandSend(String command, ICommandSender icommandsender, String[] args) {
		if (command.equals("resetleader")) {
			resetLeader();
		} else if (command.equals("confirmleader")) {
			confirmLeader();
		}
	}

}
