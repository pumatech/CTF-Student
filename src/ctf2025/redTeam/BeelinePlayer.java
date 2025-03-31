package ctf2025.redTeam;

import ctf2025.Player;
import info.gridworld.grid.Location;

public class BeelinePlayer extends Player {

	public BeelinePlayer(Location startLocation) {
		super(startLocation);
	}

	public Location getMoveLocation() {
		System.out.println("Blue get");
		int dir;
		if (this.hasFlag()) {
			return this.getMyTeam().getFlag().getLocation();
		} else {
			return getOtherTeam().getFlag().getLocation();
		}
	}

	public String toString() {
		return "Beeline Player Team: " + this.getTeam().getName() + " Side: " + this.getTeam().getSide();
	}

}
