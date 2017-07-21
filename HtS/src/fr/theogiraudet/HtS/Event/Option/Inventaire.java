package fr.theogiraudet.HtS.Event.Option;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.SelectMod;
import fr.theogiraudet.HtS.Commands.Start;
import fr.theogiraudet.HtS.Enumeration.DisableCraftEnum;
import fr.theogiraudet.HtS.Event.Option.EnableItem.DisableCraft;
import fr.theogiraudet.HtS.Event.Option.Time.Border;
import fr.theogiraudet.HtS.Event.Option.Time.DepthBreath;
import fr.theogiraudet.HtS.Objects.ItemStackManager;

public class Inventaire implements Listener {

	private HtS main;
	public static int depthBreathBegin = 70;
	public static int borderD = 500;
	public static ItemStackManager dc = new ItemStackManager(Material.DOUBLE_PLANT, (byte)0, 1, "§rCycle jour/nuit", "§r§2Activé", false);
	public static ItemStackManager weather = new ItemStackManager(Material.WATER_BUCKET, (byte)0, 1, "§rPluie", "§r§2Activé", false);
	public static ItemStackManager disableWeapons = new ItemStackManager(Material.DIAMOND_SWORD, (byte)0, 1, "§rDésactiver/Activé", null, false);
	public static ItemStackManager tearsDrop = new ItemStackManager(Material.GHAST_TEAR, (byte)0, 1, "§rLarme de Ghasts", "§r§2Activé", false);
	public static ItemStackManager depthBreath = new ItemStackManager(Material.GLOWSTONE_DUST, (byte)0, 1, "§rSouffle des profondeurs", "§r§2Activé", false);
	public static ItemStackManager depthBreathB = new ItemStackManager(Material.WATCH, (byte)0, 1, "§rDébut du souffle des profondeurs", "§r§5Débute au bout de " + depthBreathBegin + " minutes.", false);
	public static ItemStackManager border = new ItemStackManager(Material.IRON_FENCE, (byte)0, 1, "§rDistance de la bordure du centre", "§r§5" + borderD, false);
	public static ItemStackManager mod = new ItemStackManager(Material.COMPASS, (byte)0, 1, "§rSélectionner un mode", false);
	public static ItemStackManager cancel = new ItemStackManager(Material.BARRIER, (byte)0, 1, "§rRetour", false);

	
	public Inventory inv = Bukkit.createInventory(null, 18, "Activer/Désactiver");
	public static List<ItemStackManager> ism = new ArrayList<ItemStackManager>();
	
	public Inventaire(HtS htS) {
		htS = main; 
		for(DisableCraftEnum dce : DisableCraftEnum.values()) {
		
		ism.add(new ItemStackManager(dce));
	}
	}
	
	public static void createInventory(Player p) {
		Inventory inv = Bukkit.createInventory(null, 18, "Options");
		inv.setItem(0, dc.getItemStack());
		inv.setItem(1, weather.getItemStack());
		inv.setItem(2, disableWeapons.getItemStack());
		inv.setItem(3, tearsDrop.getItemStack());
		inv.setItem(4, depthBreath.getItemStack());
		if(!depthBreath.isGlint()) {
			depthBreathB.setLore("§r§5Débute au bout de " + depthBreathBegin + " minutes.");
			inv.setItem(5, depthBreathB.getItemStack());
		}
		inv.setItem(6, border.getItemStack());
		inv.setItem(17, mod.getItemStack());
		p.openInventory(inv);
	}
	
	
	public static void createDisableInventory(Player p, Inventory inv) {
		
		int i = 0;
		for(ItemStackManager ItemM : ism) {
			inv.setItem(i ,ItemM.getItemStack());
			i++;
		}
		inv.setItem(17, cancel.getItemStack());
		p.closeInventory();
		p.openInventory(inv);
	}
	
	public void setState(ItemStackManager ism) {
		if (ism.isGlint()) { ism.setGlint(false); ism.setLore("§r§2Activé"); }
		else {ism.setGlint(true); ism.setLore("§r§4Désactivé"); }
	}
	
	public void setDisable(ItemStackManager ismC) {
		setState(ismC);
		if (ismC.isGlint()) {
			DisableCraft.craft.addAll(ismC.getItemDisabled());
		} else {
			DisableCraft.craft.removeAll(ismC.getItemDisabled());
		}
	}
	
	@EventHandler
	public void onClickInventory(InventoryClickEvent e) {
		if(e.getInventory().getTitle().equalsIgnoreCase("Options") && e.getCurrentItem() != null) {
			if(e.getCurrentItem().getType() == Material.DOUBLE_PLANT) {
				setState(dc);
			} else if(e.getCurrentItem().getType() == Material.WATER_BUCKET) {
				setState(weather);
			} else if(e.getCurrentItem().getType() == Material.DIAMOND_SWORD) {
				createDisableInventory(Bukkit.getServer().getPlayer(e.getWhoClicked().getName()), inv);
				return;
			} else if(e.getCurrentItem().getType() == Material.GHAST_TEAR) {
				setState(tearsDrop);
			} else if(e.getCurrentItem().getType() == Material.GLOWSTONE_DUST) {
				setState(depthBreath);
				if(!depthBreath.isGlint()) {
					depthBreathB.setLore("§r§5Débute au bout de " + depthBreathBegin + " minutes.");
					Start.depthBreathT = depthBreathBegin;
					Start.depthBreathA = true;
				} else {
					Start.depthBreathA = false;
				}
				
			} else if(e.getCurrentItem().getType() == Material.WATCH) {
				Bukkit.getServer().getPlayer(e.getWhoClicked().getName()).closeInventory();
				e.getWhoClicked().sendMessage("§2Veuillez entrer la valeur voulu dans le chat, nombre entier compris entre 0 et 90 minutes.");
				DepthBreath.playerDepthBreath = e.getWhoClicked().getUniqueId();
				return;
				
			} else if(e.getCurrentItem().getType() == Material.IRON_FENCE) {
				Bukkit.getServer().getPlayer(e.getWhoClicked().getName()).closeInventory();
				e.getWhoClicked().sendMessage("§2Veuillez entrer la valeur voulu dans le chat, nombre entier compris entre 250 et 2000 blocks.");
				Border.playerBorder = e.getWhoClicked().getUniqueId();
				return;

			} else if(e.getCurrentItem().getType() == Material.COMPASS) {
				Bukkit.getServer().getPlayer(e.getWhoClicked().getName()).closeInventory();
				SelectMod.createInventory((Player)e.getWhoClicked());
				return;
				
			} else {
			e.setCancelled(true);
			}
			createInventory(Bukkit.getServer().getPlayer(e.getWhoClicked().getName()));
		} 
		
		
		
		else if(e.getInventory().getTitle().equalsIgnoreCase("Activer/Désactiver") && e.getCurrentItem() != null) {
			e.setCancelled(true);
			boolean asInList = false;
			ItemStackManager itemSM = null;
			if(e.getCurrentItem().getType() == Material.BARRIER) {
				createInventory((Player)e.getWhoClicked());
			} else {
				for(ItemStackManager ismC : ism) {
					if(e.getCurrentItem().getType() == ismC.getMaterial()) {
						asInList = true;
						itemSM = ismC;
						break;
					}
				}
			}
			
			if(asInList) {
				setDisable(itemSM);
				createDisableInventory(Bukkit.getServer().getPlayer(e.getWhoClicked().getName()), inv);
			}
			
		}
	}
}
