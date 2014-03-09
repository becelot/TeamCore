package com.becelot.spacerace.team;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatMessageComponent;

public class TeamSendCommand implements ICommand {
	List<String> aliases;
	
	ChatMessageComponent invalidUsageMessage;
	ChatMessageComponent invalidTeamNameMessage;
	
	public TeamSendCommand() {
		aliases = new ArrayList<>();
		aliases.add("teamsend");
		aliases.add("ts");
		
		invalidUsageMessage = new ChatMessageComponent();
		invalidUsageMessage.addText("Invalid argument");
		invalidUsageMessage.addText(this.getCommandUsage(null));
		
		invalidTeamNameMessage = new ChatMessageComponent();
		invalidTeamNameMessage.addText("Team not found");
		invalidTeamNameMessage.addText(this.getCommandUsage(null));
	}

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "teamsend";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "/teamsend <teamname> <message>";
	}

	@Override
	public List<String> getCommandAliases() {
		return this.aliases;
	}

	@Override
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

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender) {
		return true;
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender icommandsender,
			String[] astring) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] astring, int i) {
		return false;
	}

}
