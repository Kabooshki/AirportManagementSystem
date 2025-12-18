package AirportSystem;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Maintenance_hall {
    private final int MAX_CAPACITY = 20;
    private final BlockingQueue<Airplane> maintenanceBuffer;
    private final ConcurrentLinkedQueue<Airplane> overflowQueue;
    private final Lock door;
    
    
    
    public Maintenance_hall()
    {
        this.door = new ReentrantLock();
        this.maintenanceBuffer = new ArrayBlockingQueue(20);
        this.overflowQueue = new ConcurrentLinkedQueue();
    }
    
    public int get_size(){return this.maintenanceBuffer.size();}
    public Airplane get_first(){
        return this.overflowQueue.peek();
    }
    
    public synchronized void enter_maintenance(Airplane a){
        door.lock();
        try{
            this.maintenanceBuffer.add(a);
        }finally{
            door.unlock();
        }
    }
    public synchronized void exit_maintenance(Airplane a){
        door.lock();
        try{
            this.maintenanceBuffer.remove(a);
        }finally{
            door.unlock();
        }
    }
    
    public synchronized void access_queue(Airplane a){
        this.overflowQueue.add(a);
    }
    public synchronized void leave_queue(Airplane a){
        
        if(a == this.overflowQueue.peek()){
            this.enter_maintenance(a);
        }
    }
    

    
}
