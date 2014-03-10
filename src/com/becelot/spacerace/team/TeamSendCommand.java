package com.becelot.spacerace.team;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatMessageComponent;

import com.becelot.spacerace.command.ICommandHandler;

public class TeamSendCommand implements ICommandHandler {
	
	ChatMessageComponent invalidUsageMessage;
	ChatMessageComponent invalidTeamNameMessage;
	
	public TeamSendCommand() {
		
		invalidUsageMessage = new ChatMessageComponent();
		invalidUsageMessage.addText("Invalid argument");
		
		invalidTeamNameMessage = new ChatMessageComponent();
		invalidTeamNameMessage.addText("Team not found");
	}


	public void processCommand(ICommandSender icommandsender, String[] astring) {
		if (icommandsender instanceof EntityPlayerMP) {
			if (astring.length >= 2) {
				EntityPlayerMP player = (EntityPlayerMP)icommandsender;
				Team target = TeamManager.getInstance().getTeamByTeamName(astring[0]);
				if (target == null) {
					player.sendChatToPlayer(invalidTeamNameMessage);
					return;
				}
				Team from = TeamManager.getInstance().getTeamByPlayerName(player);
				if (from == null) {
					player.sendChatToPlayer(ChatMessageComponent.createFromText("You suck"));
					return;
				}
				String message = "";
				for (int i = 1; i < astring.length; i++) {
					message += astring[i] + " ";
				}
				target.sendMessageToTeam(player.getDisplayName() + " [" + from.getTeamName() +"]"+ ": " + message);
				
			} else {
				icommandsender.sendChatToPlayer(invalidUsageMessage);
			}
		}
	}

}
