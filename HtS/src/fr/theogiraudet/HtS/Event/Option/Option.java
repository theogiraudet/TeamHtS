package fr.theogiraudet.HtS.Event.Option;

import org.bukkit.inventory.ItemStack;

import fr.theogiraudet.HtS.Objects.ItemStackManager;

public abstract class Option {
	
	protected ItemStackManager icon;
	protected CustomInventory inventory;
	
	protected String name = null;
	protected String lore = null;
	
	
	public Option(ItemStackManager item, CustomInventory customInventory) {
		this.icon = item;
		this.inventory = customInventory;
		this.setInventory(inventory);
	}
	
	public void setIcon(ItemStackManager item) {
		this.icon = item;
	}
	
	public ItemStackManager getIcon() {
		return icon;
	}
	
	public String getLore() {
		return icon.getLore();
	}
	
	public String getName() {
		return icon.getName();
	}
	

	public ItemStack getItemStack() {
		return icon.getItemStack();
	}
	
	public void setLore(String lore) {
		icon.setLore(lore);
	}

	public void setName(String name) {
		icon.setName(name);
	}

	public void setInventory(CustomInventory inv) {
		inv.addItem(this);
	}
	
}
