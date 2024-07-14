package com.example.core;

public class CoinChangeErrorMessages {
    public static final String ERR_EXCEED_RETURNING_COIN_THRESHOLD = "Not enough coins, please select bigger denominators";
    public static final String ERR_FAIL_TO_MADE_UP_TARGET = "Target amount can't be made up with the selected coin denominators";
    public static final String ERR_TARGET_AMOUNT_NULL = "Target amount can't be null";
    public static final String ERR_TARGET_AMOUNT_OUT_OF_RANGE = "Target amount must be within the range between %f (exclusive) and %f";
    public static final String ERR_TARGET_EXCEED_TWO_DECIMAL = "Target amount must have no more than two decimal places";
    public static final String ERR_DENOMINATION_EMPTY = "Please provide at least one denomination";
    public static final String ERR_DENOMINATION_NOT_ALLOW = "All denominations must be within the allowed list";
}
