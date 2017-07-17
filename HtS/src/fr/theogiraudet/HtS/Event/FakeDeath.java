package fr.theogiraudet.HtS.Event;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.ScoreboardSign;
import fr.theogiraudet.HtS.Enumeration.ModState;
import fr.theogiraudet.HtS.Objects.DeathEntityLoot;
import fr.theogiraudet.HtS.Objects.ItemStackManager;
import fr.theogiraudet.HtS.Objects.Team;

public class FakeDeath implements Listener {	
	
	private DeathEntityLoot playerLoot = new DeathEntityLoot(new ItemStack(Material.GOLDEN_APPLE, 1),
			new ItemStackManager(Material.SKULL_ITEM, (short) 3, 1, "").getItemStack());
	private HtS main;
	
	public FakeDeath(HtS htS) { main = htS; }



	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if (!(p.getKiller() instanceof Creeper)) {
			p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
			for (ItemStack is : playerLoot.getDrops()) {
				if (is.getData().getData() == 3 && is.getType() == Material.SKULL_ITEM) {
					SkullMeta isM = (SkullMeta) is.getItemMeta();
					isM.setOwner(p.getName());
					isM.setDisplayName("Tête de " + p.getName());
					is.setItemMeta(isM);
				}
				p.getWorld().dropItem(p.getLocation(), is);
				((Player) p).setGameMode(GameMode.SPECTATOR);
				main.players.removePlayer(p.getUniqueId());
				
				for (Player p2 : Bukkit.getOnlinePlayers()) {
					p2.playSound(p2.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1.0f, 1.0f);
				}
			}
			
			if(!main.teams.isEmpty() && main.isSyTState(ModState.OFF)) {
				Team team = main.getTeam(p);
				team.removePlayer(p.getName());
				if(team.getTeamSize() == 0) {
					main.removeTeam(team);
				}
				if(main.teams.size() == 1) {
					Bukkit.broadcastMessage("§2L'équipe " + main.getTeams().get(0).getTeamColor() + main.getTeams().get(0).getTeamName() + "§r§2 a gagné !");
				}
			} else if(main.teams.isEmpty() && main.players.getPlayersInGame().size() == 1) {
				Bukkit.broadcastMessage("§2" + Bukkit.getPlayer(main.players.getPlayersInGame().get(0)).getName() + " a gagné !");
			}
			
			if(p.getKiller() instanceof Player) {
				if(main.board.containsKey(p.getKiller())) {
					main.board.get(p.getKiller()).setLine(10, "§o" + p.getStatistic(Statistic.PLAYER_KILLS));
				}
			}
			for(Entry<Player, ScoreboardSign> sign : main.board.entrySet()) {
				sign.getValue().setLine(6, "§o" + main.players.getPlayersInGame().size());
			}
		}
	}
	
	
	
	@SuppressWarnings("deprecation")
	public void fakeDeath(Entity p) {
				((Player) p).setHealth(20.0D);
				for (ItemStack is : playerLoot.getDrops()) {
					if (is.getData().getData() == 3 && is.getType() == Material.SKULL_ITEM) {
						SkullMeta isM = (SkullMeta) is.getItemMeta();
						isM.setOwner(p.getName());
						isM.setDisplayName("Tête de " + p.getName());
						is.setItemMeta(isM);
					}
					p.getWorld().dropItem(p.getLocation(), is);
					((Player) p).setGameMode(GameMode.SPECTATOR);
					main.players.removePlayer(p.getUniqueId());
					for(Player p2 : Bukkit.getOnlinePlayers()) {
						p2.playSound(p2.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1.0f, 1.0f);
					}
				}
			}

}
