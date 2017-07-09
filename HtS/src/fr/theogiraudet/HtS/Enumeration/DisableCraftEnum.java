package fr.theogiraudet.HtS.Enumeration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

public enum DisableCraftEnum {

	DIAMOND_SWORD(Material.DIAMOND_SWORD, (byte)0, 1, "§rÉpée en diamant", "§2Activé", false, new ItemStack(Material.DIAMOND_SWORD, 1)),
	SWORD(Material.IRON_SWORD, (byte)0, 1, "§rÉpées", "§2Activé", false, new ItemStack(Material.WOOD_SWORD, 1), new ItemStack(Material.STONE_SWORD, 1), new ItemStack(Material.IRON_SWORD, 1), new ItemStack(Material.GOLD_SWORD, 1), new ItemStack(Material.DIAMOND_SWORD, 1)),
	BOW(Material.BOW, (byte)0, 1, "§rArc", "§2Activé", false, new ItemStack(Material.BOW, 1)),
	DIAMOND_AXE(Material.DIAMOND_AXE, (byte)0, 1, "§rHâche en diamant", "§2Activé", false, new ItemStack(Material.DIAMOND_AXE, 1)),
	AXE(Material.IRON_AXE, (byte)0, 1, "§rHâches", "§2Activé", false, new ItemStack(Material.WOOD_AXE, 1), new ItemStack(Material.STONE_AXE, 1), new ItemStack(Material.IRON_AXE, 1), new ItemStack(Material.GOLD_AXE, 1), new ItemStack(Material.DIAMOND_AXE, 1)),
	FISHING_ROD(Material.FISHING_ROD, (byte)0, 1, "§rCanne à pèche", "§2Activé", false, new ItemStack(Material.FISHING_ROD, 1)),
	STRONG_POTION_II(Material.POTION, (byte)0, 1, "§rPotion de force II", "§2Activé", false, PotionType.STRENGTH, true, new ItemStack(Material.POTION, 1)),
	POISON_POTION_II(Material.POTION, (byte)0, 1, "§rPotion de poison II", "§2Activé", false, PotionType.POISON, true, new ItemStack(Material.POTION, 1)),
	POTION_II(Material.POTION, (byte)0, 1, "§rPotion niveau II", "§2Activé", false, PotionType.INSTANT_DAMAGE, true, new ItemStack(Material.POTION, 1)),
	POTION(Material.BREWING_STAND_ITEM, (byte)0, 1, "§rPotion", "§2Activé", false, new ItemStack(Material.BREWING_STAND_ITEM, 1)),
	FIRE_ENCHANT(Material.ENCHANTED_BOOK, (byte)0, 1, "§rEnchantement de feu", "§2Activé", false, new ItemStack(Material.ENCHANTED_BOOK, 1)),
	ENCHANT(Material.ENCHANTMENT_TABLE, (byte)0, 1, "§rEnchantement", "§2Activé", false, new ItemStack(Material.ENCHANTMENT_TABLE, 1)),
	FLINT_AND_STEEL(Material.FLINT_AND_STEEL, (byte)0, 1, "§rBricket", "§2Activé", false, new ItemStack(Material.POTION, 1));
	
	private Material item;
	private byte data;
	private int number;
	private String name, lore;
	private boolean state, level;
	private PotionType potionType;
	private List<ItemStack> itemDisabled = new ArrayList<ItemStack>();
	
	DisableCraftEnum(Material item, byte data, int number, String name, String lore, boolean state, PotionType potionType, boolean level, ItemStack disabledPotion) {
		this.item = item;
		this.data = data;
		this.number = number;
		this.name= name;
		this.lore = lore;
		this.state = state;
		this.potionType = potionType;
		this.level = level;
		this.itemDisabled.addAll(Arrays.asList(disabledPotion));
	}
	
	DisableCraftEnum(Material item, byte data, int number, String name, String lore, boolean state, ItemStack... disabled) {
		this.item = item;
		this.data = data;
		this.number = number;
		this.name= name;
		this.lore = lore;
		this.state = state;
		this.itemDisabled.addAll(Arrays.asList(disabled));
	}
	
	public Material getMaterial() { return item; }
	public byte getData() { return data; }
	public int getNumber() { return number; }
	public String getName() { return name; }
	public String getLore() { return lore; }
	public boolean getState() { return state; }
	public boolean getLevel() { return level; }
	public PotionType getPotionType() { return potionType; }
	public boolean getPotionLevel() { return level; }
	public List<ItemStack> getItemDisabled() { return itemDisabled; }
	
}
