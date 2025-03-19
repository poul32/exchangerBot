package org.example.chipexchangerbot.bot;

import org.example.chipexchangerbot.tronwallet.Wallet;
import org.telegram.telegrambots.abilitybots.api.db.DBContext;
import org.telegram.telegrambots.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class MessageFactory {
    private static final String CHAT_STATES = "CHAT_STATES";
    private static final String FIRST_CURRENCY = "FIRST_CURRENCY";
    private static final String SECOND_CURRENCY = "SECOND_CURRENCY";
    private static final String AMOUNT = "AMOUNT";
    private final SilentSender sender;
    private final KeyboardFactory keyboardFactory;
    private final DBContext db;
    private final Map<String, Wallet> wallets;

    public MessageFactory(KeyboardFactory keyboardFactory, SilentSender sender, DBContext db, Wallet tronWallet) {
        this.sender = sender;
        this.keyboardFactory = keyboardFactory;
        this.db = db;
        wallets = new HashMap<>();
        wallets.put("USDT", tronWallet);
    }

    public void start(long chatId) {
        SendMessage message = new SendMessage(String.valueOf(chatId), "Выберите валюту для обмена");
        message.setReplyMarkup(keyboardFactory.currencyInlineKeyboard());
        sender.execute(message);
        Map<Long, ChatState> chatStateMap = db.getMap(CHAT_STATES);
        chatStateMap.put(chatId, ChatState.CHOOSING_FIRST_CURRENCY);
    }

    public void replyToInlineButtons(Long chatId, String callbackQuery) {
        Map<Long, ChatState> chatStateMap = db.getMap(CHAT_STATES);
        switch (chatStateMap.get(chatId)) {
            case CHOOSING_FIRST_CURRENCY -> choosingFirstCurrency(chatId, callbackQuery);
            case CHOOSING_SECOND_CURRENCY -> choosingSecondCurrency(chatId, callbackQuery);
        }
    }

    private void choosingSecondCurrency(Long chatId, String callbackQuery) {
        Map<Long, String> firstCurrencyMap = db.getMap(FIRST_CURRENCY);
        String fistCurrency = firstCurrencyMap.get(chatId);
        if (callbackQuery.equals(fistCurrency)) {
            sender.send("Нельзя выбрать такую же валюту выберите снова:", chatId);
            SendMessage message = new SendMessage(String.valueOf(chatId), "Выберите вторую валюту для обмена");
            message.setReplyMarkup(keyboardFactory.currencyInlineKeyboard());
            sender.execute(message);
        } else {
            Map<Long, String> secondCurrencyMap = db.getMap(SECOND_CURRENCY);
            secondCurrencyMap.put(chatId, callbackQuery);
            sender.send(String.format("Введите количество %1$s для обмена", fistCurrency), chatId);
            Map<Long, ChatState> chatStateMap = db.getMap(CHAT_STATES);
            chatStateMap.put(chatId, ChatState.TYPING_AMOUNT);
        }
    }

    public void choosingFirstCurrency(long chatId, String callbackQuery) {
        Map<Long, String> firstCurrencyMap = db.getMap(FIRST_CURRENCY);
        firstCurrencyMap.put(chatId, callbackQuery);
        SendMessage message = new SendMessage(String.valueOf(chatId), "Выберите вторую валюту для обмена");
        message.setReplyMarkup(keyboardFactory.currencyInlineKeyboard());
        sender.execute(message);
        Map<Long, ChatState> chatStateMap = db.getMap(CHAT_STATES);
        chatStateMap.put(chatId, ChatState.CHOOSING_SECOND_CURRENCY);
    }

    public void replyToText(Update update) {
        Map<Long, ChatState> chatStateMap = db.getMap(CHAT_STATES);
        if (chatStateMap.get(update.getMessage().getChatId()) != null) {
            switch (chatStateMap.get(update.getMessage().getChatId())) {
                case TYPING_AMOUNT -> typingAmount(update.getMessage().getChatId(), update.getMessage().getText());
                default -> {}
            }
        }
    }

    private void typingAmount(Long chatId, String text) {
        Map<Long, BigDecimal> amountMap = db.getMap(AMOUNT);
        amountMap.put(chatId, new BigDecimal(text));
        SendMessage message = new SendMessage(String.valueOf(chatId), String.format("Курс обмена: %1$s. Продолжить?", 41));
        message.setReplyMarkup(keyboardFactory.yesOrNoInlineKeyboard());
        sender.execute(message);
        Map<Long, ChatState> chatStateMap = db.getMap(CHAT_STATES);
        chatStateMap.put(chatId, ChatState.EXCHANGE_CONFIRMATION);
    }

}