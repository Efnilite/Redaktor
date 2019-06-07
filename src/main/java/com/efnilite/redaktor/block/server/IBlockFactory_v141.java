package com.efnilite.redaktor.block.server;

import com.efnilite.redaktor.block.IBlockFactory;
import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.IBlockData;
import net.minecraft.server.v1_14_R1.World;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.block.data.CraftBlockData;

public class IBlockFactory_v141 implements IBlockFactory {

    @Override
    public void setBlock(Location location, org.bukkit.Material material) {
        IBlockData data = ((CraftBlockData) material.createBlockData()).getState();
        World world = ((CraftWorld) location.getWorld()).getHandle();
        BlockPosition position = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());

        IBlockData old = world.getType(position);
        boolean success = world.setTypeAndData(position, data, 1042);
        if (success) {
            world.notify(position, old, data, 3);
        }
    }

    @Override
    public void setBlock(Block block, org.bukkit.Material material) {
        this.setBlock(block.getLocation(), material);
    }
}