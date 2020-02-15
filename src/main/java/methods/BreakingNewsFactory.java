package methods;

import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.pinnedmessages.PinChatMessage;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

public class BreakingNewsFactory implements Factory{
    @Override
    public BotApiMethod createMethod(Update update) {
        PinChatMessage pinChatMessage = new PinChatMessage();

        pinChatMessage.setChatId(update.getMessage().getChatId());
        pinChatMessage.setMessageId(update.getMessage().getMessageId());

        return pinChatMessage;
    }
}
