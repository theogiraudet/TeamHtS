package fr.theogiraudet.HtS;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Statistics {
	
	private HtS main;

	public Statistics(HtS htS) {
		this.main = htS;
	}
	
	public void createFile() {
		for(UUID p : main.players.getPlayersInGame()) {
			try {
				File f = new File("\\stats" + p.toString() + ".txt");
			
				if (f.createNewFile()){
					System.out.println("File is created!");
				}else{
					System.out.println("File already exists.");
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}	
}
