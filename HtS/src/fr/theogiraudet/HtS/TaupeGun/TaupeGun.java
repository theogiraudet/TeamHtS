package fr.theogiraudet.HtS.TaupeGun;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;

import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.Timer;
import fr.theogiraudet.HtS.Objects.Randomizer;
import fr.theogiraudet.HtS.Objects.Team;

public class TaupeGun {

	private List<UUID> taupes = new ArrayList<>();
	private HtS main;
	
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
			Bukkit.getPlayer(uuid).sendMessage("Vous êtes une taupe. Votre");
		}
	 }
	 
	 public List<UUID> getTaupes() {
		 return taupes;
	 }
		 
	
}
