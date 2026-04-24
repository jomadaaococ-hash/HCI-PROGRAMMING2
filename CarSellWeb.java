import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car {
    int id;
    int year;
    String make;
    String model;
    double price;
    int mileage;
    String transmission;
    String fuel;
    String description;

    public Car(int id, int year, String make, String model, double price, int mileage, String transmission, String fuel, String description) {
        this.id = id;
        this.year = year;
        this.make = make;
        this.model = model;
        this.price = price;
        this.mileage = mileage;
        this.transmission = transmission;
        this.fuel = fuel;
        this.description = description;
    }

    public void printSummary() {
        System.out.printf("[%d] %d %s %s - $%,.2f\n", id, year, make, model, price);
    }

    public void printDetails() {
        System.out.println("\n========================================");
        System.out.println("CAR DETAILS: " + year + " " + make + " " + model);
        System.out.println("========================================");
        System.out.printf("Price:        $%,.2f\n", price);
        System.out.println("Year:         " + year);
        System.out.println("Mileage:      " + mileage + " miles");
        System.out.println("Transmission: " + transmission);
        System.out.println("Fuel:         " + fuel);
        System.out.println("Description:  " + description);
        System.out.println("========================================");
    }
}

public class CarSellWeb {
    private static List<Car> inventory = new ArrayList<>();
    private static List<Car> viewHistory = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeInventory();
        showSplashScreen();
        mainMenu();
    }

    private static void initializeInventory() {
        inventory.add(new Car(1, 2024, "Mercedes-Benz", "S-Class", 115000, 500, "Automatic", "Gasoline", "The pinnacle of luxury sedans with state-of-the-art tech."));
        inventory.add(new Car(2, 2023, "Porsche", "911 GT3", 180000, 2000, "Manual", "Gasoline", "Track-ready performance with a naturally aspirated flat-six."));
        inventory.add(new Car(3, 2025, "Tesla", "Model S Plaid", 105000, 150, "Automatic", "Electric", "0-60 in under 2 seconds. The ultimate electric performance car."));
        inventory.add(new Car(4, 2022, "Range Rover", "Autobiography", 130000, 15000, "Automatic", "Hybrid", "Premium British luxury SUV with unmatched off-road capability."));
    }

    private static void showSplashScreen() {
        System.out.println("\n==========================================");
        System.out.println(" CARDEALERSHIPS");
        System.out.println(" Experience Luxury Car");
        System.out.println("============================================");
        System.out.println("Press ENTER to enter the showroom...");
        scanner.nextLine();
    }

    private static void mainMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- TOP NAV MENU ---");
            System.out.println("1. HOME");
            System.out.println("2. SEARCH CARS (View Collection)");
            System.out.println("3. SELL/TRADE");
            System.out.println("4. HISTORY");
            System.out.println("5. EXIT");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showHome();
                    break;
                case "2":
                    showSearchCars();
                    break;
                case "3":
                    showSellTrade();
                    break;
                case "4":
                    showHistory();
                    break;
                case "5":
                    System.out.println("Thank you for visiting CARDEALERSHIPS. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }
        }
    }

    private static void showHome() {
        System.out.println("\n=== HOME ===");
        System.out.println("FIND YOUR NEXT DREAM CAR");
        System.out.println("Browse our exclusive collection of premium vehicles.");
        System.out.println("Press ENTER to view the collection...");
        scanner.nextLine();
        showSearchCars();
    }

    private static void showSearchCars() {
        System.out.println("\n=== SEARCH CARS ===");
        System.out.print("Enter search keyword (or press ENTER to view all): ");
        String keyword = scanner.nextLine().toLowerCase();

        System.out.println("\n--- " + inventory.size() + " RESULTS --- (SORT BY: RECOMMENDED)");
        boolean found = false;
        
        for (Car car : inventory) {
            if (keyword.isEmpty() || car.make.toLowerCase().contains(keyword) || 
                car.model.toLowerCase().contains(keyword)) {
                car.printSummary();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No cars matched your search.");
            return;
        }

        System.out.print("\nEnter the ID of a car to view details (or '0' to go back): ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            if (id != 0) {
                viewCarModal(id);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Returning to menu.");
        }
    }

    private static void viewCarModal(int id) {
        Car selectedCar = null;
        for (Car car : inventory) {
            if (car.id == id) {
                selectedCar = car;
                break;
            }
        }

        if (selectedCar != null) {
            // Add to history list
            if (!viewHistory.contains(selectedCar)) {
                viewHistory.add(selectedCar);
            }

            selectedCar.printDetails();
            System.out.println("\nActions:");
            System.out.println("1. BUY NOW");
            System.out.println("2. INQUIRE");
            System.out.println("3. CLOSE (Go Back)");
            System.out.print("Select action: ");
            
            String action = scanner.nextLine();
            if (action.equals("1")) {
                System.out.println(">>> Congratulations! Proceeding to checkout for the " + selectedCar.model + "...");
            } else if (action.equals("2")) {
                System.out.println(">>> Redirecting to inquiry form for the " + selectedCar.model + "...");
            }
        } else {
            System.out.println("Car ID not found.");
        }
    }

    private static void showSellTrade() {
        System.out.println("\n=== SELL OR TRADE YOUR CAR ===");
        System.out.print("Full Name: ");
        String name = scanner.nextLine();
        System.out.print("Email Address: ");
        String email = scanner.nextLine();
        System.out.print("Car Details (Make, Model, Year, Condition): ");
        String details = scanner.nextLine();

        if (!name.isEmpty() && !email.isEmpty() && !details.isEmpty()) {
            System.out.println("\n>>> Alert: Inquiry Submitted Successfully! Thank you, " + name + ".");
        } else {
            System.out.println("\n>>> Error: Please fill out all fields.");
        }
    }

    private static void showHistory() {
        System.out.println("\n=== YOUR VIEWING HISTORY ===");
        if (viewHistory.isEmpty()) {
            System.out.println("No vehicles viewed yet.");
        } else {
            for (Car car : viewHistory) {
                car.printSummary();
            }
        }
    }
}
