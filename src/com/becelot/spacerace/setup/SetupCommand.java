package com.becelot.spacerace.setup;

import net.minecraft.command.ICommandSender;

import com.becelot.spacerace.SpaceConfig;
import com.becelot.spacerace.command.GenericCommand;

public class SetupCommand extends GenericCommand {
	
	private boolean needsGameModPermission = true;


	
	public SetupCommand(String commandName, boolean gameModPermission) {
		super(commandName, null);
		this.needsGameModPermission = gameModPermission;
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		FSMTeamBuilder.instance.commandSend(this.getCommandName(), icommandsender, astring);
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender) {
		return needsGameModPermission ? icommandsender.getCommandSenderName().equals(SpaceConfig.gameMod) : true;
	}

}
