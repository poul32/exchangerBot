package org.example.chipexchangerbot.bot;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.telegram.telegrambots.abilitybots.api.objects.Locality.ALL;
import static org.telegram.telegrambots.abilitybots.api.objects.Privacy.PUBLIC;

@Component
public class ChipExchangerBot extends AbilityBot {
    private static final String BOT_USERNAME = "ChipExchangerBot";
    private final TelegramBotsLongPollingApplication botApplication;
    private final String botToken;

    public ChipExchangerBot(@Value("${bot.token}") String token,
                            TelegramBotsLongPollingApplication botApplication) {
        super(new OkHttpTelegramClient(token), BOT_USERNAME);
        this.botToken = token;
        this.botApplication = botApplication;
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

    public Ability sayHelloWorld() {
        return Ability
                .builder()
                .name("hello")
                .info("says hello world!")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send("Hello world!", ctx.chatId()))
                .build();
    }
}
