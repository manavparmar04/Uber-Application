import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

// Name: Manav Parmar
// StudentID: 501180658
import java.util.Queue;

import javax.management.RuntimeMBeanException;

// import Driver.Status;

/*
 * 
 * This class contains the main logic of the system.
 * 
 *  It keeps track of all users, drivers and service requests (RIDE or DELIVERY)
 * 
 */
public class TMUberSystemManager {
  // private ArrayList<User> users;
  private Map<String, User> users;
  private ArrayList<Driver> drivers;

  // private ArrayList<TMUberService> serviceRequests; REPLACED THIS WITH QUEUE
  private Queue<TMUberService>[] serviceRequests;

  public double totalRevenue; // Total revenues accumulated via rides and deliveries

  // Rates per city block
  private static final double DELIVERYRATE = 1.2;
  private static final double RIDERATE = 1.5;
  // Portion of a ride/delivery cost paid to the driver
  private static final double PAYRATE = 0.1;

  // These variables are used to generate user account and driver ids
  int userAccountId = 9010;
  int driverId = 7005;

  public TMUberSystemManager() throws IOException {
    // users = new ArrayList<User>();
    users = new HashMap<>();
    drivers = new ArrayList<Driver>();
    // serviceRequests = new ArrayList<TMUberService>(); REPLACING WITH LIST OF 4
    // QUEUES

    serviceRequests = new LinkedList[4];
    for (int i = 0; i < 4; i++) {
      serviceRequests[i] = new LinkedList<TMUberService>();
    }

    TMUberRegistered.loadPreregisteredUsers("users.txt");
    TMUberRegistered.loadPreregisteredDrivers("drivers.txt");

    totalRevenue = 0;
  }

  // General string variable used to store an error message when something is
  // invalid
  // (e.g. user does not exist, invalid address etc.)
  // The methods below will set this errMsg string and then return false
  String errMsg = null;

  public String getErrorMessage() {
    return errMsg;
  }

  // Given user account id, find user in list of users
  // Return null if not found
  public User getUser(String accountId) {
    for (User user : users.values()) {
      if (user.getAccountId().equals(accountId)) {
        return user;
      }
    }
    return null; // person not found
  }

  public Driver getDriver(String driverId){
    for (Driver driver: drivers){
      if (driver.getId().equals(driverId)){
        return driver;
      }
    }
    return null;
  }

  // Check for duplicate user
  private boolean userExists(User user) {
    for (int i = 0; i < users.size(); i++) {
      User use = users.get(i);
      if (use.equals(user)) {
        return true;
      }

    }
    return false;
  }

  // Check for duplicate driver
  private boolean driverExists(Driver driver) {
    for (int i = 0; i < drivers.size(); i++) {
      Driver drive = drivers.get(i);
      if (drive.equals(driver)) {
        return true;
      }

    }
    return false;
  }

  // Given a user, check if user ride/delivery request already exists in service
  // requests
  private boolean existingRequest(TMUberService req) {
    // Fill in the code
    for (int i = 0; i < serviceRequests.length; i++) {
      Queue<TMUberService> queue = serviceRequests[i];
      for (TMUberService reqs : queue) {
        if (reqs.equals(req)) {
          return true;
        }
      }

    }
    return false;
  }

  // Calculate the cost of a ride or of a delivery based on distance
  private double getDeliveryCost(int distance) {
    return distance * DELIVERYRATE;
  }

  private double getRideCost(int distance) {
    return distance * RIDERATE;
  }

  // Go through all drivers and see if one is available
  // Choose the first available driver
  // Return null if no available driver
  private Driver getAvailableDriver() {
    // Fill in the code
    for (int i = 0; i < drivers.size(); i++) {
      Driver driver = drivers.get(i);
      if (driver.getStatus() == Driver.Status.AVAILABLE) {
        return driver;

      }
      // Driver.Status.AVAILABLE Driver refers to the class where enum is used - > for
      // future reference

    }
    return null;
  }

  // Print Information (printInfo()) about all registered users in the system
  public void listAllUsers() {
    System.out.println();

    int index = 1;
    for (User user : users.values()) {
      System.out.printf("%-2s. ", index);
      user.printInfo();
      System.err.println();
      index++;
    }
  }

  // Print Information (printInfo()) about all registered drivers in the system
  public void listAllDrivers() {
    // Fill in the code
    System.err.println();

    for (int i = 0; i < drivers.size(); i++) {
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      drivers.get(i).printInfo();
      System.out.println();

    }
  }

  // Print Information (printInfo()) about all current service requests
  public void listAllServiceRequests() {
    // Fill in the code
    System.out.println();

    for (int i = 0; i < serviceRequests.length; i++) {
      System.out.println("Zone " + i);
      System.out.println("==========================================");
      System.out.println();
      Queue<TMUberService> queue = serviceRequests[i];
      for (TMUberService services : queue) {
        services.printInfo();
        System.out.println();
      }
    }
  }

  // Add a new user to the system
  public void registerNewUser(String name, String address, double wallet) throws InvalidAddressException,
      InvalidMoneyInWalletException, InvalidUserNameException, UserAlreadyExistsInSystemException {
    // Fill in the code. Before creating a new user, check paramters for validity
    // See the assignment document for list of possible erros that might apply
    // Write the code like (for example):
    // if (address is *not* valid)
    // {
    // set errMsg string variable to "Invalid Address "
    // return false
    // }
    // If all parameter checks pass then create and add new user to array list users
    // Make sure you check if this user doesn't already exist!

    // Checking to see if the address is valid
    // if (address == null || address.isEmpty()) {
    // errMsg = "Invalid Address";
    // return false;
    // }

    // Checking if address is valid using CityMap class validAddress method
    if (!CityMap.validAddress(address)) {
      throw new InvalidUserAddressException("Invalid User Address");

    }

    // Checking to see if the wallet has sufficient funds
    if (wallet < 0) {
      throw new InvalidMoneyInWalletException("Invalid Money in Wallet");

    }

    // Checking to see if the name is valid
    if (name == null || name.isEmpty()) {
      throw new InvalidUserNameException("Invalid User Name");

    }

    // Checking to see if the name already exist in the system
    for (int i = 0; i < users.size(); i++) {
      User user = users.get(i);
      if (user.getName().equals(name) && user.getAddress().equals(address)) {
        throw new UserAlreadyExistsInSystemException("User Already Exists in System");

      }
    }
    // Creating new user with a different accountId for each user
    User newUser = new User(String.valueOf(userAccountId++), name, address, wallet);
    users.put(newUser.getAccountId(), newUser);

  }

  // Add a new driver to the system
  public void registerNewDriver(String name, String carModel, String carLicencePlate, String address)
      throws InvalidDriverNameException,
      InvalidCarModelExeption, InvalidCarLisencePlateException, DriverAlreadyExistInSystemException {
    // Fill in the code - see the assignment document for error conditions
    // that might apply. See comments above in registerNewUser

    // If name is empty then it is invalid driver name
    if (name == null || name.isEmpty()) {
      throw new InvalidDriverNameException("Invalid Driver Name");
    }

    // If carmodel is empty then it is invalid car model
    if (carModel == null || carModel.isEmpty()) {
      throw new InvalidCarModelExeption("Invalid Car Model");

    }

    // If car plate is empty then it is invalid plate number
    if (carLicencePlate == null || carLicencePlate.isEmpty()) {
      throw new InvalidCarLisencePlateException("Invalid Car Licence Plate");

    }

    // Going through drivers list and checking if an existing driver with the same
    // name, car and plate exist
    for (int i = 0; i < drivers.size(); i++) {
      Driver driver = drivers.get(i);
      if (driver.getName().equals(name) && driver.getCarModel().equals(carModel)
          && driver.getLicensePlate().equals(carLicencePlate)) {
        throw new DriverAlreadyExistInSystemException("Driver Already Exists in System");

      }
    }

    // Creating new driver reference and adding it into drivers list
    Driver newDriver = new Driver(carLicencePlate, name, carModel, carModel, carLicencePlate, driverId++);
    drivers.add(newDriver);
  }

  // Request a ride. User wallet will be reduced when drop off happens
  public void requestRide(String accountId, String from, String to)
      throws InvalidRequestException, UserAccountNotFoundException, InvalidAddressException, InsufficentFundsException,
      NoDriverAvailableException, InsufficentTravelDistanceException, UserAlreadyExistsInSystemException {
    // Check for valid parameters
    // Use the account id to find the user object in the list of users
    // Get the distance for this ride
    // Note: distance must be > 1 city block!
    // Find an available driver
    // Create the TMUberRide object
    // Check if existing ride request for this user - only one ride request per user
    // at a time!
    // Change driver status
    // Add the ride request to the list of requests
    // Increment the number of rides for this user

    // Checking for any invalid requests
    if (accountId == null || accountId.isEmpty() || from == null || to == null || from.isEmpty() || to.isEmpty()) {
      throw new InvalidRequestException("Invalid Request");

    }

    // If the method gives null then no user account is found
    if (getUser(accountId) == null) {
      throw new UserAccountNotFoundException("User Account Not Found");

    }

    // Using validAddress method to take from address
    if (CityMap.validAddress(from) == false) {
      throw new InvalidAddressException("Invalid Address");

    }

    // Using validAddress method to take to address
    if (CityMap.validAddress(to) == false) {
      throw new InvalidAddressException("Invalid Address");

    }

    // Using getDistance method to check travel distance
    if (CityMap.getDistance(from, to) <= 1) {
      throw new InsufficentFundsException("Insufficient Travel Distance");

    }

    // If this method gives null, no drivers are available
    if (getAvailableDriver() == null) {
      throw new NoDriverAvailableException("No Drivers Available");

    }

    // If the user wallet is less than zero, it is not enough funds
    User user = getUser(accountId);
    if (user.getWallet() <= 0) {
      throw new InsufficentFundsException("Insufficent Funds");

    }

    // Using this method to check if user has enough funds to travel said distance
    int distance = CityMap.getDistance(from, to);
    double cost = distance * RIDERATE;

    if (user.getWallet() < cost) {
      throw new InsufficentTravelDistanceException("Insufficent Travel Funds");
    }

    // Using for loop to iterate over service requests checking if the user is
    // already on it
    for (int i = 0; i < serviceRequests.length; i++) {
      Queue<TMUberService> queue = serviceRequests[i];
      for (TMUberService service : queue) {
        if (service.getUser().equals(user)) {
          throw new UserAlreadyExistsInSystemException("User Already Has Ride Request");

        }

      }

    }
    int cityZone = CityMap.getCityZone(from); // \ Getting cityzone from location

    // Creating a new reference ride, adding it to requests list and changing driver
    // status to DRIVING
    TMUberRide newRide = new TMUberRide(getAvailableDriver(), from, to, user, driverId, DELIVERYRATE);
    Driver driver = getAvailableDriver();
    driver.setStatus(Driver.Status.DRIVING);
    serviceRequests[cityZone].add(newRide); // \ Adding service request based on appropriate queue/ cityzone
    // Also incrememting user rides
    user.addRide();

    // ISSUE IS ADDRESS IS NOT BEEING IN THE CORRECT PLACE, FIX WHEN U START AGAIN
    // TOM0

  }

  // Request a food delivery. User wallet will be reduced when drop off happens
  public void requestDelivery(String accountId, String from, String to, String restaurant, String foodOrderId)
      throws InvalidRequestException, UserAccountNotFoundException, NoDriverAvailableException,
      UserAlreadyHasDeliveryReqExeption, InvalidAddressException, InsufficentTravelDistanceException,
      InsufficentFundsException {
    // See the comments above and use them as a guide
    // For deliveries, an existing delivery has the same user, restaurant and food
    // order id
    // Increment the number of deliveries the user has had

    // If any is empty or null then it is an invalid request
    if (accountId.isEmpty() || accountId == null || from.isEmpty() || to.isEmpty() || from == null || to == null
        || restaurant == null || restaurant.isEmpty() || foodOrderId == null || foodOrderId.isEmpty()) {

      throw new InvalidRequestException("Invalid Request");

    }
    // If is null then user account is not found
    if (getUser(accountId) == null) {
      throw new UserAccountNotFoundException("User Account Not Found");

    }
    // If driver is null then no driver is available
    if (getAvailableDriver() == null) {
      throw new NoDriverAvailableException("No Available Driver");

    }

    // Iterating over service reqest list to check if restuarant, food order id and
    // accountID are the same, then the user already requested the said food order
    for (int i = 0; i < serviceRequests.length; i++) {
      Queue<TMUberService> queue = serviceRequests[i];
      for (TMUberService services : queue) {
        User user = getUser(accountId);
        if (services instanceof TMUberDelivery) {
          TMUberDelivery service = (TMUberDelivery) services;
          if (service.getRestaurant().equals(restaurant) && service.getFoodOrderId().equals(foodOrderId)
              && services.getUser().equals(user)) {
            throw new UserAlreadyHasDeliveryReqExeption(
                "User Already Has Delivery Request at Restaurant with this Food Order");

          }
        }

      }

    }

    // Using validAdress method to check if address is valid
    if (CityMap.validAddress(from) == false) {
      throw new InvalidAddressException("Invalid Address");

    }

    // Using validAdress method to check if address is valid
    if (CityMap.validAddress(to) == false) {
      throw new InvalidAddressException("Invalid Address");
    }

    // Using getDistance method to check if travel distance
    if (CityMap.getDistance(from, to) <= 1) {
      throw new InsufficentTravelDistanceException("Insufficient Travel Distance");

    }
    // Using method below to check if user has enough funds to said distance
    User user = getUser(accountId);
    int distance = CityMap.getDistance(from, to);
    double cost = distance * DELIVERYRATE;

    if (user.getWallet() < cost) {
      throw new InsufficentFundsException("Insufficent Travel Funds");

    }
    int cityZone = CityMap.getCityZone(from);

    // Creating new reference delivery, setting driver status to driver and adding
    // it to requests list
    TMUberDelivery newDelivery = new TMUberDelivery(getAvailableDriver(), from, to, user, driverId, DELIVERYRATE,
        restaurant, foodOrderId);

    Driver driver = getAvailableDriver();
    driver.setStatus(Driver.Status.DRIVING);
    serviceRequests[cityZone].add(newDelivery); // \ Adding service request based on appropriate queue/ cityzone
    getUser(accountId).addDelivery();

  }

  // Cancel an existing service request.
  // parameter int request is the index in the serviceRequests array list
  public void cancelServiceRequest(int zone, int request)
      throws InvalidZoneNumberException, InvalidRequestNumberException, RequestNotFoundException {
    // Check if valid zone number
    if (zone < 0 || zone > 3) {
      throw new InvalidZoneNumberException("Invalid Zone #");

    }
    if (request < 0 || request > serviceRequests.length) {
      throw new InvalidRequestNumberException("Invalid Request #");

    }

    Queue<TMUberService> queue = serviceRequests[zone];
    // Create an iterator
    Iterator<TMUberService> iterator = queue.iterator();
    int index = 0;
    while (iterator.hasNext()) {
      TMUberService service = iterator.next();
      // Check if the service matches the request
      if (index == request) {
        // If it does, remove it
        iterator.remove();
        // Decrement number of rides or deliveries for this user
        if (service instanceof TMUberRide) {
          service.getUser().cancelRide();
        } else if (service instanceof TMUberDelivery) {
          service.getUser().cancelDelivery();
        }

      }
      index++;
    }
    throw new RequestNotFoundException("Request not found");
  }

  // Drop off a ride or a delivery. This completes a service.
  // parameter request is the index in the serviceRequests array list
  public void dropOff(String driverId) throws InvalidRequestException, DriverNotAvailableException {
    if (driverId.isEmpty() || driverId.equals(null)) {
      throw new InvalidRequestException("Invalid Request");
    }
    Driver driver = null;
    for (Driver id : drivers) {
      if (id.getId().equals(driverId)) {
        driver = id;
        break;
      }
    }
    if (driver == null || driver.getStatus() != Driver.Status.DRIVING) {
      throw new DriverNotAvailableException("Driver is currrently not available");
    }

    // private double getDeliveryCost(int distance) {
    // return distance * DELIVERYRATE;
    // }

    // private double getRideCost(int distance) {
    // return distance * RIDERATE;
    // }

    TMUberService service = driver.getService();
    double cost = 0.0;
    if (service instanceof TMUberRide) {

      cost = getRideCost(service.getDistance());

    } else {
      cost = getDeliveryCost(service.getDistance());

    }

    totalRevenue += cost;
    driver.pay(cost * PAYRATE);
    totalRevenue -= cost * PAYRATE;

    User user = service.getUser();
    user.payForService(cost);

    driver.setService(null);
    driver.setStatus(Driver.Status.AVAILABLE);
    driver.setAddress(service.getTo());
    driver.setZone(CityMap.getCityZone(service.getTo()));

    for (Queue<TMUberService> queue : serviceRequests) {
      if (queue.contains(service)) {
        queue.remove(service);
        break;
      }
    }

  }

  public void pickup(String driverId) throws DriverNotFoundException, NoServiceRequestException {
    Driver driver = null;
    for (Driver d : drivers) {
      if (d.getId().equals(driverId)) {
        driver = d;
        break;
      }
    }

    if (driver == null) {
      throw new DriverNotFoundException("Driver with ID " + driverId + " was not found");
    }

    int zone = CityMap.getCityZone(driver.getAddress());
    if (serviceRequests[zone].isEmpty()) {
      throw new NoServiceRequestException("No request in  zone " + zone);
    }

    TMUberService service = serviceRequests[zone].poll();
    driver.setService(service);
    driver.setStatus(Driver.Status.DRIVING);
    driver.setAddress(service.getFrom());

  }

  public void driveTo(String driverId, String address)
      throws DriverNotFoundException, InvalidAddressException, DriverNotAvailableException {
    Driver driver = null;
    for (Driver d : drivers) {
      if (d.getId().equals(driverId)) {
        driver = d;
      }
    }

    if (driver == null) {
      throw new DriverNotFoundException("Driver with ID " + driverId + " was not found");
    }

    if (driver.getStatus() != Driver.Status.AVAILABLE && !CityMap.validAddress(address)) {
      throw new DriverNotAvailableException("Driver not available");
    }

    if (!CityMap.validAddress(address)) {
      throw new InvalidAddressException("Invalid Address");
    }

    driver.setAddress(address);
    driver.setZone(CityMap.getCityZone(address));
  }

  public void setUsers(ArrayList<User> userList) {
    users.clear();

    for (User user : userList) {
      users.put(user.getAccountId(), user);
    }
  }

  public void setDrivers(ArrayList<Driver> driver) {
    this.drivers = driver;
  }

  // Sort users by name
  // Then list all users
  public void sortByUserName() {
    List<User> userList = new ArrayList<>(users.values());
    userList.sort(new NameComparator());

    for (User user : userList) {
      user.printInfo();
      System.err.println();
    }
  }

  // Helper class for method sortByUserName
  private class NameComparator implements Comparator<User> {
    public int compare(User user1, User user2) {
      return user1.getName().compareTo(user2.getName());
    }

  }

  // Sort users by number amount in wallet
  // Then ist all users
  public void sortByWallet() {
    List<User> userList = new ArrayList<>(users.values());
    userList.sort(new UserWalletComparator());

    for (User user : userList) {
      user.printInfo();
      System.err.println();
    }

    // Sorting user by wallet then printing it out

  }

  // Helper class for use by sortByWallet
  private class UserWalletComparator implements Comparator<User> {
    public int compare(User user1, User user2) {
      return Double.compare(user1.getWallet(), user2.getWallet());
    }

  }

  // Sort trips (rides or deliveries) by distance
  // Then list all current service requests
  /*
   * public void sortByDistance() {
   * serviceRequests.sort(new DistanceComparator());
   * 
   * for (int i = 0; i < serviceRequests.size(); i++) {
   * System.out.printf("%-2s. ", i + 1);
   * serviceRequests.get(i).printInfo();
   * System.out.println();
   * }
   * 
   * // sorting requests by user distance then printing it
   * 
   * }
   */

  public void sortByDistance() {
    List<TMUberService> services = new ArrayList<>();
    for (Queue<TMUberService> queue : serviceRequests) {
      services.addAll(queue);
    }

    services.sort(new DistanceComparator());

    for (int i = 0; i < services.size(); i++) {
      System.out.printf("%-2s. ", i + 1);
      services.get(i).printInfo();
      System.out.println();
    }

  }
  // \ FIX THIS LATER BUT SO FAR THIS IS COMMENTED OUT BECAUSE OF THE ERRORS I AM
  // TOO LAXY TO HANDLE

  public class DistanceComparator implements Comparator<TMUberService> {
    public int compare(TMUberService sr1, TMUberService sr2) {
      String from1 = sr1.getFrom();
      String to1 = sr1.getTo();
      int distance1 = CityMap.getDistance(from1, to1);
      String from2 = sr2.getFrom();
      String to2 = sr2.getTo();
      int distance2 = CityMap.getDistance(from2, to2);
      return Integer.compare(distance1, distance2);
    }
  }

}

class InvalidUserNameException extends RuntimeException {
  public InvalidUserNameException(String message) {
    super(message);
  }
}

class InvalidUserAddressException extends RuntimeException {
  public InvalidUserAddressException(String message) {
    super(message);
  }
}

class InvalidRequestNumberException extends RuntimeException {
  public InvalidRequestNumberException(String message) {
    super(message);
  }
}

class InvalidMoneyInWalletException extends RuntimeException {
  public InvalidMoneyInWalletException(String message) {
    super(message);
  }
}

class UserAlreadyExistsInSystemException extends RuntimeException {
  public UserAlreadyExistsInSystemException(String message) {
    super(message);
  }
}

class InvalidDriverNameException extends RuntimeException {
  public InvalidDriverNameException(String message) {
    super(message);
  }
}

class InvalidCarModelExeption extends RuntimeException {
  public InvalidCarModelExeption(String message) {
    super(message);
  }
}

class InvalidCarLisencePlateException extends RuntimeException {
  public InvalidCarLisencePlateException(String message) {
    super(message);
  }
}

class DriverAlreadyExistInSystemException extends RuntimeException {
  public DriverAlreadyExistInSystemException(String message) {
    super(message);
  }
}

class UserAccountNotFoundException extends RuntimeException {
  public UserAccountNotFoundException(String message) {
    super(message);
  }
}

class UserAlreadyHasRideRequestException extends RuntimeException {
  public UserAlreadyHasRideRequestException(String message) {
    super(message);
  }
}

class InsufficentTravelDistanceException extends RuntimeException {
  public InsufficentTravelDistanceException(String message) {
    super(message);
  }
}

class InvalidAddressException extends RuntimeException {
  public InvalidAddressException(String message) {
    super(message);
  }
}

class InsufficentFundsException extends RuntimeException {
  public InsufficentFundsException(String message) {
    super(message);
  }
}

class NoDriverAvailableException extends RuntimeException {
  public NoDriverAvailableException(String message) {
    super(message);
  }
}

class UserAlreadyHasDeliveryReqExeption extends RuntimeException {
  public UserAlreadyHasDeliveryReqExeption(String message) {
    super(message);
  }
}

class InvalidRequestException extends RuntimeException {
  public InvalidRequestException(String message) {
    super(message);
  }
}

class InvalidZoneNumberException extends RuntimeException {
  public InvalidZoneNumberException(String message) {
    super(message);
  }
}

class RequestNotFoundException extends RuntimeException {
  public RequestNotFoundException(String message) {
    super(message);
  }
}

class DriverNotAvailableException extends RuntimeException {
  public DriverNotAvailableException(String message) {
    super(message);
  }
}

class DriverNotFoundException extends RuntimeException {
  public DriverNotFoundException(String message) {
    super(message);
  }
}

class NoServiceRequestException extends RuntimeException {
  public NoServiceRequestException(String message) {
    super(message);
  }
}
