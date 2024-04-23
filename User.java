// Name: Manav Parmar
// StudentID: 501180658
/*
 * 
 * Class that simulates a user of a simple Uber app
 * 
 * Everything has been done for you except equals() method
 */
public class User

{
  private String accountId;
  private String name;
  private String address;
  private double wallet; // load up with money
  private int rides;
  private int deliveries;

  public User(String id, String name, String address, double wallet) {
    this.accountId = id;
    this.name = name;
    this.address = address;
    this.wallet = wallet;
    this.rides = 0;
    this.deliveries = 0;
  }

  // Getters and Setters
  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public double getWallet() {
    return wallet;
  }

  public void setWallet(double wallet) { // I changed int to double because of the cost
    this.wallet = wallet;
  }

  public int getRides() {
    return rides;
  }

  public void addRide() {
    this.rides++;
  }

  public void addDelivery() {
    this.deliveries++;
  }

  public int getDeliveries() {
    return deliveries;
  }

  // Pay for the cost of the service
  // This method assumes that there are sufficient funds in the wallet
  public void payForService(double cost) {
    wallet -= cost;
  }

  // Print Information about a User
  public void printInfo() {
    System.out.printf("Id: %-5s Name: %-15s Address: %-15s Wallet: %2.2f", accountId, name, address, wallet);
  }

  public void cancelRide() {
    this.rides--;
  }

  public void cancelDelivery() {
    this.deliveries--;
  }

  /*
   * Two users are equal if they have the same name and address.
   * This method is overriding the inherited method in superclass Object
   * 
   * 
   * Fill in the code
   */
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof User)) {
      return false;
    }
    User otherUser = (User) other;
    if (!this.name.equals(otherUser.name) || !this.address.equals(otherUser.address)) {
      return false;
    }
    return true;
  }
}
