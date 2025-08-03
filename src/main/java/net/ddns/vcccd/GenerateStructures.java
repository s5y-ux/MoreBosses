package net.ddns.vcccd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
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
        int pasteY = 64;

        // Delay paste to avoid triggering recursive chunk loads
        new BukkitRunnable() {
            @Override
            public void run() {
                pasteSchematic(world, pasteX, pasteY, pasteZ);
            }
        }.runTaskLater(plugin, 1L); // 1 tick later
    }

    

    private void pasteSchematic(World world, int x, int y, int z) {
        File schematicFile = new File("plugins/MoreBosses/structures/GeneratedTower.schem");

        ClipboardFormat format = ClipboardFormats.findByFile(schematicFile);
        if (format == null) {
            Bukkit.getLogger().warning("Unsupported schematic format.");
            return;
        }

        try (ClipboardReader reader = format.getReader(new FileInputStream(schematicFile))) {
            if(new Random().nextInt(800) == 1) {
                Clipboard clipboard = reader.read();
                
                Location spawnLocation = new Location(world, x, y, z);
                new OswaldoEntity(plugin.getConfig().getInt("OswaldoHealth"), spawnLocation, world, plugin);
                plugin.getConsole().sendMessage("Spawned Boss");
                
                EditSession editSession = WorldEdit.getInstance()
                        .newEditSessionBuilder()
                        .world(BukkitAdapter.adapt(world))
                        .build();

                Operation operation = new ClipboardHolder(clipboard)
                        .createPaste(editSession)
                        .to(BlockVector3.at(x, y, z))
                        .ignoreAirBlocks(false)
                        .build();

                Operations.complete(operation);
                editSession.flushSession();

                
                plugin.getConsole().sendMessage("Spawned Building");
            }

        } catch (IOException | WorldEditException e) {
            e.printStackTrace();
        }
    }
    
}
