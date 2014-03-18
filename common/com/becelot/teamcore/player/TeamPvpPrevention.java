package com.becelot.teamcore.player;

import com.becelot.teamcore.team.TeamManager;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class TeamPvpPrevention {
	
	@ForgeSubscribe
	public void preventPvp(LivingAttackEvent event) {
		if (event.source.getSourceOfDamage() instanceof EntityPlayer && event.entity instanceof EntityPlayer) {
			if (TeamManager.getInstance().getTeamByPlayerName(event.source.getSourceOfDamage().getEntityName()).playerInTeam(event.entity.getEntityName()))
				event.setCanceled(true);
		}
	}
}
