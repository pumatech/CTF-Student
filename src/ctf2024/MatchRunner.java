package ctf2024;

import ctf2024.sampleTeam.SampleTeam;

import java.awt.*;

/**
 * Class builds two Teams and starts a Match between them with few Rocks
 */
public class MatchRunner {

    public static void main(String[] args) {

        // Both Teams are built for the "left side"
        // The Team assigned to the "right side" will automatically be mirrored when placed

        Team a = new SampleTeam("Red Team", Color.RED);
        Team b = new SampleTeam("Blue Team", Color.BLUE);

        a.setOpposingTeam(b);
        b.setOpposingTeam(a);

        // Build a Match with limited number of rock clumps - in the "real" competition there will be 75
        Match match = new Match(a, b, 10);

        // start the match
        match.start();
    }
}
