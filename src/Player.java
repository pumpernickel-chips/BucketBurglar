

import java.util.*;

/**
 * @author Yuliia Synytska
 * @author John Beaudry
 * @author Naomi Coakley
 *
 * TODO: John - please write decidePath() method. Feel free to make assumptions about EntityTable to do so. Focus on
 *       randomly selecting a set number (parameter) of rooms from a HashMap and storing them in roomKeys. Sort the
 *       chosen rooms first, if you have time to try that. Not a requirement.
 *       Write another method to pop the next room off of roomKeys.
 *       NOTE:
 *       Please comment out code before you commit/push to avoid throwing errors until the needed methods in other
 *       classes have been written.
 *
 * TODO: (low priority) convert line comments to formatted JavaDoc comments
 */
public class Player implements GameEntity{
    private int currentScore;
    private String name;
    private int health;
    private Deque<Integer> roomKeys;
    private Room currentRoom;
    //default constructor method
    public Player(){}
    //constructor method to set values for class attributes
    public Player(String name, int health, Deque<Integer> roomKeys) {
        this.currentScore = 0;// make final? maybe?
        this.name = name;
        this.health = health;
        this.roomKeys = roomKeys;
        this.currentRoom = null;
    }
    //method for a player than player can use to join the multi player game
    public void joinGame(){}
    //method for the player to leave the game anytime
    public void leaveGame(){}
    //method to get name of player
    public String getName(){
        return name;
    }
    /**
     * method for the player to decide a path to move in/around the castle/rooms
     */
    public void decidePath(int numberOfRooms, EntityTable allRooms){
        List<Integer> keysAsArray = new ArrayList<>(allRooms.keySet());
        Collections.shuffle(keysAsArray);
        roomKeys = new ArrayDeque<>();
        for (int i = 0; i < Math.min(numberOfRooms, keysAsArray.size()); i++) {
            roomKeys.add(keysAsArray.get(i));
        }
    }
    public Integer getNextRoom() {
        return roomKeys.poll();
    }
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            //method body tbd
        }
    }
    public void collectLoot() {
        if (currentRoom != null) {
            //method body tbd
        }
    }
    @Override
    public void store() {
    }
    @Override
    public void retrieve() {
    }
    @Override
    public void remove() {
    }
}

