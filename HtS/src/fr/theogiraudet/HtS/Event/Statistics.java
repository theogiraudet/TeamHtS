package fr.theogiraudet.HtS.Event;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.Objects.PluginFile;

public class Statistics implements Listener{
	
	private HtS main;
	 
	public Statistics(HtS htS) {
		this.main = htS;
	}

	public void createFiles() {
		for(Player player : Bukkit.getServer().getOnlinePlayers()) {
			UUID p = player.getUniqueId();
			PluginFile f = new PluginFile(main, p.toString() + ".txt");
			String path = Bukkit.getPlayer(p).getDisplayName() + ".";
			f.set(path + "logout" , 0);
			f.set(path + "sneaktime" , 0);
			f.set(path + "portalcrossed" , 0);
			f.set(path + "diamond" , 0);
			f.set(path + "gold" , 0);
			f.set(path + "itempickedup" , 0);
			f.set(path + "enchantment" , 0);
			f.set(path + "goldappleaten" , 0);
			f.set(path + "potiondrunk" , 0);
			f.set(path + "potionthrown" , 0);
			f.set(path + "monsterkilled" , 0);
			f.set(path + "damagedealt" , 0);
			f.set(path + "damagereceived" , 0);
			f.set(path + "arrow.shot" , 0);
			f.set(path + "arrow.hit" , 0);
			f.set(path + "arrow.accuracy" , 0);
			f.save();
		}
	}

	@EventHandler
	public void onLogOut(PlayerQuitEvent e) {
		PluginFile f = new PluginFile(main, e.getPlayer().getUniqueId().toString() + ".txt");
		int value = (int) f.get(e.getPlayer().getDisplayName() + ".logout");
		value = value + 1; 
		f.set(e.getPlayer().getDisplayName() + ".logout", value);
		f.save();
	}
	
	@EventHandler
	public void onPlayerPortalCross(PlayerChangedWorldEvent e) {
		PluginFile f = new PluginFile(main, e.getPlayer().getUniqueId().toString() + ".txt");
		int value = (int) f.get(e.getPlayer().getDisplayName() + ".portalcrossed");
		value++; 
		f.set(e.getPlayer().getDisplayName() + ".portalcrossed", value);
		f.save();
	}
	
	@EventHandler
	public void onPlayerSneak(PlayerToggleSneakEvent e) {
		
	}
	
	@EventHandler
	public void onOreMined(BlockBreakEvent e) {
		if(e.getPlayer() != null) {
			if(e.getBlock().getType() == Material.DIAMOND_ORE) {
				PluginFile f = new PluginFile(main, e.getPlayer().getUniqueId().toString() + ".txt");
				int value = (int) f.get(e.getPlayer().getDisplayName() + ".diamond");
				value++; 
				f.set(e.getPlayer().getDisplayName() + ".diamond", value);
				f.save();
			}
			else if(e.getBlock().getType() == Material.GOLD_ORE) {
				PluginFile f = new PluginFile(main, e.getPlayer().getUniqueId().toString() + ".txt");
				int value = (int) f.get(e.getPlayer().getDisplayName() + ".gold");
				value = value + 1; 
				f.set(e.getPlayer().getDisplayName() + ".gold", value);
				f.save();
			}
		}
	}
	
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent e) {
		PluginFile f = new PluginFile(main, e.getPlayer().getUniqueId().toString() + ".txt");
		int value = (int) f.get(e.getPlayer().getDisplayName() + ".itempickedup");
		value++; 
		f.set(e.getPlayer().getDisplayName() + ".itempickedup", value);
		f.save();
	}
	
	@EventHandler
	public void onEnchantment(EnchantItemEvent e) {
		PluginFile f = new PluginFile(main, e.getEnchanter().getUniqueId().toString() + ".txt");
		int value = (int) f.get(e.getEnchanter().getDisplayName() + ".enchantment");
		value++; 
		f.set(e.getEnchanter().getDisplayName() + ".enchantment", value);
		f.save();
	}
	
	@EventHandler
	public void onItemConsumed(PlayerItemConsumeEvent e) {
		if(e.getItem().getType() == Material.GOLDEN_APPLE) {
			PluginFile f = new PluginFile(main, e.getPlayer().getUniqueId().toString() + ".txt");
			int value = (int) f.get(e.getPlayer().getDisplayName() + ".goldappleaten");
			value++; 
			f.set(e.getPlayer().getDisplayName() + ".goldappleaten", value);
			f.save();
		}
		else if(e.getItem().getType() == Material.POTION) {
			PluginFile f = new PluginFile(main, e.getPlayer().getUniqueId().toString() + ".txt");
			int value = (int) f.get(e.getPlayer().getDisplayName() + ".potiondrunk");
			value++; 
			f.set(e.getPlayer().getDisplayName() + ".potiondrunk", value);
			f.save();
		}
	}
	
	@EventHandler
	public void onPlayerKill(EntityDeathEvent e) {
		if(e.getEntity() instanceof Monster || e.getEntityType() == EntityType.SLIME) {
			PluginFile f = new PluginFile(main, e.getEntity().getKiller().getUniqueId().toString() + ".txt");
			int value = (int) f.get(e.getEntity().getKiller().getDisplayName() + ".monsterkilled");
			value++; 
			f.set(e.getEntity().getKiller().getDisplayName() + ".monsterkilled", value);
			f.save();
		}
	}
	
	@EventHandler
	public void onDamageDealt(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player) {
			PluginFile f = new PluginFile(main, e.getDamager().getUniqueId().toString() + ".txt");
			int value = (int) f.get(((Player) e.getDamager()).getDisplayName() + ".damagedealt");
			value = (int) (value + e.getDamage()); 
			f.set(((Player) e.getDamager()).getDisplayName() + ".damagedealt", value);
			f.save();
		}
	}
	
	@EventHandler
	public void onDamageReceived(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player) {
			PluginFile f = new PluginFile(main, e.getEntity().getUniqueId().toString() + ".txt");
			int value = (int) f.get(((Player) e.getEntity()).getDisplayName() + ".damagereceived");
			value = (int) (value + e.getDamage()); 
			f.set(((Player) e.getEntity()).getDisplayName() + ".damagereceived", value);
			f.save();
		}
	}
	
	@EventHandler
	public void onProjectileShot(ProjectileLaunchEvent e) {
		if(e.getEntityType() == EntityType.ARROW && e.getEntity().getShooter() instanceof Player) {
			PluginFile f = new PluginFile(main, ((Entity) e.getEntity().getShooter()).getUniqueId().toString() + ".txt");
			int shot = (int) f.get(((Player) e.getEntity().getShooter()).getDisplayName() + ".arrow.shot");
			int hit = (int) f.get(((Player) e.getEntity().getShooter()).getDisplayName() + ".arrow.hit");
			shot++; 
			int acc = (hit/shot)*100;
			f.set(((Player) e.getEntity().getShooter()).getDisplayName() + ".arrow.shot", shot);
			f.set(((Player) e.getEntity().getShooter()).getDisplayName() + ".arrow.accuracy", acc);
			f.save();
		}
		else if(e.getEntityType() == EntityType.SPLASH_POTION && e.getEntity().getShooter() instanceof Player) {
			PluginFile f = new PluginFile(main, ((Entity) e.getEntity().getShooter()).getUniqueId().toString() + ".txt");
			int value = (int) f.get(((Player) e.getEntity().getShooter()).getDisplayName() + ".potionthrown");
			value++; 
			f.set(((Player) e.getEntity().getShooter()).getDisplayName() + ".potionthrown", value);
			f.save();
		}
	}
	
	@EventHandler
	public void onArrowHit(ProjectileHitEvent e) {
		if(e.getEntityType() == EntityType.ARROW && e.getEntity().getShooter() instanceof Player && e.getHitEntity() != null) {
			PluginFile f = new PluginFile(main, ((Entity) e.getEntity().getShooter()).getUniqueId().toString() + ".txt");
			int hit = (int) f.get(((Player) e.getEntity().getShooter()).getDisplayName() + ".arrow.hit");
			int shot = (int) f.get(((Player) e.getEntity().getShooter()).getDisplayName() + ".arrow.shot");
			hit++; 
			int acc = (hit/shot)*100;
			System.out.print(acc);
			f.set(((Player) e.getEntity().getShooter()).getDisplayName() + ".arrow.hit", hit);
			f.set(((Player) e.getEntity().getShooter()).getDisplayName() + ".arrow.accuracy", acc);
			f.save();
		}
	}
}
