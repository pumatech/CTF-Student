package ctf2024.blueTeam;

import ctf2024.Team;
import info.gridworld.grid.Location;

public class BlueTeam extends Team {

	// Give your Team a name and a color (use null to preserve icon colors)

	public BlueTeam() {
		super("Blue Team", null);
	}

	public void generateTeam() {

		// You can place your Players anywhere you want as long as they are:
		// - on the left side of the field
		// - not within TEN of the Flag

		// If this Team ends up being assigned to the right side, Players will
		// automatically be added to the corresponding Location mirrored around
		// the center line

		// Add some BeelinePlayers
		super.addPlayer(new BeelinePlayer(new Location (4, 30)));
		super.addPlayer(new BeelinePlayer(new Location(21, 30)));
		super.addPlayer(new BeelinePlayer(new Location(41, 30)));

		// Add some RandomPlayers
		super.addPlayer(new RandomPlayer(new Location(10, 30)));
		super.addPlayer(new RandomPlayer(new Location(15, 30)));
		super.addPlayer(new RandomPlayer(new Location(30, 30)));
		super.addPlayer(new RandomPlayer(new Location(34, 30)));

		// Add a TimeoutPlayer
		// TimeoutPlayer is here just show you what NOT to do - you
		// definitely should REPLACE IT with something else in the
		// opposing team when testing your code

//		super.addPlayer(new TimeoutPlayer(new Location(45, 30)));
	}
}