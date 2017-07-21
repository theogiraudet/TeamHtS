package fr.theogiraudet.HtS.Event.Option.EnableItem;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import fr.theogiraudet.HtS.Event.Option.CustomInventory;
import fr.theogiraudet.HtS.Event.Option.OptionsActivate;
import fr.theogiraudet.HtS.Objects.ItemStackManager;

public class DiamondSword extends OptionsActivate {

	
	public DiamondSword(ItemStackManager item, CustomInventory inventory) {
		super(item, inventory);	
	}

	@EventHandler
	public void onSwordCraft(PrepareItemCraftEvent e) {
		if(!this.isDisable() && e.getRecipe().getResult().equals(new ItemStack(Material.DIAMOND_SWORD, 1))) {
			e.getInventory().setResult(new ItemStack(Material.AIR));
		}
 	}
	
	@EventHandler
	public void onClickInventory(InventoryClickEvent e) {
		super.onClickInventory(e, this.icon, this.inventory, (Player) e.getWhoClicked());
	}
}
