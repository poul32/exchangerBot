package org.example.chipexchangerbot;

import com.huobi.client.MarketClient;
import com.huobi.client.req.market.MarketDetailRequest;
import com.huobi.constant.HuobiOptions;
import com.huobi.model.market.MarketDetail;
import com.huobi.model.market.MarketTicker;
import org.example.chipexchangerbot.tronwallet.TronWallet;
import org.example.chipexchangerbot.tronwallet.model.Transaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class ChipExchangerBotApplication {

    public static void main(String[] args) {
//        ConfigurableApplicationContext context = SpringApplication.run(ChipExchangerBotApplication.class, args);
//        TronWallet tronWallet = context.getBean(TronWallet.class);
//        List<Transaction> transactions = tronWallet.returnTransactionsByAddress("TLvGqh8DXy8bSmBqNuEnAemBL4J5VbHSY8");
//        System.out.println(transactions);
//        MarketClient marketClient = MarketClient.create(HuobiOptions.builder()
//                .apiKey("c1d56458-42194700-8fab447e-ghxertfvbf")
//                .secretKey("7d719eb2-68de9297-a3e34250-804bf")
//                .build());
        MarketClient marketClient = MarketClient.create(new HuobiOptions());
//        MarketDetail xmrusdt = marketClient.getMarketDetail(MarketDetailRequest.builder().symbol("xmrusdt").build());
        List<MarketTicker> tickers = marketClient.getTickers();
        System.out.println(tickers);
    }

}
