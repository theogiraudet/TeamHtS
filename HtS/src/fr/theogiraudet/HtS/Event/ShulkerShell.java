package fr.theogiraudet.HtS.Event;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.Objects.Randomizer;

public class ShulkerShell implements Listener {
	
	private HashMap<Player, Integer> shellUse = new HashMap<>();
	
	public ShulkerShell(HtS htS) {
		// TODO Auto-generated constructor stub
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player && ((Player) e.getEntity()).getInventory().contains(Material.SHULKER_SHELL)) {
			Player p = (Player) e.getEntity();
			World world = p.getWorld();
			if(Randomizer.RandRate(63)) {
				if(shellUse.containsKey(p)) {
					shellUse.replace(p, shellUse.get(p) + 1);
					if(shellUse.get(p) == 3) {
						world.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
						shellUse.replace(p, 0);
						p.getInventory().removeItem(new ItemStack(Material.SHULKER_SHELL, 1));
					} else {
						world.playSound(p.getLocation(), Sound.ITEM_SHIELD_BLOCK, 1, 1);
					}
				} else {
					shellUse.put(p, 1);
					world.playSound(p.getLocation(), Sound.ITEM_SHIELD_BLOCK, 1, 1);
				}
				e.setCancelled(true);
			}
		}
	}

}
