package com.efnilite.redaktor.block.server;

import com.efnilite.redaktor.block.BlockFactory;
import com.efnilite.redaktor.util.QuickUtil;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;

public class BlockFactory_v141 implements BlockFactory {

    @Override
    public void setBlock(Location location, BlockData data) {
        // nms stuff is still being researched
        // (if nms is faster)
        location.getBlock().setBlockData(data, QuickUtil.shouldUpdateOnPlace(data.getMaterial()));
    }
}