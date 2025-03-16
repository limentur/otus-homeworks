package ru.atm;
import java.util.List;
import java.util.TreeMap;

import static ru.atm.Currency.RUR;


abstract class AtmMachine {
    CurrencyStorageManager currencyStorageManager;

    public int getAccountBalance(Currency currency) {
        return currencyStorageManager.currencyStorage.get(currency).countAccountBalance();
    }

    public void currencyCellBuilder(List<Currency> curList) {
        for (Currency currency : curList){
            currencyStorageManager.currencyStorage.put(currency,new AtmCell());
        }
    }

    public void currencyCellBuilder(Currency currency) {
        currencyStorageManager.currencyStorage.put(currency,new AtmCell());
    }

    protected void moneyAdd(Nominal nominal, Integer count, AtmCell cell)  {
            if (cell.getCell().containsKey(nominal.getValue())){
                cell.getCell().merge(nominal.getValue(),count,Integer::sum);
            } else {
                cell.getCell().put(nominal.getValue(),count);
            }
    }

    protected void withdraw(int amount, AtmCell cell) throws Exception {
        recalkNominals(
                new TreeMap<>(
                        countWithdraw(amount,cell)
                )
        );
    }

    protected void recalkNominals (TreeMap<Integer, Integer> withdrawMap){
        AtmCell cellValueAfterWithdraw = new AtmCell();
        cellValueAfterWithdraw.setCell(withdrawMap);
        currencyStorageManager.currencyStorage.put(RUR, cellValueAfterWithdraw);
    }

    protected TreeMap<Integer, Integer> countWithdraw(int amount, AtmCell cell) throws Exception {
        TreeMap<Integer, Integer> denominationsMap = cell.getCell();
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
