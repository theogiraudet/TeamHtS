package fr.theogiraudet.HtS.Objects;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;

import net.md_5.bungee.api.ChatColor;

public class Team{
    
    private String teamName;
    private String teamColor;
    private List<String> teamPlayers = new ArrayList<>();
    private int teamSize;
    private Byte teamWool;

    private Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
    private org.bukkit.scoreboard.Team team;    
        
    public Team(String name, String color, Byte wooldata){
        teamName = name;
        teamColor = color.toUpperCase();
        teamWool = wooldata;
        
        team = board.registerNewTeam(teamName);
        team.setPrefix("[" + ChatColor.valueOf(teamColor) + teamName + "§r] ");
      //  team.setColor(ChatColor.valueOf(teamColor));
    }

    public String getTeamName(){
        return teamName;
    }
    
    public ChatColor getTeamColor(){
        return ChatColor.valueOf(teamColor);
    }
    
    public int getTeamSize(){
        return teamSize;
    }
    
    public Byte getWoolData(){
        return teamWool;
    }
    
    public List<UUID> getTeamPlayers(){
    	List<UUID> players = new ArrayList<UUID>();
    	for(String name : teamPlayers) {
        	players.add(Bukkit.getPlayer(name).getUniqueId());
        }
    	return players;
    }
    
    public void addPlayer(String player){
        team.addEntry(player);
        teamSize = team.getSize();
        teamPlayers.add(player);
        Bukkit.getPlayer(player).setScoreboard(board);
    }
    
    public void removePlayer(String player){
        team.removeEntry(player);
    }
    
    public void clearTeam(){
        for(int i=0; i<=teamSize; i++){
            team.removeEntry(teamPlayers.get(i));
        }
        teamPlayers.clear();
    }
    
    public void setFriendlyFire(Boolean b){
        team.setAllowFriendlyFire(b);
    }
    
    public boolean isTeamPlayer(Player p) {
    	return teamPlayers.contains(p.getName());
    }
    
    public void setScoreboard(Player p) {
    	p.setScoreboard(board);
    }
        
    public ItemStack getIcon()
    {
      ItemStackManager wool = new ItemStackManager(Material.WOOL, this.teamWool, 1, "§rRejoindre l'équipe " + ChatColor.valueOf(teamColor) + this.teamName);
      return wool.getItemStack();
    }
}