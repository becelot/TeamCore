package com.becelot.teamcore.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import com.becelot.teamcore.SpaceConfig;
import com.becelot.teamcore.SpaceRaceMod;
import com.becelot.teamcore.setup.Countdown;
import com.becelot.teamcore.setup.ICountdownEvent;
import com.becelot.teamcore.util.ListHelper;

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
