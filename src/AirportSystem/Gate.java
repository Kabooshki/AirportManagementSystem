package AirportSystem;

import java.util.concurrent.locks.ReentrantLock;

public class Gate {
    private final int type;
    private Airplane currentAirplane;
    private final ReentrantLock lock;

    public Gate(int type) {
        this.type = type;
        this.currentAirplane = null;
        this.lock = new ReentrantLock();
    }

    public int getType() {
        return type;
    }

    public Airplane getCurrentAirplane() {
        return currentAirplane;
    }

    // Method to assign an airplane to the gate
    public boolean assignAirplane(Airplane airplane) {
        lock.lock();
        try {
            if (currentAirplane == null) {
                currentAirplane = airplane;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    // Method to release the airplane from the gate
    public void releaseAirplane() {
        lock.lock();
        try {
            currentAirplane = null;
        } finally {
            lock.unlock();
        }
    }

    // Method to check if the gate is available
    public boolean isAvailable() {
        return currentAirplane == null;
    }
}
