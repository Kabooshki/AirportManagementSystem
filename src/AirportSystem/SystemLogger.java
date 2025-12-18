package AirportSystem;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class SystemLogger {
    
    private static final String LOG_FILE = "airportEvolution.txt";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    static {
        File file = new File(LOG_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // Method to log events
    public synchronized  void logEvent(Airplane a, String action) {
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            LocalDateTime timestamp = LocalDateTime.now();
            
            writer.println("[ "+ timestamp+ " ] Airplane" + " - " + a.get_id() + " ("+a.get_passengers()+") " + action + " " + a.get_current_airport());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public synchronized  void logEvent(Bus b, String action) {
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            LocalDateTime timestamp = LocalDateTime.now();
            
            writer.println("[ "+ timestamp+ " ] Bus "+ " - " + b.get_id() + "( "+ b.get_passengers()+") "   + action + " " + b.get_Airport());        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

    // Ensure the log file exists in the current directory
    static {
        try {
            File file = new File(LOG_FILE);
            if (!file.exists()) {
                file.createNewFile();
            }
            else{
                file.delete();
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}