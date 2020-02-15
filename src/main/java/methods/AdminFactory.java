package methods;

import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

public class AdminFactory implements Factory {
    @Override
    public BotApiMethod createMethod(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(-1001397109964L)
                .setText(update.getMessage().getText().substring(5));
        return sendMessage;
    }
}
