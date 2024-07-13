package com.example.core;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoinChangeServiceTest {
    @Test
    public void test() {
        //Test Case 1
        Double targetAmount1 = 7.03;
        List<Double> coinDenominators1 = new ArrayList<>(Arrays.asList(0.01, 0.5, 1d, 5d, 10d));
        List<Double> actualResult1 = CoinChangeService.getCoins(coinDenominators1, targetAmount1);
        List<Double> expectedResult1 = List.of(0.01, 0.01, 0.01, 1d, 1d, 5d);
        assertEquals(expectedResult1, actualResult1);


        //Test Case 2
        Double targetAmount2 = 103.0;
        List<Double> coinDenominators2 = new ArrayList<>(Arrays.asList(1d, 2d, 50d));
        List<Double> actualResult2 = CoinChangeService.getCoins(coinDenominators2, targetAmount2);
        List<Double> expectedResult2 = List.of(1d, 2d, 50d, 50d);
        assertEquals(expectedResult2, actualResult2);

    }
}
