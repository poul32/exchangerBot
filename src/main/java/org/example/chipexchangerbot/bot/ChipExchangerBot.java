package org.example.chipexchangerbot.bot;

import jakarta.annotation.PostConstruct;
import org.example.chipexchangerbot.tronwallet.Wallet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.telegrambots.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.abilitybots.api.objects.Flag;
import org.telegram.telegrambots.abilitybots.api.objects.Reply;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.function.BiConsumer;

import static org.telegram.telegrambots.abilitybots.api.objects.Locality.ALL;
import static org.telegram.telegrambots.abilitybots.api.objects.Privacy.PUBLIC;

@Component
public class ChipExchangerBot extends AbilityBot {
    private static final String BOT_USERNAME = "ChipExchangerBot";
    private final TelegramBotsLongPollingApplication botApplication;
    private final String botToken;
    private final MessageFactory messageFactory;

    public ChipExchangerBot(@Value("${bot.token}") String token,
                            TelegramBotsLongPollingApplication botApplication, KeyboardFactory keyboardFactory, Wallet tronWallet) {
        super(new OkHttpTelegramClient(token), BOT_USERNAME);
        this.botToken = token;
        this.botApplication = botApplication;
        messageFactory = new MessageFactory(keyboardFactory, silent, getDb(), tronWallet);
    }

    @Override
    public long creatorId() {
        return 311959705;
    }

    @PostConstruct
    public void registerBot() {
        this.onRegister();
        try {
            botApplication.registerBot(botToken, this);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public Ability replayToStart() {
        return Ability
                .builder()
                .name("start")
                .info("Replay on start")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> messageFactory.start(ctx.chatId()))
                .build();
    }

    public Reply replyToInlineButtons() {
        BiConsumer<BaseAbilityBot, Update> action = (abilityBot, upd)
                -> messageFactory.replyToInlineButtons(upd.getCallbackQuery().getMessage().getChatId(), upd.getCallbackQuery().getData());
        return Reply.of(action, Flag.CALLBACK_QUERY);
    }

    public Reply replyToText() {
        BiConsumer<BaseAbilityBot, Update> action = (abilityBot, upd)
                -> messageFactory.replyToText(upd);
        return Reply.of(action, Flag.TEXT, update -> !update.getMessage().isCommand());
    }

}
