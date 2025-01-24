import java.io.FileWriter;      // Imports class FileWriter to write files
import java.io.IOException;     // Imports class IOException for error handling in file operations
import java.io.Serializable;    // Imports interface Serializable to serialize objects
import java.nio.file.Files;     // Imports class Files to handle file operations
import java.nio.file.Paths;     // Imports class Paths for file paths
import java.util.ArrayList;     // Imports class ArrayList for dynamic arrays
import java.util.List;          // Imports interface List for collections
import java.util.Scanner;       // Imports class Scanner to read user input

public class Main {
    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) {
        List<Shipment> listOfShipments = new ArrayList<>(); // List to save shipments

        try (Scanner scanner = new Scanner(System.in)) {    // Opens scanner to read user input from console
            String userChoice = "";        // Variable to save users choices

            while (true) {  // Loop until valid input
                System.out.print("Do you want to create new shipments or view existing ones? (create/view): ");
                userChoice = scanner.nextLine(); // Get users choice for "create" or "view" option

                if (userChoice.equalsIgnoreCase("create") || userChoice.equalsIgnoreCase("view")) {
                    break;  // Exits the loop if input is valid
                } else {
                    System.out.println("Error: Invalid Input."); // Error message for invalid input
                }
            }

            if (userChoice.equalsIgnoreCase("create")) {      // If user wants to create shipments
                boolean moreShipments = true;                              
                while (moreShipments) {                                     // Starts a loop as long as more shipments are being added
                    Shipment shipment = new Shipment();                     // Creates a new Shipment object for each new shipment

                    System.out.println("================MAKE YOUR ORDER================");  // Headline

                    boolean validInput = false;     
                    while (!validInput) {   // Loop to get valid name input
                        System.out.print("Please enter your name: ");
                        String name = scanner.nextLine();   // Reads input
                        if (name.matches("[a-zA-Z\\s]+")) {     // Ensures name only contains letters
                            shipment.setName(name); 
                            validInput = true;  
                        } else {
                            System.out.println("Error: Invalid Input. Name should only contain letters.");
                        }
                    }

                    validInput = false;
                    while (!validInput) { // Loops until valid port of loading
                        System.out.print("Please enter port of loading: ");
                        String portOfLoading = scanner.nextLine();
                        if (portOfLoading.matches("[a-zA-Z\\s]+")) {    // Ensures it contains only letters
                            shipment.setPortOfLoading(portOfLoading);   // Sets the port of loading
                            validInput = true;
                        } else {
                            System.out.println("Error: Invalid Input. Port of loading should only contain letters.");
                        }
                    }

                    validInput = false; 
                    while (!validInput) {   
                        System.out.print("Please enter port of discharge: ");   // Loops until valid port of discharge input
                        String portOfDischarge = scanner.nextLine(); 
                        if (portOfDischarge.matches("[a-zA-Z\\s]+")) {  // Validate input
                            shipment.setPortOfDischarge(portOfDischarge);   // Sets port of loading
                            validInput = true;
                        } else {
                            System.out.println("Error: Invalid Input. Port of discharge should only contain letters.");
                        }
                    }

                    validInput = false;
                    while (!validInput) {   // Loops until valid weight input
                        System.out.print("Please enter weight (in kg): ");
                        if (scanner.hasNextInt()) {     // Checks if input valid integer
                            shipment.setWeight(scanner.nextInt());  // Sets weight of shipment
                            validInput = true;
                        } else {
                            System.out.println("Error: Invalid Input.");
                            scanner.next(); 
                        }
                    }

                    scanner.nextLine();

                    validInput = false;
                    while (!validInput) {   // Loops until valid dimensions input
                        System.out.print("Please enter dimensions (length x width x height in cm): ");
                        String dimensions = scanner.nextLine();     // Reads dimensions as single string input
                        String[] values = dimensions.split("x");    // Splits the dimensions into length, width, and height
                        if (values.length == 3) {   // Ensures thee are exactly 3 values
                            try {
                                // Code below analyses dimensions and calculates volume
                                shipment.setLength(Integer.parseInt(values[0].trim()));
                                shipment.setWidth(Integer.parseInt(values[1].trim()));
                                shipment.setHeight(Integer.parseInt(values[2].trim()));
                                shipment.setVolume(shipment.getLength() * shipment.getWidth() * shipment.getHeight());  // Calculates and sets the volume of the shipment
                                validInput = true;
                            } catch (NumberFormatException e) {     // Catch invalid format
                                System.out.println("Error: Invalid Input.");
                            }
                        } else {
                            System.out.println("Error: Invalid Input.");
                        }
                    }

                    listOfShipments.add(shipment);  // Adds the created shipment to the list
                    // Asks if you want to add more shipments
                    while (true) {
                        System.out.print("Do you want to add another shipment? (yes/no): ");
                        String addMoreChoice = scanner.nextLine();
                        if (addMoreChoice.equalsIgnoreCase("yes")) {
                            moreShipments = true;   // Contiune if "yes"
                            break;
                        } else if (addMoreChoice.equalsIgnoreCase("no")) {
                            moreShipments = false;  // Stop if "no"
                            break;
                        } else {
                            System.out.println("Error: Invalid Input.");
                        }
                    }
                }

                // Ask the user to confirm or cancel the shipments
                while (true) {
                    System.out.print("Do you want to confirm or cancel your shipments? (confirm/cancel): ");
                    String confirmChoice = scanner.nextLine();
                    if (confirmChoice.equalsIgnoreCase("confirm")) {    // Confirm
                        try (FileWriter writer = new FileWriter("Shipments.txt", true)) {  // Opens FileWriter
                            for (Shipment shipment : listOfShipments) {
                                writer.write(shipment.toString() + "\n");   // Writes the information of the shipment into the "Shipments.txt" file
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // Display created shipments at the end
                        System.out.println("-------------Confirmed Shipments-------------");
                        for (Shipment shipment : listOfShipments) {
                            System.out.println(shipment);   // Prints each created shipments 
                        }
                        System.out.println("---------------------------------------------");
                        break;

                    } else if (confirmChoice.equalsIgnoreCase("cancel")) {  // Cancelled
                        System.out.println("Your shipments have been cancelled.");
                        break;
                    } else {
                        System.out.println("Error: Invalid Input.");
                    }
                }

            } else if (userChoice.equalsIgnoreCase("view")) { // If user wants to view existing shipments
                try {
                    List<String> lines = Files.readAllLines(Paths.get("Shipments.txt"));    // Reads all lines from file "Shipments.txt"
                    System.out.println("----------------All Shipments----------------");
                    for (String line : lines) {
                        System.out.println(line);   // Displays each line an existing shipment from "Shipments.txt"
                    }
                    System.out.println("--------------------------------------------");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class Shipment implements Serializable {        // Allows the object to be Serializable
    private static final long serialVersionUID = 1L;

    private String name;    // Name of Shipment
    private int weight;     // Weight of Shipment
    private int length;     // Length of Shipment
    private int width;      // Width of Shipment
    private int height;     // Height of Shipment
    private int volume;     // Calculated value from the dimensions of Shipment
    private String portOfLoading;   // Port of Loading of Shipment
    private String portOfDischarge; // Port of Discharge of Shipment

    // Getters and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getVolume() {
        return volume;
    }
    public void setVolume(int volume) {
        this.volume = volume;
    }
    public String getPortOfLoading() {
        return portOfLoading;
    }
    public void setPortOfLoading(String portOfLoading) {
        this.portOfLoading = portOfLoading;
    }
    public String getPortOfDischarge() {
        return portOfDischarge;
    }
    public void setPortOfDischarge(String portOfDischarge) {
        this.portOfDischarge = portOfDischarge;
    }
    // Code below is for "Shipments.txt" format and "Confirmed Shipments"
    @Override
    public String toString() {
        return "Shipment{" +
                "name='" + name + '\'' +
                ", weight = " + weight +
                ", length = " + length +
                ", width = " + width +
                ", height = " + height +
                ", volume = " + volume +
                ", portOfLoading = '" + portOfLoading + '\'' +
                ", portOfDischarge = '" + portOfDischarge + '\'' +
                '}';
    }
}