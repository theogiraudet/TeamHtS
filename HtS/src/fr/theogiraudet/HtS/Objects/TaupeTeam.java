package fr.theogiraudet.HtS.Objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public class TaupeTeam {
    
    private Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
    private org.bukkit.scoreboard.Team team = board.registerNewTeam("unknown");    
        
    public TaupeTeam(){
        team = board.registerNewTeam("taupeteam");
    }
    
    public void addTaupe(Player player){
        team.addEntry(player.getDisplayName() + ".taupe");
    }
}