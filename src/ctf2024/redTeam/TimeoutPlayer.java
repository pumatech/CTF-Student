package ctf2024.redTeam;

import ctf2024.Player;
import info.gridworld.grid.Location;

public class TimeoutPlayer extends Player {

	public TimeoutPlayer(Location startLocation) {
		super(startLocation);
	}

	public Location getMoveLocation() {

		// infinite loop - this Player will ALWAYS timeout

		while (true);
	}

}
