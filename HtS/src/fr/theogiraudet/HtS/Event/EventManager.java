package fr.theogiraudet.HtS.Event;

import org.bukkit.plugin.PluginManager;

import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.Commands.TeamCommand;
import fr.theogiraudet.SyT.SyT;

public class EventManager {

	public static void loadEvents(HtS htS) {
		
		PluginManager pm = htS.getServer().getPluginManager();
		
		pm.registerEvents(new NoHeal(htS), htS);
		pm.registerEvents(new TeamCommand(htS), htS);
		pm.registerEvents(new Inventaire(htS), htS);
		pm.registerEvents(new DisableCraft(htS), htS);
		pm.registerEvents(new SyT(htS), htS);
		pm.registerEvents(new Spectator(htS), htS);
		pm.registerEvents(new MobFriendEvent(htS), htS);
		pm.registerEvents(new Statistics(htS), htS);
		pm.registerEvents(new NetherEvent(htS), htS);
		pm.registerEvents(new ShulkerShell(htS), htS);
		pm.registerEvents(new WaitState(htS), htS);
		pm.registerEvents(new DepthBreath(htS), htS);
		pm.registerEvents(new FakeDeath(htS), htS);
		pm.registerEvents(new ChangeRate(htS), htS);
		pm.registerEvents(new MobFixe(htS), htS);
		pm.registerEvents(new CustomChat(htS), htS);
		pm.registerEvents(new Border(htS), htS);
		pm.registerEvents(new JoinEvent(htS), htS);
		pm.registerEvents(new Unclassifiable(htS), htS);
	}
	
}
