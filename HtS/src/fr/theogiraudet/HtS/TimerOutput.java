package fr.theogiraudet.HtS;

import org.bukkit.Bukkit;

import fr.theogiraudet.HtS.Event.EventManager;

public class TimerOutput {
	
	HtS main;
	
	public TimerOutput(HtS main) {
		this.main = main;
	}

	public void Output(String output, int number) {
		
		if(output == "Depth Breath") {
			if(number == 0) {
				Bukkit.broadcastMessage("�4Les mineurs ont min� beaucoup trop profond�ment et ont ouvert des poches de soufre. Les mines seront envahies d'ici 1 minute !");
			} else if(number == 1) {
				EventManager.depthBreath = true;
				Bukkit.broadcastMessage("�4Le soufre a envahi les mines !");
			}
		
		
		
		} else if(output == "Skeleton") {
			if(number == 0) {
				Bukkit.broadcastMessage("�4Les squelettes feront des d�g�ts dans 1 minutes.");
			} else if(number == 1) {
				EventManager.skeleton = true;
				Bukkit.broadcastMessage("�4Les squelettes sont activ�s.");
			}
		
		
		} else if(output == "SyT") {
			this.main.stressYourTarget.randomTarget();
		
		
		} else if(output == "radar") {
			this.main.stressYourTarget.radar();
			
		} else if(output == "Taupe") {
			
		}
	}
	
}
