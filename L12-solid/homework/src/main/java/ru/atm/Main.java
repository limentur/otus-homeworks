package ru.atm;

import static ru.atm.Currency.RUR;
import static ru.atm.Nominal.*;

public class Main {
    public static void main(String... args) throws Exception {
        Atm atm = new Atm();
        atm.processAddition(RUR,FIFTY,4);
        atm.processAddition(RUR,ONE_HUNDRED,2);
        atm.processAddition(RUR,TWO_HUNDREDS,1);
        atm.processWithdraw(RUR,600);
    }
}
