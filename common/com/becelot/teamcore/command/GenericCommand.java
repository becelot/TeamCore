package com.becelot.teamcore.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.StatCollector;

import com.becelot.teamcore.dimension.SpaceEnterCommand;
import com.becelot.teamcore.team.TeamSendCommand;

import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class GenericCommand implements ICommand {
	
	protected List<String> aliases;
	private String commandName;
	private String usage;
	private CommandHandler commandHandler;
	
	/*
	 * All Command should be registered here
	 */
	public static void registerCommands(FMLServerStartingEvent event) {
		event.registerServerCommand(new GenericCommand("enter", new SpaceEnterCommand()));
		event.registerServerCommand(new GenericCommand("teamsend", new TeamSendCommand()));
	}
	
	/*
	 * Constructs a new command
	 */
	public GenericCommand(String commandName, CommandHandler handler) {
		this.commandName = commandName;
		this.usage = StatCollector.translateToLocal("command." + commandName + ".usage");
		this.aliases = new ArrayList<String>();
		this.aliases.add(commandName);
		for (String a : StatCollector.translateToLocal("command." + commandName + ".aliases").split(",")) {
			this.aliases.add(a);
		}
		
		this.commandHandler = handler;
	}
	

	public int compareTo(Object arg0) {
		return 0;
	}

	public String getCommandName() {
		return commandName;
	}

	public String getCommandUsage(ICommandSender icommandsender) {
		return usage;
	}

	public List<String> getCommandAliases() {
		return aliases;
	}

	public void processCommand(ICommandSender icommandsender, String[] astring) {
		this.commandHandler.processCommand(icommandsender, astring);
	}

	public boolean canCommandSenderUseCommand(ICommandSender icommandsender) {
		return this.commandHandler.canCommandSenderUseCommand(icommandsender);
	}

	public List<?> addTabCompletionOptions(ICommandSender icommandsender,
			String[] astring) {
		return null;
	}


	public boolean isUsernameIndex(String[] astring, int i) {
		return false;
	}

}
