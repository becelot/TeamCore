package com.becelot.spacerace.setup.state;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.StatCollector;

import com.becelot.spacerace.SpaceConfig;
import com.becelot.spacerace.dimension.DimensionTeleporter;
import com.becelot.spacerace.setup.FSMTeamBuilderState;
import com.becelot.spacerace.team.Team;
import com.becelot.spacerace.team.TeamColor;
import com.becelot.spacerace.team.TeamManager;
import com.becelot.spacerace.util.Chat;
import com.becelot.spacerace.util.SetupStructureBuilder;

public class TeamMemberSelectionState extends FSMTeamBuilderState {
	
	private ChatMessageComponent teamFull = Chat.fromRegistry("event.team.full");
	private ChatMessageComponent invalidTeam = Chat.fromRegistry("event.team.invalid");
	
	/*
	 * Checks, if the current phase has been completed
	 */
	public boolean checkComplete() {
		for (Object o : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
			EntityPlayer player = (EntityPlayer)o;
			if (TeamManager.getInstance().getTeamByPlayerName(player.getDisplayName()) == null) {
				return false;
			}
		}
		return true;
	}
	
	public void onComplete() {
		//Teleport all team leaders in their cage
		for (Team t : TeamManager.getInstance().getTeams()) {
			EntityPlayerMP player = t.getTeamLeader();
			double angle = 360f / (float)SpaceConfig.teamCount * t.getId(); 
			int xPos = (int)Math.round((SetupStructureBuilder.radius + SetupStructureBuilder.cubicCageWidth) * Math.cos(angle * Math.PI / 360f));
			int zPos = (int)Math.round((SetupStructureBuilder.radius + SetupStructureBuilder.cubicCageWidth) * Math.sin(angle * Math.PI / 360f));
			
			DimensionTeleporter.transferPlayerToDimension(player, 0, xPos, SetupStructureBuilder.cubicCageHeight + 2, zPos);
		}
	}

	@Override
	public void interact(EntityPlayer entityPlayer, int team, int x, int y,
			int z) {
		if (team == 0 || team >= SpaceConfig.maxTeams) return;

		//Check, if the player has already chosen a team
		Team tea = TeamManager.getInstance().getTeamByPlayerName(entityPlayer.getDisplayName());
		if (tea != null) return;
		
		//Get team index and teleport player in cage
		tea = TeamManager.getInstance().getTeamByColor(TeamColor.fromNum(team));
		if (tea != null) {
			if (tea.getTeamSize() < SpaceConfig.maxMemberCount) {
				int teamId =  tea.getId();

				double angle = 360f / (float)SpaceConfig.teamCount * teamId; 
				int xPos = (int)Math.round((SetupStructureBuilder.radius + SetupStructureBuilder.cubicCageWidth) * Math.cos(angle * Math.PI / 360f));
				int zPos = (int)Math.round((SetupStructureBuilder.radius + SetupStructureBuilder.cubicCageWidth) * Math.sin(angle * Math.PI / 360f));

				DimensionTeleporter.transferPlayerToDimension((EntityPlayerMP)entityPlayer, 0, xPos, SetupStructureBuilder.cubicCageHeight + 2, zPos);
				
				tea.registerTeamMember((EntityPlayerMP)entityPlayer);
				
				Chat.sendToAllPlayers(StatCollector.translateToLocalFormatted("event.team.joined.member", entityPlayer.getDisplayName(), tea.getTeamColor().getPrefix() + tea.getTeamName()));
				
				//If phase is complete
				if (this.checkComplete()) {
					this.onComplete();
					
					Chat.sendToAllPlayersFromRegistry("event.team.choose.all");
					Chat.sendToGameMod(StatCollector.translateToLocal("event.team.choose.gamemod"));
					
					this.switchState(fsmConfirmMember);
				}
			} else {
				entityPlayer.sendChatToPlayer(teamFull);
			}
		} else {
			entityPlayer.sendChatToPlayer(invalidTeam);
		}
	}

	@Override
	public void commandSend(String command, ICommandSender icommandsender,
			String[] args) {
		// Nothing here
	}

}
