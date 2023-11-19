import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import javax.swing.Timer;
/**
 * This is a game about stealing treasures from a mysterious network of hallways and rooms. Players are lucky to escape
 * alive, since many of the treasures are booby-trapped at random. Living escapees will leave this trial wealthier than
 * before. Implements ActionListener since this class manages all phases of the game, so all button-presses trigger
 * events that are meant to be controlled by this class' methods.
 * @author Naomi Coakley
 * @author John Beaudry
 * @author Yuliia Synytska
 */
public class HashGame implements ActionListener {
    private String gameName;
    private int width, height;
    private String playerKeys[];
    private boolean inGame;
    private GUI window;
    public static EntityTable entities;
    private FloorPlan level;
    private Timer sim;
    private int elapsedMs, frameTimeMs, fps;

    public HashGame(){
        this("Untitled");
    }
    public HashGame(String gameName){
        this.gameName = gameName;
        this.width = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.height = Toolkit.getDefaultToolkit().getScreenSize().height;
        selectPlayers();
    }
    public static void main(String[] args){
            String gameName = "Untitled Game About Stealing Treasure";
            HashGame test = new HashGame(gameName);
    }
    /**
     * Begins the phase of the game that allows the user to select the number of {@code Players} entering the game. Is
     * initially called by the constructor but can also be called by
     * */
    public void selectPlayers(){
        entities = new EntityTable();
        inGame = false;
        this.window = new GUI(this.gameName, width, height);

        window.linkInputs(initializeInputs());
        window.showMainMenu();
    }
    public void setPaths() {
        for(String key : playerKeys){
            Player p = (Player) entities.get(key);
            p.decidePath();
            p.setNextRoomNodes();
        }
    }
    public void setBoobyTraps(){
        Random trap = new Random();
        int trapCountInRoom;
        for (int i = 0; i < entities.getRoomCount(); i++){
            trapCountInRoom = trap.nextInt((int)((((Room) entities.get("Room " + i)).getLootKeys().size())*.5));
            do {
                ((Treasure) (entities.get("Room " + i + ", Treasure " + (trap.nextInt(((Room) entities.get("Room " + i)).getLootKeys().size()))))).setBoobyTrapped(true);
                trapCountInRoom--;
            } while (trapCountInRoom >= 0);
        }
    }
    public void startGame(){
        this.inGame = true;
        initializeLevel();
        createPlayers();
        setPaths();
        setBoobyTraps();
        simulate();
    }
    public void createPlayers(){
        this.playerKeys = new String[window.getNumPlayersJoined()];
        for (int i = 0; i < window.getNumPlayersJoined(); i++) {
            Player p = new Player((i+1), 100);
            playerKeys[i] = p.getPlayerName();
            p.setPlayerColor(GUI.playerColors[i]);
            entities.put(p.getPlayerName(), p);
        }
    }
    /**
     * Called by {@code simulate()} to either move a single {@code Player} sprite by a small interval (which increments
     * slightly with each method call to simulate acceleration), or to collect loot while the {@code isLooting()}
     * condition is satisfied. This resets to 0 after the Player stops to collect loot.
     * @param p the {@code Player} being made to perform actions
     * @return whether the {@code Player} is still alive or not
     * */
    public boolean playerAction(Player p){
        if(!p.isLooting()){
            double interval = p.getPlayerStepInterval();
            p.setPlayerStepInterval(interval + (interval < (p.isApproachingExit()? 16 : 8)? .33 : 0));
            if (p.moveSprite(p.getPlayerStepInterval())) {
                if(!p.isFinished()){
                    p.setTargetNode();
                }
            }
        }else{
            if (p.collectLoot()) {
                p.setLooting(false);
                p.setPlayerStepInterval(.0);
            }
        }
        return p.hasLost();
    }
    /**
     * Initializes {@link FloorPlan}, calls a method to build the level, and calls a {@link GUI} method to show gameplay.
     * */
    public void initializeLevel(){
        this.level = new FloorPlan();
        level.buildLevel();
        window.showGameSession(level);
    }
    /**
     * Initializes {@code level, fps, elapsedMs, and frameTimeMs} and starts a simulation timer following a lambda that
     * updates the gameplay frame-by-frame until the game ends and the timer can start.
     * */
    public void simulate(){
        level.repaint();
        fps = 60;
        elapsedMs = 0;
        frameTimeMs = 1000/fps;

        sim = new Timer(frameTimeMs, e -> {
            for(String k : playerKeys) {
                inGame = entities.get(k) != null;
                if(inGame){
                    break;
                }
            }
            inGame = playerKeys.length > 0;
            if (inGame) {
                for (String k : playerKeys) {
                    Player p;
                    if (entities.get(k) != null) {
                        p = (Player) entities.get(k);
                        if (playerAction(p)) {
                            level.killPlayer(p.getSprite().getX(), p.getSprite().getY());
                            entities.remove(p.getPlayerName());
                        }
                    }
                }
                this.window.advanceFrame(playerKeys);
            }
            elapsedMs += frameTimeMs;
            window.updateScoreboard(elapsedMs, playerKeys);
            window.repaint();
        });
        sim.start();
    }
    /**
     * Creates JButtons and gives them actionListeners
     * @return an ArrayList of JButtons
     * */
    public List<JButton> initializeInputs(){
        JButton addP1 = new JButton(), addP2 = new JButton(), addP3 = new JButton(), addP4 = new JButton(),
                start = new JButton(), newGame = new JButton(), quit = new JButton();
        List<JButton> inputs = new ArrayList<JButton>(Arrays.asList(addP1, addP2, addP3, addP4, start, newGame, quit));
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
        newGame.setActionCommand("new game");
        quit.setActionCommand("quit");
        return inputs;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "P1":
                if(!window.isPlayerJoined(1) && !window.isPlayerJoined(2)){
                    window.updateJoinButtons(true, 1);
                }else if(window.isPlayerJoined(1) && !window.isPlayerJoined(2)){
                    window.updateJoinButtons(false, 1);
                }
                break;
            case "P2":
                if(window.isPlayerJoined(1)) {
                    if (!window.isPlayerJoined(2) && !window.isPlayerJoined(3)) {
                        window.updateJoinButtons(true, 2);
                    } else if (window.isPlayerJoined(2) && !window.isPlayerJoined(3)) {
                        window.updateJoinButtons(false, 2);
                    }
                }
                break;
            case "P3":
                if(window.isPlayerJoined(1) && window.isPlayerJoined(2)) {
                    if (!window.isPlayerJoined(3) && !window.isPlayerJoined(4)) {
                        window.updateJoinButtons(true, 3);
                    } else if (window.isPlayerJoined(3) && !window.isPlayerJoined(4)) {
                        window.updateJoinButtons(false, 3);
                    }
                }
                break;
            case "P4":
                if(window.isPlayerJoined(1) && window.isPlayerJoined(2) && window.isPlayerJoined(3)) {
                    if (!window.isPlayerJoined(4)) {
                        window.updateJoinButtons(true, 4);
                    } else if (window.isPlayerJoined(1)) {
                        window.updateJoinButtons(false, 4);
                    }
                }
                break;
            case "start":
                if(window.getNumPlayersJoined()>0 && !inGame){
                    startGame();
                }
                break;
            case "new game":

                sim.stop();
                entities.clear();
                System.gc();
                selectPlayers();
                break;
            case "quit":
                System.exit(0);
                break;
        }
    }
}