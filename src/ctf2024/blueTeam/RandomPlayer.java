package ctf2024.blueTeam;

import ctf2024.Player;
import info.gridworld.grid.Location;

import java.util.List;

public class RandomPlayer extends Player {

	public RandomPlayer(Location startLocation) {
		super(startLocation);
	}

	public Location getMoveLocation() {
		List<Location> possibleMoveLocations = getGrid().getEmptyAdjacentLocations(getLocation()); 
		if (possibleMoveLocations.isEmpty()) return null;
		return possibleMoveLocations.get((int) (Math.random() * possibleMoveLocations.size()));
	}

}
