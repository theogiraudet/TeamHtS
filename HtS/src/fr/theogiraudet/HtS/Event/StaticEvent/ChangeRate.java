package fr.theogiraudet.HtS.Event.StaticEvent;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemStack;

import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.Objects.Randomizer;

public class ChangeRate implements Listener {
	
	public ChangeRate(HtS htS) { }


	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Block b = e.getBlock();
		if (b.getType() == Material.GRAVEL) {
			if (Randomizer.RandRate(21)) {
				e.setCancelled(true);
				b.setType(Material.AIR);
				b.getLocation().getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.FLINT));
			} else {
				e.setCancelled(true);
				b.setType(Material.AIR);
				b.getLocation().getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.GRAVEL));
			}
		} else if (((b.getType() == Material.LEAVES)
				&& ((b.getData() == 0) || (b.getData() == 4) || (b.getData() == 12)))
				|| ((b.getType() == Material.LEAVES_2) && (b.getData() == 1)) || (b.getData() == 5)
				|| (b.getData() == 13)) {
			
			if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.WOOD_HOE
					&& e.getPlayer().getInventory().getItemInMainHand().getType() != Material.STONE_HOE
					&& e.getPlayer().getInventory().getItemInMainHand().getType() != Material.GOLD_HOE
					&& e.getPlayer().getInventory().getItemInMainHand().getType() != Material.IRON_HOE
					&& e.getPlayer().getInventory().getItemInMainHand().getType() != Material.DIAMOND_HOE) {
				
				if (Randomizer.RandRate(3)) {
					e.setCancelled(true);
					b.setType(Material.AIR);
					b.getLocation().getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.APPLE));
				} else {
					e.setCancelled(true);
					b.setType(Material.AIR);
				}
			} else {
				if (Randomizer.RandRate(5)) {
					e.setCancelled(true);
					b.setType(Material.AIR);
					b.getLocation().getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.APPLE));
				} else {
					e.setCancelled(true);
					b.setType(Material.AIR);
				}
			}
		}
	}
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onLeavesDecay(LeavesDecayEvent e) {
		Block b = e.getBlock();
		if (((b.getType() == Material.LEAVES) && ((b.getData() == 0) || (b.getData() == 4) || (b.getData() == 12)))
				|| ((b.getType() == Material.LEAVES_2) && (b.getData() == 1)) || (b.getData() == 5)
				|| (b.getData() == 13)) {
			if (Randomizer.RandRate(3)) {
				e.setCancelled(true);
				b.setType(Material.AIR);
				b.getLocation().getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.APPLE));
			} else {
				e.setCancelled(true);
				b.setType(Material.AIR);
			}
		}
	}

}
