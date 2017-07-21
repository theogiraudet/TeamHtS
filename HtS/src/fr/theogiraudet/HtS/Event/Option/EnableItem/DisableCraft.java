package fr.theogiraudet.HtS.Event.Option.EnableItem;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import fr.theogiraudet.HtS.HtS;

public class DisableCraft implements Listener {

	private HtS main;

	public static List<ItemStack> craft = new ArrayList<ItemStack>();
	public static List<ItemStack> disableIngredient = new ArrayList<ItemStack>();

	public DisableCraft(HtS htS) {
		htS = main;
	}

	@EventHandler
	public void onCraftItem(PrepareItemCraftEvent e) {
		ItemStack i = e.getRecipe().getResult();
		for (ItemStack item : craft) {
			if (item.equals(i)) {
				e.getInventory().setResult(new ItemStack(Material.AIR));
			}
		}
	}

	@EventHandler
	public void onEnchantItem(EnchantItemEvent e) {
		System.out.println("a");
		for (ItemStack item : craft) {
			System.out.println(item);
			if (item.getType().equals(Material.ENCHANTED_BOOK)) {
				System.out.println("b");
				if (e.getEnchantsToAdd().containsKey(Enchantment.ARROW_FIRE)|| e.getEnchantsToAdd().containsKey(Enchantment.FIRE_ASPECT)) {
					System.out.println("c");
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onBrewItem(BrewEvent e) {
		Material m = e.getContents().getIngredient().getType();

		for (ItemStack item : craft) {
			Material material = item.getType();
			if (m.equals(Material.GLOWSTONE_DUST) && e.getContents().contains(material)) {
				e.setCancelled(true);
			}
		}
	}
}
