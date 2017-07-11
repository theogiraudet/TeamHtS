package fr.theogiraudet.HtS.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldBorder;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.theogiraudet.HtS.BiomeMutator;
import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.ScoreboardSign;
import fr.theogiraudet.HtS.Commands.Start;
import fr.theogiraudet.HtS.Enumeration.HtSState;
import fr.theogiraudet.HtS.Enumeration.ModState;
import fr.theogiraudet.HtS.Objects.DeathEntityLoot;
import fr.theogiraudet.HtS.Objects.ItemStackManager;
import fr.theogiraudet.HtS.Objects.Randomizer;
import fr.theogiraudet.HtS.Objects.Team;

public class EventManager implements Listener {

	private HtS main;
	public static UUID playerDepthBreath = null;
	public static UUID playerBorder = null;
	public DeathEntityLoot playerLoot = new DeathEntityLoot(new ItemStack(Material.GOLDEN_APPLE, 1),
			new ItemStackManager(Material.SKULL_ITEM, (short) 3, 1, "").getItemStack());
	public static boolean depthBreath = false;
	public static boolean skeleton = false;
	public HashMap<Player, Integer> shellUse = new HashMap<>();

	public EventManager(HtS htS) {
		this.main = htS;
	}
	
	@EventHandler
	public void onEggThrow(PlayerEggThrowEvent e) {
		Statistics stat = new Statistics(main);
		stat.createFiles();
	}
	
	@EventHandler
    public void onNetherLoad(PlayerChangedWorldEvent e) throws InterruptedException{
            if(e.getPlayer().getLocation().getWorld().getEnvironment() == Environment.NETHER){
                Thread.sleep(5000);
                @SuppressWarnings("unused")
                BiomeMutator BiomeMutator = new BiomeMutator(e.getPlayer());
            }
        }
	
	@EventHandler
    public void onShulkerDeathInNether(EntityDeathEvent e){
        if(e.getEntityType() == EntityType.SHULKER){
        	ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
            e.getDrops().clear();
            if(Randomizer.RandRate(15)){
                ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
                EnchantmentStorageMeta esm = (EnchantmentStorageMeta) book.getItemMeta();
                esm.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
                book.setItemMeta(esm);
                drops.add(book);
            }
            if(Randomizer.RandRate(25)) {
            	drops.add(new ItemStackManager(Material.SHULKER_SHELL, (short) 0, 1, "§rShulker Shell", "A un pourcentage de chance de bloquer un coup. 3 utilisations.").getItemStack());
            }
            e.getDrops().addAll(drops);
        }
    }
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player && ((Player) e.getEntity()).getInventory().contains(Material.SHULKER_SHELL)) {
			Player p = (Player) e.getEntity();
			World world = p.getWorld();
			if(Randomizer.RandRate(63)) {
				if(shellUse.containsKey(p)) {
					shellUse.replace(p, shellUse.get(p) + 1);
					if(shellUse.get(p) == 3) {
						world.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
						shellUse.replace(p, 0);
						p.getInventory().removeItem(new ItemStack(Material.SHULKER_SHELL, 1));
					} else {
						world.playSound(p.getLocation(), Sound.ITEM_SHIELD_BLOCK, 1, 1);
					}
				} else {
					shellUse.put(p, 1);
					world.playSound(p.getLocation(), Sound.ITEM_SHIELD_BLOCK, 1, 1);
				}
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
    public void onNetherProjectileShot(ProjectileLaunchEvent e){
        if(e.getEntity().getLocation().getWorld().getEnvironment() == Environment.NETHER){
            e.getEntity().setVelocity(e.getEntity().getVelocity().multiply(2.06));
        }
    }
	
	
	@EventHandler
	public void onHealLevelChange(EntityDamageEvent e) {
		if(main.isState(HtSState.WAIT) && e.getEntity() instanceof Player) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent e) {
		if(main.isState(HtSState.WAIT)) {
			e.setCancelled(true);
		}
	}
	
	
	//Souffle des profondeurs
	@EventHandler
	public void playerHeight(PlayerMoveEvent e) {
		if(depthBreath) {
			if(e.getPlayer().getLocation().getY() <= 36 && e.getPlayer().getWorld().getEnvironment() == Environment.NORMAL) {
				e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10000 * 20, 0, false, false));
				e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 10000 * 20, 0, false, false));
			} else if (e.getPlayer().getLocation().getY() > 36 && e.getPlayer().getWorld().getEnvironment() == Environment.NORMAL) {
				e.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
				e.getPlayer().removePotionEffect(PotionEffectType.CONFUSION);
				}
		}
	}
	
	
	//Entrée dans le chat
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		if(e.getPlayer().getGameMode() == GameMode.SPECTATOR) {
			String messageS = e.getMessage();
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(p.getGameMode() == GameMode.SPECTATOR) {
					p.sendMessage("§7§o[Spectateur] " + e.getPlayer().getName() + " : " + messageS);
			}
		}
			
			e.setCancelled(true);
		}
		
		if(playerDepthBreath != null) {
			e.setCancelled(true);
			String message = e.getMessage();
			int number = -1;
			try { 
				number = Integer.parseInt(message); 
				if(number >= 0 && number <= 90) {
					Inventaire.depthBreathBegin = number;
					Start.depthBreathT = number;
					e.getPlayer().sendMessage("§2Le souffle des profondeurs se déclenchera à " + number + " minutes.");
				} else {
					e.getPlayer().sendMessage("§4La valeur entrée n'est pas dans l'intervalle [0;90] !");
				}
				} 
			catch (Exception ex) { 
				e.getPlayer().sendMessage("§4La valeur entrée n'est pas un nombre !");
				}
			playerDepthBreath = null;
		} else if( playerBorder != null) {
			e.setCancelled(true);
			String message = e.getMessage();
			int number = 0;
			try { 
				number = Integer.parseInt(message); 
				if(number >= 250 && number <= 2000) {
					Inventaire.borderD = number;
					World world = Bukkit.getWorld("world");
					WorldBorder border = world.getWorldBorder();
					border.setCenter(0.0, 0.0);
					border.setSize(number*2);
					e.getPlayer().sendMessage("§2La bordure se situe à " + number + " blocs.");
					main.recap.replace("§6Bordure : ", "§r" + number*2 + " * " + number*2);
				} else {
					e.getPlayer().sendMessage("§4La valeur entrée n'est pas dans l'intervalle [250;2000] !");
				}
				}
			catch (Exception ex) { 
				e.getPlayer().sendMessage("§4La valeur entrée n'est pas un nombre !");
				}
			playerBorder = null;
		}
	}
	
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		System.out.println(main.getTeams());
		Player p = e.getPlayer();
		if (main.isState(HtSState.WAIT)) {
			p.setGameMode(GameMode.ADVENTURE);
			p.teleport(new Location(Bukkit.getWorld("world"), 0.0, 205.0, 0.0));
		} else if (main.isState(HtSState.RUNNING) && !main.players.isPlayerInGame(p.getUniqueId())) {
			p.setGameMode(GameMode.SPECTATOR);
		}
		
		if(!main.getTeams().isEmpty()) {
			for(Team team : main.teams) {
				if(team.isTeamPlayer(e.getPlayer())) {
					team.setScoreboard(e.getPlayer());
				}
			}
		}
	}

	// Nerf et modification des taux de drops (2 events pour les pommes)

	
			
	//Fausse mort
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if (!(p.getKiller() instanceof Creeper)) {
			p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
			for (ItemStack is : playerLoot.getDrops()) {
				if (is.getData().getData() == 3 && is.getType() == Material.SKULL_ITEM) {
					SkullMeta isM = (SkullMeta) is.getItemMeta();
					isM.setOwner(p.getName());
					isM.setDisplayName("Tête de " + p.getName());
					is.setItemMeta(isM);
				}
				p.getWorld().dropItem(p.getLocation(), is);
				((Player) p).setGameMode(GameMode.SPECTATOR);
				main.players.removePlayer(p.getUniqueId());
				
				for (Player p2 : Bukkit.getOnlinePlayers()) {
					p2.playSound(p2.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1.0f, 1.0f);
				}
			}
			
			if(!main.teams.isEmpty() && main.isSyTState(ModState.OFF)) {
				Team team = main.getTeam(p);
				team.removePlayer(p.getName());
				if(team.getTeamSize() == 0) {
					main.removeTeam(team);
				}
				if(main.teams.size() == 1) {
					Bukkit.broadcastMessage("§2L'équipe " + main.getTeams().get(0).getTeamColor() + main.getTeams().get(0).getTeamName() + "§r§2 a gagné !");
				}
			} else if(main.teams.isEmpty() && main.players.getPlayersInGame().size() == 1) {
				Bukkit.broadcastMessage("§2" + Bukkit.getPlayer(main.players.getPlayersInGame().get(0)).getName() + " a gagné !");
			}
			
			if(p.getKiller() instanceof Player) {
				if(main.board.containsKey(p.getKiller())) {
					main.board.get(p.getKiller()).setLine(10, "§o" + p.getStatistic(Statistic.PLAYER_KILLS));
				}
			}
			for(Entry<Player, ScoreboardSign> sign : main.board.entrySet()) {
				sign.getValue().setLine(6, "§o" + main.players.getPlayersInGame().size());
			}
		}
	}
		
		
	@EventHandler
	public void onSkeletonShoot(EntityShootBowEvent e) {
		Entity en = e.getEntity();
		if (en instanceof Skeleton && !skeleton) {
			e.setCancelled(true);
		}		
		}
	
	
	@EventHandler()
	public void onCreeperOneShot(EntityDamageByEntityEvent e) {
		Entity p = e.getEntity();
		if (((p instanceof Player))) {
			double damage = e.getDamage();
			if (damage >= ((Player) p).getHealth()) {	
				if(e.getDamager() instanceof Creeper) {
				((Player) p).setHealth(2.0D);
				e.setCancelled(true);
				} 
			}
		}
	}
	
	
	
	@SuppressWarnings("deprecation")
	public void fakeDeath(Entity p) {
				((Player) p).setHealth(20.0D);
				for (ItemStack is : playerLoot.getDrops()) {
					if (is.getData().getData() == 3 && is.getType() == Material.SKULL_ITEM) {
						SkullMeta isM = (SkullMeta) is.getItemMeta();
						isM.setOwner(p.getName());
						isM.setDisplayName("Tête de " + p.getName());
						is.setItemMeta(isM);
					}
					p.getWorld().dropItem(p.getLocation(), is);
					((Player) p).setGameMode(GameMode.SPECTATOR);
					main.players.removePlayer(p.getUniqueId());
					for(Player p2 : Bukkit.getOnlinePlayers()) {
						p2.playSound(p2.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1.0f, 1.0f);
					}
				}
			}
	
	

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Block b = e.getBlock();
		if (b.getType() == Material.GRAVEL) {
			if (Randomizer.RandRate(21)) {
				e.setCancelled(true);
				b.setType(Material.AIR);
				b.getLocation().getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.FLINT));
			} else {
				e.setCancelled(true);
				b.setType(Material.AIR);
				b.getLocation().getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.GRAVEL));
			}
		} else if (((b.getType() == Material.LEAVES)
				&& ((b.getData() == 0) || (b.getData() == 4) || (b.getData() == 12)))
				|| ((b.getType() == Material.LEAVES_2) && (b.getData() == 1)) || (b.getData() == 5)
				|| (b.getData() == 13)) {
			
			if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.WOOD_HOE
					&& e.getPlayer().getInventory().getItemInMainHand().getType() != Material.STONE_HOE
					&& e.getPlayer().getInventory().getItemInMainHand().getType() != Material.GOLD_HOE
					&& e.getPlayer().getInventory().getItemInMainHand().getType() != Material.IRON_HOE
					&& e.getPlayer().getInventory().getItemInMainHand().getType() != Material.DIAMOND_HOE) {
				
				if (Randomizer.RandRate(3)) {
					e.setCancelled(true);
					b.setType(Material.AIR);
					b.getLocation().getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.APPLE));
				} else {
					e.setCancelled(true);
					b.setType(Material.AIR);
				}
			} else {
				if (Randomizer.RandRate(5)) {
					e.setCancelled(true);
					b.setType(Material.AIR);
					b.getLocation().getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.APPLE));
				} else {
					e.setCancelled(true);
					b.setType(Material.AIR);
				}
			}
		}
	}

	@EventHandler
	public void onGhastDeath(EntityDeathEvent e) {
		Entity en = e.getEntity();
		if ((en instanceof Ghast)) {
			e.getDrops().clear();
			if (Randomizer.RandRate(51)) {
				en.getWorld().dropItemNaturally(en.getLocation(), new ItemStack(Material.GOLD_BLOCK, 1));
			} else {
				en.getWorld().dropItemNaturally(en.getLocation(), new ItemStack(Material.GOLD_INGOT, 1));
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onLeavesDecay(LeavesDecayEvent e) {
		Block b = e.getBlock();
		if (((b.getType() == Material.LEAVES) && ((b.getData() == 0) || (b.getData() == 4) || (b.getData() == 12)))
				|| ((b.getType() == Material.LEAVES_2) && (b.getData() == 1)) || (b.getData() == 5)
				|| (b.getData() == 13)) {
			if (Randomizer.RandRate(3)) {
				e.setCancelled(true);
				b.setType(Material.AIR);
				b.getLocation().getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.APPLE));
			} else {
				e.setCancelled(true);
				b.setType(Material.AIR);
			}
		}
	}

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

}
 