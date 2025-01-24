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
        List<Shipment> listOfShipments = new ArrayList<>();

        try (Scanner scanner = new Scanner(System.in)) {    // Opens scanner to read user input from console and ensures it gets closed after use
            String userChoice = "";

            while (true) {
                System.out.print("Do you want to create new shipments or view existing ones? (create/view): ");
                userChoice = scanner.nextLine();

                if (userChoice.equalsIgnoreCase("create") || userChoice.equalsIgnoreCase("view")) {
                    break;
                } else {
                    System.out.println("Error: Invalid Input.");
                }
            }

            if (userChoice.equalsIgnoreCase("create")) {
                boolean moreShipments = true;
                while (moreShipments) {                         // Starts a loop as long as more shipments are being added
                    Shipment shipment = new Shipment();         // Creates a new Shipment object for each new shipment

                    System.out.println("================MAKE YOUR ORDER================");

                    boolean validInput = false;
                    while (!validInput) {
                        System.out.print("Please enter your name: ");
                        String name = scanner.nextLine();
                        if (name.matches("[a-zA-Z\\s]+")) {
                            shipment.setName(name);
                            validInput = true;
                        } else {
                            System.out.println("Error: Invalid Input. Name should only contain letters.");
                        }
                    }

                    validInput = false;
                    while (!validInput) {
                        System.out.print("Please enter point of loading: ");
                        String pointOfLoading = scanner.nextLine();
                        if (pointOfLoading.matches("[a-zA-Z\\s]+")) {
                            shipment.setPointOfLoading(pointOfLoading);
                            validInput = true;
                        } else {
                            System.out.println("Error: Invalid Input. Point of loading should only contain letters.");
                        }
                    }

                    validInput = false;
                    while (!validInput) {
                        System.out.print("Please enter point of discharge: ");
                        String pointOfDischarge = scanner.nextLine();
                        if (pointOfDischarge.matches("[a-zA-Z\\s]+")) {
                            shipment.setPointOfDischarge(pointOfDischarge);
                            validInput = true;
                        } else {
                            System.out.println("Error: Invalid Input. Point of discharge should only contain letters.");
                        }
                    }

                    validInput = false;
                    while (!validInput) {
                        System.out.print("Please enter weight (in kg): ");
                        if (scanner.hasNextInt()) {
                            shipment.setWeight(scanner.nextInt());
                            validInput = true;
                        } else {
                            System.out.println("Error: Invalid Input.");
                            scanner.next(); // clear the invalid input
                        }
                    }

                    scanner.nextLine();

                    validInput = false;
                    while (!validInput) {
                        System.out.print("Please enter dimensions (length x width x height in cm): ");
                        String dimensions = scanner.nextLine();     // Reads dimensions as single string input
                        String[] values = dimensions.split("x");    // Splits the dimensions into length, width, and height
                        if (values.length == 3) {
                            try {
                                shipment.setLength(Integer.parseInt(values[0].trim()));
                                shipment.setWidth(Integer.parseInt(values[1].trim()));
                                shipment.setHeight(Integer.parseInt(values[2].trim()));
                                shipment.setVolume(shipment.getLength() * shipment.getWidth() * shipment.getHeight());  // Calculates and sets the volume of the shipment
                                validInput = true;
                            } catch (NumberFormatException e) {
                                System.out.println("Error: Invalid Input.");
                            }
                        } else {
                            System.out.println("Error: Invalid Input.");
                        }
                    }

                    listOfShipments.add(shipment);
                    // Asks if you want to add more shipments
                    while (true) {
                        System.out.print("Do you want to add another shipment? (yes/no): ");
                        String addMoreChoice = scanner.nextLine();
                        if (addMoreChoice.equalsIgnoreCase("yes")) {
                            moreShipments = true;
                            break;
                        } else if (addMoreChoice.equalsIgnoreCase("no")) {
                            moreShipments = false;
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
                    if (confirmChoice.equalsIgnoreCase("confirm")) {
                        try (FileWriter writer = new FileWriter("Shipments.txt", true)) {  // Opens FileWriter in append mode
                            for (Shipment shipment : listOfShipments) {
                                writer.write(shipment.toString() + "\n");                  // Writes the information of the shipment into the "Shipments.txt" file
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // Display created shipments at the end
                        System.out.println("-------------Confirmed Shipments-------------");
                        for (Shipment shipment : listOfShipments) {
                            System.out.println(shipment);
                        }
                        System.out.println("---------------------------------------------");
                        break;

                    } else if (confirmChoice.equalsIgnoreCase("cancel")) {
                        System.out.println("Your shipments have been cancelled.");
                        break;
                    } else {
                        System.out.println("Error: Invalid Input.");
                    }
                }

            } else if (userChoice.equalsIgnoreCase("view")) {
                try {
                    List<String> lines = Files.readAllLines(Paths.get("Shipments.txt"));
                    System.out.println("----------------All Shipments----------------");
                    for (String line : lines) {
                        System.out.println(line);
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

    private String name;
    private int weight;
    private int length;
    private int width;
    private int height;
    private int volume;
    private String pointOfLoading;
    private String pointOfDischarge;

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
    public String getPointOfLoading() {
        return pointOfLoading;
    }
    public void setPointOfLoading(String pointOfLoading) {
        this.pointOfLoading = pointOfLoading;
    }
    public String getPointOfDischarge() {
        return pointOfDischarge;
    }
    public void setPointOfDischarge(String pointOfDischarge) {
        this.pointOfDischarge = pointOfDischarge;
    }

    @Override
    public String toString() {
        return "Shipment{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", volume=" + volume +
                ", pointOfLoading='" + pointOfLoading + '\'' +
                ", pointOfDischarge='" + pointOfDischarge + '\'' +
                '}';
    }
}