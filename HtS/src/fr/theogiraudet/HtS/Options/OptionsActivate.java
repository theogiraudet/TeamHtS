package fr.theogiraudet.HtS.Options;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.theogiraudet.HtS.Objects.ItemStackManager;

public class OptionsActivate extends Option implements Listener {

	public OptionsActivate(ItemStackManager item) {
		super(item);

	}

	public void setEnable(boolean enable) {
		if(enable) {
			super.icon.setGlint(true);
		} else {
			super.icon.setGlint(false);
		}
	}
	
	public boolean isEnabled() {
		return super.icon.getGlint();
	}
	
	public void setInventory(String inventory) {
		Inventories.addItem(inventory, this);
		this.inventory = inventory;
	}
	
	@EventHandler
	public void onClickInventory(InventoryClickEvent e) {
		if(e.getInventory().getTitle().equalsIgnoreCase(inventory) && e.getCurrentItem() != null) {
			e.setCancelled(true);
			if(e.getCurrentItem().getType() == this.getItemStack().getType()) {
				if(isEnabled()) {
					setEnable(false);
				} else {
					setEnable(true);
				}
			}
		}
	}	
}
