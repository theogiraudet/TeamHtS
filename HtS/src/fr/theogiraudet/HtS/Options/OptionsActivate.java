package fr.theogiraudet.HtS.Options;

import fr.theogiraudet.HtS.Objects.ItemStackManager;

public class OptionsActivate extends Option {

	public OptionsActivate(ItemStackManager item) {
		super(item);

	}

	public void setEnable(boolean enable) {
		if(enable) {
			super.icon.setGlint(true);
		} else {
			super.icon.setGlint(false);
		}
	}
	
	public boolean isEnabled() {
		return super.icon.getGlint();
	}
	
}
