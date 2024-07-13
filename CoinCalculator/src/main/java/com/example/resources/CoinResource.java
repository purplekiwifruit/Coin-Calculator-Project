package com.example.resources;


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
        return CoinChangeService.getCoins(coinChange.coinDenominators, coinChange.targetAmount);
    }

}
