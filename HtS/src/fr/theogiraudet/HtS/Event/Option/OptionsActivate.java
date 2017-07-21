package fr.theogiraudet.HtS.Event.Option;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.theogiraudet.HtS.Objects.ItemStackManager;

public abstract class OptionsActivate extends Option implements Listener {

	protected List<ItemStackManager> items = new ArrayList<>();
	
	public OptionsActivate(ItemStackManager item, CustomInventory inventory) {
		super(item, inventory);
		items.add(item);

	}

	public void setEnable(boolean enable) {
		if(enable) {
			super.icon.setGlint(true);
			super.setLore("§r§2Activé");
		} else {
			super.icon.setGlint(false);
			super.setLore("§r§4Désactivé");
		}
	}
	
	public boolean isDisable() {
		return super.icon.getGlint();
	}
	
	public void onClickInventory(InventoryClickEvent e, ItemStackManager is, CustomInventory inv, Player p) {
		if(e.getInventory().getTitle().equalsIgnoreCase(inv.getName()) && e.getCurrentItem() != null) {
			if(e.getCurrentItem().getType() == is.getMaterial()) {
				if(isDisable()) {
					setEnable(false);
				} else {
					setEnable(true);
				}
			inv.openInventory(p);
			}
			e.setCancelled(true);
		}
	}	
}
