package ru.atm;

import java.util.List;
import java.util.TreeMap;

import static ru.atm.CurrencyStorageManager.currencyStorage;

public class CurrencyCellBuilder {
    public CurrencyCellBuilder(List<Currency> curList) {
        for (Currency currency : curList){
            currencyStorage.put(currency,new TreeMap<>());
        }
    }
}
