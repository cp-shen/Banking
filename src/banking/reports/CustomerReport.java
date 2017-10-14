package banking.reports;

import banking.domain.*;
import java.text.NumberFormat;
import java.util.Iterator;

/**
 * @author BJTU16301130
 * @version 20171014
 */
public class CustomerReport {
    public void generateReport() {
        NumberFormat currency_format = NumberFormat.getCurrencyInstance ();
        Bank bank = Bank.getBank ();
        bank.sortCustomers();

        Customer customer;

        //ADD by ChenXD 2010.3.31,an example usage of currency_format，output ￥12.9
        //System.out.println(currency_format.format(12.9));


        // Generate a report
        System.out.println ("\t\t\tCUSTOMERS REPORT");
        System.out.println ("\t\t\t================");

        Iterator<Customer> iterator = bank.getCustomers();
        while (iterator.hasNext()){
            customer = iterator.next();

            System.out.println ();
            System.out.println ("Customer: "
                    + customer.getLastName () + ", "
                    + customer.getFirstName ());

            Iterator<Account> iterator1 = customer.getAccounts();
            while(iterator1.hasNext()) {
                Account account = iterator1.next();
                String account_type = "";


                // Determine the account type
                /*** Step 1:
                 **** Use the instanceof operator to test what type of account
                 **** we have and set account_type to an appropriate value, such
                 **** as "Savings Account" or "Checking Account".
                 ***/
                if (account instanceof CheckingAccount) {
                    account_type = "CheckingAccount";
                } else if (account instanceof SavingsAccount) {
                    account_type = "SavingsAccount";
                }

                // Print the current balance of the account
                /*** Step 2:
                 **** Print out the type of account and the balance.
                 **** Feel free to use the currency_format formatter
                 **** to generate a "currency string" for the balance.
                 ***/
                System.out.printf (account_type + " balance:" + currency_format.format (account.getBalance ()) + "\n");
            }
        }
    }
}