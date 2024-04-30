package ctf2024.redTeam;

import ctf2024.Player;
import info.gridworld.grid.Location;

public class BeelinePlayer extends Player {

	public BeelinePlayer(Location startLocation) {
		super(startLocation);
	}

	public Location getMoveLocation() {
		int dir;
		if (this.hasFlag()) {
			return this.getMyTeam().getFlag().getLocation();
		} else {
			return getOtherTeam().getFlag().getLocation();
		}
	}
}
