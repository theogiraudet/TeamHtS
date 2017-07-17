package fr.theogiraudet.HtS.TaupeGun;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.Enumeration.ModState;

public class TaupeChannel implements Listener {

	private HtS main;

	public TaupeChannel(HtS htS) {
		main = htS;
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		if (main.isTaupeState(ModState.PRORAND) && main.taupeGun.getTaupes().contains(e.getPlayer().getUniqueId()) && e.getMessage().substring(0, 1) == "!") {
			String messageS = e.getMessage();
			for (UUID uuid : main.taupeGun.getTaupes()) {
				Bukkit.getPlayer(uuid).sendMessage("§8[§4" + e.getPlayer().getName() + "§8] " + messageS);
			}
		}

		e.setCancelled(true);
	}
}
