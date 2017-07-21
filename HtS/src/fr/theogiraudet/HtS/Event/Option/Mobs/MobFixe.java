package fr.theogiraudet.HtS.Event.Option.Mobs;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;

import fr.theogiraudet.HtS.HtS;

public class MobFixe implements Listener {
	
	public static boolean skeleton = false;
	
	public MobFixe(HtS htS) { }


	@EventHandler
	public void onSkeletonShoot(EntityShootBowEvent e) {
		Entity en = e.getEntity();
		if (en instanceof Skeleton && !skeleton) {
			e.setCancelled(true);
		}		
		}
	
	
	@EventHandler()
	public void onCreeperOneShot(EntityDamageByEntityEvent e) {
		Entity p = e.getEntity();
		if (((p instanceof Player))) {
			double damage = e.getDamage();
			if (damage >= ((Player) p).getHealth()) {	
				if(e.getDamager() instanceof Creeper) {
				((Player) p).setHealth(2.0D);
				e.setCancelled(true);
				} 
			}
		}
	}

}
