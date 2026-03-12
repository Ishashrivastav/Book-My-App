/**
 * Book My Stay App
 * Demonstrates concurrent booking simulation using threads.
 *
 * @author Isha
 * @version 11.0
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

    private Map<String, Integer> inventory;

    public InventoryService() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    // synchronized method to ensure thread safety
    public synchronized boolean allocateRoom(String roomType, String guestName) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {

            inventory.put(roomType, available - 1);

            String roomID = roomType.substring(0, 2).toUpperCase() +
                    (100 + new Random().nextInt(900));

            System.out.println(Thread.currentThread().getName()
                    + " confirmed booking for " + guestName
                    + " | Room Type: " + roomType
                    + " | Room ID: " + roomID);

            return true;

        } else {

            System.out.println(Thread.currentThread().getName()
                    + " failed booking for " + guestName
                    + " | No rooms available for " + roomType);

            return false;
        }
    }
}

// ---------------- Booking Processor Thread ----------------
class BookingProcessor extends Thread {

    private Queue<Reservation> bookingQueue;
    private InventoryService inventory;

    public BookingProcessor(String name, Queue<Reservation> queue, InventoryService inventory) {
        super(name);
        this.bookingQueue = queue;
        this.inventory = inventory;
    }

    public void run() {

        while (true) {

            Reservation request;

            synchronized (bookingQueue) {

                if (bookingQueue.isEmpty()) {
                    break;
                }

                request = bookingQueue.poll();
            }

            if (request != null) {
                inventory.allocateRoom(request.roomType, request.guestName);
            }
        }
    }
}

// ---------------- Main Application ----------------
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Book My Stay App - Version 11.0");
        System.out.println("Concurrent Booking Simulation");
        System.out.println("=================================");

        Queue<Reservation> bookingQueue = new LinkedList<>();

        // Simulated booking requests
        bookingQueue.add(new Reservation("Alice", "Single Room"));
        bookingQueue.add(new Reservation("Bob", "Double Room"));
        bookingQueue.add(new Reservation("Charlie", "Single Room"));
        bookingQueue.add(new Reservation("David", "Suite Room"));
        bookingQueue.add(new Reservation("Eva", "Suite Room"));

        InventoryService inventory = new InventoryService();

        // Create multiple booking threads
        Thread t1 = new BookingProcessor("Thread-1", bookingQueue, inventory);
        Thread t2 = new BookingProcessor("Thread-2", bookingQueue, inventory);
        Thread t3 = new BookingProcessor("Thread-3", bookingQueue, inventory);

        // Start threads
        t1.start();
        t2.start();
        t3.start();
    }
}