package ctf2024.redTeam;

import ctf2024.Team;
import info.gridworld.grid.Location;
import java.awt.*;

public class RedTeam extends Team {
	
	public RedTeam() {
		super("Red Team", Color.RED);
	}
	
	public void generateTeam() {
		// Add Players to left side only

		// If the Team is assigned to the right side, Players will automatically be added
		// to the corresponding Location mirrored around the center line

		// Add Players to Team at designated Locations
		super.addPlayer(new BeelinePlayer(new Location (5, 30)));
		super.addPlayer(new RandomPlayer(new Location(11, 30)));
		super.addPlayer(new RandomPlayer(new Location(13, 30)));
		super.addPlayer(new BeelinePlayer(new Location(20, 30)));
		super.addPlayer(new RandomPlayer(new Location(31, 30)));
		super.addPlayer(new RandomPlayer(new Location(36, 30)));
		super.addPlayer(new BeelinePlayer(new Location(40, 30)));
		super.addPlayer(new TimeoutPlayer(new Location(46, 30)));
	}
}