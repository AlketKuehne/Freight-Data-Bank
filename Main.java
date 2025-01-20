import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Shipment> listOfShipments = new ArrayList<>();

        try (Scanner scanner = new Scanner(System.in)) {
            boolean moreShipments = true;
            while (moreShipments) {
                Shipment shipment = new Shipment();

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
                String dimensions = scanner.nextLine();
                String[] values = dimensions.split("x");
                shipment.setLength(Integer.parseInt(values[0].trim()));
                shipment.setWidth(Integer.parseInt(values[1].trim()));
                shipment.setHeight(Integer.parseInt(values[2].trim()));
                shipment.setVolume(shipment.getLength() * shipment.getWidth() * shipment.getHeight());

                listOfShipments.add(shipment);
                // Asks if you want to add more shipments
                System.out.print("Do you want to add another shipment? (yes/no): ");
                moreShipments = scanner.nextLine().equalsIgnoreCase("yes");
            }
        }
        /* If "Shipments.txt" file doesn't exist it creates 
        it for you and saves the shipments you created  */ 
        try (FileWriter writer = new FileWriter("Shipments.txt")) {
            for (Shipment shipment : listOfShipments) {
                writer.write(shipment.toString() + "\n");
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

class Shipment {
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