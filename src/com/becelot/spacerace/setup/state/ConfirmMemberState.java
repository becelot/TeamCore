package com.becelot.spacerace.setup.state;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StatCollector;

import com.becelot.spacerace.SpaceConfig;
import com.becelot.spacerace.dimension.DimensionTeleporter;
import com.becelot.spacerace.setup.FSMTeamBuilderState;
import com.becelot.spacerace.team.Team;
import com.becelot.spacerace.team.TeamManager;
import com.becelot.spacerace.util.Chat;
import com.becelot.spacerace.util.SetupStructureBuilder;

public class ConfirmMemberState extends FSMTeamBuilderState {

	public ConfirmMemberState() {
		super();
		this.requiredCommandNames.add("confirmmember");
		this.requiredCommandNames.add("resetmember");
	}

	@Override
	public void interact(EntityPlayer entityPlayer, int team, int x, int y,
			int z) {
		// Nothing here

	}

	@Override
	public void commandSend(String command, ICommandSender icommandsender,
			String[] args) {
		if (command.equals("confirmmember")) {
			confirmMember();
		} else if (command.equals("resetmember")) {
			resetMember();
		}

	}

	private void resetMember() {
		for (Object o : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
			EntityPlayer player = (EntityPlayer)o;
			DimensionTeleporter.transferPlayerToDimension((EntityPlayerMP)player, SpaceConfig.dimensionId, 0, SetupStructureBuilder.height + 1, 0);;
		}
		
		for (Team team : TeamManager.getInstance().getTeams()) {
			double angle = 360f / SpaceConfig.maxTeams * team.getId();
			int x = (int)Math.round((SetupStructureBuilder.radius + SetupStructureBuilder.distance) * Math.cos(angle * Math.PI /360f));
			int z = (int)Math.round((SetupStructureBuilder.radius + SetupStructureBuilder.distance) * Math.cos(angle * Math.PI /360f));
			team.getTeamLeader().setPositionAndUpdate(x, 2, z);
		}
		
		Chat.sendToAllPlayersFromRegistry("command.resetmember.reseted");
		
		this.switchState(fsmTeamMemberSelection);
	}

	private void confirmMember() {
		
		Chat.sendToAllPlayersFromRegistry("command.confirmmember.confirmed");
		Chat.sendToGameMod(StatCollector.translateToLocal("command.confirmmember.confirmed.gamemod"));
		
		this.switchState(fsmReady);
	}

}
