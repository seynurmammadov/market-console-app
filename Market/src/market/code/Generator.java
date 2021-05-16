package market.code;

import java.util.UUID;

public class Generator {
    public static String generateId(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
    public static int generateNumber(int min, int max){
        return (int) (min+(Math.random()*(max-min)));
    }
}
