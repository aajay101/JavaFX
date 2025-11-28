package com.hedgeflows.desktop.repository;

import com.google.gson.reflect.TypeToken;
import com.hedgeflows.desktop.model.Trade;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TradeRepository extends JsonRepository<Trade> {
    
    public TradeRepository() {
        super("data/trades.json", new TypeToken<ArrayList<Trade>>() {}.getType());
    }

    @Override
    protected List<Trade> seedData() {
        List<Trade> trades = new ArrayList<>();
        // Add sample trades
        trades.add(new Trade("EUR/USD", 10000.0, "BUY", "OPEN", LocalDate.now().minusDays(5)));
        trades.add(new Trade("GBP/USD", 15000.0, "SELL", "CLOSED", LocalDate.now().minusDays(3)));
        trades.add(new Trade("USD/JPY", 20000.0, "BUY", "OPEN", LocalDate.now().minusDays(2)));
        trades.add(new Trade("AUD/USD", 5000.0, "SELL", "CLOSED", LocalDate.now().minusDays(1)));
        trades.add(new Trade("USD/CAD", 7500.0, "BUY", "OPEN", LocalDate.now()));
        return trades;
    }
}