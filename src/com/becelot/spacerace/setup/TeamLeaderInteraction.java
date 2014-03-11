package com.becelot.spacerace.setup;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;

import com.becelot.spacerace.SpaceConfig;
import com.becelot.spacerace.team.Team;
import com.becelot.spacerace.team.TeamColor;
import com.becelot.spacerace.team.TeamManager;
import com.becelot.spacerace.util.Chat;

public class TeamLeaderInteraction implements ITeamBlockInteractionEvent {
	
	public static String successMessage = "%sTeam %s has selected a color!";

	@Override
	public void interact(EntityPlayer entityPlayer, int team, double x, double y,
			double z) {
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
		entityPlayer.worldObj.setBlockMetadataWithNotify((int)x, (int)y, (int)z, 0, 1+2);
		entityPlayer.setPosition(x, y+3, z);
		
		Team tea = manager.getTeamByPlayerName(entityPlayer.getDisplayName());
		tea.setTeamColor(selectedColor);
		Chat.sendToAllPlayers(String.format(successMessage, selectedColor.getPrefix(), tea.getTeamName()));
	}



}
