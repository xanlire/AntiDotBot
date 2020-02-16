package methods;

import java.util.*;

import dao.MessageDao;
import model.user.User;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

public class GetTop10CommandFactory implements Factory {
    @Override
    public BotApiMethod createMethod(Update update) {
        MessageDao messageDao = new MessageDao();

        Set<User> sortedUsers = new TreeSet<>(messageDao.getTop10());

        StringBuilder builder = new StringBuilder();              

        Random random = new Random();
        User[] users = sortedUsers.toArray(new User[sortedUsers.size()]);

        for(int i = 0; i < users.length; i ++ ) {//(random.nextInt(3) + 1)) {
            builder.append( "_").append(i + 1).append("_")
                    .append(". ")
                    .append(users[i].toString())
                    .append("\n");
        }

        String stringUserSet;
        stringUserSet = builder.toString();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText(stringUserSet);
        sendMessage.setParseMode("Markdown");

        return sendMessage;
    }
}
