package banking.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author BJTU16301130
 * @version 20171014
 */
public class Bank{
    private List<Customer> customers = null ;
    private static Bank bankInstance = new Bank();

    /**
     * initializes the customers array with maximum size as 6
     */
    private Bank(){
        customers = new ArrayList<>();
    }

    public void addCustomer (String f , String l){
        Customer customer = new Customer(f,l);
        customers.add(customer);
    }

    public int getNumOfCustomers() {
        return customers.size();
    }

    /**
     * @return  the customer associated with the given index parameter
     */
    public Customer getCustomer(int i) {
        return customers.get(i);
    }
    public Iterator<Customer> getCustomers(){
        return customers.iterator();
    }

    public void sortCustomers(){
        Collections.sort(customers);
    }

    public static Bank getBank(){
        return bankInstance;
    }
}

