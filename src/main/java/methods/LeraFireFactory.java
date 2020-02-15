package methods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

public class LeraFireFactory implements Factory{

    private static final String[] leraFlames = {
        "Да огонь ты, огонь",
        "Вообще огонь",
        "Ты АГОНЬ!"
    };

    private static final String[] otherFlames = {
        "Я тебе говорю точно, Лера огонь",
        "Не, не, Лера огонь",
        "Попрошу не путать, Лера агонь",
        "Вроде как Лера агонь"
    };

    private static final String[] alinaSpecial = {
        "Ты пожар!!!",
    };


    private static ArrayList<String> orderedFlamesLera = new ArrayList<>();
    private static ArrayList<String> orderedFlamesOther = new ArrayList<>();

    @Override
    public BotApiMethod createMethod(Update update) {

        String currentFlame = getOrderedFlame(update.getMessage().getFrom().getFirstName());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText(currentFlame);
        sendMessage.setReplyToMessageId(update.getMessage().getMessageId());
        return sendMessage;
    }

    private String getOrderedFlame(String firstName){

        if(firstName.equals("Алина")){
            return "Ты пожар!!!";//alinaSpecial[new Random().nextInt(alinaSpecial.length)];
        }

        if(orderedFlamesLera.isEmpty()){
            orderedFlamesLera = new ArrayList<>(Arrays.asList(leraFlames));
        }
        if(orderedFlamesOther.isEmpty()){
            orderedFlamesOther = new ArrayList<>(Arrays.asList(otherFlames));
        }

        ArrayList<String> currentFlames;
        currentFlames = orderedFlamesOther;
        if(firstName.equals("Lera")){
            currentFlames = orderedFlamesLera;
        }

        String firstFlame = currentFlames.get(currentFlames.size() - 1);

        currentFlames.remove(currentFlames.size() - 1);
        return firstFlame;
    }
}
