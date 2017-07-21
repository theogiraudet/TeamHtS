package fr.theogiraudet.HtS.Event.StaticEvent;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.Enumeration.HtSState;
import fr.theogiraudet.HtS.Objects.Team;

public class JoinEvent implements Listener {
	
	private HtS main;
	
	public JoinEvent(HtS htS) { main = htS;	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (main.isState(HtSState.WAIT)) {
			p.setGameMode(GameMode.ADVENTURE);
			p.teleport(new Location(Bukkit.getWorld("world"), 0.0, 205.0, 0.0));
		} else if (main.isState(HtSState.RUNNING) && !main.players.isPlayerInGame(p.getUniqueId())) {
			p.setGameMode(GameMode.SPECTATOR);
		}
		
		if(!main.getTeams().isEmpty()) {
			for(Team team : main.teams) {
				if(team.isTeamPlayer(e.getPlayer())) {
					team.setScoreboard(e.getPlayer());
				}
			}
		}
	}

}
