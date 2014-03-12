package com.becelot.spacerace.setup.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.StatCollector;

import com.becelot.spacerace.SpaceConfig;
import com.becelot.spacerace.SpaceraceState;
import com.becelot.spacerace.command.CommandHandler;
import com.becelot.spacerace.player.PlayerEvent;
import com.becelot.spacerace.setup.TeamBuildPhase;
import com.becelot.spacerace.setup.TeamLeaderInteraction;
import com.becelot.spacerace.util.Chat;
import com.becelot.spacerace.util.SetupStructureBuilder;

public class TeamLimitsCommand extends CommandHandler {

	private ChatMessageComponent invalidArgument = Chat.fromRegistry("command.teamlimit.invalidargument");
	private ChatMessageComponent invalidArgumentCount = Chat.fromRegistry("command.teamlimit.invalidargumentcount");

	public void processCommand(ICommandSender icommandsender, String[] astring) {
		if (SpaceConfig.raceState == SpaceraceState.SR_PREPARING && SpaceConfig.buildPhase == TeamBuildPhase.TBP_SETUP) {
			if (astring.length <= 2) {
				//Confirm selection
				if (astring.length == 1) {
					if (astring[0].toLowerCase().equals("ok")) {
						Chat.sendToAllPlayersFromRegistryFormatted("command.teamlimit.successfull.server", 
								SpaceConfig.teamCount, SpaceConfig.minMemberCount, SpaceConfig.maxMemberCount);
						
						SetupStructureBuilder.buildMidCage(MinecraftServer.getServer().worldServerForDimension(SpaceConfig.dimensionId), SpaceConfig.unbreakableGlassId);
						SetupStructureBuilder.buildTeamSelection(MinecraftServer.getServer().worldServerForDimension(SpaceConfig.dimensionId));
						SpaceConfig.buildPhase = TeamBuildPhase.TBP_CHOOSE_LEADERS;
						PlayerEvent.registerTeamInteractionListener(new TeamLeaderInteraction());
						return;
					}
				}
				
				//No valid arguements given
				icommandsender.sendChatToPlayer(invalidArgumentCount);
				return;
			}

			//Parse arguments and safe them
			try {
				int teamCount = Integer.parseInt(astring[0]);
				int minMemberCount = Integer.parseInt(astring[1]);
				int maxMemberCount = Integer.parseInt(astring[2]);

				if (minMemberCount > maxMemberCount) throw new Exception();

				SpaceConfig.teamCount = teamCount;
				SpaceConfig.minMemberCount = minMemberCount;
				SpaceConfig.maxMemberCount = maxMemberCount;
			} catch (Exception e) {
				icommandsender.sendChatToPlayer(invalidArgument);
				return;
			}

			//Send confirmation to GameMod
			Chat.sendToPlayerFormatted((EntityPlayer)icommandsender, "command.teamlimit.successfull", 
					SpaceConfig.teamCount, SpaceConfig.minMemberCount, SpaceConfig.maxMemberCount);
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender) {
		return icommandsender.getCommandSenderName().equals(SpaceConfig.gameMod);
	}

}
