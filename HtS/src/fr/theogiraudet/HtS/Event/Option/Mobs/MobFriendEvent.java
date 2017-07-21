package fr.theogiraudet.HtS.Event.Option.Mobs;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.Objects.ItemStackManager;
import fr.theogiraudet.HtS.Objects.Randomizer;
import net.md_5.bungee.api.ChatColor;

public class MobFriendEvent implements Listener{

	private HtS main;
	private boolean mobfriend = false;
	
	public MobFriendEvent(HtS htS) {
		this.main = htS;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onMobTarget(EntityTargetLivingEntityEvent e){
		if (mobfriend) {
			if (e.getTarget() instanceof Player) {
				if (e.getEntity().getCustomName() != null) {
					if (e.getEntity().getCustomName() == main.getTeam((Player) e.getTarget()).getTeamName())
						e.setCancelled(true);
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMobSpawn(PlayerInteractEvent e){
		if (mobfriend ) {
			if (e.getPlayer().getItemInHand().getType() == Material.MONSTER_EGG
					&& e.getItem().getItemMeta().hasDisplayName() && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				e.setCancelled(true);
				Entity mob = e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation(),
						EntityType.fromName(e.getItem().getItemMeta().getDisplayName().split(": ")[1]));
				mob.setCustomNameVisible(false);
				mob.setCustomName(main.getTeam(e.getPlayer()).getTeamName());
				e.getPlayer().getItemInHand().setAmount(e.getPlayer().getItemInHand().getAmount() - 1);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMobDeath(EntityDeathEvent e){
		if (mobfriend) {
			if (e.getEntity().getCustomName() == null
					&& (e.getEntity() instanceof Monster || e.getEntityType() == EntityType.SLIME)
					&& e.getEntity().getKiller() instanceof Player
					&& main.players.isPlayerInGame(e.getEntity().getKiller().getUniqueId())) {
				if (Randomizer.RandRate(10)) {
					ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
					drops.add(new ItemStackManager(Material.MONSTER_EGG, e.getEntityType().getTypeId(), 1,
							ChatColor.GREEN + main.getTeam(e.getEntity().getKiller()).getTeamName()
									+ " a un appel à un : " + e.getEntity().getName(),
							"Fait apparaître un monstre qui combattra à vos côtés (il ne vous suivera pas).")
									.getItemStack());
					e.getDrops().addAll(drops);
				}
			}
		}
	}
}
