package banking.reports;

import banking.domain.*;

import java.text.NumberFormat;

public class CustomerReport {
    public void generateReport() {
        NumberFormat currency_format = NumberFormat.getCurrencyInstance ();
        Bank bank = Bank.getBank ();
        Customer customer;

        //ADD by ChenXD 2010.3.31,an example usage of currency_format，output ￥12.9
        //System.out.println(currency_format.format(12.9));


        // Generate a report
        System.out.println ("\t\t\tCUSTOMERS REPORT");
        System.out.println ("\t\t\t================");

        for (int cust_idx = 0; cust_idx < bank.getNumOfCustomers (); cust_idx++) {
            customer = bank.getCustomer (cust_idx);

            System.out.println ();
            System.out.println ("Customer: "
                    + customer.getLastName () + ", "
                    + customer.getFirstName ());

            for (int acct_idx = 0; acct_idx < customer.getNumOfAccounts (); acct_idx++) {
                Account account = customer.getAccount (acct_idx);
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