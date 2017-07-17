package fr.theogiraudet.HtS.Objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import fr.theogiraudet.HtS.HtS;

public class TaupeTeam {    
	
	private Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
	private org.bukkit.scoreboard.Team taupe;
	private HtS main;    
	
	public TaupeTeam(HtS htS) {
		this.main = htS;
	}
	
	public TaupeTeam() {
    	taupe = board.registerNewTeam("taupeteam");
    }
    
    public void addTaupe(Player p) {
    	taupe.addEntry(p + ".taupe");
    }
    
    public void removeTaupe(Player p) {
    	main.getTeam(p).removePlayer(p.getName());
    }
}