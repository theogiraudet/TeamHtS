package fr.theogiraudet.HtS.Event;

import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;

import fr.theogiraudet.HtS.CustomCraft;
import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.Commands.TeamCommand;
import fr.theogiraudet.HtS.Event.Option.CustomInventory;
import fr.theogiraudet.HtS.Event.Option.CustomInventoryEvent;
import fr.theogiraudet.HtS.Event.Option.Inventaire;
import fr.theogiraudet.HtS.Event.Option.Test;
import fr.theogiraudet.HtS.Event.Option.EnableItem.DiamondSword;
import fr.theogiraudet.HtS.Event.Option.EnableItem.DisableCraft;
import fr.theogiraudet.HtS.Event.Option.EnableItem.Sword;
import fr.theogiraudet.HtS.Event.Option.Mobs.MobFixe;
import fr.theogiraudet.HtS.Event.Option.Mobs.MobFriendEvent;
import fr.theogiraudet.HtS.Event.Option.Mobs.NetherEvent;
import fr.theogiraudet.HtS.Event.Option.Time.Border;
import fr.theogiraudet.HtS.Event.Option.Time.DepthBreath;
import fr.theogiraudet.HtS.Event.StaticEvent.ChangeRate;
import fr.theogiraudet.HtS.Event.StaticEvent.CustomChat;
import fr.theogiraudet.HtS.Event.StaticEvent.FakeDeath;
import fr.theogiraudet.HtS.Event.StaticEvent.HeadShot;
import fr.theogiraudet.HtS.Event.StaticEvent.JoinEvent;
import fr.theogiraudet.HtS.Event.StaticEvent.NoHeal;
import fr.theogiraudet.HtS.Event.StaticEvent.ShulkerShell;
import fr.theogiraudet.HtS.Event.StaticEvent.Spectator;
import fr.theogiraudet.HtS.Event.StaticEvent.Statistics;
import fr.theogiraudet.HtS.Event.StaticEvent.Unclassifiable;
import fr.theogiraudet.HtS.Event.StaticEvent.WaitState;
import fr.theogiraudet.HtS.Objects.ItemStackManager;
import fr.theogiraudet.SyT.SyT;
import fr.theogiraudet.TaupeGun.TaupeChannel;

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
		pm.registerEvents(new CustomCraft(htS), htS);
		
		pm.registerEvents(new DiamondSword(new ItemStackManager(Material.DIAMOND_SWORD, (short) 0, 1, "§rÉpée en diamant", "§r§2Activé", true), CustomInventory.DISABLE), htS);
		pm.registerEvents(new Sword(new ItemStackManager(Material.IRON_SWORD, (short) 0, 1, "§rÉpée", "§r§2Activé", true), CustomInventory.DISABLE), htS);

	}

}
