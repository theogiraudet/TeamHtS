package fr.theogiraudet.HtS.Options;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;


public class Inventories {

		private Inventory main = Bukkit.createInventory(null, 18, "Options");
		private Inventory enable = Bukkit.createInventory(null, 18, "Activer/Désactiver");
		private Inventory mods = Bukkit.createInventory(null, 18, "Modes");
	
		private List<Inventory> inventories = new ArrayList<>(Arrays.asList(main, enable, mods));
		
	public void addItem(String inventoryMain, OptionsActivate option) {
			inventories.clear();
	}
	
	public void addItem(String inventoryMain, OptionsDisableItems option) {
		
	}
	
	public void addItem(String inventoryMain, OptionsInput option) {
		
	}
	
}
