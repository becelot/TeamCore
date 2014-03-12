package com.becelot.spacerace.command;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.becelot.spacerace.dimension.SpaceEnterCommand;
import com.becelot.spacerace.setup.commands.ConfirmLeaderCommand;
import com.becelot.spacerace.setup.commands.ConfirmMemberCommand;
import com.becelot.spacerace.setup.commands.ResetLeaderCommand;
import com.becelot.spacerace.setup.commands.ResetMemberCommand;
import com.becelot.spacerace.setup.commands.SpaceraceStartCommand;
import com.becelot.spacerace.setup.commands.TeamLimitsCommand;
import com.becelot.spacerace.team.TeamRegisterCommand;
import com.becelot.spacerace.team.TeamSendCommand;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.StatCollector;
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
		event.registerServerCommand(new GenericCommand("enter", SpaceEnterCommand.class));
		event.registerServerCommand(new GenericCommand("registerteam", TeamRegisterCommand.class));
		event.registerServerCommand(new GenericCommand("teamsend", TeamSendCommand.class));
		event.registerServerCommand(new GenericCommand("teamlimits", TeamLimitsCommand.class));
		event.registerServerCommand(new GenericCommand("startrace", SpaceraceStartCommand.class));
		event.registerServerCommand(new GenericCommand("confirmleader", ConfirmLeaderCommand.class));
		event.registerServerCommand(new GenericCommand("resetleader", ResetLeaderCommand.class));
		event.registerServerCommand(new GenericCommand("confirmmember", ConfirmMemberCommand.class));
		event.registerServerCommand(new GenericCommand("resetmember", ResetMemberCommand.class));
	}
	
	/*
	 * Constructs a new command
	 */
	public GenericCommand(String commandName, Class<? extends CommandHandler> clazz) {
		this.commandName = commandName;
		this.usage = StatCollector.translateToLocal("command." + commandName + ".usage");
		this.aliases = new ArrayList<String>();
		this.aliases.add(commandName);
		for (String a : StatCollector.translateToLocal("command." + commandName + ".aliases").split(",")) {
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
