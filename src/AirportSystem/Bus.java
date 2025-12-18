package AirportSystem;
import java.util.Random;


public class Bus extends Thread {
    
    private String ID;
    private int passengers;
    private Airport airport;
    private Random r;
    private SystemLogger logger;
    
    public Bus(String id,Airport a,SystemLogger logger){
        this.ID = id;
        this.airport = a;
        this.passengers = 0;
        r = new Random();
        this.logger = logger;
    }
    public String get_id(){return this.ID;}
    public String get_Airport(){return this.airport.get_id();}
    public int get_passengers(){return this.passengers;}
    
    
    
    
    public void run(){
        int temp;
        
        while(true){
            
            // wait for passengers at the stop
            System.out.println("Bus "+ this.ID+ ": is now waiting for passengers");
            this.logger.logEvent(this, "waiting for passengers to to the airport " );
            try{Thread.sleep(1000*r.nextInt(2, 5));}catch(Exception e){}
            
            
            //loading passengers
            temp = r.nextInt(0,50);
            System.out.println("Bus "+ this.ID+ ": "+temp+" passengers are now getting on the bus");
            this.logger.logEvent(this, temp+" passengers are now getting on the bus to go to the airport " );
            this.passengers += temp;
            
            //heads to airport
            System.out.println("Bus: "+ this.ID+ ": is now heading to the "+airport.get_id()+" airport");
            this.logger.logEvent(this, "heading to  the airport " );
            
            try{Thread.sleep(1000*r.nextInt(5, 10));}catch(Exception e){}
            
            //arrives at the airport and loads passengers into airport system
            System.out.println("Bus: "+ this.ID+ ": has arrived to the "+airport.get_id()+" airport");
             this.logger.logEvent(this, "arrived to  the airport " );
            airport.add_passengers(this.passengers);
            this.passengers = 0;
            
            //waits for other passengers to come
            System.out.println("Bus "+ this.ID+ ": is now waiting for passengers");
             this.logger.logEvent(this, "waiting for passengers at airport " );
            try{Thread.sleep(1000*r.nextInt(2, 5));}catch(Exception e){}
            temp = airport.free_passengers(r.nextInt(0,50));
            
            
            System.out.println("Bus "+ this.ID+ ": "+temp+" passengers are now getting on the bus");
            this.logger.logEvent(this, temp+" passengers are now getting on the bus from the airport " );
            this.logger.logEvent(this, "heading to  the airport " );
            this.passengers += temp;
            
            
            System.out.println("Bus: "+ this.ID+ ": is now heading to the downtown");
            this.logger.logEvent(this, " now heading downtown from the airport " );
            try{Thread.sleep(1000*r.nextInt(5, 10));}catch(Exception e){}
            
            
            System.out.println("Bus: "+ this.ID+ ": has arrived to the downtown");
            this.logger.logEvent(this, " arrived downtown from the airport " );
            this.passengers = 0;
            
        
        }
    
    
    
    }
    
}
