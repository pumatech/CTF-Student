package ctf2022.teams.sample;

import java.awt.Color;

import ctf2022.Team;

import info.gridworld.grid.Location;

public class SampleTeam extends Team {

	public SampleTeam() {
		this("Sample Team", Color.DARK_GRAY);
	}

	public SampleTeam(Color color) {
		this("Sample Team", color);
	}
	
	public SampleTeam(String name, Color color) {
		super(name, color);
	}
	
	public void generateTeam() {
		addPlayer(new ProtectPlayer(new Location(5 + (int)(Math.random()*3 - 1), 30)));
		addPlayer(new BeelinePlayer(new Location(10 + (int)(Math.random()*3 - 1), 30)));
		addPlayer(new RandomPlayer(new Location(15 + (int)(Math.random()*3 - 1), 30)));
		addPlayer(new BeelinePlayer(new Location(20 + (int)(Math.random()*3 - 1), 30)));
		addPlayer(new BeelinePlayer(new Location(30 + (int)(Math.random()*3 - 1), 30)));
		addPlayer(new RandomPlayer(new Location(35 + (int)(Math.random()*3 - 1), 30)));
		addPlayer(new BeelinePlayer(new Location(40 + (int)(Math.random()*3 - 1), 30)));
		addPlayer(new ProtectPlayer(new Location(45 + (int)(Math.random()*3 - 1), 30)));
	}

}