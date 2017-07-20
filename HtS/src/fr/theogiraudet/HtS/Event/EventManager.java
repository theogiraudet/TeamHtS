package fr.theogiraudet.HtS.Event;

import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;

import fr.theogiraudet.HtS.CustomCraft;
import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.Commands.TeamCommand;
import fr.theogiraudet.HtS.Objects.ItemStackManager;
import fr.theogiraudet.HtS.Options.CustomInventoryEvent;
import fr.theogiraudet.HtS.Options.Sword;
import fr.theogiraudet.HtS.Options.Test;
import fr.theogiraudet.HtS.TaupeGun.TaupeChannel;
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
		pm.registerEvents(new TaupeChannel(htS), htS);
		pm.registerEvents(new HeadShot(htS), htS);
		pm.registerEvents(new CustomInventoryEvent(htS), htS);

		pm.registerEvents(new Test(), htS);
		pm.registerEvents(new Sword(new ItemStackManager(Material.DIAMOND_SWORD, (short) 0, 1, "Épée", "§2Activé", true)), htS);
		pm.registerEvents(new CustomCraft(htS), htS);
	}

}
