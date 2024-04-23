/*
// Name: Manav Parmar
// StudentID: 501180658
 * 
 * General class that simulates a ride or a delivery in a simple Uber app
 * 
 * This class is made abstract since we never create an object. We only create subclass objects. 
 * 
 * Implement the Comparable interface and compare two service requests based on the distance
 */

// ##### CHANGES MADE WERE REMOVING INSTANCE OF DRIVER CLASS (COMMENTED OUT) ############################################
abstract public class TMUberService {
  // private Driver driver;
  private String from;
  private String to;
  private User user;
  private String type; // Currently Ride or Delivery but other services could be added
  private int distance; // Units are City Blocks
  private double cost; // Cost of the service

  public TMUberService(Driver driver, String from, String to, User user, int distance, double cost, String type) {
    // this.driver = driver;
    this.from = from;
    this.to = to;
    this.user = user;
    this.distance = distance;
    this.cost = cost;
    this.type = type;
    // this.distance = 0; D2L was told to safely remove this line
  }

  // Subclasses define their type (e.g. "RIDE" OR "DELIVERY")
  abstract public String getServiceType();

  // Getters and Setters
  // public Driver getDriver() {
  // return driver;
  // }

  // public void setDriver(Driver driver) {
  // this.driver = driver;
  // }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public int getDistance() {
    int distance = CityMap.getDistance(from, to);
    return distance;
  }

  public void setDistance(int distance) {
    this.distance = distance;
  }

  public double getCost() {
    return cost;
  }

  public void setCost(double cost) {
    this.cost = cost;
  }

  // Compare 2 service requests based on distance
  // Add the appropriate method

  public int compareTo(TMUberService other) {
    return Integer.compare(this.distance, other.distance);
  }
  // Comparing distances

  // Check if 2 service requests are equal (this and other)
  // They are equal if its the same type and the same user
  // Make sure to check the type first
  public boolean equals(Object other) {
    if (this == other) {
      return false;
    }

    if (!(other instanceof TMUberService)) {
      return false;
    }
    TMUberService otherService = (TMUberService) other;

    if (!this.type.equals(otherService.type) && !this.user.equals(otherService.user)) {
      return false;
    }

    return true;
  }

  // Print Information
  
  public void printInfo() {
    

    System.out.println( "----------------------------------------------------------------------------------");
    System.out.printf("\nType: %-9s From: %-15s To: %-15s", getServiceType(), from, to);
    System.out.print("\nUser: ");
    user.printInfo();
    System.out.println("");
    // THIS NEEDS TO PROVIDE LIST NUMBER INSIDE OF THE ZONES 

    // System.out.print("\nDriver: ");
    // driver.printInfo();
  }

}
