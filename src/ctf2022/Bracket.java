package ctf2022;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Bracket extends JPanel {
    private static final long serialVersionUID = 1L;

    private ArrayList<Team> teams;
    private ArrayList<Integer> xLocs;
    private ArrayList<Integer> yLocs;
    private ArrayList<String> teamNames;
    int gameNum;

    public Bracket(ArrayList<Team> t) {
        teams = t;
        gameNum = 0;
        xLocs = new ArrayList<>();
        yLocs = new ArrayList<>();
        teamNames = new ArrayList<>();
        int width = 150;
        int height = 40;
        int x = 25;
        int n = teams.size();
        while (n > 1) {
            int y = 400 - ((height + 10) * n) / 2;
            for (int i = 0; i < n; i++) {
                xLocs.add(x);
                yLocs.add(y + i * height + i * 10 + height / 2);
            }
            x += width + 20;
            if (n % 2 == 1) {
                n++;
            }
            n = n / 2;
        }
        xLocs.add(x);
        yLocs.add(400 - height / 2 - 5 + height / 2);
    }

    public Dimension getPreferredSize() {
        return new Dimension(1000, 1200);
    }

    public Dimension getMinimumSize() {
        return new Dimension(1000, 1200);
    }

    public Dimension getMaximumSize() {
        return new Dimension(1000, 1200);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Consolas", Font.PLAIN, 14));
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 800, 800);
        g.setColor(Color.BLACK);

        int width = 150;
        int height = 40;
        for (int i = 0; i < xLocs.size(); i++) {
            g.fillRect(xLocs.get(i), yLocs.get(i), width, height);
        }

        g.setColor(Color.WHITE);

        for (int i = 0; i < teamNames.size(); i++) {
            g.drawString(teamNames.get(i), xLocs.get(i) + 10, yLocs.get(i) + height / 2);
        }
    }

    public void addWinner(String teamName) {
        teamNames.add(teamName);
    }

}
