package com.efnilite.redaktor.block.server.v1_14_R1;

import com.efnilite.redaktor.block.BlockFactory;
import com.efnilite.redaktor.util.QuickUtil;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;

public class BlockFactory_v1_14_R1 extends BlockFactory {

    @Override
    public void setBlock(Location location, BlockData data) {
        location.getBlock().setBlockData(data, QuickUtil.shouldUpdateOnPlace(data.getMaterial()));

        /*BlockPosition position = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        World world = ((CraftWorld) location.getWorld()).getHandle();
        net.minecraft.server.v1_14_R1.Chunk chunk = world.getChunkAtWorldCoords(position);
        IBlockData blockData = ((CraftBlockData) data).getState();

        if (QuickUtil.shouldUpdateOnPlace(data.getMaterial())) {
            world.setTypeAndData(position, blockData, 3);
        } else {
            chunk.setType(position, blockData, false, false);
        }

        if (!chunks.containsKey(chunk.getBukkitChunk())) {
            List<Location> locs = new ArrayList<>();
            locs.add(location);
            chunks.put(chunk.getBukkitChunk(), locs);
        } else {
            List<Location> locs = new ArrayList<>(chunks.get(chunk.getBukkitChunk()));
            chunks.put(chunk.getBukkitChunk(), locs);
        }
        locations.add(location);
        change++;*/
    }

    @Override
    public void flush() {
        /*Queue<Chunk> chunks = new LinkedList<>(this.chunks.keySet());

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (chunks.peek() == null) {
                    this.cancel();
                    return;
                }

                Chunk chunk = chunks.poll();

                short[] indices = new short[locations.size()];
                int index = 0;
                for (Location loc : BlockFactory_v1_14_R1.this.chunks.get(chunk)) {
                    if (loc.getChunk() == chunk) {
                        int x = loc.getBlockX();
                        int y = loc.getBlockY();
                        int z = loc.getBlockZ();
                        indices[index++] = (short) (((x & 0xF) << 12) | ((z & 0xF) << 8) | y);
                    }
                }

                PacketPlayOutMultiBlockChange packet = new PacketPlayOutMultiBlockChange(change, indices, ((CraftChunk) chunk).getHandle());
                for (Player player : Bukkit.getOnlinePlayers()) {
                    PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
                    connection.sendPacket(packet);
                }
            }
        };
        Tasks.repeatAsync(runnable, 20);*/
    }
}