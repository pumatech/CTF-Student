package ctf2025;

import ctf2025.seedTeam.SeedTeam;
import teams2024.blueTeamTucker.BlueTeamTwo;

/**
 * Class builds two Teams and starts a Match between them with few Rocks
 */
public class MatchRunner {

    public static void main(String[] args) {

        // Both Teams are built for the "left side"
        // The Team assigned to the "right side" will automatically be mirrored when placed

        Team a = new BlueTeamTwo();
        Team b = new SeedTeam();

        a.setOpposingTeam(b);
        b.setOpposingTeam(a);

        // Build a Match with limited number of rock clumps - in the "real" competition there will be 75
        Match match = new Match(a, b, 75);

        // start the match
        match.start();
    }
}
