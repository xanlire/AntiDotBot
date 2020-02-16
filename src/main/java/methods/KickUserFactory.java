package methods;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import dao.BlockDao;
import dao.UserDao;
import model.user.User;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.groupadministration.KickChatMember;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;


public class KickUserFactory implements Factory {
    @Override
    public BotApiMethod createMethod(Update update) {

        String message = update.getMessage().getText();
        String [] userToKick = new String[1];
        Byte direction = 0;
        if(message.startsWith("/kick")) {
            if (message.charAt(6) == '@') {
                userToKick[0] = message.substring(7);
            } else {
                userToKick[0] = message.substring(6);
            }
            direction = 1;
        } else if(message.startsWith("/nokick")) {
            if (message.charAt(8) == '@') {
                userToKick[0] = message.substring(9);
            } else {
                userToKick[0] = message.substring(8);
            }
            direction = -1;
        }

        UserDao userDao = new UserDao();
        Set<User> users = userDao.getAll();

        List<User> kickHim = users.stream()
                .filter(user -> userToKick[0].equals(user.getFirstname()) || userToKick[0].equals(user.getUsername()))
                .collect(Collectors.toList());

        Integer userId;
        if (!kickHim.isEmpty()) {
            userId = kickHim.get(0).getId();
        } else {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId());
            sendMessage.setReplyToMessageId(update.getMessage().getMessageId());
            sendMessage.setText("Чёт не могу найти активного юзера с таким именем");

            return sendMessage;
        }

        BlockDao blockDao = new BlockDao();
        try {
            blockDao.addBlock(userId, update.getMessage().getFrom().getId(), direction);
        } catch (SQLIntegrityConstraintViolationException e) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId());
            sendMessage.setReplyToMessageId(update.getMessage().getMessageId());
            sendMessage.setText("Вы уже голосовали за этого чела");

            return sendMessage;
        }

        Byte blockSum = blockDao.getBlockSum(userId);

        if (blockSum >= 7) {
            KickChatMember kickChatMember = new KickChatMember();
            kickChatMember.setChatId(update.getMessage().getChatId());
            kickChatMember.setUserId(userId);

            return kickChatMember;
        }

        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText(kickHim.get(0).toShortString() + "\n.Ваша карма составляет: " + blockSum + " .Когда наберется 7 нам придется с вами проститься");

        return sendMessage;
    }
}
