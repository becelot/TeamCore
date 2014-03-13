package com.becelot.spacerace.dimension;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class SpaceChunkProvider implements IChunkProvider {
	
	private final int size = 5;
	private World worldObj;
	
	public SpaceChunkProvider(World world) {
		worldObj = world;
		//world.setSpawnLocation(0, 0, 0);
	}

	@Override
	public boolean chunkExists(int i, int j) {
		if (Math.abs(i) > size) return false;
		if (Math.abs(j) > size) return false;
		return true;
	}

	@Override
	public Chunk provideChunk(int i, int j) {
		//Don't generate any further than 5 chunks
		if (Math.abs(i) > size || Math.abs(j) > size)	
			return new EmptyChunk(this.worldObj, i, j);
		
        Chunk chunk = new Chunk(this.worldObj, i, j);

        //Generate an layerd chunk
        for (int k = 0; k < 1; ++k)
        {
            int l = k >> 4;
            ExtendedBlockStorage extendedblockstorage = chunk.getBlockStorageArray()[l];

            if (extendedblockstorage == null)
            {
                extendedblockstorage = new ExtendedBlockStorage(k, !this.worldObj.provider.hasNoSky);
                chunk.getBlockStorageArray()[l] = extendedblockstorage;
            }

            for (int i1 = 0; i1 < 16; ++i1)
            {
                for (int j1 = 0; j1 < 16; ++j1)
                {
                    extendedblockstorage.setExtBlockID(i1, k & 15, j1, Block.grass.blockID);
                }
            }
            
        }
        
        chunk.generateSkylightMap();
		
		return chunk;
	}

	@Override
	public Chunk loadChunk(int i, int j) {
		return this.provideChunk(i, j);
	}

	@Override
	public void populate(IChunkProvider ichunkprovider, int i, int j) {

	}

	@Override
	public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate) {
		return true;
	}

	@Override
	public boolean unloadQueuedChunks() {
		return false;
	}

	@Override
	public boolean canSave() {
		return true;
	}

	@Override
	public String makeString() {
		return "SpaceDimensionETA";
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getPossibleCreatures(EnumCreatureType enumcreaturetype, int i,
			int j, int k) {
		return null;
	}

	@Override
	public ChunkPosition findClosestStructure(World world, String s, int i,
			int j, int k) {
		return null;
	}

	@Override
	public int getLoadedChunkCount() {
		return 0;
	}

	@Override
	public void recreateStructures(int i, int j) {

	}

	@Override
	public void saveExtraData() {
	}

}
