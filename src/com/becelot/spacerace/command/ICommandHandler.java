package com.becelot.spacerace.command;

import net.minecraft.command.ICommandSender;

public interface ICommandHandler {
	public void processCommand(ICommandSender icommandsender, String[] astring);
}
