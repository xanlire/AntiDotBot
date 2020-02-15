package methods;

import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Update;

public interface Factory {
    BotApiMethod createMethod(Update update);
}
