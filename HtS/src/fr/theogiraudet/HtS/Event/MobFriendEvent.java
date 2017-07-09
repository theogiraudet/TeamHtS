package fr.theogiraudet.HtS.Event;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.ItemStack;

import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.Objects.ItemStackManager;
import fr.theogiraudet.HtS.Objects.Randomizer;
import fr.theogiraudet.HtS.Objects.Team;
import net.md_5.bungee.api.ChatColor;

public class MobFriendEvent implements Listener{

	public HtS main;
	
	public MobFriendEvent(HtS htS) {
		this.main = htS;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onMobTarget(EntityTargetLivingEntityEvent e){
		if(e.getTarget() instanceof Player){
			if(e.getEntity().getCustomName() == main.getTeam((Player) e.getTarget()).getTeamName()){	
				e.setCancelled(true);
			}
		}	
	}
	
	@EventHandler
	public void onMobSpawn(EntitySpawnEvent e){
		if(e.getEntity() instanceof Monster){
			for(Team t : main.getTeams())
				if(t.getTeamName() == e.getEntity().getCustomName().split(",")[0]){
					World w = e.getEntity().getWorld();
					Location l = e.getEntity().getLocation();
					EntityType type = e.getEntityType();
				
					Entity mob = w.spawnEntity(l,type);
					mob.setCustomName(e.getEntity().getCustomName().split(",")[0]);
					mob.setCustomNameVisible(false);				
			}
		}
	}
	
	@EventHandler
	public void onMobDeath(EntityDeathEvent e){
		if(e.getEntity() instanceof Monster){
			if(Randomizer.RandRate(10)){
				if(e.getEntity().getKiller() instanceof Player){
					Player p = e.getEntity().getKiller();
					
					ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
					drops.add(new ItemStackManager(Material.MONSTER_EGG, (short) e.getEntity().getEntityId(), 1, ChatColor.GREEN + main.getTeam(p).getTeamName() + ", appel � un ami", "Fait appara�tre un monstre qui combattera � vos c�t�s (il ne vous suivera pas).").getItemStack());
				}
			}	
		}
	}
}