package banking;

/**
 * @author BJTU16301130
 * @version 20170919
 */
public class Bank{
    private Customer[] customers = null ;
    private int numberOfCustomers =0 ;

    /**
     * initializes the customers array with maximum size as 6
     */
    public Bank(){
        customers = new Customer[6];
    }
    public void addCustomer (String f , String l){

        Customer customer = new Customer(f,l);

        //skip the existing customer, place the new customer in the array and update numberOfCustomers
        int i = 0;
        while (customers[i] != null && i < customers.length){
            i++;
        }
        if (customers[i] == null && i < customers.length){
        customers[i] = customer;
        numberOfCustomers = i + 1;
        }
    }

    public int getNumOfCustomers() {
        return numberOfCustomers;
    }

    /**
     * @return  the customer associated with the given index parameter
     */
    public Customer getCustomer(int i) {
        return customers[i];
    }
}

