package fr.theogiraudet.HtS.Commands;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.ScoreboardSign;
import fr.theogiraudet.HtS.Enumeration.HtSState;
import fr.theogiraudet.HtS.Enumeration.ModState;
import fr.theogiraudet.HtS.Event.Inventaire;
import fr.theogiraudet.HtS.Objects.Randomizer;

public class Commands implements CommandExecutor {
	
	private HtS main;

	public Commands(HtS htS) { main = htS; }

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player) {
			
			if(cmd.getName().equalsIgnoreCase("option") && sender.hasPermission("option.use") && main.isState(HtSState.WAIT)) {
				Inventaire.createInventory((Player) sender);
			}
			
			
			else if(cmd.getName().equalsIgnoreCase("res") && sender.hasPermission("res.use") && args.length >= 1 && main.isState(HtSState.RUNNING)) {
				 Player target = Bukkit.getServer().getPlayer(args[0]);
				 if(target != null) {
					 main.players.addPlayer(target.getUniqueId());
					 target.teleport(new Location(target.getWorld(), target.getLocation().getX(), 256, target.getLocation().getZ()));
					 target.setGameMode(GameMode.SURVIVAL);
					 target.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 3 * 20, 255, false, false));
					 for(Entry<Player, ScoreboardSign> sign : main.board.entrySet()) {
							sign.getValue().setLine(6, "§o" + main.players.getPlayersInGame().size());
						}
				 
				 } else {
					sender.sendMessage("§4Ce joueur n'existe pas !");
					}
				 return true;
					
				}
			
			
			else if (cmd.getName().equalsIgnoreCase("remove") && sender.hasPermission("remove.use") && args.length >= 1
					&& main.isState(HtSState.RUNNING)) {
				String name = args[0];
				for (HashMap.Entry<Player, UUID> entry : main.uuidPlayer.entrySet()) {
					if (entry.getKey().getName() == name) {
						main.players.removePlayer(entry.getValue());
						for (Entry<Player, ScoreboardSign> sign : main.board.entrySet()) {
							sign.getValue().setLine(6, "§o" + main.players.getPlayersInGame().size());
						}
						if (main.isSyTState(ModState.PRORAND)) {
							main.stressYourTarget.removePlayer(entry.getKey());
						}
						return true;
					}
				}
				sender.sendMessage("§4Ce joueur n'existe pas !");
				return true;
			}

			else if(cmd.getName().equalsIgnoreCase("spectate") && main.isState(HtSState.WAIT)) {
				((Player) sender).setGameMode(GameMode.SPECTATOR);
				 return true;
			}
			
			else if(cmd.getName().equalsIgnoreCase("heal") && sender.hasPermission("heal.use") && args.length == 1) {
				Player target = Bukkit.getServer().getPlayer(args[0]);
				 if(target != null) {
					 target.setHealth(target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());;
				 
				 } else {
					sender.sendMessage("§4Ce joueur n'existe pas !");
					}
				 return true;
			}
			
			else if(cmd.getName().equalsIgnoreCase("feed") && sender.hasPermission("feed.use") && args.length == 1) {
				Player target = Bukkit.getServer().getPlayer(args[0]);
				 if(target != null) {
					 target.setFoodLevel(20);;
				 
				 } else {
					sender.sendMessage("§4Ce joueur n'existe pas !");
					}
				 return true;
			}
			
			else if(cmd.getName().equalsIgnoreCase("broadcast") && sender.hasPermission("broadcast.use") && args.length >= 1) {
				
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < args.length; i++) {
                         sb.append(args[i]+" ");
                    }
                    String message = "&4[&6HtS&4]&r " + sb.toString();
                    message = message.replaceAll("&", "§");
                    Bukkit.broadcastMessage(message);
			
			
			} else if(cmd.getName().equalsIgnoreCase("players") && sender.hasPermission("players.use")) {
				for(UUID uuid : main.players.getPlayersInGame()) {
					System.out.println(Bukkit.getPlayer(uuid).getName());
				}
			
			
			} else if(cmd.getName().equalsIgnoreCase("random") && sender.hasPermission("random.use")) {
				int x, z, isNegative;
				int negative[] = {-1,1};
				World world = Bukkit.getWorld("world");
				WorldBorder border = world.getWorldBorder();
				
				isNegative = negative[1 - Randomizer.Rand(negative.length)];
				x = isNegative * (0 + Randomizer.Rand((int) Math.floor(border.getSize()/2 - 0)));
				
				isNegative = negative[1 - Randomizer.Rand(negative.length)];
				z = isNegative * (0 + Randomizer.Rand((int) Math.floor(border.getSize()/2 - 0)));
				sender.sendMessage(x + " " + z);
				
			}
			
			}
			
		return false;
		}
	}

