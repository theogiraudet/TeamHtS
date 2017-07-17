package fr.theogiraudet.HtS.Event;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.theogiraudet.HtS.BiomeMutator;
import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.Objects.ItemStackManager;
import fr.theogiraudet.HtS.Objects.Randomizer;

public class NetherEvent implements Listener{

	public HtS main;

	public NetherEvent(HtS htS) {
		this.main = htS;
	}

	@EventHandler
    public void onNetherLoad(PlayerChangedWorldEvent e) throws InterruptedException{
            if(e.getPlayer().getLocation().getWorld().getEnvironment() == Environment.NETHER){
				e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10000 * 20, 1, false, false));
            	Thread.sleep(5000);
                @SuppressWarnings("unused")
                BiomeMutator BiomeMutator = new BiomeMutator(e.getPlayer());
            }
            else {
				e.getPlayer().removePotionEffect(PotionEffectType.JUMP);
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
    public void onNetherProjectileShot(ProjectileLaunchEvent e){
        if(e.getEntity().getLocation().getWorld().getEnvironment() == Environment.NETHER){
            e.getEntity().setVelocity(e.getEntity().getVelocity().multiply(2.06));
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

}
