package methods;

import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.objects.Update;

public class DeleteSomeStickerFactory implements Factory {
    @Override
    public BotApiMethod createMethod(Update update) {
        DeleteMessage deleteMessage = new DeleteMessage();

        deleteMessage.setChatId(update.getMessage().getChatId().toString());
        deleteMessage.setMessageId(update.getMessage().getMessageId());

        return deleteMessage;
    }
}
