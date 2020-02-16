import java.util.HashMap;
import java.util.Map;

import methods.*;
import model.Command;
import model.CommandKey;

import org.telegram.telegrambots.api.objects.Update;

public class FactoryController {

    private Map<CommandKey, Factory> commands = new HashMap<>();

    {
        commands.put(CommandKey.KICK, new KickUserFactory());
        commands.put(CommandKey.NO_KICK, new KickUserFactory());
        commands.put(CommandKey.NEWS, new BreakingNewsFactory());
        commands.put(CommandKey.SAY, new AdminFactory());
        commands.put(CommandKey.KARMA, new MyKarmaFactory());
        commands.put(CommandKey.TOP, new GetTop10CommandFactory());
    }


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

            if (message.startsWith("/") || message.startsWith("#")) {
                Command command = getCommand(message);
                factory = resolveFactory(command.getKey());
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

    private Command getCommand(String message) {
        String[] parsedMessage = message.split(" ", 2);
        return new Command(CommandKey.commandFromString(parsedMessage[0]), parsedMessage[1]);
    }

    private Factory resolveFactory(CommandKey key) {
        return this.commands.get(key);
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
