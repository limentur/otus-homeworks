package ru.atm;

import java.util.HashMap;
import java.util.Map;

public class CurrencyStorageManager {

    public CurrencyStorageManager() {
    }

    public Map<Currency, AtmCell> currencyStorage = new HashMap<>();

    public AtmCell getCell(Currency cur){
        return currencyStorage.get(cur);
    }
}
