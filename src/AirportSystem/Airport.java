package AirportSystem;
import java.util.Random;
public class Airport {
    
    private String id;
    private int num_of_passengers;
    private Hangar hangar;
    private Parking_Area parking;
    private Gate[] gates = new Gate[6]; 
    private Taxi_Area taxi;
    private Runway[] runways = new Runway[4];
    private Maintenance_hall maintenance;
    private int boarding_index;
    private int landing_index;
    private int runway_index;
    private Airway MAD_BAR;
    private Airway BAR_MAD;
    private Random r;
    
    public Airport(String id,Airway bar_mad, Airway mad_bar){
        this.id = id;
        this.r = new Random();
        this.boarding_index = 1;
        this.landing_index = 0;
        this.runway_index = 0;
        this.num_of_passengers = 0;
        this.hangar = new Hangar();
        this.parking = new Parking_Area();
        this.gates[0] = new Gate(0);
        this.gates[1] = new Gate(1);
        for(int i = 2; i<6; i++){
            this.gates[i] = new Gate(2);
        }
        this.taxi = new Taxi_Area();
        for(int i = 0; i<4; i++){
            this.runways[i] = new Runway();
        }
        this.maintenance = new Maintenance_hall();
        this.BAR_MAD = bar_mad;
        this.MAD_BAR = mad_bar;
    }
    
    //getters
    public String get_id(){return this.id;}
    public int get_boarding_index(){return this.boarding_index;}
    public int get_landing_index(){return this.landing_index;}
    public int get_runway_index(){return this.runway_index;}
    //setters
    public synchronized void add_passengers(int p){
        this.num_of_passengers+=p;
    }
    public synchronized void update_boarding(){
        
        this.boarding_index = this.check_gate_avaible(1);
        
    };
    public synchronized void update_landing(){
        
        this.boarding_index = this.check_gate_avaible(0);
        
    };
    public synchronized void update_runway(){
        
        this.runway_index = this.check_runway_avaible();
    }
    
    
    public synchronized int free_passengers(int request){
        if(request>this.num_of_passengers){
            int temp = this.num_of_passengers;
            this.num_of_passengers = 0;
            return temp;
        }else
        {
            this.num_of_passengers-=request;
            return request;
        }
    }
    public synchronized int board_request(int request){
        if(request<= this.num_of_passengers){
            this.num_of_passengers-=request;
            try{Thread.sleep(request*1000*r.nextInt(1,3));}catch(Exception e){};
            return request;
        }
        else{
            int temp = this.num_of_passengers;
            this.num_of_passengers = 0;
            return temp;
        }
    }
    
    
    
    //entering the hangar
    public synchronized void enter_hangar(Airplane a){
        this.hangar.addAirplane(a);
    }
    
    //exiting the hangar
    public synchronized void exit_hangar(Airplane a){
        this.hangar.removeAirplane(a);
    }
    
    //entering parking Area
    public synchronized void enter_parking(Airplane a){
        parking.addAirplane(a);
    }
    
    
    // check for av aible gate of given type
    public synchronized int check_gate_avaible(int type){
        int index = 6;
        for(int i = 0; i<5; i++)
        {
            if(this.gates[i].isAvailable() && (this.gates[i].getType() == type || this.gates[i].getType() == 2)){
                index = i;
                break;
            }
        }
        return index;
    }
    
    public synchronized int check_runway_avaible(){
        
        int index = 4;
        for (int i = 0; i<4; i++){
            if(runways[i].isAvailable()){
                index = i;
                break;
            }
        }
        return index;
    }
    
    public synchronized boolean is_first_in_parking(Airplane a){
        
        return this.parking.is_first(a);
    }
    
    public synchronized void acquire_gate(Airplane a){
      
        this.gates[a.get_gate()].assignAirplane(this.parking.getNextAirplane()) ;
    }
    public synchronized void acquire_landing_gate(Airplane a){
        this.gates[a.get_gate()].assignAirplane(a);
    }
    //leving gate
    public synchronized void leave_gate(Airplane a)
    {
        this.gates[a.get_gate()].releaseAirplane();
        
    }
    //access taxi
    public synchronized void acces_taxi(Airplane a ){
        this.taxi.addAirplane(a);
    }
    public synchronized void exit_taxi(Airplane a ){
        this.taxi.removeAirplane(a);
    } 
    // runway methods
    public synchronized void acquire_runway(Airplane a ){
        this.runways[a.get_runway()].assignAirplane(a);
    }
    public synchronized void release_runway(Airplane a){
            
        if(this.runways[a.get_runway()].getCurrentAirplane() == a)
        {
            this.runways[a.get_runway()].releaseAirplane();
        }
    }
    public synchronized void enter_airway(Airplane a){
        
       if(a.get_current_airport().equals("MAD")){
           
           this.MAD_BAR.addAirplane(a);
       }
       else{
           this.BAR_MAD.addAirplane(a);
       }
        
        
    }
    public synchronized void leave_airway(Airplane a){
        if(a.get_current_airport().equals("BAR")){
           
           this.MAD_BAR.removeAirplane(a);
       }
       else{
           this.BAR_MAD.removeAirplane(a);
       }
        
    }
    
    //maintenance
    public synchronized void try_maintenance(Airplane a,int time) throws Exception{
        int start = 5-time;
        int finish = (int)(10-time*1.25);
        if(this.maintenance.get_size() >=20){
            this.maintenance.access_queue(a);
            while(this.maintenance.get_size() >=20 && this.maintenance.get_first()!=a )
            {a.wait();}
            this.maintenance.leave_queue(a);
            a.notify();
            a.simulate("going over maintenance", start,finish );
            this.maintenance.exit_maintenance(a);
            
            
        }
        else{
            this.maintenance.enter_maintenance(a);
            a.simulate("going over maintenance", start, finish);
            this.maintenance.exit_maintenance(a);
        }
    }
    
    
    
    
    
    
    
    
    
    
}
