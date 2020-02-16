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

        /*
            1: Инициазировать из объекта update необходимые для работы абстракции приложения.
            2: Определить кто инициировал действие по роли пользователя => на основе результата отправить в подсистему необходимые данные
            Действия в подсистеме
            2: 1.Определить действие в подсистеме
            2: 2.Выполнить действие в подсистеме
            ---------------------
            3: Отправить результат
         */

        //Есть возможность трансляции сообщений чата в CLI
        server.printMessage(update.getMessage().getFrom().getFirstName(), update.getMessage().getText());

        //Определяем чат из которого пришло сообщение, для других id бот ничего не делает
        //TODO: убрать хардкод
        if(update.getMessage().getChatId().equals(CHAT_ID)
                || update.getMessage().getChatId().equals(ADMIN_ID)
                || update.getMessage().getChatId().equals(TEST_CHAT_ID)
                || update.getMessage().getChatId().equals(OLYA_ID)) {

            //Работа с пользователем
            //TODO: вынести в отдельный модуль с адекватными названиями
            new Saver().saveUpdateInfo(update);

            //Передаём управление подсистеме управления, по факту логика работы бота
            FactoryController controller = new FactoryController().setFactory(update);

            //Отправляем response
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

    /**
     * Имя бота.
     * todo: избавиться от sensitive data
     * Необходимо заменить при запуске локальных инстансов на своё
     * @return имя бота
     */
    @Override
    public String getBotUsername() {
        return "AntiDotBot";
    }

    /**
     * Токен
     * todo: избавиться от sensitive data
     * Необходимо заменить при запуске локальных инстансов на своё
     * @return токен бота
     */
    @Override
    public String getBotToken() {
        return "485868783:AAEgfzZpntb_IdegN6R92u_4Fubvfwe26ts";
    }
}
