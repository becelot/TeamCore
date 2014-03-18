package com.becelot.teamcore.setup.state;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

import com.becelot.teamcore.setup.FSMTeamBuilderState;

public class StartedState extends FSMTeamBuilderState {

	@Override
	public void interact(EntityPlayer entityPlayer, int team, int x, int y,
			int z) {

	}

	@Override
	public void commandSend(String command, ICommandSender icommandsender,
			String[] args) {

	}

}
