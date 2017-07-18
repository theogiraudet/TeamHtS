package fr.theogiraudet.HtS.TaupeGun;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.Enumeration.ModState;

public class RevealCommand implements CommandExecutor {

	private HtS main;

	public RevealCommand(HtS htS) {
		main = htS;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		if (sender instanceof Player && cmd.getName().equalsIgnoreCase("reveal") && main.isTaupeState(ModState.PRORAND)) {
			if (main.taupeGun.getTaupes().contains(((Player) sender).getUniqueId())) {
				if (!main.taupeGun.taupeReveal.get(((Player) sender).getUniqueId())) {
					Bukkit.broadcastMessage("§6" + sender + " §4se révèle être une taupe !");
					((Player) sender).getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 1));
					main.taupeGun.taupeReveal.replace(((Player) sender).getUniqueId(), true);
				} else {
					sender.sendMessage("§4Vous vous êtes déjà révélé !");
				}
		} else {
			sender.sendMessage("§4Vous n'êtes pas une taupe !");
		}
		}
		return false;
	}

}
