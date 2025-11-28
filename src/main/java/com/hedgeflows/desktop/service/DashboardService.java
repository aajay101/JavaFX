package com.hedgeflows.desktop.service;

import com.hedgeflows.desktop.model.Metric;
import com.hedgeflows.desktop.model.Trade;
import com.hedgeflows.desktop.repository.TradeRepository;

import java.util.ArrayList;
import java.util.List;

public class DashboardService {
    private TradeRepository tradeRepository;

    public DashboardService() {
        this.tradeRepository = new TradeRepository();
    }

    public List<Metric> getKeyMetrics() {
        List<Metric> metrics = new ArrayList<>();
        
        // Mock metrics - in a real app, these would be calculated from data
        metrics.add(new Metric("Total Trades", "24", "+12%", true));
        metrics.add(new Metric("Profit/Loss", "$12,450", "+8.2%", true));
        metrics.add(new Metric("Active Positions", "7", "-2", false));
        metrics.add(new Metric("Win Rate", "68%", "+3.5%", true));
        
        return metrics;
    }

    public List<Trade> getRecentTrades() {
        // Return all trades for now - could be filtered/sorted in a real app
        return tradeRepository.findAll();
    }
}