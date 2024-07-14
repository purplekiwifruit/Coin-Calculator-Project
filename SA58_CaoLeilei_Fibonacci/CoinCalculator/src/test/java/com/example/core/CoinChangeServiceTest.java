package com.example.core;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CoinChangeServiceTest {
    final String targetOutOfRangeErrorMessage = String.format(CoinChangeErrorMessages.ERR_TARGET_AMOUNT_OUT_OF_RANGE, CoinChangeService.MIN_TARGET_EXCLUSIVE, CoinChangeService.MAX_TARGET_INCLUSIVE);

    @Test
    public void testValidInput() {
        Double targetAmount1 = 7.03;
        List<Double> coinDenominators1 = new ArrayList<>(Arrays.asList(0.01, 0.5, 1d, 5d, 10d));
        List<Double> actualResult1 = CoinChangeService.getCoins(coinDenominators1, targetAmount1);
        List<Double> expectedResult1 = List.of(0.01, 0.01, 0.01, 1d, 1d, 5d);
        assertEquals(expectedResult1, actualResult1);

        Double targetAmount2 = 103.0;
        List<Double> coinDenominators2 = new ArrayList<>(Arrays.asList(1d, 2d, 50d));
        List<Double> actualResult2 = CoinChangeService.getCoins(coinDenominators2, targetAmount2);
        List<Double> expectedResult2 = List.of(1d, 2d, 50d, 50d);
        assertEquals(expectedResult2, actualResult2);
    }

    @Test
    public void testTargetAmountZero() {
        Double targetAmount = 0.0;
        List<Double> coinDenominators = new ArrayList<>(Arrays.asList(1d, 2d, 50d));
        BadRequestException exception = assertThrows(BadRequestException.class, () -> CoinChangeService.getCoins(coinDenominators, targetAmount));
        assertEquals(targetOutOfRangeErrorMessage, exception.getMessage());
    }

    @Test
    public void testTargetAmountNegative() {
        List<Double> coinDenominators = new ArrayList<>(Arrays.asList(1d, 2d, 50d));
        Double targetAmount = -5.0;
        BadRequestException exception = assertThrows(BadRequestException.class, () -> CoinChangeService.getCoins(coinDenominators, targetAmount));
        assertEquals(targetOutOfRangeErrorMessage, exception.getMessage());
    }

    @Test
    public void testTargetAmountExceedMaximum() {
        List<Double> coinDenominators = new ArrayList<>(Arrays.asList(1d, 2d, 50d));
        Double targetAmount = 10001.0;
        BadRequestException exception = assertThrows(BadRequestException.class, () -> CoinChangeService.getCoins(coinDenominators, targetAmount));
        assertEquals(targetOutOfRangeErrorMessage, exception.getMessage());
    }

    @Test
    public void testEmptyDenominators() {
        double targetAmount = 50.0;
        List<Double> coinDenominators = new ArrayList<>();
        BadRequestException exception = assertThrows(BadRequestException.class, () -> CoinChangeService.getCoins(coinDenominators, targetAmount));
        assertEquals(CoinChangeErrorMessages.ERR_DENOMINATION_EMPTY, exception.getMessage());
    }

    @Test
    public void testInvalidDenominations() {
        Double targetAmount = 5.0;
        List<Double> invalidDenominators = new ArrayList<>(Arrays.asList(300d, 400d));
        BadRequestException exception = assertThrows(BadRequestException.class, () -> CoinChangeService.getCoins(invalidDenominators, targetAmount));
        assertEquals(CoinChangeErrorMessages.ERR_DENOMINATION_NOT_ALLOW, exception.getMessage());
    }

    @Test
    public void testNullDenominators() {
        Double targetAmount = 5.0;
        BadRequestException exception = assertThrows(BadRequestException.class, () -> CoinChangeService.getCoins(null, targetAmount));
        assertEquals(CoinChangeErrorMessages.ERR_DENOMINATION_EMPTY, exception.getMessage());
    }

    @Test
    public void testNullTargetAmount() {
        List<Double> coinDenominators = new ArrayList<>(Arrays.asList(1d, 2d, 5d));
        BadRequestException exception = assertThrows(BadRequestException.class, () -> CoinChangeService.getCoins(coinDenominators, null));
        assertEquals(CoinChangeErrorMessages.ERR_TARGET_AMOUNT_NULL, exception.getMessage());
    }

    @Test
    public void testExceedingReturningCoinThreshold() {
        List<Double> coinDenominators = new ArrayList<>(Arrays.asList(0.01));
        double targetAmount = 10000;
        BadRequestException exception = assertThrows(BadRequestException.class, () -> CoinChangeService.getCoins(coinDenominators, targetAmount));
        assertEquals(CoinChangeErrorMessages.ERR_EXCEED_RETURNING_COIN_THRESHOLD, exception.getMessage());
    }

    @Test
    public void testTargetCannotMadeUpBySelectedDenominators() {
        List<Double> coinDenominators = new ArrayList<>(Arrays.asList(10.0));
        double targetAmount = 1;
        BadRequestException exception = assertThrows(BadRequestException.class, () -> CoinChangeService.getCoins(coinDenominators, targetAmount));
        assertEquals(CoinChangeErrorMessages.ERR_FAIL_TO_MADE_UP_TARGET, exception.getMessage());
    }

    @Test
    public void testTargetDecimalPlaces() {
        List<Double> coinDenominators = new ArrayList<>(Arrays.asList(1.0));

        Double targetAmount1 = 7.001;
        BadRequestException exception1 = assertThrows(BadRequestException.class, () -> CoinChangeService.getCoins(coinDenominators, targetAmount1));
        assertEquals(CoinChangeErrorMessages.ERR_TARGET_EXCEED_TWO_DECIMAL, exception1.getMessage());

        double targetAmount2 = 1.11;
        BadRequestException exception2 = assertThrows(BadRequestException.class, () -> CoinChangeService.getCoins(coinDenominators, targetAmount2));
        assertEquals(CoinChangeErrorMessages.ERR_FAIL_TO_MADE_UP_TARGET, exception2.getMessage());

        double targetAmount3 = 1.123;
        BadRequestException exception3 = assertThrows(BadRequestException.class, () -> CoinChangeService.getCoins(coinDenominators, targetAmount3));
        assertEquals(CoinChangeErrorMessages.ERR_TARGET_EXCEED_TWO_DECIMAL, exception3.getMessage());
    }
}
