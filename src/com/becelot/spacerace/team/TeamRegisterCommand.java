package com.becelot.spacerace.team;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.util.ChatMessageComponent;

public class TeamRegisterCommand implements ICommand {
	private List<String> aliases;
	private TeamManager teamManager;
	
	private ChatMessageComponent invalidTeamNameMessage;

	public TeamRegisterCommand() {
		aliases = new ArrayList<>();
		aliases.add("registerteam");
		aliases.add("rt");
		
		teamManager = TeamManager.getInstance();
		
		invalidTeamNameMessage = new ChatMessageComponent();
		invalidTeamNameMessage.addText("An error occured.");
		invalidTeamNameMessage.addText("The team name is not valid!");
	}
	
	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "registerTeam";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "/registerteam <teamname>";
	}

	@Override
	public List<String> getCommandAliases() {
		return this.aliases;
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
