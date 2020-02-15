package methods;

import java.util.Random;

import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

public class SayHelloFactory implements Factory{

    private static final String[] goodMornings = {
            "Доброе утро, ",
            "И тебе, ",
            "Моё почтение, ",
            "Низкий поклон, ",
            "Как спалось, "
    };

    private static final String[] goodMorningsSimple = {
            "А по утру они проснулись..",
            "Утро добрым не бывает.",
            "Ути-пути, а кто это у нас тут проснулся?)"
    };

    @Override
    public BotApiMethod createMethod(Update update) {

        Random random = new Random();
        int randomMorning = random.nextInt(goodMornings.length + goodMorningsSimple.length);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId())
                .setReplyToMessageId(update.getMessage().getMessageId())
                .setText(randomMorning < goodMornings.length
                        ? goodMornings[randomMorning] + update.getMessage().getFrom().getFirstName()
                        : goodMorningsSimple[randomMorning - goodMornings.length]);
        return sendMessage;
    }
}
