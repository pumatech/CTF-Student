package ctf2019;

import javax.swing.JOptionPane;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Rock;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class Match {
	private static final int NUM_ROCKS = 75;
	
	private Team teamA, teamB;
	private Team winner;
	CtfWorld world;
	
	public Match(Team a, Team b, CtfWorld w) {
		teamA = a;
		teamB = b;
		world=w;
	}
	
	public void start() {
		BoundedGrid<Actor> grid = new BoundedGrid<>(50, 100);
		
		double randomNumber = Math.random();
		teamA.addTeamToGrid(grid, randomNumber < .5 ? 0 : 1);
		teamB.addTeamToGrid(grid, randomNumber < .5 ? 1 : 0);
		teamA.setOpposingTeam(teamB);
		teamB.setOpposingTeam(teamA);
		
		for (int i = 0; i < NUM_ROCKS; i++) {
			Location rockClumpLocation;
			do {
				rockClumpLocation = new Location((int) (Math.random() * (grid.getNumRows())), (int) (Math.random() * ((grid.getNumCols()) - 6)) + 3);
			} while (nearFlag(grid, rockClumpLocation) || grid.get(rockClumpLocation) != null);
			
			if (!nearPlayer(teamA, rockClumpLocation) && !nearPlayer(teamB, rockClumpLocation))
				new Rock().putSelfInGrid(grid, rockClumpLocation);
			for (int j = 0; j < 8; j++) {
				int randomDirection = (int) (Math.random() * 8) * Location.HALF_RIGHT;
				Location possibleRockLocation = rockClumpLocation.getAdjacentLocation(randomDirection);
				if (grid.isValid(possibleRockLocation) && !nearFlag(grid, possibleRockLocation) && grid.get(possibleRockLocation) == null)
					if (!nearPlayer(teamA, possibleRockLocation) && !nearPlayer(teamB, possibleRockLocation))
						new Rock().putSelfInGrid(grid, possibleRockLocation);
			}
		}
		
		world.setGrid(grid);
		
		if (teamA.getSide() == 0) {
			JOptionPane.showMessageDialog(null, teamA.getName() + " vs. " + teamB.getName());
		} else {
			JOptionPane.showMessageDialog(null, teamB.getName() + " vs. " + teamA.getName());
		}
		System.out.println("Playing game");
		
		world.show();
		
		while (!teamA.hasWon() && !teamB.hasWon()) {
		} // There might be a better way to do this
		
		winner = teamA.hasWon() ? teamA : teamB;
		JOptionPane.showMessageDialog(null, winner.getName() + " has won!");
		
	}
	
	public Team getWinner() {
		return winner;
	}
	
	private static boolean nearFlag(Grid<Actor> grid, Location loc) {
		for (int i = loc.getCol() - 5; i <= loc.getCol() + 5; i++) {
			for (int j = loc.getRow() - 5; j <= loc.getRow() + 5; j++) {
				Location newloc = new Location(j, i);
				if (grid.isValid(newloc) && (grid.get(newloc) instanceof Flag)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean nearPlayer(Team t, Location loc) {
		for (AbstractPlayer p : t.getPlayers()) {
			if (distance(p.getLocation(), loc) < 3)
				return true;
		}
		return false;
	}
	
	private double distance(Location loc1, Location loc2) {
		return Math.sqrt(Math.pow(loc1.getRow() - loc2.getRow(), 2) + Math.pow(loc1.getCol() - loc2.getCol(), 2));
	}
}
