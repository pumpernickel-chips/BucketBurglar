public class PlayerJ {
    private String name;
    private int health;
    private int attackPower;
    private int defense;
    private Room currentRoom;

    public PlayerJ(String name, int health, int attackPower, int defense) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.defense = defense;
    }

    // Getters and setters for attributes
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

/*    public int getAttackPower() {
        return attackPower;
    }

    public int getDefense() {
        return defense;
    }*/

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    // Method to move the player to another room
    public void moveToRoom(Room newRoom) {
        if (newRoom != null) {
            currentRoom = newRoom;
            System.out.println(name + " has moved to " + newRoom.getName());
        } else {
            System.out.println("Invalid room. Cannot move.");
        }
    }

    /*// Method to attack another player (Naomi - likely unused)
    public void attack(PlayerJ target) {
        int damage = attackPower - target.getDefense();
        if (damage > 0) {
            target.takeDamage(damage);
            System.out.println(name + " attacks " + target.getName() + " for " + damage + " damage.");
        } else {
            System.out.println(name + " attacks " + target.getName() + " but it has no effect.");
        }
    }*/

    // Method to take damage and update health
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            System.out.println(name + " has been defeated.");
            // You can add more logic here for handling player defeat
        }
    }

    // Method to collect loot from the current room
    public void collectLoot() {
        if (currentRoom != null) {
            int loot = currentRoom.collectLoot();
            System.out.println(name + " collects " + loot + " loot from " + currentRoom.getName());
            // You can add more logic for handling loot collection
        }
    }



}
