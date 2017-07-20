package fr.theogiraudet.HtS;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class CustomCraft implements Listener{
	
	@SuppressWarnings("unused")
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
	public void craft(PrepareItemCraftEvent e) {
		if (e.getInventory() instanceof CraftingInventory) {
			CraftingInventory inv = (CraftingInventory) e.getInventory();
			if (e.getRecipe().getResult().equals(new ItemStack(Material.ELYTRA))) {
				if (inv.getMatrix()[3] != null && inv.getMatrix()[4] != null && inv.getMatrix()[5] != null) {
					org.bukkit.inventory.ItemStack lwing = inv.getMatrix()[3];
					org.bukkit.inventory.ItemStack rwing = inv.getMatrix()[5];
					org.bukkit.inventory.ItemStack string = inv.getMatrix()[4];
					if (rwing.hasItemMeta() && lwing.hasItemMeta()) {
						if (rwing.containsEnchantment(Enchantment.PROTECTION_FALL)
								&& lwing.containsEnchantment(Enchantment.PROTECTION_FALL)
								&& string.getType() == Material.STRING) {
							inv.setResult(new ItemStack(Material.ELYTRA));
						} else {
							inv.setResult(new ItemStack(Material.AIR));
						}
					} else {
						inv.setResult(new ItemStack(Material.AIR));
					}
				} else {
					inv.setResult(new ItemStack(Material.AIR));
				}
			}
		}
	}
}
