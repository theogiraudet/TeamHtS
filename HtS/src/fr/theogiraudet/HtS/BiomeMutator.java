package fr.theogiraudet.HtS;

import org.bukkit.Chunk;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;

import fr.theogiraudet.HtS.Objects.Randomizer;

public class BiomeMutator{
	
	private static boolean wart = false, netherwart = false, shulker = false;	
	private static Player p;
	private static int cx, cz, x, y, z, ybc, ytc, xlc, xrc, zlc, zrc;
	
	@SuppressWarnings("deprecation")
	public static void NetherGenerator(Player player){	
		p = player;
		World w = p.getLocation().getWorld();		
		for(Chunk c : w.getLoadedChunks()){
			if(Randomizer.RandRate(3)){
				cx = c.getX() << 4;
				cz = c.getZ() << 4;	
				for(x = cx; x < cx + 16; x++) {
					for(z = cz; z < cz + 16; z++) {
						for(y = 50; y < 100; y++) {
							if(w.getBlockAt(x, y, z).getType() == Material.NETHER_WARTS){
								wart = true;
							}
						}
					}
				}
				if(!wart){
					for(int i = 0; i < 500000; i++){
						int[] coords = Randomizer.RandCoord(cx, cx+16, cz, cz+16, 50, 100);						
						ybc = coords[1]-1;
						ytc = coords[1]+1;
						xlc = coords[0]-1;
						xrc = coords[0]+1;
						zlc = coords[2]-1;
						zrc = coords[2]+1;
						
						if (w.getBlockAt(coords[0], coords[1], coords[2]).getType() == Material.AIR
								&& w.getBlockAt(coords[0], ytc, coords[2]).getType() == Material.AIR
								&& w.getBlockAt(coords[0], ybc, coords[2]).getType() != Material.AIR
								&& w.getBlockAt(coords[0], ybc, coords[2]).getType() != Material.LAVA
								&& w.getBlockAt(coords[0], ybc, coords[2]).getType() != Material.NETHER_BRICK
								&& w.getBlockAt(coords[0], ybc, coords[2]).getType() != Material.NETHER_BRICK_STAIRS
								&& w.getBlockAt(coords[0], ybc, coords[2]).getType() != Material.NETHER_FENCE
								&& w.getBlockAt(coords[0], ybc, coords[2]).getType() != Material.STEP
								&& !netherwart){
							Location lb = new Location(w,coords[0],ybc,coords[2]);
							Location lt = new Location(w,coords[0],coords[1],coords[2]);
							
							lb.getBlock().setType(Material.SOUL_SAND);
							lt.getBlock().setType(Material.NETHER_WARTS);
							lt.getBlock().setData((byte) 3);
							netherwart = true;
						}
						else if (w.getBlockAt(coords[0], coords[1], coords[2]).getType() == Material.AIR
								&& ((w.getBlockAt(coords[0], ybc, coords[2]).getType() != Material.AIR
										&& w.getBlockAt(coords[0], ytc, coords[2]).getType() == Material.AIR
										&& w.getBlockAt(xlc, coords[1], coords[2]).getType() == Material.AIR
										&& w.getBlockAt(xrc, coords[1], coords[2]).getType() == Material.AIR
										&& w.getBlockAt(coords[0], coords[1], zlc).getType() == Material.AIR
										&& w.getBlockAt(coords[0], coords[1], zrc).getType() == Material.AIR)
										|| (w.getBlockAt(coords[0], ybc, coords[2]).getType() == Material.AIR
												&& w.getBlockAt(coords[0], ytc, coords[2]).getType() != Material.AIR
												&& w.getBlockAt(xlc, coords[1], coords[2]).getType() == Material.AIR
												&& w.getBlockAt(xrc, coords[1], coords[2]).getType() == Material.AIR
												&& w.getBlockAt(coords[0], coords[1], zlc).getType() == Material.AIR
												&& w.getBlockAt(coords[0], coords[1], zrc).getType() == Material.AIR)) && !shulker){
							Shulker sh = (Shulker) (w.spawnEntity(new Location(w,coords[0],coords[1],coords[2]), EntityType.SHULKER));
							sh.setColor(DyeColor.RED);
							shulker = true;
						}
						else if(netherwart && shulker){
							netherwart = false;
							shulker = false;
							break;
						}
					}
				}
				else{
					wart = false;
				}	
			}
		}	
	}
}
