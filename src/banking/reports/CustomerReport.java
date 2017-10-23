package banking.reports;

import banking.domain.*;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Iterator;

/**
 * @author BJTU16301130
 * @version 20171023
 */
public class CustomerReport {
    public void generateReport() {

        try{
            File file = new File("report.txt");
            FileWriter fileWriter = new FileWriter(file);



            NumberFormat currency_format = NumberFormat.getCurrencyInstance ();
            Bank bank = Bank.getBank ();
            bank.sortCustomers();

            Customer customer;

            //ADD by ChenXD 2010.3.31,an example usage of currency_format，output ￥12.9
            //System.out.println(currency_format.format(12.9));


            // Generate a report
            fileWriter.write ("\t\t\tCUSTOMERS REPORT\r\n");
            fileWriter.write ("\t\t\t================\r\n");

            Iterator<Customer> iterator = bank.getCustomers();
            while (iterator.hasNext()){
                customer = iterator.next();

                fileWriter.write("\r\n");
                fileWriter.write ("Customer: "
                        + customer.getLastName () + ", "
                        + customer.getFirstName () + "\r\n");

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
                    fileWriter.write (account_type + " balance:" + currency_format.format (account.getBalance ()) + "\r\n");
                }
            }

            fileWriter.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}