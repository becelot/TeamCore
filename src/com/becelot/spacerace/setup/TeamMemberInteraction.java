package com.becelot.spacerace.setup;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.StatCollector;

import com.becelot.spacerace.SpaceConfig;
import com.becelot.spacerace.dimension.DimensionTeleporter;
import com.becelot.spacerace.team.Team;
import com.becelot.spacerace.team.TeamColor;
import com.becelot.spacerace.team.TeamManager;
import com.becelot.spacerace.util.Chat;
import com.becelot.spacerace.util.SetupStructureBuilder;

/*
 * Is called, when a team member clicks a colored block.
 */
public class TeamMemberInteraction implements ITeamBlockInteractionEvent {
	
	private ChatMessageComponent teamFull = Chat.fromRegistry("event.team.full");
	private ChatMessageComponent invalidTeam = Chat.fromRegistry("event.team.invalid");

	@Override
	public void interact(EntityPlayer entityPlayer, int team, int x, int y,
			int z) {
		//Check for valid parameters
		if (team == 0 || team >= SpaceConfig.maxTeams) return;

		//Get team index and teleport player in cage
		Team tea = TeamManager.getInstance().getTeamByColor(TeamColor.fromNum(team));
		if (tea != null) {
			if (tea.getTeamSize() < SpaceConfig.maxMemberCount) {
				int teamId =  tea.getId();

				double angle = 360f / (float)SpaceConfig.teamCount * teamId; 
				int xPos = (int)Math.round((SetupStructureBuilder.radius + SetupStructureBuilder.cubicCageWidth) * Math.cos(angle * Math.PI / 360f));
				int zPos = (int)Math.round((SetupStructureBuilder.radius + SetupStructureBuilder.cubicCageWidth) * Math.sin(angle * Math.PI / 360f));

				DimensionTeleporter.transferPlayerToDimension((EntityPlayerMP)entityPlayer, 0, xPos, SetupStructureBuilder.cubicCageHeight + 2, zPos);
				
				tea.registerTeamMember((EntityPlayerMP)entityPlayer);
				
				Chat.sendToAllPlayers(StatCollector.translateToLocalFormatted("event.team.joined.member", entityPlayer.getDisplayName(), tea.getTeamColor().getPrefix() + tea.getTeamName()));
			} else {
				entityPlayer.sendChatToPlayer(teamFull);
			}
		} else {
			entityPlayer.sendChatToPlayer(invalidTeam);
		}
	}

}
