package fr.theogiraudet.HtS.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.Commands.Start;
import fr.theogiraudet.HtS.Enumeration.HtSState;
import fr.theogiraudet.HtS.Objects.PluginFile;
import net.md_5.bungee.api.ChatColor;

public class Statistics implements Listener{
	
	private HtS main;
	private ArrayList<Integer> maximum = new ArrayList<>();
	private ArrayList<Integer> minimum = new ArrayList<>();
	@SuppressWarnings("serial")
	ArrayList<String> character = new ArrayList<String>() {
		{
			add(".logout");
			add(".sneaktime");
			add(".portalcrossed");
			add(".diamond");
			add(".gold");
			add(".itempickedup");
			add(".enchantment");
			add(".goldappleaten");
			add(".potiondrunk");
			add(".potionthrown");
			add(".playerkilled");
			add(".monsterkilled");
			add(".damagedealt");
			add(".damagereceived");
			add(".arrow.shot");
			add(".arrow.hit");
			add(".arrow.accuracy");
			add(".arrow.headshot");
			add(".arrow.longshot");
		}
	};
	@SuppressWarnings("serial")
	ArrayList<String> stat = new ArrayList<String>() {
		{
			add("Déconnexion:");
			add("Temps Accroupi:");
			add("Portail Pris:");
			add("Diamant:");
			add("Or:");
			add("Item ramassé:");
			add("Enchantement Fait:");
			add("Pomme d'or:");
			add("Potion Bue:");
			add("Potion Lancée");
			add("Joueur tué:");
			add("Mob tué:");
			add("Dégât Fait:");
			add("Dégât Reçu:");
			add("Fléche Tirée:");
			add("Flèche Touchée:");
			add("Précision:");
			add("Headshot:");
			add("Tir >50m:");
		}
	};
	
	public Statistics(HtS htS) {
		this.main = htS;
	}

	public void createFiles() {
		PluginFile g = new PluginFile(main, "general.txt");
		g.set("time", "hh:mm:ss");
		g.save();
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			PluginFile f = new PluginFile(main, p.getDisplayName() + ".txt");
			for(int i = 0; i < character.size(); i++) {
				String path = p.getDisplayName() + character.get(i);
				f.set(path, 0);
			}
			f.save();
		}
	}
	
	public void getStatistics() {
		PluginFile g = new PluginFile(main, "general.txt");
		Bukkit.broadcastMessage("La partie a durée: " + (String) g.get("time"));
		for(int i = 0; i < character.size(); i++) {
			ArrayList<Integer> array = new ArrayList<>();
			for(Player p : Bukkit.getServer().getOnlinePlayers()) {
				PluginFile f = new PluginFile(main, p.getDisplayName() + ".txt");
				String path = p.getDisplayName() + character.get(i);
				array.add((Integer) f.get(path));
			}
			int max = getMax(array);
			int min = getMin(array);
			minimum.add(min);
			maximum.add(max);
		}
		for (int i = 0; i < character.size(); i++) {
			if (maximum.get(i) != 0 || minimum.get(i) != 0) {
				Bukkit.broadcastMessage("§6§l" + stat.get(i));
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					PluginFile f = new PluginFile(main, p.getDisplayName() + ".txt");
					String path = p.getDisplayName() + character.get(i);
					if (maximum.get(i) == f.get(path) && maximum.get(i) != 0) {
						Bukkit.broadcastMessage(ChatColor.GREEN + p.getDisplayName() + ": " + maximum.get(i));
					}
					if (minimum.get(i) == f.get(path) && minimum.get(i) != 0) {
						Bukkit.broadcastMessage(ChatColor.RED + p.getDisplayName() + ": " + minimum.get(i));
					}
				}
			}
		}
	}

	private int getMax(ArrayList<Integer> array) {
		int max = 0;
		for(int i = 0; i < array.size(); i++) {
			if(array.get(i) > max) {
				max = array.get(i);
			}
		}
		return max;	
	}
	
	private int getMin(ArrayList<Integer> array) {
		int min = array.get(0);
		for(int i = 0; i < array.size(); i++) {
			if(array.get(i) < min) {
				min = array.get(i);
			}
		}
		return min;	
	}

	//General Statistics
	
	public void gameTime() {
		PluginFile f = new PluginFile(main, "general.txt");
		f.set("time", Start.timerGame);
		f.save();
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		if(main.isState(HtSState.RUNNING)) {
			PluginFile f = new PluginFile(main, "general.txt");
			f.set("death." + e.getEntity().getDisplayName(), Start.timerGame);
			f.save();
		}
	}
	
	public void statTaupe(List<UUID> taupes) {
		PluginFile f = new PluginFile(main, "general.txt");
		for(UUID t : taupes) {
			Player p = Bukkit.getPlayer(t);
			f.set("taupe.", p.getDisplayName());
		}
		f.save();
	}
	
	//Individual Player Statistics
	
	@EventHandler
	public void onLogOut(PlayerQuitEvent e) {
		if(main.isState(HtSState.RUNNING)) {
			PluginFile f = new PluginFile(main, e.getPlayer().getDisplayName() + ".txt");
			int value = (int) f.get(e.getPlayer().getDisplayName() + ".logout");
			value = value + 1;
			f.set(e.getPlayer().getDisplayName() + ".logout", value);
			f.save();
		}
	}
	
	@EventHandler
	public void onPlayerSneak(PlayerToggleSneakEvent e) {
		if(main.isState(HtSState.RUNNING)) {
			if (!e.getPlayer().isSneaking()) {
				e.getPlayer().getStatistic(Statistic.SNEAK_TIME);
				PluginFile f = new PluginFile(main, e.getPlayer().getDisplayName() + ".txt");
				f.set(e.getPlayer().getDisplayName() + ".sneaktime",e.getPlayer().getStatistic(Statistic.SNEAK_TIME) / 20);
				f.save();
			}
		}
	}
	
	@EventHandler
	public void onPlayerPortalCross(PlayerChangedWorldEvent e) {
		if(main.isState(HtSState.RUNNING)) {
			PluginFile f = new PluginFile(main, e.getPlayer().getDisplayName() + ".txt");
			int value = (int) f.get(e.getPlayer().getDisplayName() + ".portalcrossed");
			value++;
			f.set(e.getPlayer().getDisplayName() + ".portalcrossed", value);
			f.save();
		}
	}
	
	@EventHandler
	public void onOreMined(BlockBreakEvent e) {
		if(main.isState(HtSState.RUNNING)) {
			if (e.getPlayer() != null) {
				if (e.getBlock().getType() == Material.DIAMOND_ORE) {
					PluginFile f = new PluginFile(main, e.getPlayer().getDisplayName() + ".txt");
					int value = (int) f.get(e.getPlayer().getDisplayName() + ".diamond");
					value++;
					f.set(e.getPlayer().getDisplayName() + ".diamond", value);
					f.save();
				} else if (e.getBlock().getType() == Material.GOLD_ORE) {
					PluginFile f = new PluginFile(main, e.getPlayer().getDisplayName() + ".txt");
					int value = (int) f.get(e.getPlayer().getDisplayName() + ".gold");
					value = value + 1;
					f.set(e.getPlayer().getDisplayName() + ".gold", value);
					f.save();
				}
			}
		}
	}
	
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent e) {
		if(main.isState(HtSState.RUNNING)) {
			PluginFile f = new PluginFile(main, e.getPlayer().getDisplayName() + ".txt");
			int value = (int) f.get(e.getPlayer().getDisplayName() + ".itempickedup");
			value++;
			f.set(e.getPlayer().getDisplayName() + ".itempickedup", value);
			f.save();
		}
	}
	
	@EventHandler
	public void onEnchantment(EnchantItemEvent e) {
		if(main.isState(HtSState.RUNNING)) {
			PluginFile f = new PluginFile(main, e.getEnchanter().getDisplayName() + ".txt");
			int value = (int) f.get(e.getEnchanter().getDisplayName() + ".enchantment");
			value++;
			f.set(e.getEnchanter().getDisplayName() + ".enchantment", value);
			f.save();
		}
	}
	
	@EventHandler
	public void onItemConsumed(PlayerItemConsumeEvent e) {
		if(main.isState(HtSState.RUNNING)) {
			if (e.getItem().getType() == Material.GOLDEN_APPLE) {
				PluginFile f = new PluginFile(main, e.getPlayer().getDisplayName() + ".txt");
				int value = (int) f.get(e.getPlayer().getDisplayName() + ".goldappleaten");
				value++;
				f.set(e.getPlayer().getDisplayName() + ".goldappleaten", value);
				f.save();
			} else if (e.getItem().getType() == Material.POTION) {
				PluginFile f = new PluginFile(main, e.getPlayer().getDisplayName() + ".txt");
				int value = (int) f.get(e.getPlayer().getDisplayName() + ".potiondrunk");
				value++;
				f.set(e.getPlayer().getDisplayName() + ".potiondrunk", value);
				f.save();
			}
		}
	}
	
	@EventHandler
	public void onPlayerPlayerKill(PlayerDeathEvent e) {
		if(main.isState(HtSState.RUNNING) || main.isState(HtSState.FINISHING)) {
			if (e.getEntity() instanceof Player) {
				PluginFile f = new PluginFile(main, e.getEntity().getKiller().getDisplayName() + ".txt");
				int value = (int) f.get(e.getEntity().getKiller().getDisplayName() + ".playerkilled");
				value++;
				f.set(e.getEntity().getKiller().getDisplayName() + ".playerkilled", value);
				f.save();
			}
		}
	}
	
	@EventHandler
	public void onPlayerMonsterKill(EntityDeathEvent e) {
		if(main.isState(HtSState.RUNNING)) {
			if (e.getEntity() instanceof Monster || e.getEntityType() == EntityType.SLIME) {
				PluginFile f = new PluginFile(main, e.getEntity().getKiller().getDisplayName() + ".txt");
				int value = (int) f.get(e.getEntity().getKiller().getDisplayName() + ".monsterkilled");
				value++;
				f.set(e.getEntity().getKiller().getDisplayName() + ".monsterkilled", value);
				f.save();
			}
		}
	}
	
	@EventHandler
	public void onDamageDealt(EntityDamageByEntityEvent e) {
		if(main.isState(HtSState.RUNNING)) {
			if (e.getDamager() instanceof Player) {
				PluginFile f = new PluginFile(main, ((Player) e.getDamager()).getDisplayName() + ".txt");
				int value = (int) f.get(((Player) e.getDamager()).getDisplayName() + ".damagedealt");
				value = (int) (value + e.getDamage());
				f.set(((Player) e.getDamager()).getDisplayName() + ".damagedealt", value);
				f.save();
			}
		}
	}
	
	@EventHandler
	public void onDamageReceived(EntityDamageByEntityEvent e) {
		if(main.isState(HtSState.RUNNING)) {
			if (e.getEntity() instanceof Player) {
				PluginFile f = new PluginFile(main, ((Player) e.getEntity()).getDisplayName() + ".txt");
				int value = (int) f.get(((Player) e.getEntity()).getDisplayName() + ".damagereceived");
				value = (int) (value + e.getDamage());
				f.set(((Player) e.getEntity()).getDisplayName() + ".damagereceived", value);
				f.save();
			}
		}
	}
	
	@EventHandler
	public void onProjectileShot(ProjectileLaunchEvent e) {
		if(main.isState(HtSState.RUNNING)) {
			if (e.getEntityType() == EntityType.ARROW && e.getEntity().getShooter() instanceof Player) {
				PluginFile f = new PluginFile(main, ((Player) e.getEntity().getShooter()).getDisplayName() + ".txt");
				int shot = (int) f.get(((Player) e.getEntity().getShooter()).getDisplayName() + ".arrow.shot");
				int hit = (int) f.get(((Player) e.getEntity().getShooter()).getDisplayName() + ".arrow.hit");
				shot++;
				int acc = (hit * 100 / shot);
				f.set(((Player) e.getEntity().getShooter()).getDisplayName() + ".arrow.shot", shot);
				f.set(((Player) e.getEntity().getShooter()).getDisplayName() + ".arrow.accuracy", acc);
				f.save();
			} else if (e.getEntityType() == EntityType.SPLASH_POTION && e.getEntity().getShooter() instanceof Player) {
				PluginFile f = new PluginFile(main, ((Player) e.getEntity().getShooter()).getDisplayName() + ".txt");
				int value = (int) f.get(((Player) e.getEntity().getShooter()).getDisplayName() + ".potionthrown");
				value++;
				f.set(((Player) e.getEntity().getShooter()).getDisplayName() + ".potionthrown", value);
				f.save();
			}
		}
	}
	
	@EventHandler
	public void onArrowHit(ProjectileHitEvent e) {
		if(main.isState(HtSState.RUNNING))  {
			if (e.getEntityType() == EntityType.ARROW && e.getEntity().getShooter() instanceof Player
					&& e.getHitEntity() != null) {
				PluginFile f = new PluginFile(main, ((Player) e.getEntity().getShooter()).getDisplayName() + ".txt");
				int hit = (int) f.get(((Player) e.getEntity().getShooter()).getDisplayName() + ".arrow.hit");
				int shot = (int) f.get(((Player) e.getEntity().getShooter()).getDisplayName() + ".arrow.shot");
				hit++;
				int acc = (hit * 100 / shot);
				f.set(((Player) e.getEntity().getShooter()).getDisplayName() + ".arrow.hit", hit);
				f.set(((Player) e.getEntity().getShooter()).getDisplayName() + ".arrow.accuracy", acc);
				f.save();
			}
		}
	}
	
	@EventHandler
	public void onLongShot(ProjectileHitEvent e) {
		if(main.isState(HtSState.RUNNING)) {
			if (e.getEntityType() == EntityType.ARROW && e.getEntity().getShooter() instanceof Player
					&& e.getHitEntity() != null) {
				if(e.getHitBlock().getLocation().distance(((Player) e.getEntity().getShooter()).getLocation()) >= 50) {
				PluginFile f = new PluginFile(main, ((Player) e.getEntity().getShooter()).getDisplayName() + ".txt");
				int value = (int) f.get(((Player) e.getEntity().getShooter()).getDisplayName() + ".arrow.longshot");
				value++;
				f.set(((Player) e.getEntity().getShooter()).getDisplayName() + ".arrow.longshot", value);
				}
			}
		}
	}
}
