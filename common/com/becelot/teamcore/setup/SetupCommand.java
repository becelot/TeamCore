package com.becelot.teamcore.setup;

import net.minecraft.command.ICommandSender;

import com.becelot.teamcore.SpaceConfig;
import com.becelot.teamcore.command.GenericCommand;

import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class SetupCommand extends GenericCommand {
	
	private boolean needsGameModPermission = true;

	public static void registerCommands(FMLServerStartingEvent event) {
		event.registerServerCommand(new SetupCommand("registerteam", false));
		event.registerServerCommand(new SetupCommand("teamlimits", true));
		event.registerServerCommand(new SetupCommand("startrace", true));
		event.registerServerCommand(new SetupCommand("confirmleader", true));
		event.registerServerCommand(new SetupCommand("resetleader", true));
		event.registerServerCommand(new SetupCommand("confirmmember", true));
		event.registerServerCommand(new SetupCommand("resetmember", true));
	}

	
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
