package banking.domain;

/**
 * @author BJTU16301130
 * @version 201270922
 */
public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(double balance, double interest_rate){
        super(balance);
        interestRate = interest_rate;
    }
}

