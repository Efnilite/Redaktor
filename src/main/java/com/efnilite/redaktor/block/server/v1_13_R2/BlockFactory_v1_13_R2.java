package com.efnilite.redaktor.block.server.v1_13_R2;

import com.efnilite.redaktor.block.BlockFactory;
import com.efnilite.redaktor.util.QuickUtil;
import com.efnilite.redaktor.util.thread.QueueThread;
import net.minecraft.server.v1_14_R1.PacketPlayOutMultiBlockChange;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;

public class BlockFactory_v1_13_R2 extends BlockFactory {

    @Override
    public void setBlock(Location location, BlockData data) {
        location.getBlock().setBlockData(data, QuickUtil.shouldUpdateOnPlace(data.getMaterial()));
    }

    @Override
    public void flush() {
        PacketPlayOutMultiBlockChange packet = new PacketPlayOutMultiBlockChange();
    }
}