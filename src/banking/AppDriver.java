package banking;

import banking.UI.BankUserUI;
import banking.domain.Account;
import banking.domain.Customer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author BJTU16301130
 */
public class AppDriver{
    private List<Customer> customers = new ArrayList<>();
    private List<Account> accounts = new ArrayList<>();
    private final static AppDriver appDriver = new AppDriver();

    private AppDriver(){}

    public static AppDriver getAppDriver(){
        return appDriver;
    }

    public static void main(String[] args){
        BankUserUI.getBankUerUI().initGUI(appDriver.customers,appDriver.accounts);
    }

    public void CreateACustomer(JFrame frame){
        String input, firstName, lastName;
        input = JOptionPane.showInputDialog(frame,"customer name (eg. Bob Smith)", "new customer",
                    JOptionPane.INFORMATION_MESSAGE);

        if((input != null) && input.split(" +").length == 2){
            firstName = input.split(" +")[0];
            lastName = input.split(" +")[1];
            appDriver.customers.add(new Customer(firstName,lastName));
            BankUserUI.getBankUerUI().updateCustomers(appDriver.customers,appDriver.accounts);
        }
    }
    public void CreateAnAccount(JFrame frame, Customer customer){
        String input;
        double balance;
        input = JOptionPane.showInputDialog(frame, "balance:", "new account",JOptionPane.INFORMATION_MESSAGE);
        try{
            balance = Double.parseDouble(input);
            customer.addAccount(new Account(balance));
        }catch(NullPointerException ex){ }
        catch(NumberFormatException ex){ }

        BankUserUI.getBankUerUI().updateAccounts(customer);
    }
}
