/**
 * Book My Stay App
 * Demonstrates data persistence and system recovery using serialization.
 *
 * @author Isha
 * @version 12.0
 */

import java.io.*;
import java.util.*;

// ---------------- Reservation ----------------
class Reservation implements Serializable {

    String reservationID;
    String guestName;
    String roomType;

    public Reservation(String reservationID, String guestName, String roomType) {
        this.reservationID = reservationID;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void displayReservation() {
        System.out.println("Reservation ID: " + reservationID +
                " | Guest: " + guestName +
                " | Room Type: " + roomType);
    }
}

// ---------------- System State ----------------
class SystemState implements Serializable {

    Map<String, Integer> inventory;
    List<Reservation> bookingHistory;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookingHistory) {
        this.inventory = inventory;
        this.bookingHistory = bookingHistory;
    }
}

// ---------------- Persistence Service ----------------
class PersistenceService {

    private static final String FILE_NAME = "hotel_state.ser";

    // Save system state
    public static void saveState(SystemState state) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    // Load system state
    public static SystemState loadState() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("System state loaded successfully.");
            return (SystemState) in.readObject();

        } catch (FileNotFoundException e) {

            System.out.println("No saved data found. Starting with fresh state.");
            return null;

        } catch (Exception e) {

            System.out.println("Error loading saved state. Starting fresh.");
            return null;
        }
    }
}

// ---------------- Main Application ----------------
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Book My Stay App - Version 12.0");
        System.out.println("Data Persistence & Recovery");
        System.out.println("=================================");

        // Try loading saved system state
        SystemState state = PersistenceService.loadState();

        Map<String, Integer> inventory;
        List<Reservation> bookingHistory;

        if (state == null) {

            // Initialize new system state
            inventory = new HashMap<>();
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 2);
            inventory.put("Suite Room", 1);

            bookingHistory = new ArrayList<>();

            bookingHistory.add(new Reservation("RES101", "Alice", "Single Room"));
            bookingHistory.add(new Reservation("RES102", "Bob", "Double Room"));

            System.out.println("New system state created.");

        } else {

            inventory = state.inventory;
            bookingHistory = state.bookingHistory;

            System.out.println("Recovered system state from file.");
        }

        // Display inventory
        System.out.println("\nInventory State:");
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " : " + inventory.get(roomType));
        }

        // Display booking history
        System.out.println("\nBooking History:");
        for (Reservation r : bookingHistory) {
            r.displayReservation();
        }

        // Save system state before shutdown
        PersistenceService.saveState(new SystemState(inventory, bookingHistory));
    }
}