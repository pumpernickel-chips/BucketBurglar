/**
 * @author Yuliia Synytska
 *
 * */


import java.awt.*;
import java.util.ArrayList;

public class RoomY {
    private Dimension dimension;
    private boolean isEmpty;
    private ArrayList<Player> players;

    //default method for Room
    public RoomY() {
        isEmpty = false;
        players = new ArrayList<>();
    }

    //parameterized method to set class attributes for Room
    public RoomY(Dimension dimension, boolean isEmpty) {
        this.dimension = dimension;
        this.isEmpty = isEmpty;
        players = new ArrayList<>();
    }

    //method to add payers to a room
    public void addPlayer(Player p){
        players.add(p);
    }

    //method to delete a payer in a room
    public void deletePlayer(String name){
        for (int a = 0; a < players.size(); a++) {
            if(players.get(a).getName().equalsIgnoreCase(name)){
                players.remove(a);
                return;
            }
        }
    }

    //method to return payers in a room
    public ArrayList<Player> getPlayers(){
        return players;
    }

}
