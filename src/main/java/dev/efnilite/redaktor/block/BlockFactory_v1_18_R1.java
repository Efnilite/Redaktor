package dev.efnilite.redaktor.block;

import dev.efnilite.redaktor.util.QuickUtil;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;

public class BlockFactory_v1_18_R1 extends BlockFactory {

    @Override
    public void setBlock(Location location, BlockData data) {
        location.getBlock().setBlockData(data, QuickUtil.shouldUpdateOnPlace(data.getMaterial()));
    }
}