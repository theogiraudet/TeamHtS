package fr.theogiraudet.HtS;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;

import fr.theogiraudet.HtS.Objects.Randomizer;

public class BonusChest {
	
	private static Boolean place = true; 
	
	public static void setBonusChest() {
				
		World w = Bukkit.getWorld("world");
		WorldBorder border = w.getWorldBorder();
		place = true;
		
		while(place){
			int coords[] = Randomizer.RandCoord((int) Math.floor(-1*border.getSize()/2), (int) Math.floor(border.getSize()/2), (int) Math.floor(-1*border.getSize()/2), (int) Math.floor(border.getSize()/2), 60, 128);
			if ((w.getBlockAt(coords[0], coords[1], coords[2]).getType() == Material.AIR
				|| w.getBlockAt(coords[0], coords[1], coords[2]).getType() == Material.SNOW)
				&& (w.getBlockAt(coords[0], coords[1] - 1, coords[2]).getType() == Material.GRASS
				|| w.getBlockAt(coords[0], coords[1] - 1, coords[2]).getType() == Material.SAND
				|| w.getBlockAt(coords[0], coords[1] - 1, coords[2]).getType() == Material.GRAVEL
				|| w.getBlockAt(coords[0], coords[1] - 1, coords[2]).getType() == Material.DIRT
				|| w.getBlockAt(coords[0], coords[1] - 1, coords[2]).getType() == Material.PACKED_ICE
				|| w.getBlockAt(coords[0], coords[1] - 1, coords[2]).getType() == Material.ICE
				|| w.getBlockAt(coords[0], coords[1] - 1, coords[2]).getType() == Material.CLAY
				||w.getBlockAt(coords[0], coords[1] - 1, coords[2]).getType() == Material.HARD_CLAY
				|| w.getBlockAt(coords[0], coords[1] - 1, coords[2]).getType() == Material.STAINED_CLAY)) {
				for(int j = coords[1]+3; j < coords[1]+40; j++) {
					if (w.getBlockAt(coords[0], j, coords[2]).getType() != Material.AIR
						&& w.getBlockAt(coords[0], j, coords[2]).getType() != Material.LEAVES
						&& w.getBlockAt(coords[0], j, coords[2]).getType() != Material.LEAVES_2) {
						place = false;
						break;
					}
				}
				if(place) {
					Location l = new Location(w, coords[0], coords[1], coords[2]);
					l.getBlock().setType(Material.CHEST);
					Bukkit.broadcastMessage("§4[§6HtS§4]§r Bonus Chest: " + coords[0] + " " + coords[2] + " !");
					place = false;
				}
				else {
					place = true;
				}
			}
		}
	}
}
