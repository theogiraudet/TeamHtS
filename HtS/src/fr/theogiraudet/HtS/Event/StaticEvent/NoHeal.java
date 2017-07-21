package fr.theogiraudet.HtS.Event.StaticEvent;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import fr.theogiraudet.HtS.HtS;

public class NoHeal implements Listener {

	@SuppressWarnings("unused")
	private HtS main;
	
	public NoHeal(HtS htS) {this.main = htS; }

	@EventHandler
	public void onFoodHealEvent(EntityRegainHealthEvent e) {
		Entity p = e.getEntity();
		if(p instanceof Player && e.getRegainReason() == RegainReason.SATIATED) {
			e.setCancelled(true);
		}
	}
}
