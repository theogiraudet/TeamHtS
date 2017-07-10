package fr.theogiraudet.HtS.Options;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import fr.theogiraudet.HtS.Objects.ItemStackManager;

public class Sword {


	OptionsDisableItems sword = new OptionsDisableItems(new ItemStackManager(Material.DIAMOND_SWORD, (short) 0, 0, "Test", false));
	
	@EventHandler
	public void onSwordCraft(CraftItemEvent e) {
		if(sword.isEnabled() && e.getRecipe().getResult() == new ItemStack(Material.DIAMOND_SWORD, 1)) {
			e.setCancelled(true);
		}
 	}
}
