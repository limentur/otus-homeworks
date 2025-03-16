package ru.atm;

import java.util.Map;
import java.util.TreeMap;

public class AtmCell {
    public Nominal nominal;
    public Integer count;

    private TreeMap <Integer,Integer> cell;

    public AtmCell() {
        this.cell = new TreeMap<>();
    }

    public int countAccountBalance() {
        int balance = 0;
        if (!cell.isEmpty()) {
            for (Map.Entry<Integer, Integer> entry : cell.entrySet()) {
                balance += entry.getKey() * entry.getValue();
            }
        }
        return balance;
    }

    public TreeMap<Integer, Integer> getCell() {
        return cell;
    }

    public void setCell(TreeMap<Integer, Integer> cell) {
        this.cell = cell;
    }
}
