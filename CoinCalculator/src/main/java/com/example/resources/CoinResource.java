package com.example.resources;


import com.example.core.BadRequestException;
import com.example.core.CoinChangeService;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;


@Path("/coin")
@Produces(MediaType.APPLICATION_JSON)
public class CoinResource {

    public static class CoinChange {
        public Double targetAmount;
        public List<Double> coinDenominators;
    }

    @POST
    public List<Double> getCoin(CoinChange coinChange) {
        validateInput(coinChange);
        return CoinChangeService.getCoins(coinChange.coinDenominators, coinChange.targetAmount);
    }

    private void validateInput(CoinChange coinChange) {
        if (coinChange.coinDenominators == null) {
            throw new BadRequestException("Coin denominators cannot be null");
        }

        if (coinChange.targetAmount <= 0.0 || coinChange.targetAmount > 10000.0) {
            throw new BadRequestException("Target amount must be between 0.01 and 10000.00");
        }

        if (Math.round(coinChange.targetAmount * 100) != coinChange.targetAmount * 100) {
            throw new BadRequestException("Target amount must have no more than two decimal places");
        }
    }

}
