package com.becelot.spacerace.player;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import com.becelot.spacerace.SpaceConfig;
import com.becelot.spacerace.setup.ITeamBlockInteractionEvent;

public class PlayerEvent {
	
	private static ITeamBlockInteractionEvent listener;
	
	@ForgeSubscribe
	public void playerInteract(PlayerInteractEvent event) {
		if (event.action == Action.RIGHT_CLICK_BLOCK) {
			int id = event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z);
			if (id == SpaceConfig.teamSelectionId) {
				int meta = event.entityPlayer.worldObj.getBlockMetadata(event.x, event.y, event.z);
				if (listener != null) {
					listener.interact(event.entityPlayer, meta, event.x, event.y, event.z);
				}
			}
		}
	}
	
	@ForgeSubscribe
	public void playerFallingDamage(LivingFallEvent event) {
		event.setCanceled(true);
	}
	
	public static void registerTeamInteractionListener(ITeamBlockInteractionEvent listener) {
		PlayerEvent.listener = listener;
	}
}
