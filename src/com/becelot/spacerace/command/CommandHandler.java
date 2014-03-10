package com.becelot.spacerace.command;

import net.minecraft.command.ICommandSender;

public abstract class CommandHandler {
	public abstract void processCommand(ICommandSender icommandsender, String[] astring);
	
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender) {
		return true;
	}
}
