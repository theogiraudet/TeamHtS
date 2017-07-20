package fr.theogiraudet.HtS.Options;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;

public class Test implements Listener {
	
	@SuppressWarnings("unused")
	private List<Option> option = new ArrayList<>();
	
	//@EventHandler
	public void onDropEgg(PlayerEggThrowEvent e) {		
		CustomInventory.MODS.openInventory(e.getPlayer());
	}

}
