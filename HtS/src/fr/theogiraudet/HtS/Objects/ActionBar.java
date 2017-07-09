package fr.theogiraudet.HtS.Objects;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import fr.theogiraudet.HtS.HtS;
import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;

public class ActionBar{

	private int counter = 0;
	private PacketPlayOutChat packet;
	private Object id;
	
	public HtS main;
	public ActionBar(HtS main){
		this.main = main;
	}
	
	public void sendActionBar(Player player, String msg,int time){
		  IChatBaseComponent icbc = ChatSerializer.a("{\"text\":\"" + msg + "\"}");
		  packet = new PacketPlayOutChat(icbc, (byte)2);
		  executeActionbar(player, time);
	}
	
	private void executeActionbar(Player player, int time){			 
		id = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable(){
				
			@Override
			public void run(){	 
					
					((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
					counter ++;
					if(counter == 20){
						Bukkit.getScheduler().cancelTask((int) id);
					} 
			 } 
		}, 0L, 20L*(time+1));		 
	}
	
	public void sendActionBarAll(String msg, int time){
		  IChatBaseComponent icbc = ChatSerializer.a("{\"text\":\"" + msg + "\"}");
		  packet = new PacketPlayOutChat(icbc, (byte)2);
		  executeActionbarAll(time);
	}
	
	private void executeActionbarAll(int time){			 
		id = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable(){
				
			@Override
			public void run(){	 
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
				}
					counter ++;
				if(counter == 20){
						Bukkit.getScheduler().cancelTask((int) id);
					} 
			 } 
		}, 0L, 20L*(time+1));		 
	}
}