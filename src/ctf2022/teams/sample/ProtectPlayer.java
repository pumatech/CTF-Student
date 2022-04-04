package ctf2022.teams.sample;

import ctf2022.AbstractPlayer;
import info.gridworld.grid.Location;

public class ProtectPlayer extends AbstractPlayer {

	public ProtectPlayer(Location startLocation) {
		super(startLocation);
	}

	public Location getMoveLocation() {
		// moves towards own Flag
		int dir;
		dir = this.getLocation().getDirectionToward(getMyTeam().getFlag().getLocation());
		return this.getLocation().getAdjacentLocation(dir);
	}

}
