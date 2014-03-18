package com.becelot.teamcore.util;

import com.becelot.teamcore.SpaceConfig;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.StatCollector;

public class Chat {
	public static void sendToPlayerFormatted(EntityPlayer player, String registry, Object ... args) {
		player.sendChatToPlayer(ChatMessageComponent.createFromText(StatCollector.translateToLocalFormatted(registry, args)));
	}
	
	public static void sendToPlayer(EntityPlayer player, String registry) {
		player.sendChatToPlayer(ChatMessageComponent.createFromText(StatCollector.translateToLocal(registry)));
	}
	
	public static ChatMessageComponent fromRegistry(String registry) {
		return ChatMessageComponent.createFromText(StatCollector.translateToLocal(registry));
	}
	
	public static ChatMessageComponent fromRegistryFormatted(String registry, Object... args) {
		return ChatMessageComponent.createFromText(StatCollector.translateToLocalFormatted(registry, args));
	}
	
	public static void sendToAllPlayersFromRegistry(String reg) {
		MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayers(new Packet3Chat(fromRegistry(reg), true));
	}
	
	public static void sendToAllPlayersFromRegistryFormatted(String reg, Object... args) {
		MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayers(new Packet3Chat(fromRegistryFormatted(reg, args), true));
	}
	
	/*
	 * Send a chat message to all players
	 */
	public static void sendToAllPlayers(String s) {
		MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayers(new Packet3Chat(ChatMessageComponent.createFromText(s), true));
	}
	
	/*
	 * Send a message to the game moderator
	 */
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
