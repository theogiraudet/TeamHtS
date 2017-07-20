package fr.theogiraudet.HtS.Objects;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.Commands.Start;
import net.minecraft.server.v1_11_R1.IScoreboardCriteria;
import net.minecraft.server.v1_11_R1.Packet;
import net.minecraft.server.v1_11_R1.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_11_R1.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_11_R1.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_11_R1.Scoreboard;
import net.minecraft.server.v1_11_R1.ScoreboardObjective;
import net.minecraft.server.v1_11_R1.ScoreboardScore;

public class ScoreBoard {
	
	private static Scoreboard board;
	private static ScoreboardObjective obj;
	private static PacketPlayOutScoreboardObjective removepacket,createpacket;
	private static PacketPlayOutScoreboardDisplayObjective display;
	private static String s1 = "0 P", s3 = "0 E", s7 = "§o00:00:00", s9 = "§o1000*1000";
	private static HashMap<String, Integer> kill = new HashMap<String, Integer>();
	private static ScoreboardScore l2,l3;
	private static PacketPlayOutScoreboardScore p2,p3;	
	
	//Initialization
	
	public static void sendScoreboard(Player p, HtS main) {
		 board = new Scoreboard();
		 obj = board.registerObjective(main.htsEdition, IScoreboardCriteria.b);
		
		removepacket = new PacketPlayOutScoreboardObjective(obj, 1);
		createpacket = new PacketPlayOutScoreboardObjective(obj, 0);
		display = new PacketPlayOutScoreboardDisplayObjective(1, obj);
		
		obj.setDisplayName(main.htsEdition);
		
		ScoreboardScore l0 = new ScoreboardScore(board, obj, "§4Joueur:");
		ScoreboardScore l1 = new ScoreboardScore(board, obj, s1);
		if(!main.teams.isEmpty()) {
			l2 = new ScoreboardScore(board, obj, "§4Equipe:");
			l3 = new ScoreboardScore(board, obj, s3);
		}
		ScoreboardScore l4 = new ScoreboardScore(board, obj, "§4Tuer:");
		ScoreboardScore l5 = new ScoreboardScore(board, obj, String.valueOf(kill.get(p.getDisplayName())) + " T");
		ScoreboardScore l6 = new ScoreboardScore(board, obj, "§4Timer:");
		ScoreboardScore l7 = new ScoreboardScore(board, obj, s7);
		ScoreboardScore l8 = new ScoreboardScore(board, obj, "§4Bordure:");
		ScoreboardScore l9 = new ScoreboardScore(board, obj, s9);
		
		l0.setScore(9);
		l1.setScore(8);
		if(!main.teams.isEmpty()) {
			l2.setScore(7);
			l3.setScore(6);	
		}
		l4.setScore(5);
		l5.setScore(4);
		l6.setScore(3);
		l7.setScore(2);
		l8.setScore(1);
		l9.setScore(0);
		
		PacketPlayOutScoreboardScore p0 = new PacketPlayOutScoreboardScore(l0);
		PacketPlayOutScoreboardScore p1 = new PacketPlayOutScoreboardScore(l1);
		if(!main.teams.isEmpty()) {
			p2 = new PacketPlayOutScoreboardScore(l2);
			p3 = new PacketPlayOutScoreboardScore(l3);
		}
		PacketPlayOutScoreboardScore p4 = new PacketPlayOutScoreboardScore(l4);
		PacketPlayOutScoreboardScore p5 = new PacketPlayOutScoreboardScore(l5);
		PacketPlayOutScoreboardScore p6 = new PacketPlayOutScoreboardScore(l6);
		PacketPlayOutScoreboardScore p7 = new PacketPlayOutScoreboardScore(l7);
		PacketPlayOutScoreboardScore p8 = new PacketPlayOutScoreboardScore(l8);
		PacketPlayOutScoreboardScore p9 = new PacketPlayOutScoreboardScore(l9);
		
		sendPacket(p, removepacket);
		sendPacket(p, createpacket);
		sendPacket(p, display);
		
		sendPacket(p, p0);
		sendPacket(p, p1);
		sendPacket(p, p2);
		sendPacket(p, p3);
		sendPacket(p, p4);
		sendPacket(p, p5);
		sendPacket(p, p6);
		sendPacket(p, p7);
		sendPacket(p, p8);
		sendPacket(p, p9);
	}
	
	//Packet Sender
	
	private static void sendPacket(Player p, Packet<?> packet) {
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}
	
	//Packet Method
	
	public static void sendPlayers(HtS main) {
		s1 = String.valueOf(main.players.getPlayersInGame().size()) + " J";
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			sendScoreboard(p, main);
		}
	}
	
	public static void sendTeams(HtS main) {
		s3 = String.valueOf(main.teams.size()) + " E";
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			sendScoreboard(p, main);
		}
	}
	
	public static void sendKills(HtS main) {
		for(Player p: Bukkit.getServer().getOnlinePlayers()) {
			kill.put(p.getDisplayName(), p.getStatistic(Statistic.PLAYER_KILLS));
			sendScoreboard(p, main);
		}
	}
	
	public static void sendTimer(HtS main) {
		s7 = "§o" + Start.timerGame;
		for(Player p: Bukkit.getServer().getOnlinePlayers()) {
			sendScoreboard(p, main);
		}
	}
	
	public static void sendBorder(HtS main) {
		s9 = "§o" + (int)Bukkit.getWorld("world").getWorldBorder().getSize() + " * " + ((int)Bukkit.getWorld("world").getWorldBorder().getSize());
		for(Player p: Bukkit.getServer().getOnlinePlayers()) {
			sendScoreboard(p, main);
		}
	}
}
