package com.becelot.spacerace.setup;

import com.becelot.spacerace.SpaceConfig;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class SetupStructureBuilder {
	public static final int radius = 20;
	public static final int height = 5;
	public static final int distance = 5;
	
	private static final double conversion = Math.PI / 180f;
	
	public static void buildMidCage(World world) {
		
		for (int i = 0; i < 360; i++) {
			int x = (int)Math.floor((radius * Math.cos(i * conversion)));
			int y = (int)Math.floor((radius * Math.sin(i * conversion)));
			
			for (int j = 0; j < height; j++) {
				world.setBlock(x, j, y, Block.glass.blockID, 0, 1+2);
			}
		}
		
		world.setBlock(0, 0, 0, Block.glass.blockID);
	}
	
	public static void buildTeamSelection(World world) {
		for (int i = 0; i < 6; i++) {
			double angle = i * (360f / (SpaceConfig.maxTeams - 1f));
			
			int x = (int)Math.floor(((radius + distance) * Math.cos(angle * conversion)));
			int y = (int)Math.floor(((radius + distance) * Math.sin(angle * conversion)));
			
			world.setBlock(x, 1, y, SpaceConfig.teamSelectionId, i+1, 1+2);
		}
	}
}
