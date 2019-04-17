package ctf2019;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public abstract class AbstractPlayer extends Actor {
	
	private static final int MOVE = 1;
	private static final int MOVE_ON_OPPONENT_SIDE = 1;
	private static final int CAPTURE = 1;
	private static final int TAG = 1;
	private static final int CARRY = 1;
	
	private static final int TURNTIME = 100; // The time the whole team has, in milliseconds. Each player is individually capped on time, not the team
	
	private Team team;
	private boolean hasFlag;
	private Location startLocation;
	private int tagCoolDown;
	
	public AbstractPlayer(Location startLocation) {
		this.startLocation = startLocation;
	}
	
	public final void act() {
		try {
			if (team.hasWon() || team.getOpposingTeam().hasWon()) {
				if (team.hasWon()) {
					if (hasFlag)
						setColor(Color.MAGENTA);
					else
						setColor(Color.YELLOW);
				}
				return;
			}
			
			if (tagCoolDown > 0) {
				tagCoolDown--;
				if (tagCoolDown == 0) {
					setColor(team.getColor());
				}
			} else {
				processNeighbors();
				final Location loc = new Location(-1, -1);
				Thread getMoveLocationThread = new Thread() {
					@Override
					public void run() {
						Location l = getMoveLocation();
						loc.setCol(l.getCol());
						loc.setRow(l.getRow());
					}
				};
				getMoveLocationThread.start();
				long timeLimit = TURNTIME / team.getPlayers().size();
				long startTime = System.currentTimeMillis();
				while (!this.getGrid().isValid(loc) && System.currentTimeMillis() - startTime < timeLimit) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						
					}
				}
				if (!getMoveLocationThread.isInterrupted()) {
					getMoveLocationThread.interrupt();
				}
				makeMove(!this.getGrid().isValid(loc) ? this.getLocation() : loc); // fixed behavior
			}
		} catch (Exception e) {
			System.err.println("Player " + this + " has generated the runtime exception: " + e);
		}
	}
	
	private void processNeighbors() {
		List<Location> neighborLocations = getGrid().getOccupiedAdjacentLocations(getLocation());
		for (int i = neighborLocations.size() - 1; i >= 0; i--) {
			Actor neighbor = getGrid().get(neighborLocations.get(i));
			if (!(neighbor instanceof AbstractPlayer) || ((AbstractPlayer) neighbor).team.equals(team)) {
				neighborLocations.remove(i);
				if (neighbor instanceof Flag && !((Flag) neighbor).getTeam().equals(team)) {
					hasFlag = true;
					setColor(Color.YELLOW);
					team.getOpposingTeam().getFlag().pickUp(this);
					team.addScore(CAPTURE);
					team.addPickUp();
				}
			}
		}
		if (team.onSide(getLocation())) {
			Collections.shuffle(neighborLocations);
			for (Location neighborLocation : neighborLocations) {
				if (team.onSide(neighborLocation)) {
					Actor neighbor = getGrid().get(neighborLocation);
					if (((AbstractPlayer) neighbor).hasFlag() || Math.random() < (1. / neighborLocations.size())) {
						((AbstractPlayer) neighbor).tag();
						team.addScore(TAG);
						team.addTag();
					}
				}
			}
		}
	}
	
	private void makeMove(Location loc) {
		if (loc == null || loc == getLocation()) {
			loc = getLocation();
		} else {
			if (team.onSide(getLocation()) && getGrid().get(team.getFlag().getLocation()) instanceof Flag && team.nearFlag(getLocation())) {
				int dir = getLocation().getDirectionToward(team.getFlag().getLocation()) + Location.HALF_CIRCLE;
				loc = getLocation().getAdjacentLocation(dir);
				while (getGrid().get(loc) != null) {
					System.out.println("Relocation from Flag failed. " + loc + "is occupied");
					loc = loc.getAdjacentLocation(dir);
				}
			} else {
				loc = getLocation().getAdjacentLocation(getLocation().getDirectionToward(loc));
			}
			if (getGrid().isValid(loc) && getGrid().get(loc) == null) {
				moveTo(loc);
				if (team.onSide(getLocation()))
					team.addScore(MOVE);
				else
					team.addScore(MOVE_ON_OPPONENT_SIDE);
					team.addOffensiveMove();
				if (this.hasFlag) {
					team.addScore(CARRY);
				}
			}
		}
	}
	
	public abstract Location getMoveLocation();
	
	private void tag() {
		Location oldLoc = getLocation();
		Location nextLoc;
		do {
			nextLoc = team.adjustForSide(new Location((int) (Math.random() * getGrid().getNumRows()), 0), getGrid());
		} while (getGrid().get(nextLoc) != null);
		moveTo(nextLoc);
		tagCoolDown = 10;
		if (hasFlag) {
			team.getOpposingTeam().getFlag().putSelfInGrid(getGrid(), oldLoc);
			hasFlag = false;
		}
		setColor(Color.BLACK);
	}
	
	protected final void putSelfInGridProtected(Grid<Actor> grid, Location loc) {
		if (getGrid() != null)
			super.removeSelfFromGrid();
		hasFlag = false;
		tagCoolDown = 0;
		setColor(team.getColor());
		super.putSelfInGrid(grid, loc);
	}
	
	public final void removeSelfFromGrid() {
		System.err.println("Someone has cheated and tried to remove a player from the grid");
	}
	
	protected final void setTeam(Team team) {
		this.team = team;
		setColor(team.getColor());
	}
	
	protected final void setStartLocation(Location startLocation) {
		this.startLocation = startLocation;
	}
	
	public final boolean hasFlag() {
		return hasFlag;
	}
	
	protected final Location getStartLocation() {
		return startLocation;
	}
	
	public final Team getTeam() {
		return team;
	}
	
	public Location getLocation() {
		return new Location(super.getLocation().getRow(), super.getLocation().getCol());
	}
}
