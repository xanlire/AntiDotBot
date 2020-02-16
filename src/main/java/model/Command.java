package model;

/**
 * @author Q-DAY
 */
public class Command {

    private CommandKey key;
    private String payload;

    public Command(CommandKey key) {
        this.key = key;
    }

    public Command(CommandKey key, String payload) {
        this(key);
        this.payload = payload;
    }

    public CommandKey getKey() {
        return key;
    }

    public String getPayload() {
        return payload;
    }
}
