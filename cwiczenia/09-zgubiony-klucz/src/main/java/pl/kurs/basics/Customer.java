package pl.kurs.basics;

import java.util.Objects;

/**
 * Customer. The e-mail identifies the customer: two customers with the same e-mail are the same customer.
 * Do not change equals and hashCode.
 */
public class Customer {

    private String email;
    private final String name;

    public Customer(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String email() {
        return email;
    }

    public String name() {
        return name;
    }

    void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Customer other && email.equals(other.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return name + " <" + email + ">";
    }
}
