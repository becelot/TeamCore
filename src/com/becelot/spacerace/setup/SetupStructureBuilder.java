package com.becelot.spacerace.setup;

import com.becelot.spacerace.SpaceConfig;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class SetupStructureBuilder {
	public static final int radius = 10;
	public static final int height = 5;
	public static final int distance = 5;
	
	private static final double conversion = Math.PI / 180f;
	
	public static void buildMidCage(World world) {
		
		for (int i = 0; i < 360; i++) {
			int x = (int)Math.round((radius * Math.cos(i * conversion)));
			int y = (int)Math.round((radius * Math.sin(i * conversion)));
			
			for (int j = 0; j < height; j++) {
				world.setBlock(x, j+1, y, Block.glass.blockID, 0, 1+2);
			}
		}
		int rr = radius * radius;
		for (int x = -radius; x <= radius; x++) {
			for (int z = -radius; z <= radius; z++) {
				if (x*x + z*z <= rr) {
					world.setBlock(x,  height, z, Block.glass.blockID, 0, 1+2);
				}
			}
		}
		
		world.setBlock(0, 0, 0, Block.glass.blockID);
	}
	
	public static void buildTeamSelection(World world, boolean cages) {
		for (int i = 0; i < 6; i++) {
			double angle = i * (360f / (SpaceConfig.maxTeams - 1f));
			
			//Calculate midpoints
			int x = (int)Math.round(((radius + distance) * Math.cos(angle * conversion)));
			int y = (int)Math.round(((radius + distance) * Math.sin(angle * conversion)));
			
			world.setBlock(x, 1, y, SpaceConfig.teamSelectionId, i+1, 1+2);
			
			
			//Generate Cage
			if (cages) {
				for (int j = 0; j < height; j++) {
					world.setBlock(x-1, j+2, y, Block.glass.blockID, 0, 1+2+4);
					world.setBlock(x+1, j+2, y, Block.glass.blockID, 0, 1+2+4);
					world.setBlock(x, j+2, y+1, Block.glass.blockID, 0, 1+2+4);
					world.setBlock(x, j+2, y-1, Block.glass.blockID, 0, 1+2+4);
				}
			}
		}
	}
	
	public static void buildTeamSelection(World world) {
		SetupStructureBuilder.buildTeamSelection(world, true);
	}
}
