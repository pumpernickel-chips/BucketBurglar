import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author Naomi Coakley
 */
public class GUI extends JPanel{
    private String name;
    private boolean[] playerJoined;
    private int h, w;
    private final static Font buttonFont = new Font("Hevetica", Font.BOLD, 18);
    private JFrame frame;
    private JPanel menu;
    private JPanel game;
    private JButton[] addPlayer;
    private static final Color[] playerColors = new Color[]{new Color(28, 129, 248), new Color(234, 57, 132), new Color(18, 155, 26), new Color(210, 108, 29)};


    /**
     * Default zero-args constructor, passes default title to complete constructor
     * */
    public GUI() {
        this("Untitled Game");
    }
    /**
     * Complete constructor
     * @param name name of the window
     * */
    public GUI(String name){
        super(new GridBagLayout(), true);
        this.name = name;
    }

    public void initializeGUI(int height, int width){
        this.h = height;
        this.w = width;

        playerJoined = new boolean[]{false, false, false, false};
        this.setMinimumSize(new Dimension(h, w));
        this.setBackground(new Color(43, 43, 44, 255));

        showMenu();

        frame = new JFrame(name);
        frame.setPreferredSize(new Dimension(h, w));
        frame.setContentPane(menu);
        frame.setVisible(true);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void linkInputs(List<JButton> inputs){
        addPlayer = new JButton[4];
        for (JButton input : inputs) {
            switch(input.getActionCommand()) {
                case "P1" :
                    addPlayer[0] = input;
                case "P2" :
                    addPlayer[1] = input;
                case "P3" :
                    addPlayer[2] = input;
                case "P4" :
                    addPlayer[3] = input;
            }
        }
    }
    public void showMenu(){
        menu = new JPanel(true);
        menu.setPreferredSize(new Dimension(h, w));
        menu.setMinimumSize(new Dimension(h, w));
        menu.setBackground(new Color(43, 43, 44, 255));
        menu.setLayout(new GridBagLayout());
        GridBagConstraints grd = new GridBagConstraints();




        int playerNum = 0;

        for(JButton b : addPlayer){
            playerNum++;
            b.setText("ENTER PLAYER " + playerNum);
            b.setBackground(playerNum == 1? Color.WHITE : Color.DARK_GRAY);
            b.setForeground(playerNum == 1? Color.DARK_GRAY : Color.LIGHT_GRAY);
            b.setFocusPainted(false);
            b.setBorder(BorderFactory.createLineBorder((playerNum == 1? playerColors[playerNum - 1] : Color.LIGHT_GRAY), 3, false));
            b.setFont(buttonFont);
        }
        //constraints applying to all addPlayer buttons
        grd.weighty = 0.5;
        grd.weightx = 0.5;
        grd.ipadx = 10;
        grd.ipady = 10;
        grd.insets = new Insets (5,5,5,5);
        grd.gridy = 2;

        grd.gridx = 1;
        menu.add(addPlayer[0], grd);
        grd.gridx = 3;
        menu.add(addPlayer[1], grd);
        grd.gridx = 5;
        menu.add(addPlayer[2], grd);
        grd.gridx = 7;
        menu.add(addPlayer[3], grd);
    }
    public void showMap(){}
    public boolean isPlayerJoined() {
        if(playerJoined[0]){
            return true;
        }
        return false;
    }
    public boolean isPlayerJoined(int playerNum) {
        return playerNum <= 4 && playerNum > 0 && this.playerJoined[playerNum - 1];
    }
    public void updatePlayerButtons(boolean joined, int playerNum) {
        this.playerJoined[playerNum-1] = joined;
        addPlayer[playerNum - 1].setBackground(joined? playerColors[playerNum - 1] : Color.WHITE);
        addPlayer[playerNum - 1].setForeground(joined? Color.WHITE : Color.DARK_GRAY);
        addPlayer[playerNum - 1].setBorder(BorderFactory.createLineBorder((joined? Color.WHITE : playerColors[playerNum - 1]), 3, false));
        addPlayer[playerNum - 1].setText(joined? ("PLAYER " + playerNum + " JOINED") : ("ENTER PLAYER " + playerNum));

        if(playerNum<4){
            addPlayer[playerNum].setBackground(joined? Color.WHITE : Color.DARK_GRAY);
            addPlayer[playerNum].setForeground(joined? Color.DARK_GRAY : Color.LIGHT_GRAY);
            addPlayer[playerNum].setBorder(BorderFactory.createLineBorder((joined? playerColors[playerNum] : Color.LIGHT_GRAY), 3, false));
        }
    }
}
