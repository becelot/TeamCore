package com.becelot.teamcore.setup.state;

import java.util.HashMap;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.EnumGameType;

import com.becelot.teamcore.SpaceConfig;
import com.becelot.teamcore.SpaceraceState;
import com.becelot.teamcore.player.PvpPrevention;
import com.becelot.teamcore.setup.Countdown;
import com.becelot.teamcore.setup.FSMTeamBuilderState;
import com.becelot.teamcore.setup.ICountdownEvent;
import com.becelot.teamcore.util.Chat;
import com.becelot.teamcore.util.ListHelper;
import com.becelot.teamcore.util.SetupStructureBuilder;

public class ReadyState extends FSMTeamBuilderState implements ICountdownEvent {

	public ReadyState() {
		super();
		this.requiredCommandNames.add("startrace");
	}

	@Override
	public void interact(EntityPlayer entityPlayer, int team, int x, int y,
			int z) {
		// Nothing here

	}
	
	public void countdownOver() {
		//Set SURVIVAL mode for all players
		for (Object o : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
			EntityPlayerMP player = (EntityPlayerMP)o;
			player.setGameType(EnumGameType.SURVIVAL);
		}
		
		//Remove all cages
		SetupStructureBuilder.buildWorldCage(MinecraftServer.getServer().worldServerForDimension(0), 0);
		SpaceConfig.raceState = SpaceraceState.SR_STARTED;
		Chat.sendToAllPlayersFromRegistry("command.startrace.start");
		
		(new Countdown(10, new HashMap<Integer, String>(), new PvpPrevention())).startCountdown();
	}

	@Override
	public void commandSend(String command, ICommandSender icommandsender,
			String[] args) {
		(new Countdown(61, ListHelper.countdownNotificationsList("countdown.startrace"), this)).startCountdown();
		this.switchState(fsmStarted);
	}

}
