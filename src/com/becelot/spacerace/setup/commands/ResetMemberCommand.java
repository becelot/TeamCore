package com.becelot.spacerace.setup.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import com.becelot.spacerace.SpaceConfig;
import com.becelot.spacerace.command.CommandHandler;
import com.becelot.spacerace.dimension.DimensionTeleporter;
import com.becelot.spacerace.team.Team;
import com.becelot.spacerace.team.TeamManager;
import com.becelot.spacerace.util.Chat;
import com.becelot.spacerace.util.SetupStructureBuilder;

public class ResetMemberCommand extends CommandHandler {

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
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

	}

}
