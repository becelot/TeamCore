package com.becelot.spacerace.setup;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommandSender;

import com.becelot.spacerace.setup.state.ConfirmLeaderState;
import com.becelot.spacerace.setup.state.ConfirmMemberState;
import com.becelot.spacerace.setup.state.FSMIdleState;
import com.becelot.spacerace.setup.state.ReadyState;
import com.becelot.spacerace.setup.state.RegisterTeamState;
import com.becelot.spacerace.setup.state.TeamColorSelectionState;
import com.becelot.spacerace.setup.state.TeamLimitState;
import com.becelot.spacerace.setup.state.TeamMemberSelectionState;

public abstract class FSMTeamBuilderState implements ITeamBlockInteractionEvent {
	
	public static final FSMTeamBuilderState fsmIdle = new FSMIdleState();
	public static final FSMTeamBuilderState fsmTeamLimit = new TeamLimitState();
	public static final FSMTeamBuilderState fsmRegisterTeam = new RegisterTeamState();
	public static final FSMTeamBuilderState fsmConfirmLeaderState = new ConfirmLeaderState();
	public static final FSMTeamBuilderState fsmTeamColorSelection = new TeamColorSelectionState();
	public static final FSMTeamBuilderState fsmTeamMemberSelection = new TeamMemberSelectionState();
	public static final FSMTeamBuilderState fsmConfirmMember = new ConfirmMemberState();
	public static final FSMTeamBuilderState fsmReady = new ReadyState();
	
	
	protected List<String> requiredCommandNames;
	
	public FSMTeamBuilderState() {
		this.requiredCommandNames = new ArrayList<>();
	}
	
	public List<String> getCommandNames() {
		return this.requiredCommandNames;
	}
	
	protected void switchState(FSMTeamBuilderState nextState) {
		FSMTeamBuilder.instance.setCurrentState(nextState);
	}
	
	public abstract void commandSend(String command, ICommandSender icommandsender, String[] args);
}
