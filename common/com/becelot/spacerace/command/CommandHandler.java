package com.becelot.spacerace.command;

import net.minecraft.command.ICommandSender;

public abstract class CommandHandler {
	
	/*
	 * What does this command do?
	 */
	public abstract void processCommand(ICommandSender icommandsender, String[] astring);
	
	/*
	 * Override this if you need special permissions
	 */
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender) {
		return true;
	}
}
