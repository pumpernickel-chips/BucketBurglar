import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
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
    //private Iterator<Map.Entry<Integer, GameEntity>> iterEnt;
    public HashGame(){
        this("Untitled");
    }
    public HashGame(String gameName){
        this.gameName = gameName;
        this.width = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.height = Toolkit.getDefaultToolkit().getScreenSize().height;
        startUp();
    }
    public static void main(String[] args){
            String gameName = "Untitled Game About Stealing Treasure";
            HashGame test = new HashGame(gameName);
    }
    public void startUp(){
        this.gui = new GUI(this.gameName, width, height);
        this.entities = new EntityTable();
        //this.iterEnt = entities.entrySet().iterator();
        this.gui.linkInputs(initializeInputs());
        this.gui.showMainMenu();
    }
    public void setPaths() {
        /*while(iterEnt.hasNext()){
            GameEntity e = iterEnt.next().getValue();
            if(e instanceof Player){
                Player p = (Player) e;
                p.decidePath(entities.getRoomCount(), entities);
                System.out.println(p.getRoomKeys());
            }
        }*/
    }
    public void trapTreasures(){
        /*while(iterEnt.hasNext()){
            GameEntity t = iterEnt.next().getValue();
            if(t instanceof Treasure){
                ((Treasure) t).setBoobyTrapped(true);//method to be written in Treasure
            }
        }*/
    }
    public void createPlayers(){
        for(int i = 0; i < gui.getNumPlayersJoined(); i++){
            Player p = new Player("Player " + (i+1), 3);
            entities.put(p.getName(), p);
        }
    }
    public void movePlayer(int posX, int posY, int direction){

    }
    public void initializeLevel(){
        Room.maxSize = new Dimension (320, 160);
        FloorPlan.setHall(new Dimension((int)(0.4*GUI.h), (int)(.4*GUI.h)));

        this.level = new FloorPlan(entities);
        level.buildLevel();
        trapTreasures();
        setPaths();
        gui.showGameSession(level);
    }
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
                if(gui.getNumPlayersJoined()>0){
                    createPlayers();
                    initializeLevel();
                }
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