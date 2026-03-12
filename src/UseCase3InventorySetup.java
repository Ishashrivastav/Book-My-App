/**
 * Book My Stay App
 * Demonstrates centralized room inventory management using HashMap.
 *
 * @author Isha
 * @version 3.1
 */

import java.util.HashMap;

// Inventory class that manages room availability
class RoomInventory {

    // HashMap to store room type and available count
    private HashMap<String, Integer> inventory;

    // Constructor to initialize inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        // Register room types with availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Method to get availability of a room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Method to update room availability
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Method to display all inventory
    public void displayInventory() {
        System.out.println("Current Room Inventory:");
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " : " + inventory.get(roomType));
        }
    }
}

// Main application class
public class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Book My Stay App - Version 3.1");
        System.out.println("Centralized Room Inventory");
        System.out.println("=================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display current inventory
        inventory.displayInventory();

        System.out.println("---------------------------------");

        // Example update
        System.out.println("Updating availability for Single Room...");
        inventory.updateAvailability("Single Room", 4);

        // Display updated inventory
        inventory.displayInventory();

        System.out.println("---------------------------------");
        System.out.println("Application Finished.");
    }
}