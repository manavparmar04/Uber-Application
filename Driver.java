/*
// Name: Manav Parmar
// StudentID: 501180658
 * 
 * This class simulates a car driver in a simple uber app 
 * 
 * Everything has been done for you except the equals() method
 */

public class Driver {
  private String id;
  private String name;
  private String carModel;
  private String licensePlate;
  private double wallet;
  private String type;

  // ### NEW CODE
  // ################################################################################################
  private TMUberService service;
  private String address;
  private int zone;

  // ### NEW CODE END
  // ################################################################################################

  public static enum Status {
    AVAILABLE, DRIVING
  };

  private Status status;

  public Driver(String id, String name, String carModel, String licensePlate, String address, int zone) {
    this.id = id;
    this.name = name;
    this.carModel = carModel;
    this.licensePlate = licensePlate;
    this.status = Status.AVAILABLE;
    this.wallet = 0;
    this.type = "";
    this.address = address;
    this.zone = zone;
  }

  // Print Information about a driver
  public void printInfo() {
    System.out.printf("Id: %-3s Name: %-15s Car Model: %-15s License Plate: %-10s Wallet: %2.2f",
        id, name, carModel, licensePlate, wallet);
  }

  // Getters and Setters
  public String getType() {
    return type;
  }

  public TMUberService getService() {
    return service;
  }

  public void setService(TMUberService service){
    this.service = service;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address){
    this.address = address;
  }

  public int getZone() {
    int zone = CityMap.getCityZone(address);
    return zone;
  }

  public void setZone(int zone){
    this.zone = zone;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCarModel() {
    return carModel;
  }

  public void setCarModel(String carModel) {
    this.carModel = carModel;
  }

  public String getLicensePlate() {
    return licensePlate;
  }

  public void setLicensePlate(String licensePlate) {
    this.licensePlate = licensePlate;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public double getWallet() {
    return wallet;
  }

  public void setWallet(double wallet) {
    this.wallet = wallet;
  }

  public void setStatusAvailable() {
    this.status = Status.AVAILABLE;
  }

  public void setStatusDriving() {
    this.status = Status.DRIVING;
  }

  /*
   * Two drivers are equal if they have the same name and license plates.
   * This method is overriding the inherited method in superclass Object
   * 
   * Fill in the code
   */
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }

    if (!(other instanceof Driver)) {
      return false;
    }
    Driver otherDriver = (Driver) other; // Converting other object to Driver
    if (!this.name.equals(otherDriver.name) || !this.licensePlate.equals(otherDriver.licensePlate)) {
      return false;
    }
    return true;
  }

  // A driver earns a fee for every ride or delivery
  public void pay(double fee) {
    wallet += fee;
  }
}
