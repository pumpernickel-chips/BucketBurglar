

import java.util.Deque;
/**
 * @author Yuliia Synytska
 */
public class Player {

    private int currentScore;
    private String name;
    private Deque<String> roomKeys;

    //default constructor method
    public Player(){

    }

    //constructor method to set values for class attributes
    public Player(int currentScore, String name, Deque<String> roomKeys) {
        this.currentScore = currentScore;
        this.name = name;
        this.roomKeys = roomKeys;
    }

    //method for a player than player can use to join the multi player game
    public void joinGame(){

    }

    //method for the player to leave the game anytime
    public void leaveGame(){

    }

    //method for the player to decide a path to move in/around the castle/rooms
    public void decidePath(){

    }




}

