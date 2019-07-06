package com.efnilite.redaktor.block.server;

import com.efnilite.redaktor.block.BlockFactory;
import net.minecraft.server.v1_13_R2.BlockPosition;
import net.minecraft.server.v1_13_R2.IBlockData;
import net.minecraft.server.v1_13_R2.World;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R2.block.data.CraftBlockData;

public class BlockFactory_v131 implements BlockFactory {

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