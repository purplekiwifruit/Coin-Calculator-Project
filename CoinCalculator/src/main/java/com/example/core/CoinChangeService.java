package com.example.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CoinChangeService {

    public static List<Double> getCoins(List<Double> coinDenominators, Double targetAmount) {

        List<Double> ALLOWED_DENOMINATIONS = List.of(0.01, 0.05, 0.1, 0.2, 0.5, 1.0, 2.0, 5.0, 10.0, 50.0, 100.0, 1000.0);

        // Input validation
        if (targetAmount <= 0 || targetAmount > 10000) {
            throw new IllegalArgumentException("Target amount must be within the range between 0 (exclusive) and 10,000.00");
        }

        List<Double> result = new ArrayList<>();

        // Handle null coinDenominators
        if (coinDenominators.size() == 0) {
            return result;
        }

        if (!ALLOWED_DENOMINATIONS.containsAll(coinDenominators)) {
            throw new IllegalArgumentException("All denominations must be within the allowed list");
        }

        // Greedy algorithm to find minimum number of coins
        Collections.sort(coinDenominators, Collections.reverseOrder());
        for (Double coin : coinDenominators) {
            while (targetAmount >= coin) {
                targetAmount -= coin;
                result.add(coin);
                // Round to avoid precision issues
                targetAmount = Math.round(targetAmount * 100.0) / 100.0;
            }
        }

        // Sort the result in ascending order before returning
        Collections.sort(result);
        return result;
    }
}
