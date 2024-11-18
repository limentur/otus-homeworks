package homework;

import java.util.*;

public class CustomerReverseOrder {

    private final Deque<Customer> reversedList;

    public CustomerReverseOrder() {
        this.reversedList = new ArrayDeque<>();
    }

    public void add(Customer customer) {
        reversedList.add(customer);
    }

    public Customer take() {
        return reversedList.pollLast();
    }
}
