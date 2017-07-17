package fr.theogiraudet.HtS.Event;

import java.util.UUID;

import org.bukkit.World.Environment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.Commands.Start;

public class DepthBreath implements Listener {
	
	public static UUID playerDepthBreath = null;
	public static boolean depthBreath = false;
	
	
	public DepthBreath(HtS htS) { }



	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		if(playerDepthBreath != null) {
			e.setCancelled(true);
			String message = e.getMessage();
			int number = -1;
			try { 
				number = Integer.parseInt(message); 
				if(number >= 0 && number <= 90) {
					Inventaire.depthBreathBegin = number;
					Start.depthBreathT = number;
					e.getPlayer().sendMessage("§2Le souffle des profondeurs se déclenchera à " + number + " minutes.");
				} else {
					e.getPlayer().sendMessage("§4La valeur entrée n'est pas dans l'intervalle [0;90] !");
				}
				} 
			catch (Exception ex) { 
				e.getPlayer().sendMessage("§4La valeur entrée n'est pas un nombre !");
				}
			playerDepthBreath = null;
		}
	}
	
	
	
	@EventHandler
	public void playerHeight(PlayerMoveEvent e) {
		if(depthBreath) {
			if(e.getPlayer().getLocation().getY() <= 36 && e.getPlayer().getWorld().getEnvironment() == Environment.NORMAL) {
				e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10000 * 20, 0, false, false));
				e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 10000 * 20, 0, false, false));
			} else if (e.getPlayer().getLocation().getY() > 36 && e.getPlayer().getWorld().getEnvironment() == Environment.NORMAL) {
				e.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
				e.getPlayer().removePotionEffect(PotionEffectType.CONFUSION);
				}
		}
	}

}
