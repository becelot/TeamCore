package com.becelot.spacerace.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import com.becelot.spacerace.SpaceConfig;
import com.becelot.spacerace.SpaceRaceMod;
import com.becelot.spacerace.setup.Countdown;
import com.becelot.spacerace.setup.ICountdownEvent;
import com.becelot.spacerace.util.ListHelper;

public class PvpPrevention implements ICountdownEvent {
	
	private boolean pvpMode = false;

	@Override
	public void countdownOver() {
		//Gain a little performance boost
		if (!pvpMode) {
			MinecraftForge.EVENT_BUS.unregister(SpaceRaceMod.eventManager);
			MinecraftForge.EVENT_BUS.register(this);
			(new Countdown(SpaceConfig.pvpPreventionMinutes*60+1, ListHelper.countdownNotificationsList("countdown.pvpprevention"), this)).startCountdown();
			pvpMode = true;
		} else {
			MinecraftForge.EVENT_BUS.unregister(this);
			if (!SpaceConfig.pvpFriendlyFireOn) {
				MinecraftForge.EVENT_BUS.register(new TeamPvpPrevention());
			}
		}
	}
	
	@ForgeSubscribe
	public void preventPvp(LivingAttackEvent event) {
		if (event.source.getSourceOfDamage() instanceof EntityPlayer && event.entity instanceof EntityPlayer) {
			event.setCanceled(true);
		}
	}

}
