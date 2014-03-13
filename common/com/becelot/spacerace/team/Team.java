package com.becelot.spacerace.team;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatMessageComponent;

public class Team {
	private Map<String, EntityPlayerMP> member;
	EntityPlayerMP leader;
	private String teamName;
	private TeamColor teamColor;
	private int teamId;
	
	/*
	 * Create new team
	 */
	public Team(String teamName, int id) {
		this.member = new HashMap<String, EntityPlayerMP>();
		this.teamName = teamName;
		this.teamColor = TeamColor.TC_GRAY;
		this.teamId = id;
	}
	
	public EntityPlayerMP getTeamLeader() {
		return this.leader;
	}
	
	
	/*
	 * Send an message to all TeamMembers
	 */
	public void sendMessageToTeam(String message) {
		ChatMessageComponent comp = ChatMessageComponent.createFromText(message);
		
		for(EntityPlayerMP player : member.values()) {
			player.sendChatToPlayer(comp);
		}
	}
	
	/*
	 * Checks, weather a player is in this team
	 */
	public boolean playerInTeam(String name) {
		for (String player : member.keySet()) {
			if (player.toLowerCase().equals(name.toLowerCase()))
				return true;
		}
		return false;
	}
	
	/*
	 * Returns the name of this team
	 */
	public String getTeamName() {
		return this.teamName;
	}
	
	public void registerTeamMember(EntityPlayerMP player) {
		member.put(player.getDisplayName(), player);
	}
	
	/*
	 * Set leader of this team
	 */
	public void setLeader(EntityPlayerMP player) {
		this.leader = player;
		if (!member.containsKey(player.getDisplayName()))
			member.put(player.getDisplayName(), player);
	}
	
	/*
	 * Sets the color for the team
	 */
	public void setTeamColor(TeamColor color) {
		this.teamColor = color;
	}
	
	/*
	 * Returns the team color
	 */
	public TeamColor getTeamColor() {
		return this.teamColor;
	}
	
	/*
	 * Returns the team id
	 */
	public int getId() {
		return this.teamId;
	}
	
	public int getTeamSize() {
		return this.member.size();
	}
}
