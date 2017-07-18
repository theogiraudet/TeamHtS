package fr.theogiraudet.HtS.Event;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import fr.theogiraudet.HtS.HtS;

public class HeadShot implements Listener {
    
	public HtS main;

	public HeadShot(HtS htS) {
		this.main = htS;
	}
		
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if(e.getCause() == DamageCause.PROJECTILE) {
			Projectile proj = (Projectile) e.getDamager();
			if(proj.getShooter() instanceof Player) {
				Entity shot = e.getEntity();
				
				double Y = proj.getLocation().getY();
				double shotY = shot.getLocation().getY();
				boolean headshot = Y - shotY > 1.35d;
				
				if(headshot) {
					e.setDamage(e.getDamage()*100);
				}
			}
		}
	}
}
