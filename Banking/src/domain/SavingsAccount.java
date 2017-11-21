package domain;

import exceptions.ConflictedIdException;
import javafx.beans.property.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author BJTU16301130
 * @version 201270922
 */
public class SavingsAccount extends Account implements Serializable{
    private DoubleProperty interestRate = new SimpleDoubleProperty();



    public double getInterestRate(){
        return interestRate.get();
    }

    public void setInterestRate(double interestRate){
        this.interestRate.set(interestRate);
    }


    public SavingsAccount(double balance, Customer owner, double interest_rate)throws ConflictedIdException{
        super(balance,owner);
        setType("SavingsAccount");
        interestRate.set(interest_rate);

        //calculate the number
        int count = 0;
        for(Account account : owner.getAccounts()){
            if(account instanceof SavingsAccount){
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


    private void writeObject(ObjectOutputStream out)throws IOException{
        out.writeObject(getBalance());
        out.writeObject(getAccountId());
        out.writeObject(getType());
        out.writeObject(getOwner());
        out.writeObject(getNumber());
        out.writeObject(interestRate.get());
    }

    private void readObject(ObjectInputStream in)throws IOException, ClassNotFoundException{
        setBalance((Double)in.readObject());
        setAccountId((String)in.readObject());
        setType((String)in.readObject());
        setOwner((Customer)in.readObject());
        setNumber((Integer)in.readObject());
        interestRate = new SimpleDoubleProperty((Double)in.readObject());
    }

}




