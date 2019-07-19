package com.efnilite.redaktor.block.server;

import com.efnilite.redaktor.block.BlockFactory;
import com.efnilite.redaktor.util.QuickUtil;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;

public class BlockFactory_v131 implements BlockFactory {

    @Override
    public void setBlock(Location location, BlockData data) {
        location.getBlock().setBlockData(data, QuickUtil.shouldUpdateOnPlace(data.getMaterial()));
    }
}