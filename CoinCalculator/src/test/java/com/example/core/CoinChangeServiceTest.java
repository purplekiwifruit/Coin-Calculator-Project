package com.example.core;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CoinChangeServiceTest {
    @Test
    public void testValidInput() {

        // Test Case 1: Normal scenario
        Double targetAmount1 = 7.03;
        List<Double> coinDenominators1 = new ArrayList<>(Arrays.asList(0.01, 0.5, 1d, 5d, 10d));
        List<Double> actualResult1 = CoinChangeService.getCoins(coinDenominators1, targetAmount1);
        List<Double> expectedResult1 = List.of(0.01, 0.01, 0.01, 1d, 1d, 5d);
        assertEquals(expectedResult1, actualResult1);

        // Test Case 2: Normal scenario
        Double targetAmount2 = 103.0;
        List<Double> coinDenominators2 = new ArrayList<>(Arrays.asList(1d, 2d, 50d));
        List<Double> actualResult2 = CoinChangeService.getCoins(coinDenominators2, targetAmount2);
        List<Double> expectedResult2 = List.of(1d, 2d, 50d, 50d);
        assertEquals(expectedResult2, actualResult2);
    }

    @Test
    public void testInvalidInput() {
        // Test Case 3: Zero target amount
        double targetAmount3 = 0.0;
        List<Double> coinDenominators3 = new ArrayList<>(Arrays.asList(1d, 2d, 50d));
        assertThrows(IllegalArgumentException.class, () -> CoinChangeService.getCoins(coinDenominators3, targetAmount3));

        // Test Case 4: Negative target amount
        double targetAmount4 = -5.0;
        assertThrows(IllegalArgumentException.class, () -> CoinChangeService.getCoins(coinDenominators3, targetAmount4));

        // Test Case 5: Target amount exceeds limit
        double targetAmount5 = 10001.0;
        assertThrows(IllegalArgumentException.class, () -> CoinChangeService.getCoins(coinDenominators3, targetAmount5));
    }

    @Test
    public void testEmptyDenominators() {
        // Test Case 6: Empty denominators list
        double targetAmount6 = 50.0;
        List<Double> coinDenominators6 = new ArrayList<>();
        List<Double> actualResult6 = CoinChangeService.getCoins(coinDenominators6, targetAmount6);
        assertEquals(new ArrayList<Double>(), actualResult6); // Expect an empty result
    }

    @Test
    public void testInvalidDenominations() {
        // Test Case: Invalid denominations
        double targetAmount = 5.0;
        List<Double> invalidDenominators = new ArrayList<>(Arrays.asList(3d, 4d)); // Not in allowed list
        assertThrows(IllegalArgumentException.class, () -> CoinChangeService.getCoins(invalidDenominators, targetAmount));
    }

}
