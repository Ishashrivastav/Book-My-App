/**
 * Book My Stay App
 * Demonstrates reservation confirmation and room allocation.
 *
 * @author Isha
 * @version 6.0
 */

import java.util.*;

// ---------------- Reservation ----------------
class Reservation {

    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// ---------------- Inventory Service ----------------
class InventoryService {

    private HashMap<String, Integer> inventory;

    public InventoryService() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decreaseRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}

// ---------------- Booking Service ----------------
class BookingService {

    private Queue<Reservation> requestQueue;
    private InventoryService inventoryService;

    // Track allocated room IDs
    private Set<String> allocatedRooms;

    // Map room types to allocated IDs
    private HashMap<String, Set<String>> roomAllocation;

    public BookingService(Queue<Reservation> queue, InventoryService inventory) {
        requestQueue = queue;
        inventoryService = inventory;
        allocatedRooms = new HashSet<>();
        roomAllocation = new HashMap<>();
    }

    public void processBookings() {

        while (!requestQueue.isEmpty()) {

            Reservation reservation = requestQueue.poll();
            String roomType = reservation.roomType;

            System.out.println("\nProcessing booking for " + reservation.guestName);

            if (inventoryService.getAvailability(roomType) > 0) {

                String roomID;

                // Generate unique room ID
                do {
                    roomID = roomType.substring(0, 2).toUpperCase() +
                            (100 + new Random().nextInt(900));
                } while (allocatedRooms.contains(roomID));

                allocatedRooms.add(roomID);

                roomAllocation.putIfAbsent(roomType, new HashSet<>());
                roomAllocation.get(roomType).add(roomID);

                // Update inventory
                inventoryService.decreaseRoom(roomType);

                System.out.println("Reservation Confirmed!");
                System.out.println("Guest: " + reservation.guestName);
                System.out.println("Room Type: " + roomType);
                System.out.println("Assigned Room ID: " + roomID);

            } else {
                System.out.println("Sorry! No rooms available for " + roomType);
            }
        }
    }
}

// ---------------- Main Application ----------------
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Book My Stay App - Version 6.0");
        System.out.println("Room Allocation Service");
        System.out.println("=================================");

        // Booking queue
        Queue<Reservation> queue = new LinkedList<>();

        queue.add(new Reservation("Alice", "Single Room"));
        queue.add(new Reservation("Bob", "Double Room"));
        queue.add(new Reservation("Charlie", "Single Room"));
        queue.add(new Reservation("David", "Suite Room"));

        InventoryService inventory = new InventoryService();

        BookingService bookingService = new BookingService(queue, inventory);

        // Process bookings
        bookingService.processBookings();

        System.out.println("\nAll booking requests processed.");
    }
}