package fr.theogiraudet.HtS;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.theogiraudet.HtS.Objects.ItemStackManager;

public class Spectator implements Listener {
	
	public List<Inventory> inventories = new ArrayList<Inventory>();

	public Spectator(HtS htS) {	}
	
	
	@EventHandler
	public void onTakeItem(InventoryClickEvent e) {
		for(Inventory inv : inventories) {
			if(e.getInventory().getTitle().equalsIgnoreCase(inv.getTitle()) && e.getCurrentItem() != null) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPickUp(PlayerPickupItemEvent e) {
		reloadInventory(e.getPlayer());
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		reloadInventory(e.getPlayer());
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@EventHandler
	public void onCloseInventory(InventoryCloseEvent e) {
		String name = e.getInventory().getTitle();
		if(e.getPlayer().getGameMode() == GameMode.SPECTATOR && inventories.contains(name)) {
			inventories.remove(name);
		}
		
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEntityEvent e) {
		if(e.getPlayer().getGameMode() == GameMode.SPECTATOR) { 
			if(e.getRightClicked() instanceof Player) {
				Player p = (Player)e.getRightClicked();
				if(p.getGameMode() == GameMode.SURVIVAL) {
					createInventory(e.getPlayer(), p);	
				}
			}
		}
	}
	
	public void createInventory(Player clicker, Player clicked) {
		
		Inventory inv = Bukkit.createInventory(null, 54, clicked.getName());
		int j = 18;
		for(int i = 9; i <= 35; i++) {
			inv.setItem(j, clicked.getInventory().getItem(i));
			j++;
		}
		j = 45;
		for(int i = 0; i <= 8; i++) {
			inv.setItem(j, clicked.getInventory().getItem(i));
			j++;
		}
		for(j = 9; j <= 17; j++) {
			inv.setItem(j, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)15));
		}
		
		inv.setItem(0, clicked.getInventory().getHelmet());
		inv.setItem(1, clicked.getInventory().getChestplate());
		inv.setItem(2, clicked.getInventory().getLeggings());
		inv.setItem(3, clicked.getInventory().getBoots());
		
		inv.setItem(4, clicked.getInventory().getItemInOffHand());
		
		ItemStackManager xp = new ItemStackManager(Material.EXP_BOTTLE, (short) 0, clicked.getLevel(), "Niveaux");
		ItemStackManager health = new ItemStackManager(Material.REDSTONE, (short) 0, (int) clicked.getHealth(), "Vie");
		ItemStackManager food = new ItemStackManager(Material.COOKED_BEEF, (short) 0, clicked.getFoodLevel(), "Faim");
		inv.setItem(6, xp.getItemStack());
		inv.setItem(7, health.getItemStack());
		inv.setItem(8, food.getItemStack());
		
		clicker.openInventory(inv);
		
		inventories.add(inv);
		
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public void reloadInventory(Player target) {
		//System.out.println("Test 1 inv");
		for(Player p : Bukkit.getOnlinePlayers()) {
			//System.out.println("Test 2 inv " + p.getName());
			//System.out.println(inventories);
			if(p.getGameMode() == GameMode.SPECTATOR && inventories.contains(target.getName())) {
				//System.out.println("Test 3 inv");
				p.closeInventory();
				createInventory(p, target);
			}
		}
	}

}
