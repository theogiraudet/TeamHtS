package fr.theogiraudet.HtS.Objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import fr.theogiraudet.HtS.HtS;

public class TaupeTeam {
    
    private Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
    private org.bukkit.scoreboard.Team team = board.registerNewTeam("unknown");
	private HtS main;    
    
    public TaupeTeam(HtS htS) {
		htS = main;
	}
    
    public TaupeTeam(){
        team = board.registerNewTeam("taupeteam");
    }
    
    public void addTaupe(Player player){
        team.addEntry(player.getDisplayName() + ".taupe");
    }
    
    public void removeTaupe(Player p) {
    	main.getTeam(p).removePlayer(p.getDisplayName());
    }
}