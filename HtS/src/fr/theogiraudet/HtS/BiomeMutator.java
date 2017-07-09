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
	
	private boolean wart = false;
	private boolean netherwart = false;
	private boolean shulker = false;	
	private Player p;
	private int cx;
	private int cz;
	private int x;
	private int y;
	private int z;
	private int ybc;
	private int ytc;
	private int xlc;
	private int xrc;
	private int zlc;
	private int zrc;
	
	@SuppressWarnings("deprecation")
	public BiomeMutator(Player player){	
		p = player;
		World w = p.getLocation().getWorld();		
		for(Chunk c : w.getLoadedChunks()){
			if(Randomizer.RandRate(5)){
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
						x = coords[0];
						z = coords[1];
						y = coords[2];
						
						ybc = y-1;
						ytc = y+1;
						xlc = x-1;
						xrc = x+1;
						zlc = z-1;
						zrc = z+1;
						
						if (w.getBlockAt(x, y, z).getType() == Material.AIR
								&& w.getBlockAt(x, ytc, z).getType() == Material.AIR
								&& w.getBlockAt(x, ybc, z).getType() != Material.AIR
								&& w.getBlockAt(x, ybc, z).getType() != Material.LAVA
								&& w.getBlockAt(x, ybc, z).getType() != Material.NETHER_BRICK
								&& w.getBlockAt(x, ybc, z).getType() != Material.NETHER_BRICK_STAIRS
								&& w.getBlockAt(x, ybc, z).getType() != Material.NETHER_FENCE
								&& w.getBlockAt(x, ybc, z).getType() != Material.STEP
								&& !netherwart){
							Location lb = new Location(w,x,ybc,z);
							Location lt = new Location(w,x,y,z);
							
							lb.getBlock().setType(Material.SOUL_SAND);
							lt.getBlock().setType(Material.NETHER_WARTS);
							lt.getBlock().setData((byte) 3);
							netherwart = true;
						}
						else if (w.getBlockAt(x, y, z).getType() == Material.AIR
								&& ((w.getBlockAt(x, ybc, z).getType() != Material.AIR
										&& w.getBlockAt(x, ytc, z).getType() == Material.AIR
										&& w.getBlockAt(xlc, y, z).getType() == Material.AIR
										&& w.getBlockAt(xrc, y, z).getType() == Material.AIR
										&& w.getBlockAt(x, y, zlc).getType() == Material.AIR
										&& w.getBlockAt(x, y, zrc).getType() == Material.AIR)
										|| (w.getBlockAt(x, ybc, z).getType() == Material.AIR
												&& w.getBlockAt(x, ytc, z).getType() != Material.AIR
												&& w.getBlockAt(xlc, y, z).getType() == Material.AIR
												&& w.getBlockAt(xrc, y, z).getType() == Material.AIR
												&& w.getBlockAt(x, y, zlc).getType() == Material.AIR
												&& w.getBlockAt(x, y, zrc).getType() == Material.AIR)) && !shulker){
							Shulker sh = (Shulker) (w.spawnEntity(new Location(w,x,y,z), EntityType.SHULKER));
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
