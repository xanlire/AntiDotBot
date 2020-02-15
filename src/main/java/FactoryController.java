import methods.*;
import org.telegram.telegrambots.api.objects.Update;

public class FactoryController {

    private static final String[] wrongs = {
            "заебали бля",
            "пошел на хуй",
            "мне похуй что ты",
            "ты пидор",
            "и пидр",
            "на хуй иди",
            "иди нахуй",
            "нахуй иди",
            "иди на хуй",
            "тебя не спросили",
            "попиздеть",
            "попизди еще",
            "вали отсюда",
            "вставай и вали",
            "и вали",
            "и сука",
            "ты сука",
            "ты заебал",
            "вы заебали",
            "тебя не спроси",
            "хуй твой",
            "пизда тебе",
            "я лыжник"
    };

    private static final String[] helloPhrases = {
            "оброе утр",
            "всем доброе"
    };

    private static final String[] leraFire = {
            "я огонь",
            "я агонь",
            "вообще огонь",
            "ваще огонь",
            "вообще агонь",
            "ваще агонь"
    };

    private static final String[] harmfullStickerSets = {
            "thngs",
            "intruder_poses_of_sex",
            "sexting",
            "making_love"
    };

    private Factory factory = null;

    public Factory getFactory() {
        return factory;
    }

    public FactoryController setFactory(Update update) {
        String message = update.getMessage().getText();

        if(update.getMessage().getSticker() != null) {
            String stickerSetName = update.getMessage().getSticker().getSetName();
            if(isMessageInList(stickerSetName, harmfullStickerSets)) {
                factory = new DeleteSomeStickerFactory();
                return this;
            }
        }

        if(message != null) {
            if(message.startsWith("/kick") || message.startsWith("/nokick")) {
                factory = new KickUserFactory();
            } else if(message.startsWith("#сводка")
                    && (update.getMessage().getFrom().getId() == 256574830
                        || update.getMessage().getFrom().getId() == 302376441
                        || update.getMessage().getFrom().getId() == 295900585)) {
                factory = new BreakingNewsFactory();
            } else if (message.startsWith("/karma")) {
                factory = new MyKarmaFactory();
            } else if (message.startsWith("/say")
                    && (update.getMessage().getChatId().equals(295900585L) || update.getMessage().getChatId().equals(313483266L))) {
                factory = new AdminFactory();
            } else if (message.startsWith("/top10")){
                factory = new GetTop10CommandFactory();
            } else if (isMessageInList(message, helloPhrases)) {
                factory = new SayHelloFactory();
            } else if (isMessageInList(message, wrongs)) {
                System.out.println("User " + update.getMessage().getFrom().getFirstName()
                        + " will banned for the text:\n" + message);
                factory = new RestrictUserFactory();
            } else if(isMessageInList(message, leraFire)){
                factory = new LeraFireFactory();
            } else {
                System.out.println("Input not found");
            }
        }
        return this;
    }

    private boolean isMessageInList(String message, String[] list){
        if(message != null){
            for (String wrong : list) {
                if(message.toLowerCase().contains(wrong)) return true;
            }
        }
        return false;
    }
}
