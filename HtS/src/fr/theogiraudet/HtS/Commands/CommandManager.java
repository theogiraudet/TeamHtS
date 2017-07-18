package fr.theogiraudet.HtS.Commands;

import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.TaupeGun.RevealCommand;
import fr.theogiraudet.SyT.SyT;

public class CommandManager {
	
	public static void loadCommands(HtS htS) {
		htS.getCommand("start").setExecutor(new Start(htS));
		htS.getCommand("team").setExecutor(new TeamCommand(htS));
		htS.getCommand("option").setExecutor(new Commands(htS));
		htS.getCommand("res").setExecutor(new Commands(htS));
		htS.getCommand("remove").setExecutor(new Commands(htS));
		htS.getCommand("spectate").setExecutor(new Commands(htS));
		htS.getCommand("radar").setExecutor(new SyT(htS));
		htS.getCommand("huntlist").setExecutor(new SyT(htS));
		htS.getCommand("cible").setExecutor(new SyT(htS));
		htS.getCommand("heal").setExecutor(new Commands(htS));
		htS.getCommand("feed").setExecutor(new Commands(htS));
		htS.getCommand("broadcast").setExecutor(new Commands(htS));
		htS.getCommand("players").setExecutor(new Commands(htS));
		htS.getCommand("random").setExecutor(new Commands(htS));
		htS.getCommand("reveal").setExecutor(new RevealCommand(htS));
	}
	
}
