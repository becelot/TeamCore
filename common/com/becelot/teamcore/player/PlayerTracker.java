package com.becelot.teamcore.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.EnumGameType;

import com.becelot.teamcore.SpaceConfig;
import com.becelot.teamcore.dimension.DimensionTeleporter;
import com.becelot.teamcore.team.Team;
import com.becelot.teamcore.team.TeamManager;

import cpw.mods.fml.common.IPlayerTracker;

public class PlayerTracker implements IPlayerTracker {
	
	/*
	 * Is called, when player logs into an ongoing match.
	 * Kicks player, if he does not participate in this match.
	 * Adds player to his team, if he participated earlier.
	 */
	private void playerLoginStarted(EntityPlayer player) {
		if (player instanceof EntityPlayerMP) {
			EntityPlayerMP playerMP = (EntityPlayerMP)player;
			Team team = TeamManager.getInstance().getTeamByPlayerName(playerMP.getDisplayName());
			
			//Player is not part of any team
			if (team == null) {
				playerMP.playerNetServerHandler.kickPlayerFromServer("You do not participate in this match!");
			}
			
			//Player is part of a team
			team.registerTeamMember(playerMP);
		}
	}

	@Override
	public void onPlayerLogin(EntityPlayer player) {
		//TODO: Remove this line, just for debugging.
		SpaceConfig.gameMod = player.getDisplayName();
		switch (SpaceConfig.raceState) {
			case SR_IDLE:
				player.setGameType(EnumGameType.ADVENTURE);
				break;
			case SR_PREPARING:
				player.setGameType(EnumGameType.ADVENTURE);
				playerLoginPreparing(player);
				break;
			case SR_STARTED:
				playerLoginStarted(player);
				break;
			default:
				break;
		}
	}

	/*
	 * Player logged in, while the match is preparing.
	 * He gets instantly teleported to the preparation dimension
	 */
	private void playerLoginPreparing(EntityPlayer player) {
		if (player instanceof EntityPlayerMP) {
			EntityPlayerMP playerMP = (EntityPlayerMP)player;
			DimensionTeleporter.transferPlayerToDimension(playerMP, SpaceConfig.dimensionId, 0, 1, 0);
			
		}
		
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {
		
	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {
		
	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) {

	}



}
