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
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;

import fr.theogiraudet.HtS.Commands.CommandManager;
import fr.theogiraudet.HtS.Enumeration.HtSState;
import fr.theogiraudet.HtS.Enumeration.ModState;
import fr.theogiraudet.HtS.Event.EventManager;
import fr.theogiraudet.HtS.Objects.PlayersInGame;
import fr.theogiraudet.HtS.Objects.Team;
import fr.theogiraudet.HtS.TaupeGun.TaupeGun;
import fr.theogiraudet.SyT.SyT;




public class HtS extends JavaPlugin {

	private HtSState currentState;
	private ModState currentSyTState;
	private ModState currentTaupeState;
    public static Scoreboard b = Bukkit.getScoreboardManager().getNewScoreboard();
	public List<Team> teams = new ArrayList<Team>();
	public PlayersInGame players = new PlayersInGame();
	public SyT stressYourTarget = new SyT(this);
	public TaupeGun taupeGun = new TaupeGun(this);
	public Map<Player, ScoreboardSign> board = new HashMap<>();
	public HashMap<Player, UUID> uuidPlayer = new HashMap<>();
	
	public HashMap<String, String> recap = new HashMap<>();
	
	@Override
	public void onEnable()  {
		
		
		Bukkit.getWorld("world").setDifficulty(Difficulty.HARD);
		Bukkit.getWorld("world_nether").setDifficulty(Difficulty.HARD);
		Bukkit.getWorld("world_the_end").setDifficulty(Difficulty.HARD);
		Bukkit.getWorld("world").setPVP(false);
		Bukkit.getWorld("world").setSpawnLocation(0, 205, 0);
		Bukkit.setSpawnRadius(0);
		this.setState(HtSState.WAIT);
		this.setSyTState(ModState.OFF);
		this.setTaupeState(ModState.OFF);
		recap.put("§6Type d'HtS : ", "§rClassique");
		recap.put("§6Équipe : ", "§rNon");
		recap.put("§6Bordure : ", "§r1000 * 1000");
		
		WorldBorder border = Bukkit.getWorld("world").getWorldBorder();
		border.setCenter(0.0, 0.0);
		border.setSize(1000);
		
		EventManager.loadEvents(this);
		CommandManager.loadCommands(this);
		
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
