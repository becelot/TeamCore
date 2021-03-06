package com.becelot.teamcore.setup.state;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;

import com.becelot.teamcore.TeamConfig;
import com.becelot.teamcore.setup.FSMTeamBuilderState;
import com.becelot.teamcore.team.Team;
import com.becelot.teamcore.team.TeamColor;
import com.becelot.teamcore.team.TeamManager;
import com.becelot.teamcore.util.Chat;
import com.becelot.teamcore.util.SetupStructureBuilder;

public class TeamColorSelectionState extends FSMTeamBuilderState {
	
	public static String successMessage = "%sTeam %s has selected a team color!";
	private ChatMessageComponent colorAlreadyChosen = Chat.fromRegistry("event.team.leader.colorchosen.chosen");
	
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
		if (team == 0 || team >= TeamConfig.maxTeams) {
			entityPlayer.sendChatToPlayer(colorAlreadyChosen);
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
		entityPlayer.worldObj.setBlock(x, y, z, TeamConfig.teamSelectionId, 0, 3);
		MinecraftServer.getServer().worldServerForDimension(TeamConfig.dimensionId).setBlockMetadataWithNotify(x, y, z, 0, 1+2);
		entityPlayer.setPositionAndUpdate(x+0.5, y+0.5+2, z+0.5);
		
		//Setup team
		Team tea = manager.getTeamByPlayerName(entityPlayer.getDisplayName());
		tea.setTeamColor(selectedColor);
		Chat.sendToAllPlayersFromRegistryFormatted("event.team.leader.colorchosen", selectedColor.getPrefix(), tea.getTeamName());
		
		//If all leaders have chosen their color
		if (this.checkComplete()) {
			Chat.sendToAllPlayersFromRegistry("event.team.leader.colorchosen.all");
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
			

			//Next phase
			SetupStructureBuilder.buildTeamSelection(MinecraftServer.getServer().worldServerForDimension(TeamConfig.dimensionId), false);
			SetupStructureBuilder.buildWorldCage(MinecraftServer.getServer().worldServerForDimension(0), TeamConfig.unbreakableGlassId);
			
			/*
			if (((TeamMemberSelectionState)fsmTeamMemberSelection).checkComplete()) {
				((TeamMemberSelectionState)fsmTeamMemberSelection).onComplete();
				this.switchState(fsmReady);
			} else {
				this.switchState(fsmTeamMemberSelection);
			} */
			this.switchState(fsmTeamMemberSelection);
		}

	}

	@Override
	public void commandSend(String command, ICommandSender icommandsender, String[] args) {
		// Nothing here

	}

}
