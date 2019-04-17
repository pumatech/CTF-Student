package ctf2019;

import java.awt.Color;

import ctf2019.teams.sample.SampleTeam;

import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Rock;
import info.gridworld.grid.BoundedGrid;

public class SimpleRunner {
	
	public static void main(String[] args) {
		BoundedGrid<Actor> grid = new BoundedGrid<Actor>(50, 100);
		ActorWorld world = new ActorWorld(grid);
		
		Team a = new SampleTeam("Team 1", Color.RED);
		Team b = new SampleTeam("Team 2", Color.BLUE);
		a.addTeamToGrid(grid, 0);
		b.addTeamToGrid(grid, 1);
		a.setOpposingTeam(b);
		b.setOpposingTeam(a);	
		
		for (int i=0; i<50; i++) world.add(new Rock());
		

		
		world.show();
	}	
}
