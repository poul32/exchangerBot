package org.example.chipexchangerbot.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

@Component
public class KeyboardFactory {
    public ReplyKeyboard currencyInlineKeyboard() {
        InlineKeyboardButton usdt = InlineKeyboardButton
                .builder()
                .text("USDT")
                .callbackData("USDT")
                .build();

        InlineKeyboardButton xmr = InlineKeyboardButton
                .builder()
                .text("XMR")
                .callbackData("XMR")
                .build();
        return InlineKeyboardMarkup
                .builder()
                .keyboardRow(new InlineKeyboardRow(usdt,xmr))
                .build();
    }

    public ReplyKeyboard yesOrNoInlineKeyboard() {
        InlineKeyboardButton usdt = InlineKeyboardButton
                .builder()
                .text("Да")
                .callbackData("Да")
                .build();
        InlineKeyboardButton xmr = InlineKeyboardButton
                .builder()
                .text("Нет")
                .callbackData("Нет")
                .build();
        return InlineKeyboardMarkup
                .builder()
                .keyboardRow(new InlineKeyboardRow(usdt,xmr))
                .build();
    }
}
