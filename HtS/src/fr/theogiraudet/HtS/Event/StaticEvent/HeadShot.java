package fr.theogiraudet.HtS.Event.StaticEvent;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.Enumeration.HtSState;
import fr.theogiraudet.HtS.Objects.PluginFile;

public class HeadShot implements Listener {
    
	public HtS main;

	public HeadShot(HtS htS) {
		this.main = htS;
	}
		
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if(e.getCause() == DamageCause.PROJECTILE && e.getEntity() instanceof Player) {
			Projectile proj = (Projectile) e.getDamager();
			if(proj.getShooter() instanceof Player) {
				Entity shot = e.getEntity();
				
				double Y = proj.getLocation().getY();
				double shotY = shot.getLocation().getY();
				boolean headshot = Y - shotY > 1.35d;
				
				if(headshot) {
					Player p = (Player) e.getEntity();
					p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 255));
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 50, 255));
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 30, 255));
					if(main.isState(HtSState.RUNNING)) {
						PluginFile f = new PluginFile(main, ((Player) proj.getShooter()).getDisplayName() + ".txt");
						int value = (int) f.get(((Player) proj.getShooter()).getDisplayName() + ".arrow.headshot");
						value++;
						f.set(((Player) proj.getShooter()).getDisplayName() + ".arrow.headshot", value);
						f.save();
					}
				}
			}
		}
	}
}
