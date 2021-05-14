package market.code;

import java.util.UUID;

public class IdGenerator {
    public static String generate(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
