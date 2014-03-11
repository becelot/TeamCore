package com.becelot.spacerace.setup;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;

import com.becelot.spacerace.SpaceConfig;
import com.becelot.spacerace.player.PlayerEvent;
import com.becelot.spacerace.team.Team;
import com.becelot.spacerace.team.TeamColor;
import com.becelot.spacerace.team.TeamManager;
import com.becelot.spacerace.util.Chat;

/*
 * Is called, when a team leader clicks a colored selection block
 */
public class TeamLeaderInteraction implements ITeamBlockInteractionEvent {
	
	public static String successMessage = "%sTeam %s has selected a team color!";
	
	/*
	 * Checks, if this phase is complete
	 */
	public boolean checkComplete() {
		TeamManager manager = TeamManager.getInstance();
		for (Team team : manager.getTeams()) {
			if (team.getTeamColor() == TeamColor.TC_GRAY) 
				return false;
		}
		
		return true;
	}

	@Override
	public void interact(EntityPlayer entityPlayer, int team, int x, int y,
			int z) {
		if (team == 0 || team >= SpaceConfig.maxTeams) {
			entityPlayer.sendChatToPlayer(ChatMessageComponent.createFromText("This color has already been choosen!"));
			return;
		}
		
		//Get selected color
		TeamColor selectedColor = TeamColor.fromNum(team);
		
		//Verify, that this color was not chosen before
		TeamManager manager = TeamManager.getInstance();
		for (Team t : manager.getTeams()) {
			if (t.getTeamColor() == selectedColor)
				return;
		}
		
		//Valid team color
		entityPlayer.worldObj.setBlock(x, y, z, SpaceConfig.teamSelectionId, 0, 3);
		MinecraftServer.getServer().worldServerForDimension(SpaceConfig.dimensionId).setBlockMetadataWithNotify(x, y, z, 0, 1+2);
		//entityPlayer.worldObj.setBlockMetadataWithNotify(x, y, z, 0, 1+2);
		entityPlayer.setPositionAndUpdate(x+0.5, y+0.5+2, z+0.5);
		
		//Setup team
		Team tea = manager.getTeamByPlayerName(entityPlayer.getDisplayName());
		tea.setTeamColor(selectedColor);
		Chat.sendToAllPlayers(String.format(successMessage, selectedColor.getPrefix(), tea.getTeamName()));
		
		//If all leaders have chosen their color
		if (this.checkComplete()) {
			Chat.sendToAllPlayers("All teams have choosen their color. Please select your team now.\nTo select your time, right-click the team color block.");
			//Teleport all players up to select their team.
			for (Object o : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {	
				EntityPlayer player = (EntityPlayer)o;
				boolean found = false;
				for (Team t : manager.getTeams()) {
					if (t.getTeamLeader().getDisplayName().equals(player.getDisplayName())) {
						found = true;
						break;
					}
				}
				
				if (!found) {
					player.setPositionAndUpdate(player.posX, player.posY+SetupStructureBuilder.height, player.posZ);
				}
			}
			
			//TODO: Generate cages in gaming world
			//Next phase
			SpaceConfig.buildPhase = TeamBuildPhase.TBP_CHOOSE_TEAMS;
			SetupStructureBuilder.buildTeamSelection(MinecraftServer.getServer().worldServerForDimension(SpaceConfig.dimensionId), false);
			PlayerEvent.registerTeamInteractionListener(new TeamMemberInteraction());
		}
		
	}



}
