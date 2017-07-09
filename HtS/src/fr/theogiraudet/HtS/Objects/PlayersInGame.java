package fr.theogiraudet.HtS.Objects;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayersInGame {

	private List<UUID> players = new ArrayList<UUID>();
	
	public PlayersInGame() {}
	
	public List<UUID> getPlayersInGame() { return players; }
	
	public void addPlayer(UUID p) { players.add(p); }
	
	public void removePlayer(UUID p) { players.remove(p); }
	
	public boolean isPlayerInGame(UUID p) { return players.contains(p);	}
	
	public int nbrePlayer() { return players.size(); }
	
}
