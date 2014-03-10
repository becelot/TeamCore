package com.becelot.spacerace.setup;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatMessageComponent;

import com.becelot.spacerace.SpaceConfig;
import com.becelot.spacerace.command.CommandHandler;

public class TeamLimitsCommand extends CommandHandler {
	
	private ChatMessageComponent invalidArgument = ChatMessageComponent.createFromText("At least one argument was not a valid number");
	private ChatMessageComponent invalidArgumentCount = ChatMessageComponent.createFromText("Please provide all parameters: <teamcount> <minMembers> <maxMembers>");
	
	private String successfullSetup = "Team setup successfull. %d are competing, member count ranging from %d to %d";

	public void processCommand(ICommandSender icommandsender, String[] astring) {
		
		if (astring.length <= 2) {
			icommandsender.sendChatToPlayer(invalidArgumentCount);
			return;
		}
		
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
		
		icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText(String.format(successfullSetup, SpaceConfig.teamCount, SpaceConfig.minMemberCount, SpaceConfig.maxMemberCount)));
		SpaceConfig.setupTeams = true;
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender) {
		return icommandsender.getCommandSenderName().equals(SpaceConfig.gameMod);
	}

}
