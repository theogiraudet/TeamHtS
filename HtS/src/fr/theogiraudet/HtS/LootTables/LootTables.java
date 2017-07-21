package fr.theogiraudet.HtS.LootTables;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;

public class LootTables {
	
	private static String worldData;

	public static void loadLootTables() {
		for(World w : Bukkit.getWorlds()) { if(w.getEnvironment() == Environment.NORMAL) { worldData = w.getName() + "/data/loot_tables";}}
		try {
			extractFile("loot_tables/bat.json", worldData + "/minecraft/entities/");
			extractFile("loot_tables/ghast.json", worldData + "/minecraft/entities/");
			extractFile("loot_tables/skeleton.json", worldData + "/minecraft/entities/");
			extractFile("loot_tables/zombie.json", worldData + "/minecraft/entities/");
			extractFile("loot_tables/fishing.json", worldData + "/minecraft/gameplay/");
			extractFile("loot_tables/coffre.json", worldData + "/customs/");
			extractFile("loot_tables/crate.json", worldData + "/customs/");
			
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	private static void extractFile(String name, String folderPath) throws IOException, URISyntaxException
    {	
		String serverPath = LootTables.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().split("plugins")[0].split("/")[1];
		String path = serverPath + folderPath;
		createFolder(path);
		
        File target = new File(path + name.split("/")[1]);
        if (target.exists())
            return;

        FileOutputStream out = new FileOutputStream(target);
        ClassLoader cl = LootTables.class.getClassLoader();
        InputStream in = cl.getResourceAsStream(name);

        byte[] buf = new byte[8*1024];
        int len;
        while((len = in.read(buf)) != -1)
        {
            out.write(buf,0,len);
        }
        out.close();
        in.close();
    }
	
	private static void createFolder(String path) {
		if(!Files.exists(Paths.get(path))) { new File(path).mkdirs();}
	}
}

