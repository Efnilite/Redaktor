package com.efnilite.redaktor.block.server;

import com.efnilite.redaktor.block.BlockFactory;
import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.IBlockData;
import net.minecraft.server.v1_14_R1.World;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.block.data.CraftBlockData;

public class BlockFactory_v141 implements BlockFactory {

    @Override
    public void setBlock(Location location, BlockData data) {
        IBlockData newData = ((CraftBlockData) data).getState();
        World world = ((CraftWorld) location.getWorld()).getHandle();
        BlockPosition position = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());

        IBlockData old = world.getType(position);
        boolean success = world.setTypeUpdate(position, newData);
        if (success) {
            world.notify(position, old, newData, 3);
        }
    }
}