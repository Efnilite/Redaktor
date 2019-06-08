package com.efnilite.redaktor.block.server;

import com.efnilite.redaktor.block.IBlockFactory;
import com.efnilite.redaktor.object.pattern.Pattern;
import net.minecraft.server.v1_13_R2.BlockPosition;
import net.minecraft.server.v1_13_R2.IBlockData;
import net.minecraft.server.v1_13_R2.World;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R2.block.data.CraftBlockData;

public class BlockFactory_v131 implements IBlockFactory {

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
}