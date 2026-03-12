/**
 * Book My Stay App
 * Demonstrates booking cancellation and inventory rollback.
 *
 * @author Isha
 * @version 10.0
 */

import java.util.*;

// ---------------- Reservation ----------------
class Reservation {

    String reservationID;
    String guestName;
    String roomType;
    String roomID;
    boolean active;

    public Reservation(String reservationID, String guestName, String roomType, String roomID) {
        this.reservationID = reservationID;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomID = roomID;
        this.active = true;
    }

    public void displayReservation() {
        System.out.println("Reservation ID: " + reservationID +
                " | Guest: " + guestName +
                " | Room Type: " + roomType +
                " | Room ID: " + roomID +
                " | Status: " + (active ? "Active" : "Cancelled"));
    }
}

// ---------------- Inventory Service ----------------
class InventoryService {

    private Map<String, Integer> inventory;

    public InventoryService() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public void increaseRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " : " + inventory.get(roomType));
        }
    }
}

// ---------------- Cancellation Service ----------------
class CancellationService {

    private Map<String, Reservation> reservations;
    private Stack<String> rollbackStack;
    private InventoryService inventory;

    public CancellationService(Map<String, Reservation> reservations, InventoryService inventory) {
        this.reservations = reservations;
        this.inventory = inventory;
        this.rollbackStack = new Stack<>();
    }

    public void cancelReservation(String reservationID) {

        if (!reservations.containsKey(reservationID)) {
            System.out.println("Cancellation Failed: Reservation does not exist.");
            return;
        }

        Reservation r = reservations.get(reservationID);

        if (!r.active) {
            System.out.println("Cancellation Failed: Reservation already cancelled.");
            return;
        }

        // Record released room ID in stack
        rollbackStack.push(r.roomID);

        // Restore inventory
        inventory.increaseRoom(r.roomType);

        // Mark reservation as cancelled
        r.active = false;

        System.out.println("\nReservation Cancelled Successfully.");
        System.out.println("Released Room ID: " + r.roomID);
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack (Recently Released Rooms):");
        for (String id : rollbackStack) {
            System.out.println(id);
        }
    }
}

// ---------------- Main Application ----------------
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Book My Stay App - Version 10.0");
        System.out.println("Booking Cancellation & Rollback");
        System.out.println("=================================");

        InventoryService inventory = new InventoryService();

        // Store confirmed reservations
        Map<String, Reservation> reservations = new HashMap<>();

        reservations.put("RES101", new Reservation("RES101", "Alice", "Single Room", "SI101"));
        reservations.put("RES102", new Reservation("RES102", "Bob", "Double Room", "DO201"));

        CancellationService cancelService = new CancellationService(reservations, inventory);

        // Display reservations
        System.out.println("\nExisting Reservations:");
        for (Reservation r : reservations.values()) {
            r.displayReservation();
        }

        // Cancel booking
        cancelService.cancelReservation("RES101");

        // Try cancelling again
        cancelService.cancelReservation("RES101");

        // Display updated inventory
        inventory.displayInventory();

        // Display rollback stack
        cancelService.showRollbackStack();
    }
}