package ctf2019;

import ctf2019.myTeam.MyTeam;
import ctf2019.teams.Angela.Angela;
import ctf2019.teams.Capturuccino.Capturuccino;
import ctf2019.teams.CostcoFoodCourt.CostcoFoodCourtTeam;
import ctf2019.teams.DOTDOTDOT.DOTDOTDOT;
import ctf2019.teams.ElectricBoogaloo.ElectricBoogaloo;
import ctf2019.teams.Emus.EmuTeam;
import ctf2019.teams.GoGo.GoGoTeam;
import ctf2019.teams.GrandeEscoobear.GrandeEscoobear;
import ctf2019.teams.Its_LeviOsa_not_LeviosA.Its_LeviOsa_not_LeviosA;
import ctf2019.teams.OrangeRedBread.OrangeRedBread;
import ctf2019.teams.Ox20B.Ox20B;
import ctf2019.teams.PerfectlyBalanced.PerfectlyBalanced;
import ctf2019.teams.TagTeam.TagTeam;
import ctf2019.teams.Thermometers.Thermometers;
import ctf2019.teams.blueCat.BlueCatTeam;
import ctf2019.teams.blueDevils.BlueDevilsTeam;
import ctf2019.teams.firebreathingrubberduckies.FireBreathingRubberDuckies;
import ctf2019.teams.hashbrowns.HashBrownTeam;
import ctf2019.teams.lostacobelljanitors.ActualTeam;
import ctf2019.teams.mothafunctionz.Mothafunctionz;
import ctf2019.teams.sample.SampleTeam;
import ctf2019.teams.teamAlmondMillk.AlmondTeam;

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
