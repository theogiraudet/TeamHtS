package fr.theogiraudet.HtS.Event.Option.EnableItem;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentOffer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import fr.theogiraudet.HtS.HtS;

public class DisableCraft implements Listener {

	private HtS main;

	public static List<ItemStack> craft = new ArrayList<ItemStack>();
	public static List<ItemStack> disableIngredient = new ArrayList<ItemStack>();
	@SuppressWarnings("serial")
	private ArrayList<Enchantment> disabledEnchant = new ArrayList<Enchantment>() {
		{
			add(Enchantment.FIRE_ASPECT);
			add(Enchantment.ARROW_FIRE);
		}
	};
	
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
	public void onEnchantItem(PrepareItemEnchantEvent e) {
		EnchantmentOffer[] offers = e.getOffers();
		for (int i = 0; i < 3; i++) {
			if (disabledEnchant.contains(offers[i].getEnchantment())) {
				offers[i].setEnchantment(Enchantment.DURABILITY);
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
