package fr.theogiraudet.HtS.Commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.Enumeration.HtSState;
import fr.theogiraudet.HtS.Objects.Team;

public class TeamCommand implements CommandExecutor, Listener {

	private HtS main;

	public TeamCommand(HtS htS) {
		main = htS;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClickInventoryEvent(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		ItemStack it = e.getItem();
		if (main.isState(HtSState.WAIT) && it != null && it.getType() == Material.WOOL) {
			for (Team team : main.teams) {
				if (team.getWoolData() == it.getData().getData()) {
					this.addPlayer(p, team);
				} else if (team.getTeamPlayers().contains(p.getUniqueId())) {
					team.removePlayer(p.getName());
				}
			}
			e.setCancelled(true);
		}
	}

	public void addPlayer(Player p, Team team) {
		if (team.getTeamPlayers().contains(p.getUniqueId())) {
			p.sendMessage("§4Vous êtes déjà dans cette team !");
			return;
		}
		p.sendMessage("§2Vous avez bien rejoins la team " + team.getTeamColor() + team.getTeamName() + ".");
		team.addPlayer(p.getName());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

		if (sender instanceof Player || sender instanceof BlockCommandSender) {

			// && main.isState(HtSState.WAIT)
			if (cmd.getName().equalsIgnoreCase("team") && sender.hasPermission("team.use")) {
				if (args.length >= 4 && args[0].equalsIgnoreCase("add")) {
					for (Team i : main.teams) {
						if (args[1].equalsIgnoreCase(i.getTeamName())) {
							sender.sendMessage("§4Cette team existe déjà !");
							return true;
						}
					}
					System.out.println(args[1] + " " + args[2] + " " + args[3]);
					main.teams.add(new Team(args[1], args[2].replace("&", "§"), Byte.parseByte(args[3])));
					sender.sendMessage("§2Team ajoutée !");
					main.recap.replace("§6Équipe : ", "§rOui");
					return true;

				} else if (args.length >= 2 && args[0].equalsIgnoreCase("remove")) {
					for (Team i : main.teams) {
						if (args[1].equalsIgnoreCase(i.getTeamName())) {
							main.teams.remove(i);
							sender.sendMessage("§2Team supprimée !");
							if (main.teams.size() == 0) {
								main.recap.replace("§6Équipe : ", "§rNon");
							}
							return true;
						}
					}
					sender.sendMessage("§4Cette team n'existe pas !");
					return true;
				} else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
					for (Team i : main.teams) {
						sender.sendMessage(i.getTeamName());
						for (UUID uuid : i.getTeamPlayers()) {
							sender.sendMessage(Bukkit.getPlayer(uuid) + "");
						}
					}
					return true;

				} else if (args.length >= 1 && args[0].equalsIgnoreCase("give")) {
					for (Player p : Bukkit.getOnlinePlayers()) {
						p.getInventory().clear();
						for (Team i : main.teams) {
							p.getInventory().addItem(i.getIcon());
						}
					}
					return true;

				} else if (args.length >= 2 && args[0].equalsIgnoreCase("teamhit")) {
					if (args[1].equalsIgnoreCase("true")) {
						for (Team t : main.teams) {
							t.setFriendlyFire(true);
						}
						sender.sendMessage("§2Friendly fire activé !");
						return true;
					} else if (args[1].equalsIgnoreCase("false")) {
						for (Team t : main.teams) {
							t.setFriendlyFire(false);
						}
						sender.sendMessage("§2Friendly fire désactivé !");
						return true;
					} else {
						sender.sendMessage("§4" + args[1] + " n'est pas une valeur correcte !");
						return true;
					}
				} else if (args.length >= 3 && args[0].equalsIgnoreCase("join")) {
					for (Team team : main.teams) {
						if (args[1] == team.getTeamName()) {
							for (Player p : Bukkit.getOnlinePlayers()) {
								if (args[2] == p.getName()) {
									this.addPlayer(p, team);
								}
							}
						}
					}
				}

			}

		}

		return false;
	}

}
