package ru.atm;

import java.util.List;

import static ru.atm.Currency.*;
import static ru.atm.CurrencyStorageManager.getAccountBalance;
import static ru.atm.Nominal.*;

public class Main {
    public static void main(String... args) throws Exception {
        Atm atm = new Atm(List.of(RUR,EUR,USD));
        atm.processAddition(RUR,FIFTY,4);
        atm.processAddition(RUR,ONE_HUNDRED,2);
        atm.processAddition(RUR,TWO_HUNDREDS,1);
        atm.processWithdraw(RUR,600);
        System.out.println("Баланс на счете "+ getAccountBalance(RUR));
    }
}
