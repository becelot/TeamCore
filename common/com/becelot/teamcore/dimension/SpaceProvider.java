package com.becelot.teamcore.dimension;

import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

import com.becelot.teamcore.SpaceConfig;

public class SpaceProvider extends WorldProvider{
	
	public SpaceProvider() {
		this.dimensionId = SpaceConfig.dimensionId;
	}

	@Override
	public String getDimensionName() {
		return "Space";
	}
	
	public IChunkProvider createChunkGenerator() {
		return new SpaceChunkProvider(this.worldObj);
	}

}
