package ctf2024.blueTeam;

import ctf2024.CtfWorld;
import ctf2024.Player;
import info.gridworld.grid.Location;

public class BeelinePlayer extends Player {

	public BeelinePlayer(Location startLocation) {
		super(startLocation);
	}

	public Location getMoveLocation() {
		int dir;
		System.out.println(CtfWorld.getStepNum());
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
