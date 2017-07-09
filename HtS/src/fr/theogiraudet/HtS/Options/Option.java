package fr.theogiraudet.HtS.Options;

import org.bukkit.inventory.ItemStack;

import fr.theogiraudet.HtS.Objects.ItemStackManager;

public abstract class Option {
	
	private ItemStackManager icon;
	
	
	public Option(ItemStackManager item) {
		this.icon = item;
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
	
	public void setInventory() {
		
	}

}
