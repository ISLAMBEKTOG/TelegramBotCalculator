import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        }catch (TelegramApiRequestException e){
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());

        String text = message.getText();

        switch (text){
            case "/start":
                sendMessage.setText("Напишите арифметику...");
                try {
                    execute(sendMessage);
                }catch (TelegramApiException e){
                    e.printStackTrace();
                }
                break;
            default:
                try {
                    sendMessage.setText("Результат: " + String.valueOf(getMsg(text)));
                    execute(sendMessage);
                }catch (TelegramApiException e){
                    e.printStackTrace();
                }
        }
    }

    public float getMsg(String text) {
        try {
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
            return Float.valueOf(engine.eval(text).toString());
        }catch (ScriptException e){
            return 0;
        }
    }

    public String getBotUsername() {
        return "Calculator";
    }

    public String getBotToken() {
        return "630992306:AAE7kkGVbRw5Wb6WiZrEkvA97IbV3DpJezs";
    }
}
