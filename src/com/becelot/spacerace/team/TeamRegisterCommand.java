package com.becelot.spacerace.team;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.StatCollector;

import com.becelot.spacerace.SpaceConfig;
import com.becelot.spacerace.command.CommandHandler;
import com.becelot.spacerace.setup.TeamBuildPhase;
import com.becelot.spacerace.util.Chat;

public class TeamRegisterCommand extends CommandHandler {
	private TeamManager teamManager;
	
	private ChatMessageComponent invalidTeamNameMessage;

	public TeamRegisterCommand() {
		teamManager = TeamManager.getInstance();
		
		invalidTeamNameMessage = new ChatMessageComponent();
		invalidTeamNameMessage.addText("An error occured.\n");
		invalidTeamNameMessage.addText("The team name is not valid!");
	}


	public void processCommand(ICommandSender icommandsender, String[] astring) {
		//Make sure, that we are currently accepting new leaders
		if (SpaceConfig.buildPhase != TeamBuildPhase.TBP_CHOOSE_LEADERS) return;
		if (teamManager.getTeamCount() >= SpaceConfig.teamCount) return;
		
		
		if (icommandsender instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP)icommandsender;
			
			//If player has no team
			if (TeamManager.getInstance().getTeamByPlayerName(player) == null) {
				if (astring.length > 0) {
					Team t = teamManager.registerTeam(astring[0]);
					if (t == null) {
						player.sendChatToPlayer(invalidTeamNameMessage);
					} else {
						//Create team and inform players
						t.setLeader(player);
						ChatMessageComponent teamRegisteredMessage = new ChatMessageComponent();
						teamRegisteredMessage.addText("Team " + t.getTeamName() + " created by " + player.getDisplayName());
						player.mcServer.getConfigurationManager().sendPacketToAllPlayers(new Packet3Chat(teamRegisteredMessage, true));
					}
				}
			}
		}
		
		if (teamManager.getTeamCount() == SpaceConfig.teamCount) {
			Chat.sendToAllPlayersFromRegistry("command.registerteam.sucessfull.all");
			Chat.sendToGameMod(StatCollector.translateToLocal("command.registerteam.sucessfull.gamemod"));
			SpaceConfig.buildPhase = TeamBuildPhase.TBP_CONFIRM_LEADERS;
		}
	}
	
}
