package net.ddns.vcccd;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Buildings {

    private final Main main;

    public Buildings(Main main) {
        this.main = main;
        Path path = Paths.get("plugins", "WorldEdit", "schematics");
        if(Files.exists(path) && Files.isDirectory(path)){
            
        }
    }

    public enum Building{
        OSWALDOBUILDING,
        BIGBOYBUILDING,
        TIMMOTHYBUILDING,
        BARTHOLOMEWBUILDING,
        PIGGYBUILDING,
        GORTBUILDING,
        DRSTRANGEBUILDING
    }

    public File getSchematic(Building building){
        switch(building){
            case OSWALDOBUILDING:
            File oswaldoSchematicFile = new File("plugins/WorldEdit/schematics/Oswaldo.schem");
            return oswaldoSchematicFile;
            case BIGBOYBUILDING:
            File bigBoySchematicFile = new File("plugins/WorldEdit/schematics/Bigboy.schem");
            return bigBoySchematicFile;
            case TIMMOTHYBUILDING:
            File timmothySchematicFile = new File("plugins/WorldEdit/schematics/Timmothy.schem");
            return timmothySchematicFile;
            case BARTHOLOMEWBUILDING:
            File bartholomewSchematicFile = new File("plugins/WorldEdit/schematics/Bartholomew.schem");
            return bartholomewSchematicFile;
            case PIGGYBUILDING:
            File piggySchematicFile = new File("plugins/WorldEdit/schematics/Piggy.schem");
            return piggySchematicFile;
            case GORTBUILDING:
            File gortSchematicFile = new File("plugins/WorldEdit/schematics/Gort.schem");
            return gortSchematicFile;
            case DRSTRANGEBUILDING:
            File drStrangeSchematicFile = new File("plugins/WorldEdit/schematics/DrStrange.schem");
            return drStrangeSchematicFile;
            default:
            return null;

        }
    }
}
