package fr.theogiraudet.HtS.LootTables;

import java.io.IOException;
import java.net.URISyntaxException;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;

public class LootTables {
	
	private static String wdir;
	private static final String lt = "loot_tables/";
	private static final String Edir = "/minecraft/entities/";
	private static final String Gdir = "/minecraft/gameplay/";
	private static final String Cdir = "/customs/";
	
	public static void loadLootTables() {
		for(World w : Bukkit.getWorlds()) {if(w.getEnvironment() == Environment.NORMAL) { wdir = w.getName() + "/data/loot_tables";}}
		try {
			//Entities
			FileExtractor.extractFile(lt + "bat.json", wdir + Edir);
			FileExtractor.extractFile(lt + "ghast.json", wdir + Edir);
			FileExtractor.extractFile(lt + "skeleton.json", wdir + Edir);
			FileExtractor.extractFile(lt + "zombie.json", wdir + Edir);
			//Gameplay
			FileExtractor.extractFile(lt + "fishing.json", wdir + Gdir);
			//Custom
			FileExtractor.extractFile(lt + "coffre.json", wdir + Cdir);
			FileExtractor.extractFile(lt + "crate.json", wdir + Cdir);
			
		} catch (IOException | URISyntaxException e) {e.printStackTrace();}
	}
	
}

