package ctf2024.sampleTeam;

import java.awt.Color;

import ctf2024.Team;

import info.gridworld.grid.Location;

public class SampleTeam extends Team {
	
	public SampleTeam(String name, Color color) {
		super(name, color);
	}
	
	public void generateTeam() {
		super.addPlayer(new BeelinePlayer(new Location(5 + (int) (Math.random() * 3 - 1), 30)));
		super.addPlayer(new RandomPlayer(new Location(10 + (int) (Math.random() * 3 - 1), 30)));
		super.addPlayer(new RandomPlayer(new Location(15 + (int) (Math.random() * 3 - 1), 30)));
		super.addPlayer(new BeelinePlayer(new Location(20 + (int) (Math.random() * 3 - 1), 30)));
		super.addPlayer(new RandomPlayer(new Location(30 + (int) (Math.random() * 3 - 1), 30)));
		super.addPlayer(new RandomPlayer(new Location(35 + (int) (Math.random() * 3 - 1), 30)));
		super.addPlayer(new RandomPlayer(new Location(40 + (int) (Math.random() * 3 - 1), 30)));
		super.addPlayer(new BeelinePlayer(new Location(45 + (int) (Math.random() * 3 - 1), 30)));
	}
}