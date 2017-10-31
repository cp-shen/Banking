/*
 * This class creates the program to test the banking classes.
 * It creates a set of customers, with a few accounts each,
 * and generates a report of current account balances.
 */

import banking.domain.*;
import banking.reports.CustomerReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


//public class TestBanking {
//
//    public static void main(String[] args) {
//        Bank     bank = Bank.getBank();
//        Customer customer;
//        CustomerReport report = new CustomerReport();
//
//        try{
//            BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"));
//
//            //skip the first line
//            reader.readLine();
//            String got = reader.readLine();
//            int customerIndex = 0;
//
//            while(got != null && !got.trim().equals("")){
//                String[] contents = got.split("\t+");
//
//                bank.addCustomer(contents[1].trim(),contents[0].trim());
//                customer = bank.getCustomer(customerIndex);
//
//                if(!contents[2].trim().equals("null")){
//                    customer.addAccount(new SavingsAccount(Double.parseDouble(contents[2].trim()) , 0)  );
//                }
//                if(!contents[3].trim().equals("null")){
//                    customer.addAccount(new CheckingAccount(Double.parseDouble(contents[3].trim()), Double.parseDouble(contents[4].trim()) ));
//                }
//                customerIndex++;
//                got = reader.readLine();
//            }
//        }catch(IOException ex){
//            ex.printStackTrace();
//        }
//        // Generate a report
//        report.generateReport();
//    }
//}