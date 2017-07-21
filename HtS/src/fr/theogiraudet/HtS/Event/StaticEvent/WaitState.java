package fr.theogiraudet.HtS.Event.StaticEvent;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.Enumeration.HtSState;

public class WaitState implements Listener {

	private HtS main;
	
	public WaitState(HtS htS) {
		main = htS;
	}

	@EventHandler
	public void onHealLevelChange(EntityDamageEvent e) {
		if(main.isState(HtSState.WAIT) && e.getEntity() instanceof Player) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent e) {
		if(main.isState(HtSState.WAIT)) {
			e.setCancelled(true);
		}
	}
	
}
