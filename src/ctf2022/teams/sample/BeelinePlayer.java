package ctf2022.teams.sample;

import ctf2022.AbstractPlayer;

import info.gridworld.grid.Location;

public class BeelinePlayer extends AbstractPlayer {

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
