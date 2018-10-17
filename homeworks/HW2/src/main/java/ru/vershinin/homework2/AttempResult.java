package ru.vershinin.homework2;

public class AttempResult {
    private Boolean result;
    private int bulls;
    private int cows;
    private int attemptNumber;

    AttempResult(Boolean result, int bulls, int cows, int attemptNumber) {
        this.result = result;
        this.bulls = bulls;
        this.cows = cows;
        this.attemptNumber = attemptNumber;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public int getBulls() {
        return bulls;
    }

    public void setBulls(int bulls) {
        this.bulls = bulls;
    }

    public int getCows() {
        return cows;
    }

    public void setCows(int cows) {
        this.cows = cows;
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(int attemptNumber) {
        this.attemptNumber = attemptNumber;
    }
}