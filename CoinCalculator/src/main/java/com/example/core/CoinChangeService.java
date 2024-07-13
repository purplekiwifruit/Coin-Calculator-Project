package com.example.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CoinChangeService {

    public static List<Double> getCoins(List<Double> coinDenominators, Double targetAmount) {

        // Input validation
        if (targetAmount < 0 || targetAmount > 10000) {
            throw new IllegalArgumentException("Target amount must be within the range between 0 and 10,000.00");
        }

        List<Double> result = new ArrayList<>();

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
