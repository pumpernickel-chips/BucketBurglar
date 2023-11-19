import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;

/**
 * An implementation of GameEntity that represents a Player and it's statuses, such as position, score, and health.
 * @author Yuliia Synytska
 * @author John Beaudry
 * @author Naomi Coakley
 */
public class Player implements GameEntity{
    private final static int out = 0, in = 1;
    private int playerNum, currentScore, targetLoot;
    private double playerStepInterval, lootStepInterval = .0;
    private final int sideLength = (int) (24*(GUI.scale != null? GUI.scale : 1));
    private boolean finished, approachingExit, looting;
    private String playerName;
    private Rectangle2D playerSprite;
    private Point2D targetNode, roomCenter;
    private Color playerColor;
    /**
     * {@code double} representing how much health this {@code Player} has
     */
    private final double maxHealth;
    private double currentHealth;
    /**
     * {@code Deque} containing path for player to take
     */
    private Deque<String> pathKeys;
    private Deque<Point2D> nextRoomNodes;
    private Room currentRoom;
    private String lootInProgress;
    /**
     * {@code true} if player is out of the game
     */
    private boolean lost;
    /**
     * Default zero-args constructor, passes default player number and maxHealth
     * */
    public Player() {
        this(1, 100);
    }
    /**
     * Complete constructor
     * @param playerNum int to derive player name from (ex: Player 1)
     * @param maxHealth player health
     */
    public Player(int playerNum, int maxHealth) {
        this.playerNum = playerNum;
        this.playerName = "Player "  + this.playerNum;
        this.maxHealth = maxHealth;
        this.currentHealth = this.maxHealth;
        this.pathKeys = new ArrayDeque<String>();
        this.nextRoomNodes = new ArrayDeque<Point2D>();
        this.currentRoom = null;
        this.lost = false;
        this.playerStepInterval = .0;
        this.lootInProgress = "";
        this.finished = false;
        this.playerSprite = new Rectangle2D.Double(FloorPlan.pathNodes[FloorPlan.exit].getX()+((sideLength*1.6)*playerNum), FloorPlan.pathNodes[FloorPlan.exit].getY(), sideLength, sideLength);
        this.targetNode = new Point2D.Double(playerSprite.getX(), playerSprite.getY());
        this.playerColor = new Color(142,140,140);
        this.currentScore = 0;
        this.approachingExit = false;
    }
    /**
     * Method to get name of Player
     * @return name
     */
    public String getPlayerName(){
        return playerName;
    }
    /**
     * Method for the player to decide a path to move in/around the castle/rooms
     */
    public void decidePath(){
        int targetNumRooms = 0;
        switch(HashGame.entities.getPlayerCount()){
            case 1 :
                targetNumRooms = 8;
                break;
            case 2 :
                targetNumRooms = 7;
                break;
            case 3 :
                targetNumRooms = 5;
                break;
            case 4 :
                targetNumRooms = 4;
                break;
        }
        Random rand = new Random();
        //System.out.println(HashGame.entities.getRoomCount());
        List<Integer> keys = new LinkedList<Integer>();
        for(int k = 0; k < HashGame.entities.getRoomCount(); k++) keys.add(k);
        for(int i = 0; i < HashGame.entities.getRoomCount()-targetNumRooms; i++){
            //System.out.println(keys.size());
            keys.remove(rand.nextInt(keys.size()));
        }
        Collections.sort(keys);
        pathKeys = new ArrayDeque<>();
        for (int i = 0; i < Math.min(HashGame.entities.getRoomCount(), keys.size()); i++) {
            pathKeys.add("Room " + keys.get(i));
        }
        nextRoomNodes.addLast(FloorPlan.pathNodes[FloorPlan.NEc]);
        System.out.println(playerName.toUpperCase() + " pathKeys:\n" + pathKeys);
    }
    /**
     * Method to assess damage to player based on value passed to {@code damage}
     * @param damage damage to be assessed
     * @return {@code lost} if {@code boolean} lost is returned false, player is still in the game
     */
    public boolean takeDamage(double damage) {
        currentHealth -= damage;
        if (currentHealth < 1) {
            lost = true;
        }
        return lost;
    }
    /**
     * Method to collect loot from {@link Room}
     */
    public boolean collectLoot() {
        if (looting && currentRoom != null && targetLoot > 0) {
            while (lootInProgress.equals("")) if (currentRoom.getLootKeys().size() > 0) {
                setLootInProgress(getCurrentRoom().claimRandomLootKey());
                lootStepInterval = .0;
            } else {
                return true;
            }
            ((Treasure)(HashGame.entities.get(lootInProgress))).setTargetPlayer(new Point2D.Double(getSprite().getX(), getSprite().getY()));
            this.lootStepInterval += (lootStepInterval < 12 ? .8 : .0);
            if (((Treasure) (HashGame.entities.get(lootInProgress))).moveSprite(lootStepInterval)) {
                if(((Treasure) (HashGame.entities.get(lootInProgress))).isBoobyTrapped()) {
                    currentRoom.removeLootKey(lootInProgress);
                    HashGame.entities.remove(lootInProgress);
                    lootInProgress = "";
                    targetLoot = 0;
                    lootStepInterval = .0;
                    if (takeDamage((maxHealth * .25) + (Math.random()*(maxHealth * .18)))) {
                        setLost(true);
                    }
                    return true;
                }else {
                    currentRoom.removeLootKey(lootInProgress);
                    HashGame.entities.remove(lootInProgress);
                    lootInProgress = "";
                    currentScore += 1000;
                    lootStepInterval = .0;
                    targetLoot--;
                }
            }
        }else{
            if(targetLoot == 0){
                lootStepInterval = .0;
                return true;
            }
        }
        return false;
    }
    /**
     * Returns this players current score
     * @return currentScore current score
     */
    public int getCurrentScore() {
        return currentScore;
    }
    public int getPlayerNum() {
        return playerNum;
    }
    public double getMaxHealth() {
        return maxHealth;
    }
    /**
     * Method returns remaining health of player
     * @return health remaining health
     */
    public double getCurrentHealth() {
        return currentHealth;
    }
    /**
     * Method to get room player in currently
     * @return currentRoom
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }
    /**
     * Returns player sprite color
     * @return {@code playerColor}
     */
    public Color getPlayerColor() {
        return playerColor;
    }
    /**
     * Set player sprite color
     * @param c {@code Color}
     */
    public void setPlayerColor(Color c) {
        playerColor = c;
    }
    /**
     * Returns player sprite
     * @return playerSprite
     */
    @Override
    public Rectangle2D getSprite() {
        return playerSprite;
    }
    /**
     * Sets {@code playerSprite}
     * @param playerSprite
     */
    public void setPlayerSprite(Rectangle2D playerSprite) {
        this.playerSprite = playerSprite;
    }
    public boolean isFinished() {
        return new Point2D.Double(getSprite().getX(), getSprite().getY()).equals(new Point2D.Double(GUI.screenSize.width+30, getSprite().getY()));
    }
    public boolean isApproachingExit() {
        return approachingExit;
    }
    /**
     * Returns if player is still in game
     * @return lost
     */
    public boolean hasLost() {
        return lost;
    }
    /**
     * Sets whether player is still in game
     * @param lost {@code boolean}
     */
    public void setLost(boolean lost) {
        this.lost = lost;
    }
    /**
     * Method to get next room from roomKeys {@code Deque<Integer>}
     * @return {@code roomKeys.poll}
     */
    public boolean isLooting() {
        return this.looting;
    }
    public void setLooting(boolean looting) {
        this.looting = looting;
        if(!this.looting) setLootInProgress("");
    }

    public double getPlayerStepInterval() {
        return playerStepInterval;
    }
    public void setPlayerStepInterval(double playerStepInterval) {
        this.playerStepInterval = playerStepInterval;
    }
    public void rollTargetLoot() {
        int bound = currentRoom.getLootKeys().size();
        this.targetLoot = bound < 1? 0 : currentRoom.getLootKeys().size() - new Random().nextInt((int) (1+currentRoom.getLootKeys().size()*.8));
        System.out.println("target = " + targetLoot);
    }
    public String getLootInProgress() {
        return lootInProgress;
    }
    public void setLootInProgress(String lootKey) {
        this.lootInProgress = lootKey;
    }
    public void setTargetNode() {
        if(nextRoomNodes.isEmpty() && !approachingExit){
            this.approachingExit = setNextRoomNodes();
        }
        if(this.targetNode.equals(this.roomCenter)){
            this.looting = true;
            rollTargetLoot();
            setLootInProgress(currentRoom.claimRandomLootKey());
        }
        boolean exiting = nextRoomNodes.peek() == null;
        this.targetNode = !exiting? nextRoomNodes.poll() : new Point2D.Double(GUI.screenSize.width+30, getSprite().getY());
    }
    public Point2D getTargetNode() {
        return this.targetNode;
    }
    public boolean setNextRoomNodes() {
        if(!pathKeys.isEmpty()) {
            Room previousRoom = currentRoom;
            currentRoom = (Room) HashGame.entities.get(pathKeys.poll());
            int last = previousRoom == null? -1 : previousRoom.getRoomNumber();
            int next = currentRoom.getRoomNumber();
            if (next > 2 && last < 2) {
                nextRoomNodes.addLast(FloorPlan.pathNodes[FloorPlan.NWc]);
                System.out.println(playerName.toUpperCase() + " to NWc after " + (last < 0? "entering game" : "Room " + last));
            }
            if (next > 4 && last < 5) {
                nextRoomNodes.addLast(FloorPlan.pathNodes[FloorPlan.SWc]);
                System.out.println(playerName.toUpperCase() + " to SWc after " + (last < 0? "entering game" : "Room " + last));
            }
            if (next > 7 && last < 8) {
                nextRoomNodes.addLast(FloorPlan.pathNodes[FloorPlan.SEc]);
                System.out.println(playerName.toUpperCase() + " to SEc after " + (last < 0? "entering game" : "Room " + last));
            }
            nextRoomNodes.addLast(currentRoom.getPathNodes()[0]);
            nextRoomNodes.addLast(currentRoom.getPathNodes()[1]);
            nextRoomNodes.addLast(currentRoom.getPathNodes()[0]);
            roomCenter = currentRoom.getPathNodes()[1];
        }else {
            int last = currentRoom.getRoomNumber();
            if (last < 2) {
                nextRoomNodes.addLast(FloorPlan.pathNodes[FloorPlan.NWc]);
                //System.out.println(name.toUpperCase() + " to NWc after " + (last < 0? "entering game" : "Room " + last));
            }
            if (last < 5) {
                nextRoomNodes.addLast(FloorPlan.pathNodes[FloorPlan.SWc]);
                //System.out.println(name.toUpperCase() + " to SWc after " + (last < 0? "entering game" : "Room " + last));
            }
            if (last < 8) {
                nextRoomNodes.addLast(FloorPlan.pathNodes[FloorPlan.SEc]);
                //System.out.println(name.toUpperCase() + " to SEc after " + (last < 0? "entering game" : "Room " + last));
            }
            nextRoomNodes.addLast(FloorPlan.pathNodes[FloorPlan.NEc]);
            nextRoomNodes.addLast(new Point2D.Double(FloorPlan.pathNodes[FloorPlan.exit].getX(), FloorPlan.pathNodes[FloorPlan.exit].getY()));
            return true;
        }
        return false;
    }
    /*if (currentRoom == null) {
            nextRoomNodes.addLast(FloorPlan.pathNodes[FloorPlan.NEc]);
            //if((getSprite().getX().equals getSprite().getY()))
            System.out.println(name + "to exit after Room " + previousRoom.getRoomNumber());
            return true;*/
    @Override
    public boolean moveSprite(double stepInterval) {
        double x1, y1, x2, y2, delta_x, delta_y, nextNodeDistance, proportion, destX, destY;
        x1 = this.getSprite().getX();
        y1 = this.getSprite().getY();
        x2 = this.getTargetNode().getX();
        y2 = this.getTargetNode().getY();

        delta_x = x2 - x1;
        delta_y = y2 - y1;

        nextNodeDistance = Math.pow(Math.pow(delta_x, 2) + Math.pow(delta_y, 2), 0.5);

        boolean arrived = nextNodeDistance < stepInterval;

        proportion = stepInterval / nextNodeDistance;

        destX = arrived? x2 : x1 + proportion * delta_x;
        destY = arrived? y2 : y1 + proportion * delta_y;
        this.setPlayerSprite(new Rectangle2D.Double(destX, destY, sideLength, sideLength));

        return arrived;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return getPlayerNum() == player.getPlayerNum() && getCurrentScore() == player.getCurrentScore() && targetLoot == player.targetLoot && Double.compare(player.getPlayerStepInterval(), getPlayerStepInterval()) == 0 && Double.compare(player.lootStepInterval, lootStepInterval) == 0 && sideLength == player.sideLength && isFinished() == player.isFinished() && isApproachingExit() == player.isApproachingExit() && isLooting() == player.isLooting() && Double.compare(player.getMaxHealth(), getMaxHealth()) == 0 && Double.compare(player.getCurrentHealth(), getCurrentHealth()) == 0 && lost == player.lost && Objects.equals(getPlayerName(), player.getPlayerName()) && Objects.equals(playerSprite, player.playerSprite) && Objects.equals(getTargetNode(), player.getTargetNode()) && Objects.equals(roomCenter, player.roomCenter) && Objects.equals(getPlayerColor(), player.getPlayerColor()) && Objects.equals(pathKeys, player.pathKeys) && Objects.equals(nextRoomNodes, player.nextRoomNodes) && Objects.equals(getCurrentRoom(), player.getCurrentRoom()) && Objects.equals(getLootInProgress(), player.getLootInProgress());
    }
    @Override
    public int hashCode() {
        return Objects.hash(playerNum, playerName, playerColor);
    }
}

