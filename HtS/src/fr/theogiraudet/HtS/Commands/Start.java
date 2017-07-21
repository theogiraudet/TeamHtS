package fr.theogiraudet.HtS.Commands;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.Timer;
import fr.theogiraudet.HtS.Enumeration.HtSState;
import fr.theogiraudet.HtS.Enumeration.ModState;
import fr.theogiraudet.HtS.Event.StaticEvent.Statistics;
import fr.theogiraudet.HtS.Objects.Randomizer;
import fr.theogiraudet.HtS.Objects.ScoreBoard;
import fr.theogiraudet.HtS.Objects.Team;


public class Start implements CommandExecutor {

	private HtS main;
	public static int depthBreathT = 70;
	public static boolean depthBreathA = true;
	public int timer = 0;
	public static String timerGame;
	
	public Start(HtS htS) { main = htS;}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player && cmd.getName().equalsIgnoreCase("start") && sender.hasPermission("start.use") && main.isState(HtSState.WAIT)) {

			
			HashMap<String, String> recap2 = new HashMap<>();
			recap2 = main.recap;
			Bukkit.getServer().broadcastMessage("§6Type d'HtS : " + recap2.get("§6Type d'HtS : "));
			Bukkit.getServer().broadcastMessage("§6Équipe : " + recap2.get("§6Équipe : "));
			recap2.remove("§6Type d'HtS : ");
			recap2.remove("§6Équipe : ");

			for (HashMap.Entry<String, String> entry : recap2.entrySet()) {
				Bukkit.getServer().broadcastMessage(entry.getKey() + entry.getValue());
			}
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "gamerule sendCommandFeedback false");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "scoreboard objectives add §4\u2764 health");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "scoreboard objectives setdisplay belowName §4\u2764");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "scoreboard objectives add keurkeur health");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "scoreboard objectives setdisplay list keurkeur");

			
			if(main.isSyTState(ModState.OFF)) {
				for(World world : Bukkit.getWorlds()) {
					world.setPVP(true);
				}
			}
			
			main.setState(HtSState.RUNNING);
			if(depthBreathA) {
				Timer db = new Timer(main, "Depth Breath",depthBreathT, depthBreathT - 1);
				Timer skeleton = new Timer(main, "Skeleton",40, 39);
				db.runTaskTimer(main, 20, 20);
				skeleton.runTaskTimer(main, 20, 20);
			}
			
			if(main.isSyTState(ModState.PRERAND)) {
				main.stressYourTarget.SyTLaunch();
			} else if(main.isTaupeState(ModState.PRERAND)) {
				main.taupeGun.TaupeLaunch();
			}
			
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(p.getGameMode() != GameMode.SPECTATOR) {
					main.players.addPlayer(p.getUniqueId());
					main.uuidPlayer.put(p, p.getUniqueId());
					p.setGameMode(GameMode.SURVIVAL);
				}
			}
			
			if(main.teams.isEmpty()) {
				Statistics s = new Statistics(main);
				s.createFiles();
				for(UUID uuid : main.players.getPlayersInGame()) {
					Player p = Bukkit.getServer().getPlayer(uuid);
					p.setStatistic(Statistic.SNEAK_TIME, 0);
					p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
					p.setHealth(20);
					p.setFoodLevel(20);
					p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 30 * 20, 255, false, false));
					p.getInventory().clear();
					p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 1));
					p.teleport(teleport());
					
				}
			} else {
				Statistics s = new Statistics(main);
				s.createFiles();
				for(Team t : main.getTeams()) {
					
					Location loc = teleport();
					
					for(UUID uuid : t.getTeamPlayers()) {
						Player p = Bukkit.getServer().getPlayer(uuid);
						p.setStatistic(Statistic.SNEAK_TIME, 0);
						p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
						p.setHealth(20);
						p.setFoodLevel(20);
						p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 30 * 20, 255, false, false));
						p.getInventory().clear();
						p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 1));
						p.teleport(loc);
					}
				}
			}
			
			
			Bukkit.getScheduler().runTaskTimer(main, new Runnable(){

				DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("HH:mm:ss"); // formateur
				LocalTime timer = LocalTime.ofSecondOfDay(0); // initialisation
				
				@Override
				public void run() {	
					
					if(main.isState(HtSState.FINISHING)) {
						Bukkit.getScheduler().cancelAllTasks();;
					}
					
					timer = timer.plusSeconds(1);
					timerGame = dateformat.format(timer);
					ScoreBoard.sendTimer(main);
				}
				
			},20,20);
			
		
		for(Player p : Bukkit.getOnlinePlayers()) {
				ScoreBoard.sendScoreboard(p, main);
			}
		ScoreBoard.sendPlayers(main);
		if(!main.teams.isEmpty()) {
			ScoreBoard.sendTeams(main);
		}
		ScoreBoard.sendKills(main);
		ScoreBoard.sendBorder(main);
		
		
		Timer chest = new Timer(main, "Chest", 180, 30, 60, 90, 120, 150);
		chest.runTaskTimer(main, 20, 20);
		
		return true;	
		
		
		}
		return false;
	}
	
	
	public Location teleport() {
		int x, y, z, isNegative;
		int negative[] = {-1,1};
		World world = Bukkit.getWorld("world");
		WorldBorder border = world.getWorldBorder();
		
		isNegative = negative[1 - Randomizer.Rand(negative.length)];
		x = isNegative * (0 + Randomizer.Rand((int) Math.floor(border.getSize()/2 - 0)));
	
		y = 255;
		
		isNegative = negative[1 - Randomizer.Rand(negative.length)];
		z = isNegative * (0 + Randomizer.Rand((int) Math.floor(border.getSize()/2 - 0)));
		return new Location(Bukkit.getWorld("world"), x, y, z);
	}

}
