package fr.theogiraudet.SyT;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World.Environment;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.Timer;
import fr.theogiraudet.HtS.Enumeration.ModState;
import fr.theogiraudet.HtS.Objects.ActionBar;
import fr.theogiraudet.HtS.Objects.Randomizer;
import fr.theogiraudet.HtS.Objects.Team;

public class SyT implements Listener, CommandExecutor {

	public HtS main;
	public static List<UUID> players = new ArrayList<UUID>();
	public static List<UUID> targetCycle = new ArrayList<UUID>();
	public static List<Team> teams = new ArrayList<Team>();
	private ActionBar bar = new ActionBar(main);
	
	 public SyT(HtS main) {
		 this.main = main;
	}
	 
	 public void SyTLaunch() {
		Timer syt = new Timer(main, "SyT", 20);
		syt.runTaskTimer(main, 20, 20);
		Timer radar = new Timer(main, "radar", 30);
		radar.runTaskTimer(main, 20, 20);
	 }
	 
	 
	 public void randomTarget() {
		
		SyT.players = main.players.getPlayersInGame();
		SyT.teams = main.getTeams();
		int security = 0;
		
		while(SyT.players.size() > 0) {
			int rand = 0;
			if(SyT.players.size()  != 0) {
				rand = Randomizer.Rand(SyT.players.size());
			} else {
				rand = 0;
			}
			UUID p = SyT.players.get(rand);
			if(SyT.targetCycle.isEmpty()) {
				SyT.targetCycle.add(p);
				SyT.players.remove(p);
				security = 0;
			} else {
				if (security < 50) {
					if (main.getTeam(Bukkit.getPlayer(SyT.targetCycle.get(SyT.targetCycle.size() - 1))) != main.getTeam(Bukkit.getPlayer(p))) {
						SyT.targetCycle.add(p);
						SyT.players.remove(p);
					} else {
						security++;
					}
				} else {
					security = 0;
					SyT.targetCycle.add(p);
					SyT.players.remove(p);
				}
			}
		}
		
		for(UUID p : SyT.targetCycle)
		{
			Player player = Bukkit.getPlayer(p);		
			player.sendMessage("§4Votre cible est " + getVictim(player).getName() + ".");
		}
		main.setSyTState(ModState.PRORAND);
		Bukkit.getWorld("world").setPVP(true);
		Bukkit.getWorld("world_nether").setPVP(true);
		
	 }
	 
	 
	 
	 
	 
	 
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player victim = e.getEntity();
		if (main.isSyTState(ModState.PRERAND)) {
			e.setDeathMessage("");
			Bukkit.broadcastMessage(victim.getName() + " est mort.");
		}
		if (main.isSyTState(ModState.PRORAND)) {

			e.setDeathMessage("");
			Player killer = e.getEntity().getKiller();

			// Si le tueur est un joueur
			if (killer instanceof Player) {
				int indexOfKiller = SyT.targetCycle.indexOf(killer.getUniqueId());
				int indexOfVictim = SyT.targetCycle.indexOf(victim.getUniqueId());

				if (indexOfKiller == SyT.targetCycle.size() - 1) {
					System.out.println("Test 2");
					if (indexOfVictim == 0) {
						System.out.println("Test 3");
						rewardKillVictim(killer, victim);
					} else if (indexOfVictim == indexOfKiller - 1) {
						System.out.println("Test 4");
						rewardKillKiller(killer, victim);
						System.out.println("Test 4.5");
					} else {
						System.out.println("Test 5");
						punishment(killer, victim);
					}

				} else if (indexOfKiller == 0) {
					if (indexOfVictim == 1) {
						rewardKillVictim(killer, victim);			
					} else if (indexOfVictim == SyT.targetCycle.size() - 1) {
						System.out.println("Test 6");
						rewardKillKiller(killer, victim);
						System.out.println("Test 6.5");
					} else {
						punishment(killer, victim);
					}

				} else {
					if (indexOfVictim == indexOfKiller + 1) {
						rewardKillVictim(killer, victim);
					} else if (indexOfVictim == indexOfKiller - 1) {
						System.out.println("Test 7");
						rewardKillKiller(killer, victim);
						System.out.println("Test 7.5");
					} else {
						punishment(killer, victim);
					}

				}
				// Si le tueur n'est pas un joueur
			} else {

				Bukkit.broadcastMessage(victim.getName() + " est mort.");
				getKiller(victim).sendMessage("§2Votre cible a été tuée, une nouvelle cible vous est attribuée : " + getVictim(victim).getName());
				SyT.targetCycle.remove(victim.getUniqueId());
				System.out.println(SyT.targetCycle);

			}
		}
	}
	
	 
	 public void rewardKillVictim(Player killer, Player victim) {
		 killer.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 0));
		 killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000, 0));
		 
		 if(killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() < 24) {
			killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + 2);
			killer.setHealth(killer.getHealth() + 2);
		 }
		 
		 Bukkit.broadcastMessage(victim.getName() + " a été tué par son chasseur.");
		 killer.sendMessage("§2Cible éliminée. Nouvelle cible : " + getVictim(killer).getName());
		 SyT.targetCycle.remove(victim.getUniqueId());
	 }
	 
	 public void rewardKillKiller(Player killer, Player victim) {
		 killer.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 0));
		 if(killer.getHealth() >= killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - 4) {
			killer.setHealth(killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()); 
		 } else {
			killer.setHealth(killer.getHealth() + 4);
		 }
		 Bukkit.broadcastMessage(victim.getName() + " a été tué par sa cible.");
		 getKiller(victim).sendMessage("§2Cible éliminée. Nouvelle cible : " + getVictim(victim).getName());
		 killer.sendMessage("§6Celui-ci semblait vous vouloir du mal, il est fort probable qu'il cherchait à vous éliminer.");
		 SyT.targetCycle.remove(victim.getUniqueId());
		 
	 }
	 
	 public void punishment(Player killer, Player victim) {
		 killer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 0));
		 killer.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 6000, 0));
		 
		 if(killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() > 16) {
			 killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - 2);
		 }
		 
		 Bukkit.broadcastMessage(victim.getName() + " est mort.");
		 getKiller(victim).sendMessage("§2Votre cible a été tuée, une nouvelle cible vous est attribuée : " + getVictim(victim).getName());
		 killer.sendMessage("§4Que faites-vous ?! Ce n'était pas la cible qui vous était attribuée !");
		 killer.sendMessage("§7§oVous avez du remord...");
		 SyT.targetCycle.remove(victim.getUniqueId());
	 }
	 
	 public Player getKiller(Player player) {
		 if(SyT.targetCycle.indexOf(player.getUniqueId()) == 0) {
			 return Bukkit.getPlayer(SyT.targetCycle.get(SyT.targetCycle.size() - 1));
		 } else {
			 System.out.println(SyT.targetCycle.indexOf(player.getUniqueId()) - 1);
			 System.out.println(Bukkit.getPlayer(SyT.targetCycle.get(SyT.targetCycle.indexOf(player.getUniqueId()) - 1)));
			 return Bukkit.getPlayer(SyT.targetCycle.get(SyT.targetCycle.indexOf(player.getUniqueId()) - 1));
		 }
	 }
	 
	 public Player getVictim(Player player) {
		 if(SyT.targetCycle.indexOf(player.getUniqueId()) == SyT.targetCycle.size() - 1) {
			 return Bukkit.getPlayer(SyT.targetCycle.get(0));
		 } else {
			 return Bukkit.getPlayer(SyT.targetCycle.get(SyT.targetCycle.indexOf(player.getUniqueId()) + 1));
		 }
	 }

	 
	 
	 public void radar() {
		 
		 Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
			
			 @Override
			 public void run() {
				 for(UUID uuid : SyT.targetCycle) {
					 
					 Player player = Bukkit.getPlayer(uuid);
					 Player victim = getVictim(player);
					 
					 if(victim.getLocation().getBlockY() >= 36 && victim.getLocation().getWorld().getEnvironment() == Environment.NORMAL) {					 					 
						  bar.sendActionBar(player, "§4§lCible repérée : " + victim.getLocation().getBlockX() + " " + victim.getLocation().getBlockY() + " " + victim.getLocation().getBlockZ(), 30);
					 } else if(victim.getLocation().getBlockY() < 36 && victim.getLocation().getWorld().getEnvironment() == Environment.NORMAL) {
						 bar.sendActionBar(player, "§4§lTrop faible signal détecté : impossible de localiser la cible.", 30);
					 } else if(victim.getLocation().getWorld().getEnvironment() == Environment.NETHER || player.getLocation().getWorld().getEnvironment() == Environment.NETHER) {
						 bar.sendActionBar(player, "§4§lAucun signal détecté : impossible de localiser la cible.", 30);					 }
				 }
			 }
			 
		 }, 0L, 20L * 600);
		 
	 }
	 
	 
	 public void removePlayer(Player p) {
		 getKiller(p).sendMessage("§4Votre cible a mystérieusement disparu, une nouvelle vous est donc attribuée : " + getVictim(p).getName() + ".");
		 SyT.targetCycle.remove(p.getUniqueId());
	 }
	 
	 
	 
	@Override
	public boolean onCommand(CommandSender p, Command cmd, String arg, String[] args) {

		if(p instanceof Player && cmd.getName().equalsIgnoreCase("cible") && main.isSyTState(ModState.PRORAND)) {
			p.sendMessage("§4Votre cible est " + getVictim((Player)p).getName() + ".");
			return true;
		} else if(p instanceof Player && cmd.getName().equalsIgnoreCase("huntlist") && p.hasPermission("huntlist.use")) {
			for(UUID uuid : SyT.targetCycle) {
				Player player = Bukkit.getPlayer(uuid);
				Bukkit.broadcastMessage(player + " : " + getVictim(player));
			}
			return true;
		} else if((p instanceof Player && cmd.getName().equalsIgnoreCase("radar") && p.hasPermission("radar.use"))) {
			if(args.length == 1 && args[0].equals("1")) {
				bar.sendActionBarAll("§4§lCible repérée : X Y Z", 5);
				return true;
			} else if(args.length == 1 && args[0].equals("2")) {
				bar.sendActionBarAll("§4§lTrop faible signal détecté : impossible de localiser la cible.", 5);
				return true;
			} else if(args.length == 1) {
				bar.sendActionBarAll("§4§lAucun signal détecté : impossible de localiser la cible.", 5);
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
}
