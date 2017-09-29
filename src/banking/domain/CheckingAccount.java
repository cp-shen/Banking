package banking.domain;

/**
 * @author BJTU16301130
 * @version 201270929
 */
public class CheckingAccount extends Account{
    private double overdraftProtection;
    public CheckingAccount(double balance){
        super(balance);
    }
    public CheckingAccount(double balance, double protect){
        super(balance);
        overdraftProtection = protect;
    }
    public void withdraw(double amt)throws OverdraftException{
        if(balance >= amt){
            balance -= amt;
        }
        else if(balance + overdraftProtection >= amt){
            overdraftProtection -= amt - balance;
            balance = 0;
        }
        else if(overdraftProtection > 0){
            throw new OverdraftException("Insufficient funds for overdraft protection", amt - balance - overdraftProtection);
        }
        else{
            throw new OverdraftException("No overdraft protection", amt - balance );
        }
    }
}