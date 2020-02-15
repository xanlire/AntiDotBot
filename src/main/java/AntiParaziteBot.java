import cli.CliServer;
import methods.Factory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import service.Saver;

public class AntiParaziteBot extends TelegramLongPollingBot {

    private static final Long CHAT_ID = -1001397109964L;
    private static final Long ADMIN_ID = 295900585L;
    private static final Long OLYA_ID = 313483266L;
    private static final Long TEST_CHAT_ID = -1001332538571L;
    private CliServer server = new CliServer();

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new AntiParaziteBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }              

    @Override
    public void onUpdateReceived(Update update) {                           

        server.printMessage(update.getMessage().getFrom().getFirstName(), update.getMessage().getText());

        if(update.getMessage().getChatId().equals(CHAT_ID)
                || update.getMessage().getChatId().equals(ADMIN_ID)
                || update.getMessage().getChatId().equals(TEST_CHAT_ID)
                || update.getMessage().getChatId().equals(OLYA_ID)) {
            new Saver().saveUpdateInfo(update);

            FactoryController controller = new FactoryController().setFactory(update);

            try {
                Factory factory = controller.getFactory();
                if(factory != null){
                    BotApiMethod method = factory.createMethod(update);
                    if (method != null) {
                        execute(method);
                    }
                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "AntiDotBot";
    }

    @Override
    public String getBotToken() {
        return "485868783:AAEgfzZpntb_IdegN6R92u_4Fubvfwe26ts";
    }
}
