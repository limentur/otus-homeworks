package ru.atm;

import java.util.*;

public class Atm extends AtmMachine{
    public Atm(List<Currency> currencies) {
        currencyStorageManager = new CurrencyStorageManager();
        currencyCellBuilder(currencies);
    }

    public Atm(Currency currency) {
        currencyStorageManager = new CurrencyStorageManager();
        currencyCellBuilder(currency);
    }

    public void processAddition(Currency cur, Nominal nominal, Integer count) {
        moneyAdd(nominal, count, currencyStorageManager.getCell(cur));
    }

    public void processWithdraw(Currency cur,Integer amount) throws Exception {
        withdraw(amount, currencyStorageManager.getCell(cur));
    }
}
