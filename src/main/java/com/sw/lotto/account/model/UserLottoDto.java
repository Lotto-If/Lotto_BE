package com.sw.lotto.account.model;

public class UserLottoDto {
    private int round;
    private String predictedNumbers;
    private Boolean notification;

    // Getters and Setters
    public int getRound() {
        return round;
    }
    public String getPredictedNumbers() {
        return predictedNumbers;
    }
    public Boolean getNotification() {
        return notification;
    }
}