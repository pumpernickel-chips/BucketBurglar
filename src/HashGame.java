import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

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
    private Iterator<Map.Entry<Integer, GameEntity>> iterEnt;
    public HashGame(){
        this("Untitled");
    }
    public HashGame(String gameName){
        this.gameName = gameName;
        this.width = 1600;
        this.height = 1000;
        startUp();
    }
    public static void main(String[] args){
            String gameName = "Untitled Game About Stealing Treasure";
            HashGame test = new HashGame(gameName);
    }
    public void startUp(){
        this.gui = new GUI(this.gameName, width, height);
        this.entities = new EntityTable();
        this.level = new FloorPlan(entities);
        this.iterEnt = entities.entrySet().iterator();
        this.gui.linkInputs(initializeInputs());
        this.gui.initializeGUI();
        trapTreasures();
        setPaths();
    }
    public void setPaths() {
        while(iterEnt.hasNext()){
            GameEntity p = iterEnt.next().getValue();
            if(p instanceof Player){
                ((Player) p).decidePath(entities.getRoomCount(), entities);
                System.out.println(((Player) p).getRoomKeys());
            }
        }
    }
    public void trapTreasures(){
        while(iterEnt.hasNext()){
            GameEntity t = iterEnt.next().getValue();
            if(t instanceof Treasure){
                ((Treasure) t).setBoobyTrapped(true);//method to be written in Treasure
            }
        }
    }
    public void createPlayers(){}
    public void movePlayer(int posX, int posY, int direction){}
    public void initializeMap(){}
    public List<JButton> initializeInputs(){
        JButton addP1 = new JButton(), addP2 = new JButton(), addP3 = new JButton(), addP4 = new JButton(),
                start = new JButton(), end = new JButton(), quit = new JButton();
        List<JButton> inputs = new ArrayList<JButton>(Arrays.asList(addP1, addP2, addP3, addP4, start, end, quit));
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
        end.setActionCommand("end");
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

                break;
            case "end":
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
