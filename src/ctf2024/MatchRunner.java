package ctf2024;

import ctf2024.sampleTeam.SampleTeam;

import java.awt.*;

public class MatchRunner {

    public static void main(String[] args) {

        Team a = new SampleTeam("Red Team", Color.RED);
        Team b = new SampleTeam("Blue Team", Color.BLUE);

        a.setOpposingTeam(b);
        b.setOpposingTeam(a);

        Match match = new Match(a, b, 10);
        match.start();
    }
}
