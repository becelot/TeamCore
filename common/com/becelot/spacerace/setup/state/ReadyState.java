package com.becelot.spacerace.setup.state;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.EnumGameType;

import com.becelot.spacerace.SpaceConfig;
import com.becelot.spacerace.SpaceraceState;
import com.becelot.spacerace.setup.Countdown;
import com.becelot.spacerace.setup.FSMTeamBuilderState;
import com.becelot.spacerace.setup.ICountdownEvent;
import com.becelot.spacerace.util.Chat;
import com.becelot.spacerace.util.SetupStructureBuilder;

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
	}

	@Override
	public void commandSend(String command, ICommandSender icommandsender,
			String[] args) {
		(new Countdown(61, new int[] {0, 1, 2, 3, 10, 15, 30, 45, 60}, this)).startCountdown();

	}

}
