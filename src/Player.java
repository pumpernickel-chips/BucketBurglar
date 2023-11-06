

import java.util.Deque;
/**
 * @author Yuliia Synytska
 * @author John Beaudry
 * @author Naomi Coakley
 *
 * TODO: write decidePath() method. Feel free to make assumptions about EntityTable to do so. Focus on randomly selecting
 *       a set number (parameter) of rooms from a HashMap and storing them in roomKeys. Sort the chosen rooms first, if
 *       you have time to try that. Not a requirement.
 *       Write another method to pop the next room off of roomKeys.
 *       Please comment out code before you commit/push to avoid throwing errors until the needed methods in other
 *       classes have been written.
 */
public class Player implements GameEntity{
    private int currentScore;
    private String name;
    private int health;
    private Deque<String> roomKeys;
    private Room currentRoom;
    //default constructor method
    public Player(){}
    //constructor method to set values for class attributes
    public Player(String name, int health, Deque<String> roomKeys) {
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
    /**
     * method for the player to decide a path to move in/around the castle/rooms
     */
    public void decidePath(){}
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

