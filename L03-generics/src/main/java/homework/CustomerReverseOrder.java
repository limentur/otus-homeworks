package homework;

import java.util.*;

public class CustomerReverseOrder {

    private List<Customer> reversedList = new LinkedList<>();
    private boolean isReversed = false;

    public void add(Customer customer) {
        reversedList.add(customer);
    }

    public Customer take() {
        Customer lastCustomer;
        if (!isReversed){
            reversedList = reversedList.reversed();
            isReversed = true;
        }
        lastCustomer =  reversedList.getFirst();
        reversedList.remove(reversedList.getFirst());
        return lastCustomer;
    }
}
