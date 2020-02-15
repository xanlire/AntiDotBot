package methods;

import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;

import dao.BlockDao;

/**
 * @author Q-DAY
 */
public class MyKarmaFactory implements Factory {

    @Override
    public BotApiMethod createMethod(Update update) {
        User user = update.getMessage().getFrom();
        Byte karma = new BlockDao().getBlockSum(user.getId());

        SendMessage sendMessage = new SendMessage();

        String username = "".equals(user.getFirstName()) ? user.getUserName() : user.getFirstName();

        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText(String.format("%s - ваша карма составляет: %s", username, karma));

        return sendMessage;
    }
}
