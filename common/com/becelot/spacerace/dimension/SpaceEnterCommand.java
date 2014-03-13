package com.becelot.spacerace.dimension;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatMessageComponent;

import com.becelot.spacerace.SpaceConfig;
import com.becelot.spacerace.command.CommandHandler;


//TODO: Remove later
/*
 * Just for debugging. Should be removed later
 */
public class SpaceEnterCommand extends CommandHandler {


	public void processCommand(ICommandSender icommandsender, String[] astring) {
		if (icommandsender instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP)icommandsender;
			if (player.dimension != SpaceConfig.dimensionId)
				DimensionTeleporter.transferPlayerToDimension(player, SpaceConfig.dimensionId);
			else 
				DimensionTeleporter.transferPlayerToDimension(player, 0);
			
		} else {
			icommandsender.sendChatToPlayer((new ChatMessageComponent()).addText("Not valid"));
		}
	}

}
