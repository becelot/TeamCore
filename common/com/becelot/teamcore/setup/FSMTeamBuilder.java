package com.becelot.teamcore.setup;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

public class FSMTeamBuilder implements ITeamBlockInteractionEvent {
	
	private FSMTeamBuilderState currentState = FSMTeamBuilderState.fsmIdle;
	
	public static FSMTeamBuilder instance = new FSMTeamBuilder();
	
	public void setCurrentState(FSMTeamBuilderState nextState) {
		this.currentState = nextState;
	}
	
	
	private FSMTeamBuilder() {}
	
	/*
	 * Resets all set information
	 */
	private void resetRace() {
		//TODO: Reset the complete race
	}

	
	public void commandSend(String command, ICommandSender icommandsender, String[] args) {
		if (command.equals("racereset")) {
			resetRace();
		} else if (currentState.requiredCommandNames.contains(command) || currentState.requiredCommandNames.contains("*")) {
			currentState.commandSend(command, icommandsender, args);
		}
	}


	public void interact(EntityPlayer entityPlayer, int team, int x, int y,
			int z) {
		currentState.interact(entityPlayer, team, x, y, z);
	}

}
