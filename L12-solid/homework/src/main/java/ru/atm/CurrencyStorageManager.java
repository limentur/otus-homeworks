package ru.atm;

import java.util.HashMap;
import java.util.Map;

public class CurrencyStorageManager {
    public static Map<Currency, AtmCell> currencyStorage = new HashMap<>();

    public static AtmCell getCell(Currency cur){
        return currencyStorage.get(cur);
    }

    public static int getAccountBalance(Currency currency) {
            return currencyStorage.get(currency).countAccountBalance();
    }
}
