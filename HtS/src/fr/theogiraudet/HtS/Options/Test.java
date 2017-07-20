package fr.theogiraudet.HtS.Options;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings("unused")
public class Test implements Listener {
	
	private List<Option> option = new ArrayList<>();
	
	@EventHandler
	public void onDropEgg(PlayerEggThrowEvent e) {	
		
		//CustomInventory.MODS.openInventory(e.getPlayer());
	}

}
