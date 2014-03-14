package com.becelot.spacerace.setup;

import java.util.EnumSet;

import com.becelot.spacerace.util.Chat;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class Countdown implements ITickHandler {

	private long lastTick;
	private int secondsRemaining;
	private int[] notifications;
	private ICountdownEvent event;
	
	public Countdown(int seconds, int[] messages, ICountdownEvent event) {
		secondsRemaining = seconds;
		this.notifications = messages;
		this.event = event;
	}

	public void startCountdown() {
		lastTick = System.nanoTime();
		TickRegistry.registerTickHandler(this, Side.SERVER);
	}
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		//Check, if this countdown is over
		//TODO: Unregister this tick
		if (secondsRemaining == 0) return;
		long tick = System.nanoTime();
		
		//If one second has passed
		if (tick - lastTick > 1000000000) {
			lastTick = lastTick + 1000000000;
			secondsRemaining--;
			
			if (secondsRemaining == 0) {
				event.countdownOver();
			}
			
			boolean found = false;
			for (int i : this.notifications) {
				if (i == secondsRemaining) {
					found = true;
					break;
				}
			}
			if (found) {
				Chat.sendToAllPlayers("" + secondsRemaining + " to start!");
			}
			
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		// Nothing here

	}

	@Override
	public EnumSet<TickType> ticks() {
		return null;
	}

	@Override
	public String getLabel() {
		return "CountdownTimer";
	}

}
