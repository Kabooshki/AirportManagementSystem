package AirportSystem;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Parking_Area {
    private ConcurrentLinkedQueue<Airplane> airplanes;
    private int num_of_airplanes;

    public Parking_Area() {
        airplanes = new ConcurrentLinkedQueue<>();
        this.num_of_airplanes = 0;
    }

    // Method to add an airplane to the parking area
    public synchronized void addAirplane(Airplane airplane) {
        airplanes.add(airplane);
        this.num_of_airplanes++;
    }

    // Method to remove and return the next airplane from the parking area (FIFO behavior)
    public  synchronized Airplane getNextAirplane() {
        this.num_of_airplanes--;
        return airplanes.poll();
    }

    // Method to check if the parking area is empty
    public boolean isEmpty() {
        return airplanes.isEmpty();
    }

    // Method to get the size of the parking area
    public int getSize() {
        return airplanes.size();
    }
    public boolean is_first(Airplane a){
        
        return a==this.airplanes.peek();
        
    }
}