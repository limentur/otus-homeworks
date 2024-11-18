package homework;

import java.util.*;

public class CustomerService {

    private final NavigableMap<Customer, String> customerMap;

    public CustomerService() {
        this.customerMap = new TreeMap<>(Comparator.comparingLong(Customer::getScores));
    }

    public Map.Entry<Customer, String> getSmallest() {
        return copyOfEntry(customerMap.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return copyOfEntry(customerMap.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        customerMap.put(customer,data);
    }

    private Map.Entry<Customer, String> copyOfEntry(Map.Entry<Customer, String> newMap) {
        if (newMap == null) return null;
        return new Map.Entry<>() {
            @Override
            public Customer getKey() {
                return newMap.getKey().newCustomer();
            }

            @Override
            public String getValue() {
                return newMap.getValue();
            }

            @Override
            public String setValue(String value) {
                return newMap.setValue(value);
            }
        };
    }
}
