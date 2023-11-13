import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * The HashGame class represents a game with players, treasures, and a floor plan.
 * It implements the ActionListener interface to handle GUI events.
 *
 * @author Naomi Coakley
 */
public class HashGame implements ActionListener {
    private String gameName;
    private int width, height;
    public static final int STAY = 0, NORTH = 1, EAST = 2, SOUTH = 3, WEST = 4, N_EAST = 5, N_WEST = 6, S_EAST = 7, S_WEST = 8;
    public static final int[] DIRECTIONS = new int[]{0, 1 ,2 , 3, 4, 5, 6, 7, 8};
    private GUI gui;
    private EntityTable entities;
    private FloorPlan level;
    private Iterator<Map.Entry<Integer, GameEntity>> iterEnt;

    /**
     * Constructs a new HashGame with the default name "Untitled" and
     * initializes the game.
     */
    public HashGame(){
        this("Untitled");
    }

    /**
     * Constructs a new HashGame with the specified name and initializes the
     * game.
     *
     * @param gameName The name of the game.
     */
    public HashGame(String gameName){
        this.gameName = gameName;
        this.width = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.height = Toolkit.getDefaultToolkit().getScreenSize().height;
        startUp();
    }

    /**
     * The main method to launch the HashGame application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args){
            String gameName = "Untitled Game About Stealing Treasure";
            HashGame test = new HashGame(gameName);
    }

    /**
     * Initializes the game components and GUI.
     */
    public void startUp(){
        this.gui = new GUI(this.gameName, width, height);
        this.entities = new EntityTable();
        this.iterEnt = entities.entrySet().iterator();
        this.gui.linkInputs(initializeInputs());
        this.gui.initializeGUI();
    }

    /**
     * Sets paths for all players in the game.
     */
    public void setPaths() {
        while(iterEnt.hasNext()){
            GameEntity e = iterEnt.next().getValue();
            if(e instanceof Player){
                Player p = (Player) e;
                p.decidePath(entities.getRoomCount(), entities);
                System.out.println(p.getRoomKeys());
            }
        }
    }

    /**
     * Sets booby traps for all treasures in the game.
     */
    public void trapTreasures(){
        while(iterEnt.hasNext()){
            GameEntity t = iterEnt.next().getValue();
            if(t instanceof Treasure){
                ((Treasure) t).setBoobyTrapped(true);//method to be written in Treasure
            }
        }
    }

    /**
     * Creates players for the game.
     */
    public void createPlayers(){
        // Implementation to be added.
    }

    /**
     * Moves a player in the specified direction.
     *
     * @param posX The X-coordinate of the player's current position.
     * @param posY The Y-coordinate of the player's current position.
     * @param direction The direction in which the player should move.
     */
    public void movePlayer(int posX, int posY, int direction){
        // Implementation to be added.
    }

    /**
     * Initializes the game map, including players, rooms, hallways, and
     * treasures.
     */
    public void initializeMap(){
        Room.maxSize = new Dimension (320, 160);
        FloorPlan.hallCircuit = new Dimension((int)(0.8*GUI.h), (int)(.75*GUI.h)-(2*Room.maxSize.height));

        for(int i = 0; i < gui.getNumPlayersJoined(); i++){
            Player p = new Player("Player " + (i+1), 3);
            entities.put(((Player) p).hashCode(), p);
        }

        this.level = new FloorPlan(entities.getPlayerCount() + 3, entities);
        level.buildLevel();
        trapTreasures();
        setPaths();
    }

    /**
     * Initializes input buttons for the GUI.
     *
     * @return A list of JButton objects representing input buttons.
     */
    public List<JButton> initializeInputs(){
        JButton addP1 = new JButton(), addP2 = new JButton(), addP3 = new JButton(), addP4 = new JButton(),
                start = new JButton(), restart = new JButton(), quit = new JButton();
        List<JButton> inputs = new ArrayList<JButton>(Arrays.asList(addP1, addP2, addP3, addP4, start, restart, quit));
        for(JButton b : inputs){
            b.addActionListener(this);
        }
        //add players
        addP1.setActionCommand("P1");
        addP2.setActionCommand("P2");
        addP3.setActionCommand("P3");
        addP4.setActionCommand("P4");
        //start, quit, retry
        start.setActionCommand("start");
        restart.setActionCommand("restart");
        quit.setActionCommand("quit");
        return inputs;
    }

    /**
     * Action listener method for all the event listeners of the GUI.
     * This method handles the buttons clicks on the GUI.
     * @param e Action event passed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "P1":
                if(!gui.isPlayerJoined(1) && !gui.isPlayerJoined(2)){
                    gui.updateJoinButtons(true, 1);
                }else if(gui.isPlayerJoined(1) && !gui.isPlayerJoined(2)){
                    gui.updateJoinButtons(false, 1);
                }
                break;
            case "P2":
                if(gui.isPlayerJoined(1)) {
                    if (!gui.isPlayerJoined(2) && !gui.isPlayerJoined(3)) {
                        gui.updateJoinButtons(true, 2);
                    } else if (gui.isPlayerJoined(2) && !gui.isPlayerJoined(3)) {
                        gui.updateJoinButtons(false, 2);
                    }
                }
                break;
            case "P3":
                if(gui.isPlayerJoined(1) && gui.isPlayerJoined(2)) {
                    if (!gui.isPlayerJoined(3) && !gui.isPlayerJoined(4)) {
                        gui.updateJoinButtons(true, 3);
                    } else if (gui.isPlayerJoined(3) && !gui.isPlayerJoined(4)) {
                        gui.updateJoinButtons(false, 3);
                    }
                }
                break;
            case "P4":
                if(gui.isPlayerJoined(1) && gui.isPlayerJoined(2) && gui.isPlayerJoined(3)) {
                    if (!gui.isPlayerJoined(4)) {
                        gui.updateJoinButtons(true, 4);
                    } else if (gui.isPlayerJoined(1)) {
                        gui.updateJoinButtons(false, 4);
                    }
                }
                break;
            case "start":
                initializeMap();
                break;
            case "restart":
                gui.dispose();
                startUp();
                break;
            case "quit":
                gui.dispose();
                System.exit(0);
                break;
        }
    }
}