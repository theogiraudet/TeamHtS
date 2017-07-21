package fr.theogiraudet.HtS.Event.Option;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import fr.theogiraudet.HtS.Objects.ItemStackManager;

public enum CustomInventory {
	
	OPTIONS("Option", 18, null),
	DISABLE("Désactiver", 18, OPTIONS),
	MODS("Mode", 18, DISABLE);

	private String name;
	private int size;
	private CustomInventory inventoryMain;
	private ArrayList<Option> option = new ArrayList<>();

	CustomInventory(String name, int size, CustomInventory inventoryMain) {
		this.name = name;
		this.size = size;
		this.inventoryMain = inventoryMain;
	}
	
	public void openInventory(Player p) {
		Inventory inv = Bukkit.createInventory(null, this.size, this.name);
		for(Option option : option) {
			inv.addItem(option.getItemStack());
		}
		if(this.inventoryMain != null) {
			inv.setItem(this.size - 1, new ItemStackManager(Material.BARRIER, (short) 0, 1, "Retour").getItemStack());
		}
		p.openInventory(inv);
	}
	
	public String getName() {
		return name;
	}
	
	public int getSize() {
		return size;
	}
	
	public CustomInventory getInventoryMain() {
		return inventoryMain;
	}
	
	public void addItem(Option option) {
		this.option.add(option);
	}
	
}
