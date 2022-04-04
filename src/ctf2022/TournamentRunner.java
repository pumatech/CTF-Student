package ctf2022;

import ctf2022.myTeam.MyTeam;
import ctf2022.teams.Angela.Angela;
import ctf2022.teams.Capturuccino.Capturuccino;
import ctf2022.teams.CostcoFoodCourt.CostcoFoodCourtTeam;
import ctf2022.teams.DOTDOTDOT.DOTDOTDOT;
import ctf2022.teams.ElectricBoogaloo.ElectricBoogaloo;
import ctf2022.teams.Emus.EmuTeam;
import ctf2022.teams.GoGo.GoGoTeam;
import ctf2022.teams.GrandeEscoobear.GrandeEscoobear;
import ctf2022.teams.Its_LeviOsa_not_LeviosA.Its_LeviOsa_not_LeviosA;
import ctf2022.teams.OrangeRedBread.OrangeRedBread;
import ctf2022.teams.Ox20B.Ox20B;
import ctf2022.teams.PerfectlyBalanced.PerfectlyBalanced;
import ctf2022.teams.TagTeam.TagTeam;
import ctf2022.teams.Thermometers.Thermometers;
import ctf2022.teams.blueCat.BlueCatTeam;
import ctf2022.teams.blueDevils.BlueDevilsTeam;
import ctf2022.teams.firebreathingrubberduckies.FireBreathingRubberDuckies;
import ctf2022.teams.hashbrowns.HashBrownTeam;
import ctf2022.teams.lostacobelljanitors.ActualTeam;
import ctf2022.teams.mothafunctionz.Mothafunctionz;
import ctf2022.teams.teamAlmondMillk.AlmondTeam;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class TournamentRunner {
    public static void main(String[] args) throws IOException {
        ArrayList<Team> teams = new ArrayList<Team>();

//        teams.add(new CostcoFoodCourtTeam());
//        teams.add(new MyTeam());
//        teams.add(new OrangeRedBread());
//        teams.add(new AlmondTeam());
//        teams.add(new Ox20B());
//        teams.add(new PerfectlyBalanced());
//        teams.add(new ActualTeam());
        teams.add(new FireBreathingRubberDuckies("FireBreathingRubberDuckies", Color.RED));
        teams.add(new GrandeEscoobear());
//        teams.add(new SampleTeam());
//        teams.add(new Angela());
//        teams.add(new BlueCatTeam());
//        teams.add(new BlueDevilsTeam());
//        teams.add(new Capturuccino());
//        teams.add(new DOTDOTDOT());
//        teams.add(new ElectricBoogaloo());
//        teams.add(new EmuTeam());
//        teams.add(new GoGoTeam());
//        teams.add(new HashBrownTeam());
//        teams.add(new Its_LeviOsa_not_LeviosA());
//        teams.add(new Mothafunctionz());
//        teams.add(new TagTeam());
//        teams.add(new Thermometers());

        Collections.shuffle(teams);

        CtfWorld world = new CtfWorld();

        Tournament tournament = new Tournament(teams, world);
        tournament.start();
        Team winner = tournament.getWinner();
        world.setMessage(winner.getName() + " won the tournament");
    }
}
