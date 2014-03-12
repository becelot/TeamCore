package com.becelot.spacerace.setup.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.StatCollector;

import com.becelot.spacerace.SpaceConfig;
import com.becelot.spacerace.command.CommandHandler;
import com.becelot.spacerace.setup.TeamBuildPhase;
import com.becelot.spacerace.util.Chat;

public class ConfirmMemberCommand extends CommandHandler {

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		if (SpaceConfig.buildPhase != TeamBuildPhase.TBP_CONFIRM_MEMBERS) return;
		
		Chat.sendToAllPlayersFromRegistry("command.confirmmember.confirmed");
		Chat.sendToGameMod(StatCollector.translateToLocal("command.confirmmember.confirmed.gamemod"));
		
		SpaceConfig.buildPhase = TeamBuildPhase.TBP_DONE;
	}

}
