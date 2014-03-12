package com.becelot.spacerace.team;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.StatCollector;

import com.becelot.spacerace.command.CommandHandler;
import com.becelot.spacerace.util.Chat;

public class TeamSendCommand extends CommandHandler {
	
	ChatMessageComponent invalidUsageMessage = Chat.fromRegistry("command.teamsend.invalidusage");
	ChatMessageComponent invalidTeamNameMessage = Chat.fromRegistry("command.teamsend.nosuchteam");
	ChatMessageComponent noMemberMessage = Chat.fromRegistry("command.teamsend.nomember");


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
					player.sendChatToPlayer(noMemberMessage);
					return;
				}
				String message = "";
				for (int i = 1; i < astring.length; i++) {
					message += astring[i] + " ";
				}
				target.sendMessageToTeam(
						StatCollector.translateToLocalFormatted("command.teamsend.format", 
								player.getDisplayName(), 
								from.getTeamColor().getPrefix()+from.getTeamName(), 
								message
							)
						);
				
			} else {
				icommandsender.sendChatToPlayer(invalidUsageMessage);
			}
		}
	}

}
