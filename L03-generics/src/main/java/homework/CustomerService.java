package homework;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {

    private final Map<Customer, String> customerMap = new HashMap<>();

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> min = null;
        for (Map.Entry<Customer, String> entryMap : customerMap.entrySet()) {
            if (min == null || min.getKey().getScores() > entryMap.getKey().getScores()) {
                min = new AbstractMap.SimpleEntry<>(entryMap.getKey(), entryMap.getValue());
            }
        }

        assert min != null;
        return copyOfEntry(min);
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> middle = null;
        for (Map.Entry<Customer, String> entryMap : customerMap.entrySet()) {
            if (middle == null || (middle.getKey().getScores() < entryMap.getKey().getScores()) && middle.getKey().getScores() < customer.getScores()) {
                middle = entryMap;
            }
        }

        assert middle != null;
        if (customer.getScores() > middle.getKey().getScores()){
            return null;
        }

        return middle;
    }

    public void add(Customer customer, String data) {
        customerMap.put(customer,data);
    }

    private Map.Entry<Customer, String> copyOfEntry(Map.Entry<Customer, String> newMap) {
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
