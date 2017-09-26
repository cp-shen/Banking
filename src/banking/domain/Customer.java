package banking.domain;

import banking.domain.Account;

public class Customer
{
    private String firstName , lastName;

    private Account[] accounts = null ;
    private int numberOfAccounts =0 ;

    public String getFirstName () {
        return firstName;
    }
    public String getLastName () {
        return lastName;
    }

    public void addAccount(Account account){

        //skip the existing account, place the new account in the array and update numberOfAccounts
        int i = 0;
        while (accounts[i] != null && i < accounts.length){
            i++;
        }
        if (accounts[i] == null && i < accounts.length){
            accounts[i] = account;
            numberOfAccounts = i + 1;
        }
    }
    public Account getAccount(int i){
        return accounts[i];
    }
    public int getNumOfAccounts() {
        return numberOfAccounts;
    }

    /**
     * @param f fist name
     * @param l last name
     * initializes the accounts array with maximum size as 6
     */
    public Customer(String f , String l)
    {
        firstName = f;
        lastName = l;
        accounts = new Account[6];
    }
}
