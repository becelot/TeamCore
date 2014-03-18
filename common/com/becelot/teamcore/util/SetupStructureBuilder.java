package com.becelot.teamcore.util;

import net.minecraft.world.World;

import com.becelot.teamcore.TeamConfig;

public class SetupStructureBuilder {
	public static final int radius = 10;
	public static final int cubicCageWidth = 5;
	public static final int cubicCageHeight = 200;
	public static final int height = 5;
	public static final int distance = 5;
	
	
	
	private static final double conversion = Math.PI / 180f;
	
	/*
	 * Connects to points in space
	 */
	private static void connectCorner(World world, Vector corner1, Vector corner2, int blockId) {
		Vector dir = corner1.clone().subtract(corner2).multiply(-1).normalize();
		int dist = (int)corner1.distance(corner2);
		
		Vector c1 = corner1.clone();
		
		for (int i=0; i <= dist; i++) {
			world.setBlock(c1.getBlockX(), c1.getBlockY(), c1.getBlockZ(), blockId, 0, 3);
			c1.add(dir);
		}
	}
	
	/*
	 * Generates a plane spanned by corner1, corner2 and corner3.
	 */
	private static void generatePlane(World world, Vector corner1, Vector corner2, Vector corner3, int blockId) {
		int iterations = (int)corner1.distance(corner3);
		
		Vector c1 = corner1.clone(), c2 = corner2.clone();
		Vector dir = corner1.clone().subtract(corner3).multiply(-1).normalize();
		
		
		for (int i = 0; i <= iterations; i++) {
			connectCorner(world, c1, c2, blockId);
			c1.add(dir);
			c2.add(dir);
		}
	}
	
	/*
	 * Builds the starting point for the different teams.
	 */
	public static void buildWorldCage(World world, int blockId) {
		int teams = TeamConfig.teamCount;
		Vector[] corners = new Vector[4];
		Vector downVector = new Vector(0, -cubicCageWidth, 0);
		
		for (int i = 0; i < teams; i++) {
			//Calculate cage position
			double angle = 360f / (float)teams * i; 
			int x = (int)Math.round((radius + cubicCageWidth) * Math.cos(angle * conversion));
			int y = (int)Math.round((radius + cubicCageWidth) * Math.sin(angle * conversion));
			
			corners[0] = new Vector(x - (cubicCageWidth / 2), cubicCageHeight, y - (cubicCageWidth / 2));
			corners[1] = new Vector(x + (cubicCageWidth / 2), cubicCageHeight, y - (cubicCageWidth / 2));
			corners[2] = new Vector(x + (cubicCageWidth / 2), cubicCageHeight, y + (cubicCageWidth / 2));
			corners[3] = new Vector(x - (cubicCageWidth / 2), cubicCageHeight, y + (cubicCageWidth / 2));
			
			//Generate outer walls
			for(int k=0; k < 4; k++) {
				int j = (k+1) % 4;
				
				generatePlane(world, corners[k], corners[j], corners[k].clone().subtract(downVector), blockId);
			}
			
			//Generate floor and ceiling
			generatePlane(world, corners[0], corners[1], corners[3], blockId);
			generatePlane(world, corners[0].subtract(downVector), corners[1].subtract(downVector), corners[3].subtract(downVector), blockId);
		}
	}
	
	/*
	 * Builds the cage in the team builder dimension
	 */
	public static void buildMidCage(World world, int blockId) {
		
		for (int i = 0; i < 360; i++) {
			int x = (int)Math.round((radius * Math.cos(i * conversion)));
			int y = (int)Math.round((radius * Math.sin(i * conversion)));
			
			for (int j = 0; j < height; j++) {
				world.setBlock(x, j+1, y, blockId, 0, 1+2);
			}
		}
		int rr = radius * radius;
		for (int x = -radius; x <= radius; x++) {
			for (int z = -radius; z <= radius; z++) {
				if (x*x + z*z <= rr) {
					world.setBlock(x,  height, z, blockId, 0, 1+2);
				}
			}
		}
	}
	
	/*
	 * Builds the TeamSelection areas with or without cages.
	 */
	public static void buildTeamSelection(World world, boolean cages) {
		for (int i = 0; i < 6; i++) {
			double angle = i * (360f / (TeamConfig.maxTeams - 1f));
			
			//Calculate midpoints
			int x = (int)Math.round(((radius + distance) * Math.cos(angle * conversion)));
			int y = (int)Math.round(((radius + distance) * Math.sin(angle * conversion)));
			
			world.setBlock(x, 1, y, TeamConfig.teamSelectionId, i+1, 1+2);
			
			
			//Generate Cage
			if (cages) {
				for (int j = 0; j < height; j++) {
					world.setBlock(x-1, j+2, y, TeamConfig.unbreakableGlassId, 0, 1+2+4);
					world.setBlock(x+1, j+2, y, TeamConfig.unbreakableGlassId, 0, 1+2+4);
					world.setBlock(x, j+2, y+1, TeamConfig.unbreakableGlassId, 0, 1+2+4);
					world.setBlock(x, j+2, y-1, TeamConfig.unbreakableGlassId, 0, 1+2+4);
				}
			}
		}
	}
	
	public static void buildTeamSelection(World world) {
		SetupStructureBuilder.buildTeamSelection(world, true);
	}
}
