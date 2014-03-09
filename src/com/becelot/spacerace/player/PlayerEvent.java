package com.becelot.spacerace.player;

import com.becelot.spacerace.SpaceConfig;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class PlayerEvent {
	
	@ForgeSubscribe
	public void playerInteract(PlayerInteractEvent event) {
		if (event.action == Action.RIGHT_CLICK_BLOCK) {
			int id = event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z);
			if (id == SpaceConfig.teamSelectionId) {
				int meta = event.entityPlayer.worldObj.getBlockMetadata(event.x, event.y, event.z);
				if (meta > 0)
					event.entityPlayer.worldObj.setBlockMetadataWithNotify(event.x, event.y, event.z, 0, 1+2);
			}
		}
	}
}
