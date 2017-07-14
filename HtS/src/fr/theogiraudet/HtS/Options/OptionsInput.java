package fr.theogiraudet.HtS.Options;

import fr.theogiraudet.HtS.Objects.ItemStackManager;

public class OptionsInput extends Option {
	
	public OptionsInput(ItemStackManager item) {
		super(item);

	}

	public int requestInt () {
		return 0;
	}
	
	public boolean requestBoolean() {
		return false;
	}
	
	public String requestString() {
		return "";
	}
	
	public void setInventory(String inventory) {
		Inventories.addItem(inventory, this);
		this.inventory = inventory;
	}

}
