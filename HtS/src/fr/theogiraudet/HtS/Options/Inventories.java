package fr.theogiraudet.HtS.Options;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Inventories {

	private static Inventory main = Bukkit.createInventory(null, 18, "Options");
	private static Inventory enable = Bukkit.createInventory(null, 18, "Activer/Désactiver");
	private static Inventory mods = Bukkit.createInventory(null, 18, "Modes");

	private static List<Inventory> inventories = new ArrayList<>(Arrays.asList(main, enable, mods));
	private static List<OptionsActivate> optionA = new ArrayList<>();
	private static List<OptionsDisableItems> optionD = new ArrayList<>();
	private static List<OptionsInput> optionI = new ArrayList<>();

	public static void addItem(String inventorySave, OptionsActivate option) {
		optionA.add(option);
		add(inventorySave, option);
	}

	public static void addItem(String inventorySave, OptionsDisableItems option) {
		optionD.add(option);
		add(inventorySave, option);
	}

	public static void addItem(String inventorySave, OptionsInput option) {
		optionI.add(option);
		add(inventorySave, option);
	}

	private static void add(String inventorySave, Option o) {
		for (Inventory i : inventories) {
			if (i.getTitle() == inventorySave) {
				i.addItem(o.getItemStack());
			}
		}
	}
	
	public static void createInventory(String inv, Player p) {
		for (Inventory i : inventories) {
			if (i.getTitle() == inv) {
				i.setItem(17, new ItemStack(Material.BARRIER));
				p.openInventory(i);
			}
		}
	}
}
