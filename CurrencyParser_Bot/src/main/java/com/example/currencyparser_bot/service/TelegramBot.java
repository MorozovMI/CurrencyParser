package com.example.currencyparser_bot.service;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.example.currencyparser_bot.config.BotConfig;
import com.example.currencyparser_bot.config.Parser;

@Component //Аннотация, указывающая, что класс TelegramBot является компонентом Spring и должен быть управляемым контейнером Spring.
public class TelegramBot extends TelegramLongPollingBot { //Объявление класса TelegramBot, который наследует класс TelegramLongPollingBot, предоставляемый Telegram Bots API.

    final BotConfig config; //Объявление переменной config типа BotConfig, которая хранит конфигурационные данные для бота.

    public TelegramBot(BotConfig config) {
        this.config = config;
    } //Конструктор класса TelegramBot, принимающий объект config типа BotConfig и присваивающий его переменной this.config.
    @Override
    public String getBotUsername() {
        return this.config.getBotName();
    } //Переопределение метода getBotUsername() из класса TelegramLongPollingBot, возвращающего имя бота, указанное в конфигурации.
    @Override
    public String getBotToken() {
        return this.config.getToken();
    } // Переопределение метода getBotToken() из класса TelegramLongPollingBot, возвращающего токен бота, указанный в конфигурации.
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            if (message != null && message.hasText()) {
                String command = message.getText();
                if ("/start".equals(command)) {
                    sendStartMessage(message);
                } else if ("Курс доллара".equals(command)) {
                    sendDollarRate(message);
                } else if ("Курс евро".equals(command)) {
                    sendEuroRate(message);
                } else if ("Курс юаня".equals(command)) {
                    sendCNYRate(message);
                } else {
                    sendUnknownCommandMessage(message);
                }
                }
            }
    }
    //Переопределение метода onUpdateReceived() из класса TelegramLongPollingBot, который вызывается при получении обновления от Telegram API.
    // В этом методе обрабатываются только сообщения с текстовым содержимым.
    // Если сообщение соответствует определенным командам (/start, Курс Доллара, Курс Евро, Курс Юаня), вызывается соответствующий метод для отправки ответного сообщения.


    private void sendStartMessage(Message message) { //Метод sendStartMessage(), который отправляет приветственное сообщение с доступными командами для получения актуальных курсов валют.
        String text = "Добрый день! Данный бот разработан студентом группы РИЗ-120916-у Морозовым Михаилом Игоревичем. Бот предназначен для получения актуальных курсов валют, для ввода доступны следующие команды:\n"
                + "Курс доллара\n"
                + "Курс евро\n"
                + "Курс юаня";
        TelegramUtil.sendMessage(this, message, text);
    }
    private void sendUnknownCommandMessage(Message message) { //Метод sendUnknownCommandMessage(), который отправляет сообщение, если была получена неизвестная команда.
        String text = "Я вас не понимаю. Пожалуйста, используйте доступные команды:\n"
                + "Курс доллара\n"
                + "Курс евро\n"
                + "Курс юаня";
        TelegramUtil.sendMessage(this, message, text);}


    private void sendDollarRate(Message message) { //Метод sendDollarRate(), который получает курс доллара с использованием метода getDollarRate() из класса Parser. З
        // Затем курс форматируется и отправляется в ответное сообщение.
        try {
            double rate = Parser.getDollarRate();
            NumberFormat formatter = new DecimalFormat("#0.00");
            String text = "Курс доллара: " + formatter.format(rate) + " руб.";
            TelegramUtil.sendMessage(this, message, text);
        } catch (IOException e) {
            e.printStackTrace();
            TelegramUtil.sendMessage(this, message, "Не удалось получить курс доллара");
        }
    }

    private void sendEuroRate(Message message) {
        try {
            double rate = Parser.getEuroRate();
            NumberFormat formatter = new DecimalFormat("#0.00");
            String text = "Курс евро: " + formatter.format(rate) + " руб.";
            TelegramUtil.sendMessage(this, message, text);
        } catch (IOException e) {
            e.printStackTrace();
            TelegramUtil.sendMessage(this, message, "Не удалось получить курс Евро");
        }
    }
    //Метод sendEuroRate(), который получает курс евро с использованием метода getEuroRate() из класса Parser. Затем курс форматируется и отправляется в ответное сообщение.

    private void sendCNYRate(Message message) {
        try {
            double rate = Parser.getCNYRate();
            NumberFormat formatter = new DecimalFormat("#0.00");
            String text = "Курс юаня: " + formatter.format(rate) + " руб.";
            TelegramUtil.sendMessage(this, message, text);
        } catch (IOException e) {
            e.printStackTrace();
            TelegramUtil.sendMessage(this, message, "Не удалось получить курс Юаня");
        }
    }
    //Метод sendCNYRate(), который получает курс юаня с использованием метода getCNYRate() из класса Parser. Затем курс форматируется и отправляется в ответное сообщение.

    public class TelegramUtil {

        public static void sendMessage(AbsSender sender, Message message, String text) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(message.getChatId());
            sendMessage.setText(text);

            try {
                sender.execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        //Класс TelegramUtil, содержащий статический метод sendMessage(), который используется для отправки сообщений через объект sender.
        // Он создает объект SendMessage, устанавливает идентификатор чата и текст сообщения, а затем вызывает метод execute() для отправки сообщения через sender.

    }
}