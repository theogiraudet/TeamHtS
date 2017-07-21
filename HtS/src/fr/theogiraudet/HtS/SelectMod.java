package fr.theogiraudet.HtS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import fr.theogiraudet.HtS.Enumeration.ModState;
import fr.theogiraudet.HtS.Event.Option.Inventaire;
import fr.theogiraudet.HtS.Objects.ItemStackManager;

public class SelectMod implements Listener {
	
	private static List<ItemStackManager> mods = new ArrayList<>(
			Arrays.asList(
					new ItemStackManager(Material.GOLDEN_CARROT, (short) 0, 1, "§rHtS", true),
					new ItemStackManager(Material.COMPASS, (short) 0, 1, "§rSyT", false),
					new ItemStackManager(Material.DIAMOND_SPADE, (short) 0, 1, "§rTaupe Gun", false)					
					));

	private static ItemStackManager cancel = new ItemStackManager(Material.BARRIER, (short) 0, 1, "§rRetour", false);
	private HtS main;
	
	public SelectMod(HtS htS) {	 this.main = htS; }

	public static void createInventory(Player p) {
		Inventory inv = Bukkit.createInventory(null, 9, "Mode");
		int i = 0;
		for(ItemStackManager ism : mods) {
			inv.setItem(i, ism.getItemStack());
			i++;
		}
		inv.setItem(8, cancel.getItemStack());
		p.openInventory(inv);
	}
	
	public void setState(Material m) {
		for(ItemStackManager mod : mods) {
			if(mod.getMaterial() == m) {
				mod.setGlint(true);
				if(m == Material.GOLDEN_CARROT) {
					main.setSyTState(ModState.OFF);
					main.setTaupeState(ModState.OFF);
					main.recap.replace("§6Type d'HtS : ", "§rClassique");
					
				} else if(m == Material.COMPASS) {
					main.setSyTState(ModState.PRERAND);
					main.setTaupeState(ModState.OFF);
					main.recap.replace("§6Type d'HtS : ", "§rSyT");
					
				} else if(m == Material.DIAMOND_SPADE) {
					main.setSyTState(ModState.OFF);
					main.setTaupeState(ModState.PRERAND);
					main.recap.replace("§6Type d'HtS : ", "§rTaupe Gun");
				}
			} else {
				mod.setGlint(false);
			}
		}
	}
	
	
	@EventHandler
	public void onClickInventory(InventoryClickEvent e) {
		if(e.getInventory().getTitle().equalsIgnoreCase("Mode") && e.getCurrentItem() != null) {	
			e.setCancelled(true);
			if(e.getCurrentItem().getType() != Material.BARRIER) {
				setState(e.getCurrentItem().getType());				
				createInventory(Bukkit.getServer().getPlayer(e.getWhoClicked().getName()));
				return;
			} else {
				Inventaire.createInventory((Player)e.getWhoClicked());
				return;
			}
		}
	} 

}
