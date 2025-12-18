package AirportSystem;
import java.util.Random;



public class Airplane extends Thread {
    
    private final String id;
    private final int capacity;
    private int passengers;
    private int num_flight;
    private Airport current;
    private Airport other;
    private int gate;
    private int runway;
    private Random r;
    private SystemLogger logger;
    
    //constructor
    public Airplane(String id,Airport first,Airport second,SystemLogger logger)
    {
        this.id = id;
        this.r = new Random();
        this.capacity = r.nextInt(100,300);
        this.current = first;
        this.other = second;
        this.passengers = 0;
        this.gate = 6;
        this.runway = 4;
        
        this.num_flight = 0;
        this.logger = logger;
        
    }
    
    //getters
    public String get_id(){return id;}
    public int get_gate(){return gate;}
    public int get_runway(){return runway;}
    public String get_current_airport(){return current.get_id();}
    public int get_flights(){return this.num_flight;}
    public int get_passengers(){return this.passengers;};
    
    //pieces of lifecycle
    public synchronized void acces_gate(int type) throws Exception{
        //type = 0 is landing type = 1 if for boarding
        if(type == 1)
        {while(!this.current.is_first_in_parking(this) && current.get_boarding_index()>5){
            
            wait();
            
        }
            
            this.gate = current.get_boarding_index();
            this.current.acquire_gate(this);
            this.current.update_boarding();
            
            
        }
        else if(type == 0){
            
            while(this.current.get_landing_index()>5){
                wait();
            }
            this.gate = current.get_landing_index();
            this.current.acquire_landing_gate(this);
            this.current.exit_taxi(this);
            this.current.update_landing();
            
            
            
            
        }
            
           }
    public synchronized void release_gate(int type){
        
        if(type == 1)
        {
           this.current.leave_gate(this);
           this.current.update_boarding();
           this.gate = 6;
           notifyAll();
            
            
        }
        else if(type == 0){
            
            
           this.current.leave_gate(this);
           this.current.update_boarding();
           this.gate = 6;
           notifyAll();
        
        
        
        
    }}
    public synchronized void boarding(){
        if(gate<6){
            System.out.println("Airplane: "+id+" is in "+current.get_id()+" gate n "+gate+" loading passengers");
            int count = 0;
            while(count <3 || this.passengers == this.capacity){
                
                this.passengers = this.current.board_request(this.capacity-this.passengers);
                count++;
                
                
                
            }
            
        }
    }
    
    public synchronized void access_runway(int from)throws Exception{
        // 0 means is trying to access runway from taxi area
        if(from == 0){
            while(current.get_runway_index()>3)
            {
                wait();
            }
            
            this.runway = current.get_runway_index();
            this.current.acquire_runway(this);
            this.current.update_runway();
            this.current.exit_taxi(this);
            
            
            
            }
        // from =1 -> trying to access runway from airway
        else if(from == 1){
            while(current.get_runway_index()>3)
            {
                wait();
            }
            
            this.runway = current.get_runway_index();
            this.current.acquire_runway(this);
            this.current.update_runway();
            this.current.leave_airway(this);
            
        }
        
    }
    public synchronized void release_runway(){
       this.current.release_runway(this);
       this.current.update_runway();
       this.runway = 4;
       notifyAll();
    }
    public synchronized void fly_to(){
        
        Airport temp = this.current;
        this.current = this.other;
        this.other = temp;
        
    }
    public synchronized void land(){
        
        boolean landed = false;
        while(landed == false){
            
            
            if(this.current.get_runway_index() == 4){
                this.simulate("taking a detour waiting for runway", 1, 5);
            }
            else{
                try{this.access_runway(1);}catch(Exception e){};
                landed = true;
            }
             
            
        }
        
        
        
    }
    public synchronized void debarking(){
        
        System.out.println("Airplane: "+id+" is in "+current.get_id()+" gate n "+gate+" debarking  passengers");
        this.current.add_passengers(this.passengers);
        try{Thread.sleep(r.nextInt(1,5)*1000*this.passengers);}catch(Exception e){};
        this.passengers = 0;
        
        
    }
    public synchronized void go_over_inspection(){
        
        if(this.num_flight%15 == 0)
        {
            try{this.current.try_maintenance(this, 0);}catch(Exception e){};
        }else{
            try{this.current.try_maintenance(this, 4);}catch(Exception e){};
        }
        
    }
    
    //utility
    public void simulate(String action,int start,int bound){
        
        int time = r.nextInt(start, bound);
        System.out.println("Airplane: "+id+" is in "+current.get_id()+" airport "+action);
        try{
            Thread.sleep(time*1000);
        }catch(Exception e){};

        
        
    }
    
    
    
    public void run(){
        
        //before the lifecycle actualy starts the airplane is created in the hangarù
        current.enter_hangar(this);
        System.out.println("Airplane: "+id+" is in "+current.get_id()+" hangar");
        logger.logEvent(this, "is created at");
        //leaves hangar
        current.exit_hangar(this);
        System.out.println("Airplane: "+id+" is in "+current.get_id()+"  leaving the hangar");
        logger.logEvent(this, "is leaving the hangar at");
        //start of lifecycle
        while(true){
            
            
            
            
            // go to the parking area
            this.current.enter_parking(this);
            System.out.println("Airplane: "+id+" is in "+current.get_id()+" parking area");
            logger.logEvent(this, "is in the parking area at");
            
            //boarding
            System.out.println("Airplane: "+id+" is in "+current.get_id()+" waiting for a  gate");
            logger.logEvent(this, "is waiting for a gate at");
            try{this.acces_gate(1);}catch(Exception e){};
            System.out.println("Airplane: "+id+" is in "+current.get_id()+" gate n. "+gate);
            logger.logEvent(this, "is at the gate n."+gate+" at");
            this.boarding();
            
            //move to taxi area
            System.out.println("Airplane: "+id+" is in "+current.get_id()+" leaving gate n. "+gate);
            logger.logEvent(this, "leaving gate n. "+gate+"at");
            this.release_gate(1);
            current.acces_taxi(this);
            System.out.println("Airplane: "+id+" is in "+current.get_id()+" taxi area ");
            logger.logEvent(this, "is in the taxi area at ");
            
            //wait for a ryìunway;
            System.out.println("Airplane: "+id+" is in "+current.get_id()+" waiting for a  runway");
            logger.logEvent(this, "is iis waiting for a runway at ");
            try{this.access_runway(0);}catch(Exception e){};
            System.out.println("Airplane: "+id+" is in "+current.get_id()+" runway n. "+runway);
            logger.logEvent(this, "is at the runway n."+runway+" at");
            //do controls
            this.simulate("doing last checks", 1, 3);
            logger.logEvent(this, "doing last checks at ");

            
            //takes off
            this.release_runway();
            this.simulate("now taking off", 1, 5);
            logger.logEvent(this, "now taking off from ");
            //flight
            this.simulate("now flying to "+ other.get_id()+ " airport" , 15, 30);
            this.fly_to();
            logger.logEvent(this, "flight to ");
            
            //now we are flying over the other airport became our current
            this.land();
            this.num_flight++;
            this.simulate("landing at runway "+this.get_runway(), 1, 5);
            logger.logEvent(this, "landing at runway"+runway+" at ");
            
            // access taxi aerea
            this.release_runway();
            this.current.acces_taxi(this);
            System.out.println("Airplane: "+id+" is in "+current.get_id()+" taxi area");
            logger.logEvent(this, "din the taxi area at ");
            
            //goes for landing gate
            try{this.acces_gate(0);}catch(Exception e){};
            this.simulate(" going to gate "+gate+"for debarking", 3, 5);
            logger.logEvent(this, "going  to gate for debarking at  ");
            
            //debarking
            this.debarking();
            
            
            //checks in the maintanace hall
            this.release_gate(0);
            this.go_over_inspection();
            logger.logEvent(this, "going over inspection at ");
            
            //coin toss
            int toss = r.nextInt(2);
            if(toss == 1){
                this.current.enter_hangar(this);
                this.simulate("resting in the hangar", 15, 30);
                logger.logEvent(this, "resting in the hangar at ");
                this.current.exit_hangar(this);
                System.out.println("Airplane: "+id+" is in "+current.get_id()+"  leaving the hangar");
                logger.logEvent(this, "is leaving the hangar at");
             
            }
            
            
            
            
            
           
            
            
            
        }
        
        
        
    }
    
    
}
