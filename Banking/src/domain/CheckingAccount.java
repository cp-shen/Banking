package domain;

import exceptions.ConflictedIdException;
import exceptions.OverdraftException;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author BJTU16301130
 * @version 201270929
 */
public class CheckingAccount extends Account implements Serializable{
    private DoubleProperty overdraftProtection = new SimpleDoubleProperty();



    public double getOverdraftProtection(){
        return overdraftProtection.get();
    }

    public void setOverdraftProtection(double overdraftProtection){
        this.overdraftProtection.set(overdraftProtection);
    }


    public CheckingAccount(double balance, Customer owner, double protect)throws ConflictedIdException{
        super(balance,owner);
        setType("CheckingAccount");
        overdraftProtection.set(protect);

        //calculate the number
        int count = 0;
        for(Account account : owner.getAccounts()){
            if(account instanceof CheckingAccount){
                count++;
            }
        }
        setNumber(count);

        String accountId = owner.getId()+"-"+getType()+"-"+getNumber();
        for(Account account : owner.getAccounts()){
            if(account.getAccountId().equals(accountId)){
                throw new ConflictedIdException();
            }
        }
        setAccountId(accountId);
    }




    public void withdraw(double amt)throws OverdraftException{
        if( getBalance()>= amt){
            setBalance(getBalance()-amt);
        }
        else if(getBalance() + overdraftProtection.get() >= amt){
            overdraftProtection.set(overdraftProtection.get() + getBalance() - amt);
            setBalance(0);
        }
        else if(overdraftProtection.get() > 0){
            throw new OverdraftException("Insufficient funds for overdraft protection", amt - getBalance() - overdraftProtection.get());
        }
        else{
            throw new OverdraftException("No overdraft protection", amt - getBalance() );
        }
    }

    private void writeObject(ObjectOutputStream out)throws IOException{
        out.writeObject(getBalance());
        out.writeObject(getAccountId());
        out.writeObject(getType());
        out.writeObject(getOwner());
        out.writeObject(getNumber());
        out.writeObject(overdraftProtection.get());
    }

    private void readObject(ObjectInputStream in)throws IOException, ClassNotFoundException{
        setBalance((Double)in.readObject());
        setAccountId((String)in.readObject());
        setType((String)in.readObject());
        setOwner((Customer)in.readObject());
        setNumber((Integer)in.readObject());
        overdraftProtection = new SimpleDoubleProperty((Double)in.readObject());
    }

}