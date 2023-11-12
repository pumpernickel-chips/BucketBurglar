import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author Naomi Coakley
 */
public class GUI extends JFrame{
    public static Dimension screenSize;
    public static int h, w;
    private String name;
    private boolean[] playerJoined;
    private final static Font buttonFont = new Font("Hevetica", Font.BOLD, 18);
    private JPanel main, title, select, menu, game, level;
    private JButton[] joinButtons;
    private JButton[] menuControls;
    private static final Color[] playerColors = new Color[]{new Color(28, 129, 248), new Color(234, 57, 132), new Color(18, 155, 26), new Color(210, 108, 29)};
    private static final Color intelliJGray = new Color(43, 43, 44, 255);
    /**
     * Default zero-args constructor, passes default title to complete constructor
     * */
    public GUI() {
        this("Game Title Here", 800, 600);
    }
    /**
     * Complete constructor
     * @param title title of game
     * */
    public GUI(String title, int width, int height){
        super(title);
        this.name = title;

        w = width;
        h = height;
        screenSize = new Dimension(w, h);

        main = new JPanel(new GridBagLayout(), true);
        main.setBackground(intelliJGray);
        this.setContentPane(main);

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void initializeGUI(){
        title = showTitleBanner();
        select = showPlayerSelection();
        menu = showMenuBar();


        this.setBackground(intelliJGray);

        GridBagConstraints gC = new GridBagConstraints();

        gC.weightx = 1;
        //define unique gC.fill & gC.weighty, then add by row starting at 0
        gC.fill = GridBagConstraints.HORIZONTAL;
        gC.gridy = 0;
        gC.weighty = 0.06;
        main.add(title, gC);
        gC.fill = GridBagConstraints.BOTH;
        gC.weighty = 0.9;
        gC.gridy++;
        main.add(select, gC);
        gC.fill = GridBagConstraints.HORIZONTAL;
        gC.weighty = 0.04;
        gC.gridy++;
        main.add(menu, gC);
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
                case "restart":
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
        //tB.setPreferredSize(new Dimension(w, (int) (h*0.2)));
        //tB.setMinimumSize(new Dimension(w, (int) (h*0.2)));
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
        //pS.setPreferredSize(new Dimension(w, (int) (h*0.7)));
        //pS.setMinimumSize(new Dimension(w, (int) (h*0.7)));
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
            b.setBackground(playerNum == 1? Color.WHITE : Color.DARK_GRAY);
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
    public JPanel showMenuBar(){
        JPanel mB = new JPanel(true);
        //mB.setPreferredSize(new Dimension(w, (int) (h*0.1)));
        //mB.setMaximumSize(new Dimension(w, (int) (h*0.1)));
        mB.setBackground(intelliJGray);
        mB.setLayout(new GridBagLayout());
        GridBagConstraints mC = new GridBagConstraints();
        //constraints applying to all menuControls
        mC.gridx = 0;
        mC.gridy = 0;
        mC.ipadx = 20;
        mC.ipady = 20;
        mC.insets = new Insets (20,20,20,20);
        mC.anchor = GridBagConstraints.SOUTH;
        mC.fill = GridBagConstraints.HORIZONTAL;
        //add every 1 column starting at 0
        mC.weightx = 1;
        mB.add(Box.createHorizontalStrut(0), mC);
        for(JButton b : menuControls){
            b.setText(b.getActionCommand().toUpperCase());
            b.setBackground(Color.DARK_GRAY);
            b.setForeground(Color.LIGHT_GRAY);
            b.setFocusPainted(false);
            b.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 4, false));
            b.setFont(buttonFont);
            mC.gridx++;
            mC.weightx = 0.6;
            mB.add(Box.createHorizontalStrut(0), mC);
            mC.gridx++;
            mC.weightx = 0;
            mB.add(b, mC);
            mC.weightx = 0.6;
        }

        mC.gridx++;
        mB.add(Box.createHorizontalStrut(0), mC);
        mC.weightx = 1;
        mC.gridx++;
        mB.add(Box.createHorizontalStrut(0), mC);
        return mB;
    }
    public void showGameMap(){
    }
    public boolean isPlayerJoined() {
        if(playerJoined[0]){
            return true;
        }
        return false;
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
        joinButtons[playerNum - 1].setBackground(joined? playerColors[playerNum - 1] : Color.WHITE);
        joinButtons[playerNum - 1].setForeground(joined? Color.WHITE : Color.DARK_GRAY);
        joinButtons[playerNum - 1].setBorder(BorderFactory.createLineBorder((joined? Color.WHITE : playerColors[playerNum - 1]), 4, false));
        joinButtons[playerNum - 1].setText(joined? ("PLAYER " + playerNum + " JOINED") : ("ENTER PLAYER " + playerNum));

        if(playerNum<4){
            joinButtons[playerNum].setBackground(joined? Color.WHITE : Color.DARK_GRAY);
            joinButtons[playerNum].setForeground(joined? Color.DARK_GRAY : Color.LIGHT_GRAY);
            joinButtons[playerNum].setBorder(BorderFactory.createLineBorder((joined? playerColors[playerNum] : Color.LIGHT_GRAY), 4, false));
        }
    }
}
