package fr.theogiraudet.HtS.Event.Option.Time;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.Event.Option.Inventaire;

public class Border implements Listener {
	
	public static UUID playerBorder = null;
	private HtS main;
	
	public Border(HtS htS) { main = htS; }

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {		
		 if( playerBorder != null) {
			e.setCancelled(true);
			String message = e.getMessage();
			int number = 0;
			try { 
				number = Integer.parseInt(message); 
				if(number >= 250 && number <= 2000) {
					Inventaire.borderD = number;
					World world = Bukkit.getWorld("world");
					WorldBorder border = world.getWorldBorder();
					border.setCenter(0.0, 0.0);
					border.setSize(number*2);
					e.getPlayer().sendMessage("§2La bordure se situe à " + number + " blocs.");
					main.recap.replace("§6Bordure : ", "§r" + number*2 + " * " + number*2);
				} else {
					e.getPlayer().sendMessage("§4La valeur entrée n'est pas dans l'intervalle [250;2000] !");
				}
				}
			catch (Exception ex) { 
				e.getPlayer().sendMessage("§4La valeur entrée n'est pas un nombre !");
				}
			playerBorder = null;
		}
	}

}
