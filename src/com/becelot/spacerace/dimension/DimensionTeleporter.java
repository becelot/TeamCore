package com.becelot.spacerace.dimension;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet16BlockItemSwitch;
import net.minecraft.network.packet.Packet4UpdateTime;
import net.minecraft.network.packet.Packet70GameEvent;
import net.minecraft.network.packet.Packet9Respawn;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.common.registry.GameRegistry;

public class DimensionTeleporter {
	public static void transferPlayerToDimension(EntityPlayerMP player, int dimension) {
		//Get old and new values
		int oldDim = player.dimension;
		WorldServer oldServer = player.mcServer.worldServerForDimension(oldDim);
		WorldServer newServer = player.mcServer.worldServerForDimension(dimension);
		player.dimension = dimension;
		
		//Send Dimension change packet to client
		player.playerNetServerHandler.sendPacketToPlayer(new Packet9Respawn(player.dimension, (byte)player.worldObj.difficultySetting, newServer.getWorldInfo().getTerrainType(), newServer.getHeight(), player.theItemInWorldManager.getGameType()));
		
		//Remove entity player from old dimension
		oldServer.removePlayerEntityDangerously(player);
		player.isDead = false;
		oldServer.getPlayerManager().removePlayer(player);
		
		
		//Prepare spawn
		ChunkCoordinates chunkcoordinates = newServer.getSpawnPoint();
		newServer.getPlayerManager().addPlayer(player);
		newServer.theChunkProviderServer.loadChunk((int)player.posX >> 4, (int)player.posZ >> 4);
		newServer.spawnEntityInWorld(player);
		player.setLocationAndAngles(chunkcoordinates.posX, player.posY, chunkcoordinates.posZ, player.rotationYaw, player.rotationPitch);
		newServer.updateEntityWithOptionalForce(player, false);
		
		//Setup new world location
		player.setWorld(newServer);
		player.playerNetServerHandler.setPlayerLocation(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch);
		player.theItemInWorldManager.setWorld(newServer);
		
		//Adjust parameters
		DimensionTeleporter.updateTimeAndWeatherForPlayer(player, newServer);
		DimensionTeleporter.syncPlayerInventory(player);
		
		
		GameRegistry.onPlayerChangedDimension(player);
	}
	
    public static void syncPlayerInventory(EntityPlayerMP par1EntityPlayerMP)
    {
        par1EntityPlayerMP.sendContainerToPlayer(par1EntityPlayerMP.inventoryContainer);
        par1EntityPlayerMP.setPlayerHealthUpdated();
        par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet16BlockItemSwitch(par1EntityPlayerMP.inventory.currentItem));
    }
	
    public static void updateTimeAndWeatherForPlayer(EntityPlayerMP par1EntityPlayerMP, WorldServer par2WorldServer)
    {
        par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet4UpdateTime(par2WorldServer.getTotalWorldTime(), par2WorldServer.getWorldTime(), par2WorldServer.getGameRules().getGameRuleBooleanValue("doDaylightCycle")));

        if (par2WorldServer.isRaining())
        {
            par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(1, 0));
        }
    }
}
