package pl.kurs.basics;

import java.util.HashSet;
import java.util.Set;

/**
 * Registry of the shop's customers.
 */
public class CustomerRegistry {

    private final Set<Customer> customers = new HashSet<>();

    /** Registers the customer. Returns false if a customer with this e-mail is already registered. */
    public boolean register(Customer customer) {
        return customers.add(customer);
    }

    public boolean isRegistered(Customer customer) {
        return customers.contains(customer);
    }

    /** Removes the customer. Returns false if they were not registered. */
    public boolean remove(Customer customer) {
        return customers.remove(customer);
    }

    /** Changes the e-mail of a registered customer. */
    public void changeEmail(Customer customer, String newEmail) {
        customer.setEmail(newEmail);
    }

    public int count() {
        return customers.size();
    }
}
