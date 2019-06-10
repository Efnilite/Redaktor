package com.efnilite.redaktor.block.server;

import com.efnilite.redaktor.block.IBlockFactory;
import com.efnilite.redaktor.object.pattern.Pattern;
import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.IBlockData;
import net.minecraft.server.v1_14_R1.World;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.block.data.CraftBlockData;

public class BlockFactory_v141 implements IBlockFactory {

    @Override
    public void setBlock(Location location, Material material) {
        IBlockData data = ((CraftBlockData) material.createBlockData()).getState();
        World world = ((CraftWorld) location.getWorld()).getHandle();
        BlockPosition position = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());

        IBlockData old = world.getType(position);
        boolean success = world.setTypeUpdate(position, data);
        if (success) {
            world.notify(position, old, data, 3);
        }
    }

    @Override
    public void setBlock(Block block, Material material) {
        this.setBlock(block.getLocation(), material);
    }

    @Override
    public void setBlock(Block block, Pattern pattern) {
        this.setBlock(block.getLocation(), pattern.apply(block));
    }

    @Override
    public void setBlock(org.bukkit.World world, int x, int y, int z, Pattern pattern) {
        this.setBlock(new Location(world, x, y, z).getBlock(), pattern);
    }
}