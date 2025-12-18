package AirportSystem;
import java.util.ArrayList;
import java.util.List;

public class Hangar {
    private List<Airplane> airplanes;
    private int num_of_airplanes;

    // Constructor
    public Hangar() {
        airplanes = new ArrayList<>();
        this.num_of_airplanes = 0;
    }

    // Method to add an airplane to the hangar in a thread-safe manner
    public synchronized void addAirplane(Airplane airplane) {
        airplanes.add(airplane);
        this.num_of_airplanes++;
    }

    // Method to remove an airplane from the hangar in a thread-safe manner
    public synchronized void removeAirplane(Airplane airplane) {
        airplanes.remove(airplane);
        this.num_of_airplanes--;
    }

    // Method to get the list of airplanes in the hangar in a thread-safe manner
    public synchronized List<Airplane> getAirplanes() {
        return new ArrayList<>(airplanes); // Return a copy to prevent direct modification of the internal list
    }
}