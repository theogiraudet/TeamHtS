package fr.theogiraudet.HtS.Options;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import fr.theogiraudet.HtS.Objects.ItemStackManager;

public class Sword2 extends OptionsInput {


	public Sword2(ItemStackManager item) {
		super(item);
	}

	@EventHandler
	public void onSwordCraft(CraftItemEvent e) {
		if(e.getRecipe().getResult() == new ItemStack(Material.DIAMOND_SWORD, 1)) {
			e.setCancelled(true);
		}
 	}
}
