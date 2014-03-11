package com.becelot.spacerace.util;

import com.becelot.spacerace.SpaceConfig;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;

public class Chat {
	public static void sendToAllPlayers(String s) {
		MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayers(new Packet3Chat(ChatMessageComponent.createFromText(s), true));
	}
	
	public static void sendToGameMod(String s) {
		for (Object play : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
			EntityPlayer player = (EntityPlayer)play;
			if (player.getDisplayName().equals(SpaceConfig.gameMod)) {
				player.sendChatToPlayer(ChatMessageComponent.createFromText("[SERVER]: " + s));
				return;
			}
		}
	}
}
