package ctf2024.blueTeam;

import ctf2024.Team;
import info.gridworld.grid.Location;

import java.awt.*;

public class BlueTeam extends Team {

	public BlueTeam() {
		super("Blue Team", null);
	}

	public void generateTeam() {
		// Add Players to left side only

		// If the Team is assigned to the right side, Players will automatically be added
		// to the corresponding Location mirrored around the center line

		// Add Players to Team at designated Locations
		super.addPlayer(new BeelinePlayer(new Location (4, 30)));
		super.addPlayer(new RandomPlayer(new Location(10, 30)));
		super.addPlayer(new RandomPlayer(new Location(15, 30)));
		super.addPlayer(new BeelinePlayer(new Location(21, 30)));
		super.addPlayer(new RandomPlayer(new Location(30, 30)));
		super.addPlayer(new RandomPlayer(new Location(34, 30)));
		super.addPlayer(new BeelinePlayer(new Location(41, 30)));
		super.addPlayer(new TimeoutPlayer(new Location(45, 30)));
	}}