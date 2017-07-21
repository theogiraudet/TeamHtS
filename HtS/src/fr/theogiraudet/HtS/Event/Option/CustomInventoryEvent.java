package fr.theogiraudet.HtS.Event.Option;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.Enumeration.HtSState;

public class CustomInventoryEvent implements Listener {

	HtS main;

	public CustomInventoryEvent(HtS htS) {
		main = htS;
	}

	@EventHandler
	public void onClickInventory(InventoryClickEvent e) {
		if (main.isState(HtSState.WAIT)) {
			for (CustomInventory inv : CustomInventory.values()) {
				final String name = inv.getName();
				if (e.getInventory().getTitle().equalsIgnoreCase(name) && e.getCurrentItem() != null) {
					if (e.getCurrentItem().getType() == Material.BARRIER) {
						inv.getInventoryMain().openInventory((Player) e.getWhoClicked());
					}
					e.setCancelled(true);
				}
			}
		}
	}

}
