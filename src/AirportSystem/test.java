package AirportSystem;
import java.util.Random;

public class test {
    
    
    
    
    
    
    public static void main(String args[]) {
        Airway MAD_BAR = new Airway();
        Airway BAR_MAD = new Airway();
        Airport MAD = new Airport("MAD",BAR_MAD,MAD_BAR);
        Airport BAR = new Airport("BAR",BAR_MAD,MAD_BAR);
        SystemLogger log = new SystemLogger();
        Utility ut = new Utility();
        for(int i = 0; i<200; i++)
        {
            
             if(i%2==0){
                 Bus b = new Bus(ut.generate_Bus_ID(i),MAD,log);
                 b.start();
                 
             }else{
                 Bus b = new Bus(ut.generate_Bus_ID(i),BAR,log);
                 b.start();
             }}
        for(int i = 0; i<400; i++)
        {
            
             if(i%2==0){
                 Airplane a = new Airplane(ut.generate_airplane_ID(i),MAD,BAR,log);
                 a.start();
                 
             }else{
                 Airplane a = new Airplane(ut.generate_airplane_ID(i),BAR,MAD,log);
                 a.start();
             }
                 
            
        }
        
    }
    
    
    
    
}
