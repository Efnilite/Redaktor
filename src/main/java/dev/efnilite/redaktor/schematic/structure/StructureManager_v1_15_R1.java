package dev.efnilite.redaktor.schematic.structure;

import dev.efnilite.redaktor.player.BukkitPlayer;
import dev.efnilite.redaktor.selection.CuboidSelection;
import dev.efnilite.redaktor.selection.Selection;
import dev.efnilite.redaktor.wrapper.RedaktorPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.io.File;

public class StructureManager_v1_15_R1 implements StructureManager {

    @Override
    public Vector paste(File file, Location to) {
//            DefinedStructure structure = new DefinedStructure();
//            structure.b(NBTCompressedStreamTools.a(new FileInputStream(file)));
//
//            World world = ((CraftWorld) to.getWorld()).getHandle();
//            DefinedStructureInfo info = new DefinedStructureInfo().a(EnumBlockMirror.NONE).a(EnumBlockRotation.NONE)
//                    .a(false).a((ChunkCoordIntPair) null).c(false).a(new Random());
//            StructureBoundingBox box = structure.b(info, new BlockPosition(to.getBlockX(), to.getBlockY(), to.getBlockZ()));
//            Location pos1 = new Location(to.getWorld(), box.a, box.b, box.c); // box coords to bukkit
//            Location pos2 = new Location(to.getWorld(), box.d, box.e, box.f); // box coords to bukkit
//
//            Location min = Util.min(pos1, pos2);
//            Location max = Util.max(pos1, pos2);
//
//            int deltaX = (max.getBlockX() - min.getBlockX()) / 2;
//            int deltaZ = (max.getBlockZ() - min.getBlockZ()) / 2;
//
//            int sizeX = deltaX * 2 + 1;
//            int sizeZ = deltaZ * 2 + 1;
//
//            structure.a(world, new BlockPosition(min.getBlockX() - deltaX, Math.min(box.b, box.e), min.getBlockZ() - deltaZ), info); // box.b and box.e = y coords
            return new Vector(0, 0, 0);
    }

    @Override
    public void save(File file, CuboidSelection selection) {
        // todo
    }

    @Override
    public void showBounds(RedaktorPlayer<?> player, Selection selection) {
        if (player == null || !(selection instanceof CuboidSelection) || !player.isPlayer()) {
            return;
        }
        Player p = ((BukkitPlayer) player).getPlayer();

        CuboidSelection cuboid = (CuboidSelection) selection;
        Location pos1 = cuboid.getMaximumPoint();
        Location pos2 = cuboid.getMinimumPoint();

//        p.sendBlockChange(pos1, org.bukkit.Material.STRUCTURE_BLOCK.createBlockData());
//        BlockPosition pos = new BlockPosition(pos1.getBlockX(), pos1.getBlockY(), pos1.getBlockZ());
//        NBTTagCompound compound = new NBTTagCompound();
//        compound.setString("name", "bounding");
//        compound.setString("author", "redaktor");
//        compound.setString("metadata", "");
//        compound.setInt("x", pos1.getBlockX());
//        compound.setInt("y", pos1.getBlockY());
//        compound.setInt("z", pos1.getBlockZ());
//        compound.setInt("posX", pos1.getBlockX());
//        compound.setInt("posY", pos1.getBlockY());
//        compound.setInt("posZ", pos1.getBlockZ());
//        compound.setInt("sizeX", pos1.getBlockX() - pos2.getBlockX());
//        compound.setInt("sizeY", pos1.getBlockY() - pos2.getBlockY());
//        compound.setInt("sizeZ", pos1.getBlockZ() - pos2.getBlockZ());
//        compound.setString("rotation", "NONE");
//        compound.setString("mirror", "NONE");
//        compound.setString("mode", "SAVE");
//        compound.setBoolean("ignoreEntities", true);
//        compound.setBoolean("powered", false);
//        compound.setBoolean("showair", true);
//        compound.setBoolean("showboundingbox", true);
//        compound.setFloat("integrity", 1.0F);
//        compound.setLong("seed", 0L);
//        PacketPlayOutTileEntityData packet = new PacketPlayOutTileEntityData(pos, 7, compound);
//        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
//        BukkitRunnable runnable = new BukkitRunnable() {
//            @Override
//            public void run() {
//                PacketPlayOutTileEntityData packet = new PacketPlayOutTileEntityData(pos, 7, compound);
//                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
//            }
//        };
//        Tasks.delayTask(runnable, 1);
    }
}
