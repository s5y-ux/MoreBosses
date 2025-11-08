package net.ddns.vcccd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;

public class GenerateStructures implements Listener {

    // Prevent re-pasting same chunk
    private final Set<String> pastedChunks = new HashSet<>();
    private final Main plugin;
    private int random = new Random().nextInt(1000);

    public GenerateStructures(Main plugin) {
        this.plugin = plugin;
    }

    public boolean allBlocksNotAir(Chunk chunk){
        World world = chunk.getWorld();

        int chunkX = chunk.getX() << 4;
        int chunkZ = chunk.getZ() << 4;

        for(int x = 0; x < 12; x++){
            for(int z = 0; z < 12; z++){

                int worldX = chunkX + x;
                int worldZ = chunkZ + z;

                int worldY = world.getHighestBlockYAt(worldX, worldZ);

                Block block = world.getBlockAt(worldX, worldY - 1,  worldZ);

                Material type = block.getType();

                if(type == Material.AIR || type == Material.WATER){
                    return false;
                }


            }
        }

        return true;
    }


    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        Chunk chunk = event.getChunk();
        World world = chunk.getWorld();
        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();

        String key = world.getName() + ":" + chunkX + ":" + chunkZ;
        if (pastedChunks.contains(key)) return;
        pastedChunks.add(key);

        int pasteX = chunkX << 4;
        int pasteZ = chunkZ << 4;
        int pasteY = world.getHighestBlockYAt(chunk.getBlock(0, 0, 0).getLocation());

        // Delay paste to avoid triggering recursive chunk loads
        new BukkitRunnable() {
            @Override
            public void run() {
                if(allBlocksNotAir(chunk)){
                    switch(new Random().nextInt(6)){
                        case 0:
                            File oswaldoSchematicFile = new File("plugins/MoreBosses/structures/GeneratedTower.schem");
                            pasteSchematic(world, pasteX, pasteY, pasteZ, oswaldoSchematicFile, () -> new OswaldoEntity(plugin.getConfig().getInt("OswaldoHealth"), new Location(world, pasteX, pasteY + 2, pasteZ), world, plugin));
                            break;
                        case 1:
                            File timmothySchematicFile = new File("plugins/MoreBosses/structures/TimmothyHut.schem");
                            pasteSchematic(world, pasteX, pasteY, pasteZ, timmothySchematicFile, () -> new TimmothyEntity(plugin.getConfig().getInt("TimmothyHealth"), new Location(world, pasteX, pasteY + 2, pasteZ), world, plugin));
                            break;
                        case 2:
                            File bartholomewSchematicFile = new File("plugins/MoreBosses/structures/BartTower.schem");
                            pasteSchematic(world, pasteX, pasteY, pasteZ, bartholomewSchematicFile, () -> new BartholomewEntity(plugin.getConfig().getInt("BartholomewHealth"), new Location(world, pasteX, pasteY + 2, pasteZ), world, plugin));
                            break;
                        case 3:
                            File piggySchematicFile = new File("plugins/MoreBosses/structures/PiggyPin.schem");
                            pasteSchematic(world, pasteX, pasteY, pasteZ, piggySchematicFile, () -> new PiggyEntity(plugin.getConfig().getInt("PiggyHealth"), new Location(world, pasteX, pasteY + 2, pasteZ), world, plugin));
                            break;
                        case 4:
                            File gortSchematicFile = new File("plugins/MoreBosses/structures/GortHouse.schem");
                            pasteSchematic(world, pasteX, pasteY, pasteZ, gortSchematicFile, () -> new GortEntity(plugin.getConfig().getInt("GortHealth"), new Location(world, pasteX, pasteY + 2, pasteZ), world, plugin));
                            break;
                        case 5:
                            File drStrangeSchematicFile = new File("plugins/MoreBosses/structures/StrangeLibrary.schem");
                            pasteSchematic(world, pasteX, pasteY, pasteZ, drStrangeSchematicFile, () -> new DrStrangeEntity(plugin.getConfig().getInt("DrStrangeHealth"), new Location(world, pasteX, pasteY + 2, pasteZ), world, plugin));
                            break;
                        default:
                            break;
                    }
                    //pasteSchematic(world, pasteX, pasteY, pasteZ);
                }
            }
        }.runTaskLater(plugin, 1L); // 1 tick later
    }


    private void pasteSchematic(World world, int x, int y, int z, File schematicFile, Supplier<?> bossSpawner) {

    ClipboardFormat format = ClipboardFormats.findByFile(schematicFile);
    if (format == null) {
        Bukkit.getLogger().warning("Unsupported schematic format.");
        return;
    }

    try (ClipboardReader reader = format.getReader(new FileInputStream(schematicFile))) {

        if (new Random().nextInt(plugin.getConfig().getInt("SpawnFrequency") * 100) == 1) {

            Clipboard clipboard = reader.read();

            plugin.getConsole().sendMessage("Attempting to paste schematic at " + x + ", " + (y + 1) + ", " + z);

            EditSession editSession = WorldEdit.getInstance()
                    .newEditSessionBuilder()
                    .world(BukkitAdapter.adapt(world))
                    .build();

            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(x, y + 1, z))
                    .ignoreAirBlocks(false)
                    .build();

            Operations.complete(operation);
            editSession.flushSession();

            plugin.getConsole().sendMessage("Building pasted.");

            // Delay boss spawn to allow world to stabilize after paste
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        bossSpawner.get();
                        plugin.getConsole().sendMessage("Boss spawned at " + x + ", " + (y + 2) + ", " + z);
                    } catch (Exception ex) {
                        plugin.getConsole().sendMessage("Boss spawn failed: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                }
            }.runTaskLater(plugin, 10L);

        }

    } catch (IOException | WorldEditException e) {
        e.printStackTrace();
    }
}


    
}
