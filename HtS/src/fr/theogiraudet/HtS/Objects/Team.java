package fr.theogiraudet.HtS.Objects;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.theogiraudet.HtS.HtS;
import net.md_5.bungee.api.ChatColor;

public class Team {
    
    private String teamName;
    private String teamColor;
    private List<String> teamPlayers = new ArrayList<>();
    private Byte teamWool;
    
    private org.bukkit.scoreboard.Team team;
    
    public Team(String name, String color, Byte wooldata, HtS main){
    	teamName = name;
        teamColor = color.toUpperCase();
        teamWool = wooldata;
        
        team = HtS.b.registerNewTeam(teamName);
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
        return team.getSize();
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
        teamPlayers.add(player);
        Bukkit.getPlayer(player).setScoreboard(HtS.b);
    }
    
    public void removePlayer(String player){
        team.removeEntry(player);
        teamPlayers.remove(player);
    }
    
    public void clearTeam(){
        for(int i=0; i<=team.getSize(); i++){
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
    	p.setScoreboard(HtS.b);
    }
        
    public ItemStack getIcon()
    {
      ItemStackManager wool = new ItemStackManager(Material.WOOL, this.teamWool, 1, "§rRejoindre l'équipe " + ChatColor.valueOf(teamColor) + this.teamName);
      return wool.getItemStack();
    }
}