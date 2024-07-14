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
        final String targetOutOfRangeErrorMessage = String.format(CoinChangeErrorMessages.ERR_TARGET_AMOUNT_OUT_OF_RANGE, CoinChangeService.MIN_TARGET_EXCLUSIVE, CoinChangeService.MAX_TARGET_INCLUSIVE);

        // Test Case 3: Zero target amount
        Double targetAmount3 = 0.0;
        List<Double> coinDenominators3 = new ArrayList<>(Arrays.asList(1d, 2d, 50d));
        BadRequestException exception3 = assertThrows(BadRequestException.class, () -> CoinChangeService.getCoins(coinDenominators3, targetAmount3));
        assertEquals(targetOutOfRangeErrorMessage, exception3.getMessage());

        // Test Case 4: Negative target amount
        Double targetAmount4 = -5.0;
        BadRequestException exception4 = assertThrows(BadRequestException.class, () -> CoinChangeService.getCoins(coinDenominators3, targetAmount4));
        assertEquals(targetOutOfRangeErrorMessage, exception4.getMessage());

        // Test Case 5: Target amount exceeds limit
        Double targetAmount5 = 10001.0;
        BadRequestException exception5 = assertThrows(BadRequestException.class, () -> CoinChangeService.getCoins(coinDenominators3, targetAmount5));
        assertEquals(targetOutOfRangeErrorMessage, exception5.getMessage());

        // Test Case 6: More than two decimal places in target amount
        Double targetAmount6 = 7.001;
        BadRequestException exception6 = assertThrows(BadRequestException.class, () -> CoinChangeService.getCoins(coinDenominators3, targetAmount6));
        assertEquals(CoinChangeErrorMessages.ERR_TARGET_EXCEED_TWO_DECIMAL, exception6.getMessage());

    }

    @Test
    public void testEmptyDenominators() {
        // Test Case 7: Empty denominators list
        double targetAmount7 = 50.0;
        List<Double> coinDenominators7 = new ArrayList<>();
        BadRequestException exception = assertThrows(BadRequestException.class, () -> CoinChangeService.getCoins(coinDenominators7, targetAmount7));
        assertEquals(CoinChangeErrorMessages.ERR_DENOMINATION_EMPTY, exception.getMessage());
    }

    @Test
    public void testInvalidDenominations() {
        // Test Case 8: Invalid denominations
        Double targetAmount8 = 5.0;
        List<Double> invalidDenominators8 = new ArrayList<>(Arrays.asList(300d, 400d));
        BadRequestException exception = assertThrows(BadRequestException.class, () -> CoinChangeService.getCoins(invalidDenominators8, targetAmount8));
        assertEquals(CoinChangeErrorMessages.ERR_DENOMINATION_NOT_ALLOW, exception.getMessage());
    }

    @Test
    public void testNullDenominators() {
        // Test Case 9: Null denominators list
        Double targetAmount9 = 5.0;
        BadRequestException exception = assertThrows(BadRequestException.class, () -> CoinChangeService.getCoins(null, targetAmount9));
        assertEquals(CoinChangeErrorMessages.ERR_DENOMINATION_EMPTY, exception.getMessage());
    }

    @Test
    public void testNullTargetAmount() {
        // Test Case 10: Null target amount
        List<Double> coinDenominators10 = new ArrayList<>(Arrays.asList(1d, 2d, 5d));
        BadRequestException exception = assertThrows(BadRequestException.class, () -> CoinChangeService.getCoins(coinDenominators10, null));
        assertEquals(CoinChangeErrorMessages.ERR_TARGET_AMOUNT_NULL, exception.getMessage());
    }

    @Test
    public void testExceedingReturningCoinThreshold() {
        List<Double> coinDenominators11 = new ArrayList<>(Arrays.asList(0.01));
        double targetAmount10 = 10000;
        BadRequestException exception = assertThrows(BadRequestException.class, () -> CoinChangeService.getCoins(coinDenominators11, targetAmount10));
        assertEquals(CoinChangeErrorMessages.ERR_EXCEED_RETURNING_COIN_THRESHOLD, exception.getMessage());
    }

    @Test
    public void testTargetCannotMadeUpBySelectedDenominators() {
        List<Double> coinDenominators12 = new ArrayList<>(Arrays.asList(10.0));
        double targetAmount12 = 1;
        BadRequestException exception = assertThrows(BadRequestException.class, () -> CoinChangeService.getCoins(coinDenominators12, targetAmount12));
        assertEquals(CoinChangeErrorMessages.ERR_FAIL_TO_MADE_UP_TARGET, exception.getMessage());
    }
}
