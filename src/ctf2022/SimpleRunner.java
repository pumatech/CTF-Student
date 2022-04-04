package ctf2022;

import ctf2022.teams.GoGo.GoGoTeam;
import ctf2022.teams.sample.SampleTeam;

import java.awt.*;

public class SimpleRunner {

    public static void main(String[] args) {

        Team a = new SampleTeam("Team 1", Color.RED);
        Team b = new GoGoTeam();
        CtfWorld world = new CtfWorld(a, b);

        a.setOpposingTeam(b);
        b.setOpposingTeam(a);

        Match match = new Match(a, b, world, 10);
        match.start();
    }
}
