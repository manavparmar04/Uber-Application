import java.util.Arrays;
import java.util.Scanner;
// Name: Manav Parmar
// StudentID: 501180658

// The city consists of a grid of 9 X 9 City Blocks

// Streets are east-west (1st street to 9th street)
// Avenues are north-south (1st avenue to 9th avenue)

// Example 1 of Interpreting an address:  "34 4th Street"
// A valid address *always* has 3 parts.
// Part 1: Street/Avenue residence numbers are always 2 digits (e.g. 34).
// Part 2: Must be 'n'th or 1st or 2nd or 3rd (e.g. where n => 1...9)
// Part 3: Must be "Street" or "Avenue" (case insensitive)

// Use the first digit of the residence number (e.g. 3 of the number 34) to determine the avenue.
// For distance calculation you need to identify the the specific city block - in this example 
// it is city block (3, 4) (3rd avenue and 4th street)

// Example 2 of Interpreting an address:  "51 7th Avenue"
// Use the first digit of the residence number (i.e. 5 of the number 51) to determine street.
// For distance calculation you need to identify the the specific city block - 
// in this example it is city block (7, 5) (7th avenue and 5th street)
//
// Distance in city blocks between (3, 4) and (7, 5) is then == 5 city blocks
// i.e. (7 - 3) + (5 - 4) 

public class CityMap {
  // Checks for string consisting of all digits
  // An easier solution would use String method matches()
  private static boolean allDigits(String s) {
    for (int i = 0; i < s.length(); i++)
      if (!Character.isDigit(s.charAt(i)))
        return false;
    return true;
  }

  // Get all parts of address string
  // An easier solution would use String method split()
  // Other solutions are possible - you may replace this code if you wish
  private static String[] getParts(String address) {
    String parts[] = new String[3];

    if (address == null || address.length() == 0) {
      parts = new String[0];
      return parts;
    }
    int numParts = 0;
    Scanner sc = new Scanner(address);
    while (sc.hasNext()) {
      if (numParts >= 3)
        parts = Arrays.copyOf(parts, parts.length + 1);

      parts[numParts] = sc.next();
      numParts++;
    }
    if (numParts == 1)
      parts = Arrays.copyOf(parts, 1);
    else if (numParts == 2)
      parts = Arrays.copyOf(parts, 2);
    return parts;
  }

  // Checks for a valid address
  public static boolean validAddress(String address) {
    // Fill in the code
    // Make use of the helper methods above if you wish
    // There are quite a few error conditions to check for
    // e.g. number of parts != 3

    ///////////////////////// First
    ///////////////////////// Part//////////////////////////////////////////////////////

    // If the length of parts are not 3 -> return false
    String[] parts = getParts(address);
    if (parts.length != 3) {
      return false;
    }

    // If residence address is not all digits and not length of 2 -> return false
    if (!allDigits(parts[0]) || parts[0].length() > 2) {
      return false;
    }

    ///////////////////////// Second
    ///////////////////////// part//////////////////////////////////////////////////////
    // second part

    String secondPart = parts[1].toLowerCase();
    // Checking to see if number is between 1-9 else -> return false
    int numberCheck = Integer.parseInt(secondPart.substring(0, secondPart.length() - 2));
    if (numberCheck < 1 || numberCheck > 9) {
      return false;
    }
    // Checking to see if prefix is "st" "nd" "rd" "th" else -> return false
    String suffixCheck = secondPart.substring(secondPart.length() - 2);
    if (!suffixCheck.equals("st") && !suffixCheck.equals("nd") && !suffixCheck.equals("rd")
        && !suffixCheck.equals("th")) {
      return false;
    }

    ///////////////////////// Third
    ///////////////////////// part//////////////////////////////////////////////////////
    String thirdPart = parts[2].toLowerCase();
    if (!thirdPart.equalsIgnoreCase("Street") && !thirdPart.equalsIgnoreCase("Avenue")) {
      return false;
    }

    return true;
  }

  // Computes the city block coordinates from an address string
  // returns an int array of size 2. e.g. [3, 4]
  // where 3 is the avenue and 4 the street
  // See comments at the top for a more detailed explanation

  public static int[] getCityBlock(String address) {
    int[] block = { -1, -1 };

    // Fill in the code
    // Checking to see if the address is valid first
    if (!validAddress(address)) {
      return block;
    }

    String[] parts = getParts(address);
    // Retreiving residence first integer
    int residenceInt = Integer.parseInt(parts[0].substring(0, 1));
    // Retreiving street or avenue integer
    int street_avenueInt = Integer.parseInt(parts[1].substring(0, 1));

    if (parts[2].equalsIgnoreCase("STREET")) {
      block[0] = residenceInt;
      block[1] = street_avenueInt;
    } else {
      block[0] = street_avenueInt;
      block[1] = residenceInt;
    }

    return block;

  }

  // Calculates the distance in city blocks between the 'from' address and 'to'
  // address
  // Hint: be careful not to generate negative distances

  // This skeleton version generates a random distance
  // If you do not want to attempt this method, you may use this default code
  public static int getDistance(String from, String to) {
    // Fill in the code or use this default code below. If you use
    // the default code then you are not eligible for any marks for this part
    int fromBlock[] = getCityBlock(from);
    int toBlock[] = getCityBlock(to);

    // (3, 4) and (7, 5)
    // from to
    // i.e. (7 - 3) + (5 - 4)
    // 4 + 1
    // 5

    return Math.abs(toBlock[0] - fromBlock[0]) + Math.abs(toBlock[1] - fromBlock[1]);

    // Math.random() generates random number from 0.0 to 0.999
    // Hence, Math.random()*17 will be from 0.0 to 16.999
    // double doubleRandomNumber = Math.random() * 17;
    // // cast the double to whole number
    // int randomNumber = (int)doubleRandomNumber;
    // return (randomNumber);
  }

  public static int getCityZone(String address) {
    if (!validAddress(address)) {
      return -1;
    }

    int[] block = getCityBlock(address);
    int avenue = block[0];
    int street = block[1]; // Block 0 is avenue and Block 1 is street from the getCityBlock rule above

    if (avenue >= 1 && avenue <= 5) {
      if (street >= 6 && street <= 9) {
        return 0;
      } else if (street >= 1 && street <= 5) {
        return 3;
      }

    }
    if (avenue >= 6 && avenue <= 9) {
      if (street >= 6 && street <= 9) {
        return 1;
      } else if (street >= 1 && street <= 5) {
        return 2;
      }
    }
    
    

      return -1; // If anything is an invalid or does not fall into the zone, return -1

  }

}
