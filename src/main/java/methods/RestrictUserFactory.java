package methods;

import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.groupadministration.RestrictChatMember;
import org.telegram.telegrambots.api.objects.Update;

public class RestrictUserFactory implements Factory {
    
    @Override
    public BotApiMethod createMethod(Update update) {
        RestrictChatMember restrictChatMember = new RestrictChatMember()
                .setChatId(update.getMessage().getChat().getId())
                .setUserId(update.getMessage().getFrom().getId())
                .setCanSendMessages(false)
                .setCanSendMediaMessages(false)
                .setCanSendOtherMessages(false)
                .setCanAddWebPagePreviews(false)
                .setUntilDate(((Long)System.currentTimeMillis()).intValue() + 3600 * 1000);

        return restrictChatMember;
    }
}
