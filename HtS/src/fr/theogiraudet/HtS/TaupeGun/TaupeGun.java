package fr.theogiraudet.HtS.TaupeGun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;

import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.Timer;
import fr.theogiraudet.HtS.Enumeration.ModState;
import fr.theogiraudet.HtS.Event.Statistics;
import fr.theogiraudet.HtS.Objects.Randomizer;
import fr.theogiraudet.HtS.Objects.TaupeTeam;
import fr.theogiraudet.HtS.Objects.Team;

public class TaupeGun {

	private List<UUID> taupes = new ArrayList<>();
	private HtS main;
	private Statistics s = new Statistics(main);
	public Map<UUID, Boolean> taupeReveal = new HashMap<>();
	private TaupeTeam taupeteam = new TaupeTeam();
	
	 public TaupeGun(HtS main) {
		 this.main = main;
	}
	 
	 public void TaupeLaunch() {
			Timer taupe = new Timer(main, "Taupe", 20);
			taupe.runTaskTimer(main, 20, 20);
		 }
	 
	 public void SelectTaupe() {
		for(Team team : main.getTeams()) {
			int i = Randomizer.RandI(0, team.getTeamPlayers().size() - 1);
			UUID uuid = team.getTeamPlayers().get(i);
			taupes.add(uuid);
			Bukkit.getPlayer(uuid).sendMessage("§4Vous êtes une taupe infiltrée dans l'équipe où vous êtes actuellement. Votre mission est d'éliminer votre équipe et de rejoindre les autres taupes.\n Nous vous avons réservé un canal spécial afin de communiquer avec les autres taupes, accessible en mettant un '!' devant votre message.");
			taupeteam.addTaupe(Bukkit.getPlayer(team.getTeamPlayers().get(i)));
			taupeReveal.put(uuid, false);
		}
		s.statTaupe(taupes);
		main.setTaupeState(ModState.PRORAND);
	 }
	 
	 public List<UUID> getTaupes() {
		 return taupes;
	 }
		 
	
}
