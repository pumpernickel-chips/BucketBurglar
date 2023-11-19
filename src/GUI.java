import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * @author Naomi Coakley
 */
public class GUI extends JFrame{
    public static Dimension screenSize;
    public static int w, h;
    public static Double scale;
    public static Point2D windowOrigin;
    private String name;
    private static final int maxPlayers = 4;
    private boolean[] playerJoined;
    private boolean inGame;
    private Font buttonFont;
    private JPanel main, title, select, menu, game, scoreboard, retry;
    private JLabel[] scores, healths;
    private int[] scoreVals;
    private double[] healthVals;
    private FloorPlan level;
    private JButton[] joinButtons;
    private JButton[] menuControls;
    public static final Color[] playerColors = new Color[]{new Color(28, 129, 248), new Color(234, 57, 132), new Color(18, 155, 26), new Color(210, 108, 29)};
    public static final Color intelliJGray = new Color(43, 43, 44, 255);
    /**
     * Default zero-args constructor, passes default title to complete constructor
     * */
    public GUI() {this("Game Title Here", 800, 600);}
    /**
     * Complete constructor
     * @param title title of game
     * */
    public GUI(String title, int width, int height){
        super(title);
        this.name = title;
        this.inGame = true;

        w = width;
        h = height;
        screenSize = new Dimension(w, h);
        scale = h*.00069;//graphics were originally scaled for a 1440p monitor, thus .00069 ≈ 1/1440 and multiplying users' monitor height by it outputs a factor by which to scale various dimensions by accurately regardless of screen size.
        buttonFont = new Font("Hevetica", Font.BOLD, ((int) (20*scale)));
        //System.out.println(screenSize);
        windowOrigin = new Point2D.Double(w*.5,h*.5);

        main = new JPanel(new GridBagLayout(), true);
        main.setBackground(intelliJGray);
        this.setBackground(intelliJGray);
        this.setContentPane(main);

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void showMainMenu(){
        title = showTitleBanner();
        select = showPlayerSelection();
        menu = showNavigationBar(false);

        GridBagConstraints mC = new GridBagConstraints();

        mC.weightx = 1;
        //define unique gC.fill & gC.weighty, then add by row starting at 0
        mC.fill = GridBagConstraints.HORIZONTAL;
        mC.gridy = 0;
        mC.weighty = 0.06;
        main.add(title, mC);
        mC.fill = GridBagConstraints.BOTH;
        mC.weighty = 0.9;
        mC.gridy++;
        main.add(select, mC);
        mC.fill = GridBagConstraints.HORIZONTAL;
        mC.weighty = 0.04;
        mC.gridy++;
        main.add(menu, mC);
        revalidate();
    }
    public void linkInputs(List<JButton> inputs){
        joinButtons = new JButton[4];
        menuControls = new JButton[3];
        for (JButton input : inputs) {
            switch(input.getActionCommand()) {
                case "P1" :
                    joinButtons[0] = input;
                    break;
                case "P2" :
                    joinButtons[1] = input;
                    break;
                case "P3" :
                    joinButtons[2] = input;
                    break;
                case "P4" :
                    joinButtons[3] = input;
                    break;
                case "start":
                    menuControls[0] = input;
                    break;
                case "new game":
                    menuControls[1] = input;
                    break;
                case "quit":
                    menuControls[2] = input;
                    break;
            }
        }
    }
    public JPanel showTitleBanner(){
        JPanel tB = new JPanel(true);
        tB.setBackground(intelliJGray);
        tB.setLayout(new GridBagLayout());
        JLabel tL = new JLabel(name.toUpperCase(), SwingConstants.CENTER);
        tL.setFont(new Font ("Helvetica", Font.BOLD, w/name.length()));
        tL.setForeground(Color.LIGHT_GRAY);
        tB.add(tL,
                new GridBagConstraints(0, 0, 1, 1, 1, 0.5, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets (10,10,10,10), 25, 25));

        return tB;
    }
    public JPanel showPlayerSelection(){
        JPanel pS = new JPanel(true);
        pS.setBackground(intelliJGray);
        pS.setLayout(new GridBagLayout());
        GridBagConstraints pC = new GridBagConstraints();
        //constraints applying to all joinButtons
        pC.weighty = 0.5;
        pC.weightx = 0.5;
        pC.gridx = 0;
        pC.gridy = 0;
        pC.ipadx = 10;
        pC.ipady = 10;
        pC.anchor = GridBagConstraints.NORTH;
        pC.fill = GridBagConstraints.NONE;
        pC.insets = new Insets (5,5,5,5);
        int playerNum = 0;
        this.playerJoined = new boolean[]{false, false, false, false};
        for(JButton b : joinButtons){
            playerNum++;
            b.setText("ENTER PLAYER " + playerNum);
            b.setBackground(playerNum == 1? Color.LIGHT_GRAY : Color.DARK_GRAY);
            b.setForeground(playerNum == 1? Color.DARK_GRAY : Color.LIGHT_GRAY);
            b.setFocusPainted(false);
            b.setBorder(BorderFactory.createLineBorder((playerNum == 1? playerColors[playerNum - 1] : Color.LIGHT_GRAY), 4, false));
            b.setFont(buttonFont);
            //add every 2 columns starting at 1
            pC.gridx += (playerNum == 1? 1 : 2);
            pS.add(b, pC);
        }
        return pS;
    }
    public JPanel showNavigationBar(boolean inGame){
        JPanel nB = new JPanel(true);
        nB.setBackground(intelliJGray);
        nB.setLayout(new GridBagLayout());
        GridBagConstraints nC = new GridBagConstraints();
        //constraints applying to all menuControls
        nC.gridx = 0;
        nC.gridy = 0;
        nC.ipadx = 20;
        nC.ipady = 20;
        nC.insets = new Insets (20,20,20,20);
        nC.anchor = GridBagConstraints.SOUTH;
        nC.fill = GridBagConstraints.HORIZONTAL;
        //add every 1 column starting at 0
        nC.weightx = 1;
        nB.add(Box.createHorizontalStrut(0), nC);
        for(JButton b : menuControls){
            if(!inGame && !b.getActionCommand().equals("new game") || inGame && !b.getActionCommand().equals("start")) {
                b.setText(b.getActionCommand().toUpperCase());
                b.setBackground(Color.DARK_GRAY);
                b.setForeground(Color.LIGHT_GRAY);
                b.setFocusPainted(false);
                b.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 4, false));
                b.setFont(buttonFont);
                nC.gridx++;
                nC.weightx = 0.6;
                nB.add(Box.createHorizontalStrut(0), nC);
                nC.gridx++;
                nC.weightx = 0;
                nB.add(b, nC);
                nC.weightx = 0.6;
            }
        }
        nC.gridx++;
        nB.add(Box.createHorizontalStrut(0), nC);
        nC.weightx = 1;
        nC.gridx++;
        nB.add(Box.createHorizontalStrut(0), nC);
        return nB;
    }
    public void showGameSession(FloorPlan gameMap){
        this.inGame = true;
        this.level = gameMap;
        this.retry = showNavigationBar(true);
        this.game = new JPanel(new GridBagLayout(), true);
        game.setBackground(intelliJGray);
        GridBagConstraints gC = new GridBagConstraints();

        this.scoreboard = new JPanel(new GridBagLayout(), true);
        scoreboard.setOpaque(false);
        scoreboard.setBounds((int)FloorPlan.hallNodes[FloorPlan.NWc].getX()+48,(int)FloorPlan.hallNodes[FloorPlan.NWc].getY()+48,FloorPlan.getHall().width-96, FloorPlan.getHall().height-96);
        level.add(scoreboard);

        gC.weightx = 1;
        gC.gridy = 0;
        gC.weighty = 0.96;
        gC.fill = GridBagConstraints.BOTH;
        game.add(level, gC);

        gC.fill = GridBagConstraints.HORIZONTAL;
        gC.weighty = 0.04;
        gC.gridy++;
        game.add(retry, gC);

        this.setContentPane(game);
        this.revalidate();
    }
    public void updateScoreboard(int totalTimeElapsed, String[] playerKeys){
        if(totalTimeElapsed < 30 && level.isSimulating()){
            GridBagConstraints sbC = new GridBagConstraints(0, 0, 1, 1, 0.5, 0.125,
                                                            GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
                                                            new Insets (10,10,10,10), 25, 25);
            scoreboard.setOpaque(false);
            scores = new JLabel[maxPlayers];
            healths = new JLabel[maxPlayers];
            scoreVals = new int[]{0,0,0,0};
            double max = ((Player) (HashGame.entities.get(playerKeys[0]))).getMaxHealth();
            healthVals = new double[maxPlayers];
            for(int i = 0; i < maxPlayers; i++){
                Player p = i < playerKeys.length? (Player) HashGame.entities.get(playerKeys[i]) : null;
                scores[i] = new JLabel(p != null? p.getName().toUpperCase() + ": ----" : "PLAYER NOT JOINED", SwingConstants.LEADING);
                scores[i].setForeground(p != null? p.getPlayerColor() : new Color(43, 43, 44, 0));
                scores[i].setFont(buttonFont.deriveFont(buttonFont.getSize()*1.8f));
                scoreboard.add(scores[i], sbC);
                sbC.gridy++;
                healths[i] = new JLabel(p != null? "HEALTH: " + healthVals[i] + "%" : "PLAYER " + i + " IS DEAD", SwingConstants.LEADING);
                healths[i].setForeground(p != null? p.getPlayerColor() : new Color(43, 43, 44, 0));
                healths[i].setFont(buttonFont.deriveFont(buttonFont.getSize()*1.2f));
                scoreboard.add(healths[i], sbC);
                sbC.gridy++;
            }
        }
        inGame = false;
        for(int i = 0; i < maxPlayers; i++) {
            Player p = i < playerKeys.length ? (Player) HashGame.entities.get(playerKeys[i]) : null;
            if(p != null){
                if(scoreVals[i] < p.getCurrentScore()) {
                    scoreVals[i] += 11;
                    if(scoreVals[i] > p.getCurrentScore()) scoreVals[i] = p.getCurrentScore();
                }
                if(healthVals[i] > p.getCurrentHealth()) {
                    healthVals[i] -= 3;
                    if(healthVals[i] < p.getCurrentHealth()) healthVals[i] = p.getCurrentHealth();
                }
                scores[i].setText(p.getName().toUpperCase() + ": " + (scoreVals[i] > 0? "$" + scoreVals[i] : "- - - -"));
                healths[i].setText("HEALTH: " + ((int) (p.getCurrentHealth())) + "%");
                if(p.getCurrentHealth() < (p.getMaxHealth()*.3)) healths[i].setForeground(Color.RED);
                if(!p.isFinished()) inGame = true;
            }else if(!scores[i].getText().equals("PLAYER NOT JOINED") && !healths[i].getText().equals("PLAYER " + i + " IS DEAD")){
                scoreVals[i] *= .5;
                healths[i].setForeground(Color.RED);
                healths[i].setText("PLAYER " + (i+1) + " WAS KILLED BY TRAPS");
            }
        }
        if(!inGame){
            int bestScore = 0;
            Deque<Integer> ranking = new ArrayDeque<>();
            for(int i = 0; i < maxPlayers; i++) {
                Player p = i < playerKeys.length ? (Player) HashGame.entities.get(playerKeys[i]) : null;
                if (p != null) {
                    bestScore = Math.max(p.getCurrentScore(), bestScore);
                    if(bestScore == p.getCurrentScore()) ranking.push(i);
                }
            }
            int winnerIndex = ranking.poll();
            for(int i = 0; i < maxPlayers; i++) {
                Player p = i < playerKeys.length ? (Player) HashGame.entities.get(playerKeys[i]) : null;
                if (p != null && i == winnerIndex) {
                    scores[i].setText(p.getName().toUpperCase() + " IS THE WINNER! ");
                    healths[i].setText("ESCAPED WITH $" + scoreVals[i] + " IN TREASURE");
                    healths[i].setForeground(p.getPlayerColor());
                }else{
                    scores[i].setForeground(new Color(43, 43, 44, 0));
                    if(p != null){
                        healths[i].setForeground(p.getPlayerColor());
                        healths[i].setText(p.getName().toUpperCase() + " ESCAPED WITH $" + scoreVals[i] + " IN TREASURE");
                    }
                }
            }
        }
        revalidate();
        repaint();
    }
    public void advanceFrame(String[] playerKeys){
        Deque<String> keys = new ArrayDeque<String>();
        for(String key : playerKeys) if ((Player)(HashGame.entities.get(key)) != null) keys.push(key);
        level.setPlayerKeys(keys);
        level.setSimulating(true);
        repaint();
    }
    public int getNumPlayersJoined(){
        int playerCount = 0;
        for(boolean j : playerJoined){
            if(j) playerCount++;
        }
        return playerCount;
    }
    public boolean isPlayerJoined(int playerNum) {
        return playerNum <= 4 && playerNum > 0 && this.playerJoined[playerNum - 1];
    }
    public void updateJoinButtons(boolean joined, int playerNum) {
        this.playerJoined[playerNum-1] = joined;
        joinButtons[playerNum - 1].setBackground(joined? playerColors[playerNum - 1] : Color.LIGHT_GRAY);
        joinButtons[playerNum - 1].setForeground(joined? Color.WHITE : Color.DARK_GRAY);
        joinButtons[playerNum - 1].setBorder(BorderFactory.createLineBorder((joined? Color.WHITE : playerColors[playerNum - 1]), 4, false));
        joinButtons[playerNum - 1].setText(joined? ("PLAYER " + playerNum + " JOINED") : ("ENTER PLAYER " + playerNum));

        if(playerNum<4){
            joinButtons[playerNum].setBackground(joined? Color.LIGHT_GRAY : Color.DARK_GRAY);
            joinButtons[playerNum].setForeground(joined? Color.DARK_GRAY : Color.LIGHT_GRAY);
            joinButtons[playerNum].setBorder(BorderFactory.createLineBorder((joined? playerColors[playerNum] : Color.LIGHT_GRAY), 4, false));
        }
    }
}
