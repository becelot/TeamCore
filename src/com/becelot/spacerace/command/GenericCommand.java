package com.becelot.spacerace.command;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.becelot.spacerace.dimension.SpaceEnterCommand;
import com.becelot.spacerace.setup.TeamLimitsCommand;
import com.becelot.spacerace.team.TeamRegisterCommand;
import com.becelot.spacerace.team.TeamSendCommand;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class GenericCommand implements ICommand {
	
	protected List<String> aliases;
	private String commandName;
	private String usage;
	private CommandHandler commandHandler;
	
	public static void registerCommands(FMLServerStartingEvent event) {
		event.registerServerCommand(new GenericCommand("enter", "/enter", "enter,ent", SpaceEnterCommand.class));
		event.registerServerCommand(new GenericCommand("registerteam", "/registerteam <teamname>", "registerteam,rt", TeamRegisterCommand.class));
		event.registerServerCommand(new GenericCommand("teamsend", "/teamsend <teamname> <message>", "teamsend,ts", TeamSendCommand.class));
		event.registerServerCommand(new GenericCommand("teamlimits", "/teamlimits <teamcount> <minMemberCount> <maxMemberCount>", "teamlimits", TeamLimitsCommand.class));
	}
	
	public GenericCommand(String commandName, String usage, String aliases, Class<? extends CommandHandler> clazz) {
		this.commandName = commandName;
		this.usage = usage;
		this.aliases = new ArrayList<String>();
		for (String a : aliases.split(",")) {
			this.aliases.add(a);
		}
		
		Constructor<?>[] ctors = clazz.getDeclaredConstructors();
		Constructor<?> ctor = null;
		for (int i = 0; i < ctors.length; i++) {
			ctor = ctors[i];
			if (ctor.getGenericParameterTypes().length == 0)
				break;
		}
		try {
			ctor.setAccessible(true);
			this.commandHandler = (CommandHandler)ctor.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
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
