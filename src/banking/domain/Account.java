package banking.domain;

public class Account
{
    protected double balance;

    public Account(double init_balance)
    {
        balance = init_balance;
    }


    public double getBalance ()
    {
        return balance;
    }
    public boolean deposit(double amount)
    {
        balance += amount;
        return true;
    }
    public void withdraw (double amount) throws OverdraftException
    {
        if (amount <= balance)
        {
            balance -= amount;
        }
        else
            throw new OverdraftException("insufficient funds",amount - balance  );
    }

    @Override
    public String toString(){
        return "Account with a balance of " + new Double(balance).toString();
    }
}

