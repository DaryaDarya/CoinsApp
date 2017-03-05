package com.example.darya.test;

/**
 * Created by Darya on 05.03.2017.
 */
public class CoinModel {
    public String town;
    public Integer year;
    public Integer nomination;
    public CoinModel(String town, Integer year, Integer nomination){
        this.town = town;
        this.year = year;
        this.nomination = nomination;
    }

    public String toString(){
        return this.town + ": " + this.year + " - " + this.nomination +" руб";
    }
}
