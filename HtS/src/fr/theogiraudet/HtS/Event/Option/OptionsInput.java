package fr.theogiraudet.HtS.Event.Option;

import fr.theogiraudet.HtS.Objects.ItemStackManager;

public abstract class OptionsInput extends Option {
	
	public OptionsInput(ItemStackManager item, CustomInventory inventory) {
		super(item, inventory);

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

}
