package fr.theogiraudet.HtS.Event.StaticEvent;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.Objects.Randomizer;
import fr.theogiraudet.HtS.Objects.ScoreBoard;

public class Unclassifiable implements Listener {
	
	private HtS main;

	public Unclassifiable(HtS htS) { this.main = htS; }

	@EventHandler
	public void onDropNuggets(PlayerBucketFillEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlockClicked();
		if (b.getType() == Material.STATIONARY_WATER) {
			if (Randomizer.RandRate(2)) {
				p.getInventory().addItem(new ItemStack(Material.GOLD_NUGGET, 1));
			} else if (Randomizer.RandRate(3)) {
				p.getInventory().addItem(new ItemStack(Material.IRON_NUGGET, 1));
			}
		}
	}
	
	//Algue Urticante
	@EventHandler
	public void onAlgaeCatch(PlayerPickupItemEvent e) {
		if(e.getItem().getItemStack().getType() == Material.DOUBLE_PLANT) {
			e.setCancelled(true);
			e.getPlayer().setHealth(e.getPlayer().getHealth()-1);
			e.getItem().remove();
		}
	}

	//Player Death Scoreboard Update
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		ScoreBoard.sendKills(main);
		ScoreBoard.sendPlayers(main);
		if(!main.teams.isEmpty()) {ScoreBoard.sendTeams(main);}
	}
}
