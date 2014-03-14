package com.becelot.spacerace.setup;

import java.util.EnumSet;
import java.util.Map;

import com.becelot.spacerace.util.Chat;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class Countdown implements ITickHandler {

	private long lastTick;
	private int secondsRemaining;
	private Map<Integer, String> notifications;
	private ICountdownEvent event;
	private EnumSet<TickType> tickType;
	
	public Countdown(int seconds, Map<Integer, String> messages, ICountdownEvent event) {
		this.secondsRemaining = seconds;
		this.notifications = messages;
		this.event = event;
	}

	public void startCountdown() {
		lastTick = System.nanoTime();
		tickType = EnumSet.of(TickType.WORLD);
		TickRegistry.registerTickHandler(this, Side.SERVER);
	}
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		long tick = System.nanoTime();
		
		//If one second has passed
		if (tick - lastTick > 1000000000) {
			lastTick = lastTick + 1000000000;
			secondsRemaining--;
			
			if (secondsRemaining == 0) {
				event.countdownOver();
				tickType = EnumSet.noneOf(TickType.class);
			}
			
			if (notifications.containsKey(secondsRemaining)) {
				Chat.sendToAllPlayers(notifications.get(secondsRemaining));
			}
			
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		// Nothing here
	}

	@Override
	public EnumSet<TickType> ticks() {
		return tickType;
	}

	@Override
	public String getLabel() {
		return "CountdownTimer";
	}

}
