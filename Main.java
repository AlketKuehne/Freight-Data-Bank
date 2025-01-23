import java.io.FileWriter;      // Imports class FileWriter to write files
import java.io.IOException;     // Imports class IOException for error handling in file operations
import java.io.Serializable;    // Imports interface Serializable to serialize objects
import java.util.ArrayList;     // Imports class ArrayList for dynamic arrays
import java.util.List;          // Imports interface List for collections
import java.util.Scanner;       // Imports class Scanner to read user input

/*
*/
public class Main {
    public static void main(String[] args) {
        List<Shipment> listOfShipments = new ArrayList<>();

        /*
         * Requirement:
         * 1. Show a menu where the user can select what to do
         * 1.1. 1 - Create shipment
         * 1.2. 2 - Show existing shipments
         * 1.3. 3 - Exit
         * 
         * Bugs:
         * Validate dimension and provide proper error message and retry
         */

        try (Scanner scanner = new Scanner(System.in)) {    // Opens scanner to read user input from console and ensures it gets closed after use
            boolean moreShipments = true;
            while (moreShipments) {                         // Starts a loop as long as more shipments are being added
                Shipment shipment = new Shipment();         // Creates a new Shipment object for each new shipment

                System.out.println("================MAKE YOUR ORDER================");

                System.out.print("Please enter your name: ");
                shipment.setName(scanner.nextLine());

                System.out.print("Please enter point of loading: ");
                shipment.setPointOfLoading(scanner.nextLine());

                System.out.print("Please enter point of discharge: ");
                shipment.setPointOfDischarge(scanner.nextLine());

                System.out.print("Please enter weight (in kg): ");
                shipment.setWeight(scanner.nextInt());

                scanner.nextLine();

                System.out.print("Please enter dimensions (length x width x height in cm): ");
                String dimensions = scanner.nextLine();     // Reads dimensions as single string input
                String[] values = dimensions.split("x");    // Splits the dimensions into length, width, and height
                shipment.setLength(Integer.parseInt(values[0].trim()));
                shipment.setWidth(Integer.parseInt(values[1].trim()));
                shipment.setHeight(Integer.parseInt(values[2].trim()));
                shipment.setVolume(shipment.getLength() * shipment.getWidth() * shipment.getHeight());  // Calculates and sets the volume of the shipment

                listOfShipments.add(shipment);
                // Asks if you want to add more shipments
                System.out.print("Do you want to add another shipment? (yes/no): ");
                moreShipments = scanner.nextLine().equalsIgnoreCase("yes");
            }
        }
        try (FileWriter writer = new FileWriter("Shipments.txt", true)) {  // Opens FileWriter in append mode
            for (Shipment shipment : listOfShipments) {
                writer.write(shipment.toString() + "\n");                  // Writes the information of the shipment into the "Shipments.txt" file
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Executes the Shipments in this format
        System.out.println("----------------All Shipments----------------");
        for (Shipment shipment : listOfShipments) {
            System.out.println("Name:     " + shipment.getName());
            System.out.println("From:     " + shipment.getPointOfLoading());
            System.out.println("To:       " + shipment.getPointOfDischarge());
            System.out.println("Weight:   " + shipment.getWeight() + " kg");
            System.out.println("Volume:   " + shipment.getVolume() + " cm³");
            System.out.println("--------------------------------------------");
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

    // Code below is for the "Shipments.txt" format
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
// Done
