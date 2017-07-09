package fr.theogiraudet.HtS.Objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.inventory.ItemStack;

public class DeathEntityLoot {

	private List<ItemStack> drops = new ArrayList<ItemStack>();
	

	public DeathEntityLoot(ItemStack... is) {
		this.drops = Arrays.asList(is);
	}
	
	public void addDrops(ItemStack... is) {
		this.drops.addAll(Arrays.asList(is));
	}
	
	public void removeDrops(ItemStack... is) {
		this.drops.removeAll(Arrays.asList(is));
	}
	
	public List<ItemStack> getDrops() {
		return drops;
	}

}
