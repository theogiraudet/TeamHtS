package fr.theogiraudet.HtS.Options;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import fr.theogiraudet.HtS.Objects.ItemStackManager;

public class Sword extends OptionsDisableItems {


	ItemStackManager item;
	CustomInventory ci;
	
	public Sword(ItemStackManager item) {
		super(item);	
		this.item = item;
		ci = CustomInventory.MODS;
		this.setInventory(ci);
	}

	@EventHandler
	public void onSwordCraft(PrepareItemCraftEvent e) {
		if(!this.isDisable() && e.getRecipe().getResult().equals(new ItemStack(Material.DIAMOND_SWORD, 1))) {
			e.getInventory().setResult(new ItemStack(Material.AIR));
		}
 	}
	
	@EventHandler
	public void onClickInventory(InventoryClickEvent e) {
		super.onClickInventory(e, item, ci, (Player) e.getWhoClicked());
	}
}
