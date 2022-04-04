package ctf2022.teams.sample;

import ctf2022.AbstractPlayer;

import info.gridworld.grid.Location;

public class BeelinePlayer extends AbstractPlayer {

	public BeelinePlayer(Location startLocation) {
		super(startLocation);
	}

	public Location getMoveLocation() {
		int dir;
		if (hasFlag()) {
			dir = this.getLocation().getDirectionToward(getMyTeam().getFlag().getLocation());
		}
		else {
			dir = this.getLocation().getDirectionToward(getOtherTeam().getFlag().getLocation());
		}
		return this.getLocation().getAdjacentLocation(dir);
	}

}
