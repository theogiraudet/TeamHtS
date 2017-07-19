package fr.theogiraudet.HtS;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class CustomCraft implements Listener{
	
	private HtS main;

	public CustomCraft(HtS htS) {
		this.main = htS;
	}

	public static void loadCrafts(HtS htS) {
		Elytra(htS);
	}
	
	private static ShapedRecipe Elytra(HtS main) {
		ShapedRecipe e = new ShapedRecipe(new ItemStack(Material.ELYTRA, 1)).shape("#$#").setIngredient('#', Material.LEATHER).setIngredient('$', Material.STRING);
		main.getServer().addRecipe(e);
		return e;
	}
	
	@EventHandler
    public void craft(CraftItemEvent e){
		if (e.getInventory() instanceof CraftingInventory) {
			CraftingInventory inv = (CraftingInventory) e.getInventory();
			if (inv.getSize() != 4 && e.getRecipe().equals(Elytra(main))) {
				org.bukkit.inventory.ItemStack lwing = inv.getMatrix()[4];
				org.bukkit.inventory.ItemStack rwing = inv.getMatrix()[6];
				if (rwing.hasItemMeta() && lwing.hasItemMeta()) {
					if (rwing.getItemMeta().getEnchants() == Enchantment.PROTECTION_FALL
							&& lwing.getItemMeta().getEnchants() == Enchantment.PROTECTION_FALL) {
					} else {
						e.setCancelled(true);
						e.setResult(null);
					}
				} else {
					e.setCancelled(true);
					e.setResult(null);
				}
			}
		}
	}	
}
