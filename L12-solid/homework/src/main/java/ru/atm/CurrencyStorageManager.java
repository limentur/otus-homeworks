package ru.atm;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CurrencyStorageManager {
    public static Map<Currency, TreeMap<Integer,Integer>> currencyStorage = new HashMap<>();

    public static TreeMap<Integer,Integer> getCurrencyStorage(Currency cur){
        return currencyStorage.get(cur);
    }
}
