package com.becelot.spacerace.dimension;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatMessageComponent;

import com.becelot.spacerace.SpaceConfig;

public class SpaceEnterCommand implements ICommand {
	private List<String> aliases;
	
	public SpaceEnterCommand() {
		aliases = new ArrayList<>();
		aliases.add("enter");
		aliases.add("ent");
	}

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "enter";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "/enter";
	}

	@Override
	public List<String> getCommandAliases() {
		return this.aliases;
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		if (icommandsender instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP)icommandsender;
			if (player.dimension != SpaceConfig.dimensionId)
				DimensionTeleporter.transferPlayerToDimension(player, SpaceConfig.dimensionId);
			else 
				DimensionTeleporter.transferPlayerToDimension(player, 0);
			
		} else {
			icommandsender.sendChatToPlayer((new ChatMessageComponent()).addText("Not valid"));
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
