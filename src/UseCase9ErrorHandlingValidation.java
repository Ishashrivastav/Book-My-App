/**
 * Book My Stay App
 * Demonstrates error handling and validation in booking requests.
 *
 * @author Isha
 * @version 9.0
 */

import java.util.*;

// ---------------- Custom Exception ----------------
class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}

// ---------------- Inventory Service ----------------
class InventoryService {

    private Map<String, Integer> inventory;

    public InventoryService() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public boolean isValidRoomType(String roomType) {
        return inventory.containsKey(roomType);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decreaseRoom(String roomType) throws InvalidBookingException {

        int available = getAvailability(roomType);

        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for " + roomType);
        }

        inventory.put(roomType, available - 1);
    }
}

// ---------------- Validator ----------------
class InvalidBookingValidator {

    public static void validate(String guestName, String roomType, InventoryService inventory)
            throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        if (inventory.getAvailability(roomType) <= 0) {
            throw new InvalidBookingException("Requested room type is not available.");
        }
    }
}

// ---------------- Booking Service ----------------
class BookingService {

    private InventoryService inventory;

    public BookingService(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void bookRoom(String guestName, String roomType) {

        try {

            // Validate input before processing
            InvalidBookingValidator.validate(guestName, roomType, inventory);

            // Update inventory safely
            inventory.decreaseRoom(roomType);

            System.out.println("Reservation Confirmed!");
            System.out.println("Guest: " + guestName);
            System.out.println("Room Type: " + roomType);

        } catch (InvalidBookingException e) {

            // Graceful error handling
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}

// ---------------- Main Application ----------------
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Book My Stay App - Version 9.0");
        System.out.println("Error Handling & Validation");
        System.out.println("=================================");

        InventoryService inventory = new InventoryService();

        BookingService bookingService = new BookingService(inventory);

        // Valid booking
        bookingService.bookRoom("Alice", "Single Room");

        // Invalid room type
        bookingService.bookRoom("Bob", "Deluxe Room");

        // Empty guest name
        bookingService.bookRoom("", "Double Room");

        // Inventory validation
        bookingService.bookRoom("Charlie", "Suite Room");
        bookingService.bookRoom("David", "Suite Room"); // will fail
    }
}