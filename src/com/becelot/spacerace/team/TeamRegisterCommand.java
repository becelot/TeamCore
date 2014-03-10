package com.becelot.spacerace.team;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.util.ChatMessageComponent;

import com.becelot.spacerace.command.ICommandHandler;

public class TeamRegisterCommand implements ICommandHandler {
	private TeamManager teamManager;
	
	private ChatMessageComponent invalidTeamNameMessage;

	public TeamRegisterCommand() {
		teamManager = TeamManager.getInstance();
		
		invalidTeamNameMessage = new ChatMessageComponent();
		invalidTeamNameMessage.addText("An error occured.");
		invalidTeamNameMessage.addText("The team name is not valid!");
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
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
	}
	
}
