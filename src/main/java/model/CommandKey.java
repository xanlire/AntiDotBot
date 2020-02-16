package model;

import java.util.stream.Stream;

/**
 * @author Q-DAY
 */
public enum CommandKey {

    KICK("/kick"),

    NO_KICK("/nokick"),

    KARMA("/karma"),

    SAY("/say"),

    TOP("/top"),

    NEWS("#сводка"),

    HELP("/help"),

    NONE("");

    private String key;

    private String getKey() {
        return key;
    }

    CommandKey(String key) {
        this.key = key;
    }

    public static CommandKey commandFromString(String key) {
        return Stream.of(CommandKey.values())
            .filter(commandKey -> commandKey.key.equals(key))
            .findAny()
            .orElse(CommandKey.NONE);
    }
}
