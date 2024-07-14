package com.example.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CoinChangeService {

    public static List<Double> getCoins(List<Double> coinDenominators, Double targetAmount) {

        validateInput(coinDenominators, targetAmount);

        // Greedy algorithm to find minimum number of coins
        List<Double> result = new ArrayList<>();
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

    private static void validateInput(List<Double> coinDenominators, Double targetAmount) {
        if (targetAmount == null) {
            throw new BadRequestException("Target amount cannot be null");
        }
        if (targetAmount <= 0 || targetAmount > 10000) {
            throw new BadRequestException("Target amount must be within the range between 0 (exclusive) and 10,000.00");
        }

        if (Math.round(targetAmount * 100) != targetAmount * 100) {
            throw new BadRequestException("Target amount must have no more than two decimal places");
        }

        if (coinDenominators == null || coinDenominators.isEmpty()) {
            throw new BadRequestException("Please provide at least one denomination");
        }

        List<Double> ALLOWED_DENOMINATIONS = List.of(0.01, 0.05, 0.1, 0.2, 0.5, 1.0, 2.0, 5.0, 10.0, 50.0, 100.0, 1000.0);
        if (!ALLOWED_DENOMINATIONS.containsAll(coinDenominators)) {
            throw new BadRequestException("All denominations must be within the allowed list");
        }
    }

}
