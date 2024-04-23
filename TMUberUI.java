import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

// Name: Manav Parmar
// StudentID: 501180658

// Simulation of a Simple Command-line based Uber App 

// This system supports "ride sharing" service and a delivery service

public class TMUberUI {
  public static void main(String[] args) throws IOException {
    // Create the System Manager - the main system code is in here

    TMUberSystemManager tmuber = new TMUberSystemManager();
    // System.out.println("Aaaaafwiuehfiuwgebfuiwebfuwbefiuwbefiuwebfiubwygrewliufwjkergfwergerdd");

    Scanner scanner = new Scanner(System.in);
    System.out.print(">");

    // Process keyboard actions
    while (scanner.hasNextLine()) {
      String action = scanner.nextLine();

      if (action == null || action.equals("")) {
        System.out.print("\n>");
        continue;
      }
      // Quit the App
      else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
        return;
      // Print all the registered driver

      // ######## New Code
      // ####################################################################################################################
      else if (action.equalsIgnoreCase("PICKUP")) {
        System.out.print("Drivers Id: ");
        String driverId = "";
        if (scanner.hasNextLine()) {
          driverId = scanner.nextLine();
        }
        try {
          Driver driver = tmuber.getDriver(driverId);
          tmuber.pickup(driverId);
          System.err.println("Driver " + driverId + " Picking up in zone " + driver.getZone()); // THIS NEEDS TO PROVIDE
                                                                                                // ZONE NUMBER AT THE
                                                                                                // END SO
          // COME BACK TO THIS LATER ... "picking uo in zone
          // __"
        } catch (DriverNotFoundException e) {
          System.out.println(e.getMessage());
        } catch (NoServiceRequestException e) {
          System.out.println(e.getMessage());
        }
      }

      else if (action.equalsIgnoreCase("LOADUSERS")) {
        String filename = "";
        System.out.print("Filename: ");
        if (scanner.hasNextLine()) {
          filename = scanner.nextLine();
          System.out.println("Users loaded");
        }
        try {
          ArrayList<User> users = TMUberRegistered.loadPreregisteredUsers(filename);
          tmuber.setUsers(users);

        } catch (FileNotFoundException e) {
          System.out.println("File not found");

        } catch (IOException e) {
          System.out.println("Error occured, returning from program");
          return;
        }
      }

      else if (action.equalsIgnoreCase("LOADDRIVERS")) {
        String filename = "";
        System.out.print("Filename: ");
        if (scanner.hasNextLine()) {
          filename = scanner.nextLine();
          System.out.println("Drivers loaded");
        }
        try {
          ArrayList<Driver> driver = TMUberRegistered.loadPreregisteredDrivers(filename);
          tmuber.setDrivers(driver);
        } catch (FileNotFoundException e) {
          System.out.println("File not found");

        } catch (IOException e) {
          System.out.println("Error occured, returing from program");
          return;
        }
      }

      else if (action.equalsIgnoreCase("DRIVETO")) {
        String driverId = "";
        System.out.print("Driver Id: ");
        if (scanner.hasNextLine()) {
          driverId = scanner.nextLine();
        }
        String address = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine()) {
          address = scanner.nextLine();
        }
        try {
          tmuber.driveTo(driverId, address);
          Driver driver = tmuber.getDriver(driverId);
          System.out.print("Driver is now in zone " + driver.getZone());
        } catch (DriverNotFoundException e) {
          System.out.println(e.getMessage());
        } catch (InvalidAddressException e) {
          System.out.println(e.getMessage());
        } catch (DriverNotAvailableException e) {
          System.out.println(e.getMessage());
        }

      }

      // ######## New Code ENDING
      // ##############################################################################################################################################################
      else if (action.equalsIgnoreCase("DRIVERS")) // List all drivers
      {
        tmuber.listAllDrivers();
      }
      // Print all the registered users
      else if (action.equalsIgnoreCase("USERS")) // List all users
      {
        tmuber.listAllUsers();
      }
      // Print all current ride requests or delivery requests
      else if (action.equalsIgnoreCase("REQUESTS")) // List all requests
      {
        tmuber.listAllServiceRequests();
      }
      // Register a new driver
      else if (action.equalsIgnoreCase("REGDRIVER")) {
        // ######## NEW CODE
        // ########################################################################################################
        String address = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine()) {
          address = scanner.nextLine();
        }

        // ######## NEW CODE ENDING
        // ########################################################################################################
        String name = "";
        System.out.print("Name: ");
        if (scanner.hasNextLine()) {
          name = scanner.nextLine();
        }
        String carModel = "";
        System.out.print("Car Model: ");
        if (scanner.hasNextLine()) {
          carModel = scanner.nextLine();
        }
        String license = "";
        System.out.print("Car License: ");
        if (scanner.hasNextLine()) {
          license = scanner.nextLine();
        }
        try {
          tmuber.registerNewDriver(name, carModel, license, address);
          System.out.printf("Driver: %-15s Car Model: %-15s License Plate: %-10s Address: %-10s ", name, carModel,
              license, address); // \ Addressing address in the parameter
        } catch (InvalidDriverNameException e) {
          System.out.println(e.getMessage());
        } catch (InvalidCarModelExeption e) {
          System.out.println(e.getMessage());
        } catch (InvalidCarLisencePlateException e) {
          System.out.println(e.getMessage());
        } catch (DriverAlreadyExistInSystemException e) {
          System.out.println(e.getMessage());
        }

      }
      // Register a new user
      else if (action.equalsIgnoreCase("REGUSER")) {
        String name = "";
        System.out.print("Name: ");
        if (scanner.hasNextLine()) {
          name = scanner.nextLine();
        }
        String address = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine()) {
          address = scanner.nextLine();
        }
        double wallet = 0.0;
        System.out.print("Wallet: ");
        if (scanner.hasNextDouble()) {
          wallet = scanner.nextDouble();
          scanner.nextLine(); // consume nl!! Only needed when mixing strings and int/double
        }
        try {
          tmuber.registerNewUser(name, address, wallet);
          System.out.printf("User: %-15s Address: %-15s Wallet: %2.2f", name, address, wallet);

        } catch (InvalidUserAddressException e) {
          System.out.println(e.getMessage());
        } catch (InvalidMoneyInWalletException e) {
          System.out.println(e.getMessage());
        } catch (InvalidUserNameException e) {
          System.out.println(e.getMessage());
        } catch (UserAlreadyExistsInSystemException e) {
          System.out.println(e.getMessage());
        }

      }
      // Request a ride
      else if (action.equalsIgnoreCase("REQRIDE")) {
        // Get the following information from the user (on separate lines)
        // Then use the TMUberSystemManager requestRide() method properly to make a ride
        // request
        // "User Account Id: " (string)
        // "From Address: " (string)
        // "To Address: " (string)

        String accountId = "";
        System.out.print("User Account Id: ");
        accountId = scanner.nextLine();

        String from = "";
        System.out.print("From Address: ");
        from = scanner.nextLine();

        String to = "";
        System.out.print("To Address: ");
        to = scanner.nextLine();

        try {
          tmuber.requestRide(accountId, from, to);
          User user = tmuber.getUser(accountId);
          // System.out.printf("AccountID: %-10s To: %-15s From: %-10s", accountId, to,
          // from);
          System.out.printf("RIDE for: %-15s From: %-15s To: %-10s", user.getName(), from, to);
          // System.out.printf("AccountID: %-10s To: %-15s From: %-10s", accountId, to,
          // from);

        } catch (InvalidRequestException e) {
          System.out.println(e.getMessage());
        } catch (UserAccountNotFoundException e) {
          System.out.println(e.getMessage());
        } catch (InvalidAddressException e) {
          System.out.println(e.getMessage());
        } catch (InsufficentFundsException e) {
          System.out.println(e.getMessage());
        } catch (NoDriverAvailableException e) {
          System.out.println(e.getMessage());
        } catch (InsufficentTravelDistanceException e) {
          System.err.println(e.getMessage());
        } catch (UserAlreadyExistsInSystemException e) {
          System.out.println(e.getMessage());
        }

      }

      // Request a food delivery
      else if (action.equalsIgnoreCase("REQDLVY")) {
        // Get the following information from the user (on separate lines)
        // Then use the TMUberSystemManager requestDelivery() method properly to make a
        // ride request
        // "User Account Id: " (string)
        // "From Address: " (string)
        // "To Address: " (string)
        // "Restaurant: " (string)
        // "Food Order #: " (string)

        String accountId = "";
        System.out.print("User Account Id: ");
        accountId = scanner.nextLine();

        String from = "";
        System.out.print("From Address: ");
        from = scanner.nextLine();

        String to = "";
        System.out.print("To Address: ");
        to = scanner.nextLine();

        String restaurant = "";
        System.out.print("Restaurant: ");
        restaurant = scanner.nextLine();

        String foodOrderId = "";
        System.out.print("Food Order #: ");
        foodOrderId = scanner.nextLine();

        try {

          tmuber.requestDelivery(accountId, from, to, restaurant, foodOrderId);
          User user = tmuber.getUser(accountId);

          System.out.printf("DELIVERY for: %-10s From: %-15s To: %-10s Restaurant: %-15s Food Order #: %-15s ",
              user.getName(),
              from, to, restaurant, foodOrderId);
          // System.out.println("Ride Request failed: " + tmuber.getErrorMessage());
        } catch (InvalidRequestException e) {
          System.out.println(e.getMessage());
        } catch (UserAccountNotFoundException e) {
          System.out.println(e.getMessage());
        } catch (NoDriverAvailableException e) {
          System.out.println(e.getMessage());
        } catch (UserAlreadyHasDeliveryReqExeption e) {
          System.out.println(e.getMessage());
        } catch (InvalidAddressException e) {
          System.out.println(e.getMessage());
        } catch (InsufficentTravelDistanceException e) {
          System.out.println(e.getMessage());
        } catch (InsufficentFundsException e) {
          System.out.println(e.getMessage());
        }

      }
      // Sort users by name
      else if (action.equalsIgnoreCase("SORTBYNAME")) {
        tmuber.sortByUserName();
      }
      // Sort users by number of ride they have had
      else if (action.equalsIgnoreCase("SORTBYWALLET")) {
        tmuber.sortByWallet();
      }
      // Sort current service requests (ride or delivery) by distance
      else if (action.equalsIgnoreCase("SORTBYDIST")) {
        tmuber.sortByDistance();
      }
      // Cancel a current service (ride or delivery) request
      else if (action.equalsIgnoreCase("CANCELREQ")) {
        // ######## NEW CODE
        // ##################################################################################################################
        int zoneNumber = -1;
        System.out.println("Zone # ");
        if (scanner.hasNextLine()) {
          zoneNumber = scanner.nextInt();
          scanner.nextLine();
        }

        // ######## NEW CODE ENDING
        // ##################################################################################################################
        int request = -1;
        System.out.print("Request #: ");
        if (scanner.hasNextInt()) {
          request = scanner.nextInt();
          scanner.nextLine(); // consume nl character
        }
        try {
          tmuber.cancelServiceRequest(request, zoneNumber);
          System.out.println("Service request #" + request + " in zone" + zoneNumber + " cancelled");
        } catch (InvalidZoneNumberException e) {
          System.out.println(e.getMessage());
        } catch (InvalidRequestNumberException e) {
          System.out.println(e.getMessage());
        } catch (RequestNotFoundException e) {
          System.out.println(e.getMessage());
        }

      }
      // Drop-off the user or the food delivery to the destination address
      else if (action.equalsIgnoreCase("DROPOFF")) {
        // ###### NEW CODE
        // ############################################################################################
        String driverId = "";
        System.out.print("Driver Id: ");
        if (scanner.hasNextLine()) {
          driverId = scanner.nextLine();
          //scanner.nextLine(); // consume nl
        }
        try {
          tmuber.dropOff(driverId);
          System.out.print("Driver #" + driverId + " Dropping off ");

        } catch (InvalidRequestException e) {
          System.out.println(e.getMessage());
        } catch (DriverNotAvailableException e) {
          System.out.println(e.getMessage());
        }

        // ###### NEW CODE ENDING
        // ########################################################################
      }
      // Get the Current Total Revenues
      else if (action.equalsIgnoreCase("REVENUES")) {
        System.out.println("Total Revenue: " + tmuber.totalRevenue);
      }
      // Unit Test of Valid City Address
      else if (action.equalsIgnoreCase("ADDR")) {
        String address = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine()) {
          address = scanner.nextLine();
        }
        System.out.print(address);
        if (CityMap.validAddress(address))
          System.out.println("\nValid Address");
        else
          System.out.println("\nBad Address");
      }
      // Unit Test of CityMap Distance Method
      else if (action.equalsIgnoreCase("DIST")) {
        String from = "";
        System.out.print("From: ");
        if (scanner.hasNextLine()) {
          from = scanner.nextLine();
        }
        String to = "";
        System.out.print("To: ");
        if (scanner.hasNextLine()) {
          to = scanner.nextLine();
        }
        System.out.print("\nFrom: " + from + " To: " + to);
        System.out.println("\nDistance: " + CityMap.getDistance(from, to) + " City Blocks");
      }

      System.out.print("\n>");
    }
  }
}
