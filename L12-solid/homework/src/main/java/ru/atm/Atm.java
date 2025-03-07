package ru.atm;

import java.util.*;
import static ru.atm.Currency.*;
import static ru.atm.CurrencyStorageManager.currencyStorage;
import static ru.atm.CurrencyStorageManager.getCurrencyStorage;

public class Atm {
    public TreeMap<Integer,Integer> curStorage;

    public Atm() {
        new CurrencyCellBuilder(List.of(RUR,EUR,USD));
    }

    public void processAddition(Currency cur,Nominal nominal, Integer count){
        curStorage = getCurrencyStorage(cur);
        moneyAdd(nominal, count);
        System.out.println("Баланс "+getAccountBalance() +" "+ cur);
    }

    public void processWithdraw(Currency cur,Integer amount) throws Exception {
        curStorage = getCurrencyStorage(cur);
        withdraw(cur, amount);
        System.out.println("Остаток на счете" + getAccountBalance());
    }

    private void moneyAdd(Nominal nominal, Integer count){
        curStorage = getCurrencyStorage(RUR);
            if (curStorage.containsKey(nominal.getValue())){
                curStorage.merge(nominal.getValue(),count,Integer::sum);
            }else {
                curStorage.put(nominal.getValue(),count);
            }
    }

    private int getAccountBalance() {
        int balance = 0;
        for (Map.Entry<Integer, Integer> entry : curStorage.descendingMap().entrySet()){
            balance += entry.getKey() * entry.getValue();
        }
        return balance;
    }

    private void withdraw(Currency cur, int amount) throws Exception {
        TreeMap<Integer, Integer> withdrawMap = new TreeMap<>(countWithdraw(cur, amount));
        currencyStorage.put(RUR, new TreeMap<>(withdrawMap));
        for (Map.Entry<Integer, Integer> entry : withdrawMap.entrySet()){
            System.out.println("Выдано "+ entry.getValue()+ "купюр с номиналом "+ entry.getKey());
        }
    }

    private TreeMap<Integer, Integer> countWithdraw(Currency cur, int amount) throws Exception {
        TreeMap<Integer, Integer> denominationsMap = new TreeMap<>(currencyStorage.get(cur));
        TreeMap<Integer,Integer> withdrawMap = new TreeMap<>();

        while (amount != 0){
            if (denominationsMap.isEmpty()){
                throw new Exception("Нет необходимых купюр для выдачи");
            }

            int biggestNominal = denominationsMap.lastEntry().getKey();

            if (denominationsMap.size() == 1 && amount < biggestNominal){
                throw new Exception("Нет необходимых купюр для выдачи");
            }

            //Если запрошенная сумма больше чем макс номинал умноженный на количество купюр
            if (amount >= biggestNominal * denominationsMap.get(biggestNominal)) {
                amount = amount - (biggestNominal * denominationsMap.get(biggestNominal));
                withdrawMap.put(biggestNominal,denominationsMap.get(biggestNominal));
                denominationsMap.remove(biggestNominal);
            } else {
                //Если запрошенная сумма меньше чем одна купюра номинала
                if(amount < biggestNominal){
                    denominationsMap.remove(biggestNominal);
                }
                else {
                    //Если запрошенная сумма меньше чем макс номинал умноженный на количество купюр
                    for (int i = 1; i < denominationsMap.get(biggestNominal); i++){
                        if (((i * biggestNominal)+biggestNominal) > amount){
                            amount += amount - ((i * biggestNominal));
                            withdrawMap.put(biggestNominal,denominationsMap.get(biggestNominal));
                            denominationsMap.remove(biggestNominal);
                        }
                    }
                }
            }
        }
        return withdrawMap;
    }
}
