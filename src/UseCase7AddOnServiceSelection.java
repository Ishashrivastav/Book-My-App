/**
 * Book My Stay App
 * Demonstrates add-on service selection for reservations.
 *
 * @author Isha
 * @version 7.0
 */

import java.util.*;

// ---------------- Add-On Service ----------------
class Service {

    String serviceName;
    double price;

    public Service(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public void displayService() {
        System.out.println(serviceName + " - ₹" + price);
    }
}

// ---------------- Add-On Service Manager ----------------
class AddOnServiceManager {

    // Map reservation ID to list of services
    private HashMap<String, List<Service>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    // Add service to reservation
    public void addService(String reservationID, Service service) {

        reservationServices.putIfAbsent(reservationID, new ArrayList<>());
        reservationServices.get(reservationID).add(service);

        System.out.println("Added service '" + service.serviceName +
                "' to Reservation ID: " + reservationID);
    }

    // Display services for reservation
    public void displayServices(String reservationID) {

        List<Service> services = reservationServices.get(reservationID);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected for Reservation " + reservationID);
            return;
        }

        System.out.println("\nServices for Reservation " + reservationID + ":");

        double totalCost = 0;

        for (Service s : services) {
            s.displayService();
            totalCost += s.price;
        }

        System.out.println("Total Add-On Cost: ₹" + totalCost);
    }
}

// ---------------- Main Application ----------------
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Book My Stay App - Version 7.0");
        System.out.println("Add-On Service Selection");
        System.out.println("=================================");

        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Example reservation IDs
        String reservation1 = "RES101";
        String reservation2 = "RES102";

        // Available services
        Service breakfast = new Service("Breakfast", 500);
        Service spa = new Service("Spa Access", 1500);
        Service airportPickup = new Service("Airport Pickup", 800);

        // Guests selecting services
        serviceManager.addService(reservation1, breakfast);
        serviceManager.addService(reservation1, spa);

        serviceManager.addService(reservation2, airportPickup);

        // Display services
        serviceManager.displayServices(reservation1);
        serviceManager.displayServices(reservation2);

        System.out.println("\nBooking and inventory remain unchanged.");
    }
}