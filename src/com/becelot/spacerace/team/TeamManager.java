package com.becelot.spacerace.team;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;

public class TeamManager {
	private List<Team> teams;
	private static TeamManager instance;
	
	/*
	 * Private constructor. Called only once.
	 */
	private TeamManager() {
		teams = new ArrayList<>();
	}
	
	/*
	 * Checks, weather a teamname is existant or not
	 */
	public boolean teamExists(String name) {
		for (Team t : teams) {
			if (t.getTeamName().toLowerCase() == name.toLowerCase()) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Registers a new team name
	 */
	public Team registerTeam(String name) {
		if (this.teamExists(name)) 
				return null;
		
		Team team = new Team(name);
		teams.add(team);
		return team;
	}
	
	/*
	 * Gets the instance for TeamManager
	 */
	public static TeamManager getInstance() {
		if(instance == null) {
			instance = new TeamManager();
		}
		
		return instance;
	}
	
	/*
	 * Get the List of all registered teams
	 */
	public List<Team> getTeams() {
		return this.teams;
	}
	
	/*
	 * Get team by teamname
	 */
	public Team getTeamByTeamName(String name) {
		for (Team t : this.teams) {
			if (t.getTeamName().toLowerCase().equals(name.toLowerCase())) {
				return t;
			}
		}
		
		return null;
	}
	
	/*
	 * Get team by player
	 */
	public Team getTeamByPlayerName(EntityPlayerMP player) {
		return this.getTeamByPlayerName(player.getDisplayName());
	}
	
	public Team getTeamByPlayerName(String playerName) {
		for (Team t : this.teams) {
			if (t.playerInTeam(playerName))
				return t;
		}
		return null;
	}
	
	public int getTeamCount() {
		return this.teams.size();
	}
	
	public void resetTeams() {
		teams.clear();
	}

}
