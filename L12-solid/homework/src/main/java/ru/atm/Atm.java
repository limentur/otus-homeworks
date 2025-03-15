package ru.atm;

import java.util.*;
import static ru.atm.CurrencyStorageManager.getCell;

public class Atm extends AtmMachine{
    public List<AtmCell> cells;
    public Atm(List<Currency> currencies) {
        new CurrencyCellBuilder(currencies);
    }

    public Atm(Currency currency) {
        new CurrencyCellBuilder(List.of(currency));
    }

    public void processAddition(Currency cur, Nominal nominal, Integer count) {
        moneyAdd(nominal, count, getCell(cur));
    }

    public void processWithdraw(Currency cur,Integer amount) throws Exception {
        withdraw(amount, getCell(cur));
    }
}
