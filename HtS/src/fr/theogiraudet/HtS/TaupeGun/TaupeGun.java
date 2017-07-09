package fr.theogiraudet.HtS.TaupeGun;

import fr.theogiraudet.HtS.HtS;
import fr.theogiraudet.HtS.Timer;

public class TaupeGun {

	HtS main;
	
	 public TaupeGun(HtS main) {
		 this.main = main;
	}
	 
	 public void TaupeLaunch() {
			Timer taupe = new Timer(main, "Taupe", 20);
			taupe.runTaskTimer(main, 20, 20);
		 }
		 
	
}
