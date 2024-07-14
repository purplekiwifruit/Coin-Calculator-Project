package com.example.core;

import com.google.common.annotations.VisibleForTesting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class CoinChangeService {
    @VisibleForTesting
    public static final double MIN_TARGET_EXCLUSIVE = 0;
    @VisibleForTesting
    public static final double MAX_TARGET_INCLUSIVE = 10000;

    private static final int MAX_RETURN_COIN = 500;
    private static final Set<Double> ALLOWED_DENOMINATIONS = Set.of(0.01, 0.05, 0.1, 0.2, 0.5, 1.0, 2.0, 5.0, 10.0, 50.0, 100.0, 1000.0);

    public static List<Double> getCoins(List<Double> coinDenominators, final Double originalTargetAmount) {
        validateInput(coinDenominators, originalTargetAmount);

        Double targetAmount = originalTargetAmount;

        int countCount = 0;

        // Greedy algorithm to find minimum number of coins
        List<Double> result = new ArrayList<>();
        coinDenominators.sort(Collections.reverseOrder());
        for (Double coin : coinDenominators) {
            while (targetAmount >= coin) {
                targetAmount -= coin;
                result.add(coin);
                countCount++;

                // If the num of returning coin exceeds, throw an error before finishing the calculation
                if (countCount > MAX_RETURN_COIN) {
                    throw new BadRequestException(CoinChangeErrorMessages.ERR_EXCEED_RETURNING_COIN_THRESHOLD);
                }

                // Round to avoid precision issues
                targetAmount = Math.round(targetAmount * 100.0) / 100.0;
            }
        }

        if (targetAmount > 0) {
            throw new BadRequestException(CoinChangeErrorMessages.ERR_FAIL_TO_MADE_UP_TARGET);
        }

        // Sort the result in ascending order before returning
        Collections.sort(result);
        return result;
    }

    private static void validateInput(List<Double> coinDenominators, Double targetAmount) {
        if (targetAmount == null) {
            throw new BadRequestException(CoinChangeErrorMessages.ERR_TARGET_AMOUNT_NULL);
        }
        if (targetAmount <= MIN_TARGET_EXCLUSIVE || targetAmount > MAX_TARGET_INCLUSIVE) {
            throw new BadRequestException(String.format(CoinChangeErrorMessages.ERR_TARGET_AMOUNT_OUT_OF_RANGE, MIN_TARGET_EXCLUSIVE, MAX_TARGET_INCLUSIVE));
        }

        if (Math.round(targetAmount * 100) != targetAmount * 100) {
            throw new BadRequestException(CoinChangeErrorMessages.ERR_TARGET_EXCEED_TWO_DECIMAL);
        }

        if (coinDenominators == null || coinDenominators.isEmpty()) {
            throw new BadRequestException(CoinChangeErrorMessages.ERR_DENOMINATION_EMPTY);
        }

        if (!ALLOWED_DENOMINATIONS.containsAll(coinDenominators)) {
            throw new BadRequestException(CoinChangeErrorMessages.ERR_DENOMINATION_NOT_ALLOW);
        }
    }
}
