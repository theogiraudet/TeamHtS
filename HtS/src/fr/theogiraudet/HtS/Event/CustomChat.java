package fr.theogiraudet.HtS.Event;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.theogiraudet.HtS.HtS;

public class CustomChat implements Listener {

	public CustomChat(HtS htS) { }

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		if(e.getPlayer().getGameMode() == GameMode.SPECTATOR) {
			String messageS = e.getMessage();
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(p.getGameMode() == GameMode.SPECTATOR) {
					p.sendMessage("§7§o[Spectateur] " + e.getPlayer().getName() + " : " + messageS);
			}
		}
			
			e.setCancelled(true);
		}
	}
	
}
