package fr.theogiraudet.HtS.TaupeGun;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.theogiraudet.HtS.HtS;

public class RevealCommand implements CommandExecutor {
	
	private HtS main;

	RevealCommand(HtS htS) { main = htS;}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		if(sender instanceof Player) {
			if(main.taupeGun.getTaupes().contains(((Player) sender).getUniqueId())) {
				
			} else {
				sender.sendMessage("§4Vous n'êtes pas une taupe !");
			}
		}
		return false;
	}

}
