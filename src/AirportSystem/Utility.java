package AirportSystem;

import java.util.Random;

public class Utility {
    
    private final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    public  String generate_airplane_ID(int number) {
        
        
        final int ID_LENGTH = 4; // Length of the numeric identifier
        
        // Generate two random alphabetic characters for YY
        StringBuilder yy = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            yy.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }

        // Generate unique numeric identifier (XXXX) based on the given number
        String numericID = String.format("%04d", number);

        // Combine YY and XXXX to form the ID
        return yy.toString() + "-" + numericID;
    }
    
     public  String generate_Bus_ID(int number) {
        // Generate two random alphabetic characters for YY
        StringBuilder yy = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            yy.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }

        // Generate unique numeric identifier (XXXX)
        String numericID = String.format("%04d", number);

        // Combine YY and XXXX to form the ID
        return "B-" + yy.toString() + "-" + numericID;
    }
    
}
