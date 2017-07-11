package fr.theogiraudet.HtS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;

import fr.theogiraudet.HtS.Commands.Commands;
import fr.theogiraudet.HtS.Commands.Start;
import fr.theogiraudet.HtS.Commands.TeamCommand;
import fr.theogiraudet.HtS.Enumeration.HtSState;
import fr.theogiraudet.HtS.Enumeration.ModState;
import fr.theogiraudet.HtS.Event.DisableCraft;
import fr.theogiraudet.HtS.Event.EventManager;
import fr.theogiraudet.HtS.Event.Inventaire;
import fr.theogiraudet.HtS.Event.MobFriendEvent;
import fr.theogiraudet.HtS.Event.NoHeal;
import fr.theogiraudet.HtS.Event.Statistics;
import fr.theogiraudet.HtS.Objects.PlayersInGame;
import fr.theogiraudet.HtS.Objects.Team;
import fr.theogiraudet.HtS.TaupeGun.TaupeGun;
import fr.theogiraudet.SyT.SyT;




public class HtS extends JavaPlugin {

	private HtSState currentState;
	private ModState currentSyTState;
	private ModState currentTaupeState;
	public List<Team> teams = new ArrayList<Team>();
	public PlayersInGame players = new PlayersInGame();
	public SyT stressYourTarget = new SyT(this);
	public TaupeGun taupeGun = new TaupeGun(this);
	public Map<Player, ScoreboardSign> board = new HashMap<>();
	public HashMap<Player, UUID> uuidPlayer = new HashMap<>();
	
	public HashMap<String, String> recap = new HashMap<>();
	
	@Override
	public void onEnable()  {
		
		PluginManager pm = getServer().getPluginManager();
		Bukkit.getWorld("world").setDifficulty(Difficulty.HARD);
		Bukkit.getWorld("world_nether").setDifficulty(Difficulty.HARD);
		Bukkit.getWorld("world_the_end").setDifficulty(Difficulty.HARD);
		Bukkit.getWorld("world").setPVP(false);
		Bukkit.getWorld("world").setSpawnLocation(0, 205, 0);
		Bukkit.setSpawnRadius(0);
		this.setState(HtSState.WAIT);
		recap.put("§6Type d'HtS : ", "§rClassique");
		recap.put("§6Équipe : ", "§rNon");
		recap.put("§6Bordure : ", "§r1000 * 1000");
		
		WorldBorder border = Bukkit.getWorld("world").getWorldBorder();
		border.setCenter(0.0, 0.0);
		border.setSize(1000);
		
		pm.registerEvents(new NoHeal(this), this);
		pm.registerEvents(new EventManager(this), this);
		pm.registerEvents(new TeamCommand(this), this);
		pm.registerEvents(new Inventaire(this), this);
		pm.registerEvents(new DisableCraft(this), this);
		pm.registerEvents(new SyT(this), this);
		pm.registerEvents(new Spectator(this), this);
		pm.registerEvents(new MobFriendEvent(this), this);
		pm.registerEvents(new Statistics(this), this);

		getCommand("start").setExecutor(new Start(this));
		getCommand("team").setExecutor(new TeamCommand(this));
		getCommand("option").setExecutor(new Commands(this));
		getCommand("res").setExecutor(new Commands(this));
		getCommand("remove").setExecutor(new Commands(this));
		getCommand("spectate").setExecutor(new Commands(this));
		getCommand("radar").setExecutor(new SyT(this));
		getCommand("huntlist").setExecutor(new SyT(this));
		getCommand("cible").setExecutor(new SyT(this));
		getCommand("heal").setExecutor(new Commands(this));
		getCommand("feed").setExecutor(new Commands(this));
		getCommand("broadcast").setExecutor(new Commands(this));
		getCommand("players").setExecutor(new Commands(this));
		getCommand("random").setExecutor(new Commands(this));

		
}

	public void onDisable() {
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		}
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		}
	}
	
	public void addTeam(Team team) {
		teams.add(team);
		recap.replace("§6Équipe : ", "§rOui");
	}

	public void removeTeam(Team team) {
		teams.remove(team);
		if(teams.size() == 0) {
			recap.replace("§6Équipe : ", "§rNon");
		}
	}
	
	public Team getTeam(Player p) {
		for (Team t : teams) {
			if(t.isTeamPlayer(p)) {
				return t;
			}
		}
		return null;
	}
	
	public List<Team> getTeams() { return teams; }
	
	
	public HtSState getState() { return currentState; }
	public ModState getSyTState() { return currentSyTState; }
	public ModState getTaupeState() { return currentTaupeState; }
	
	public void setState(HtSState state) { currentState = state; }
	public void setSyTState(ModState state) { currentSyTState = state; }
	public void setTaupeState(ModState state) { currentTaupeState = state; }
	
	public boolean isState(HtSState state) { return state == currentState; }
	public boolean isSyTState(ModState state) { return state == currentSyTState; }
	public boolean isTaupeState(ModState state) { return state == currentTaupeState; }

}
