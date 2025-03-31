package ctf2025.blueTeam;

import ctf2025.Player;
import info.gridworld.grid.Location;

public class TimeoutPlayer extends Player {

	public TimeoutPlayer(Location startLocation) {
		super(startLocation);
	}

	public Location getMoveLocation() {

		// infinite loop - this Player will ALWAYS timeout

		while (true);
	}
	public String toString() {
		return "Timeout Player Team: " + this.getTeam().getName() + " Side: " + this.getTeam().getSide();
	}
}
