package domain;

import exceptions.OverdraftException;
import javafx.beans.property.*;


public abstract class Account{
    private DoubleProperty balance;
    private StringProperty accountId;
    private StringProperty type;
    private ObjectProperty<Customer> owner;
    private IntegerProperty number;


    public Account(double balance, Customer owner){
        this.balance = new SimpleDoubleProperty(balance);
        accountId = new SimpleStringProperty();
        type = new SimpleStringProperty();
        this.owner = new SimpleObjectProperty<>(owner);
        number = new SimpleIntegerProperty();
    }


    /**
     * Instantiates a new Account.
     * Used for deserialization
     */
    protected Account(){
        balance = new SimpleDoubleProperty();
        accountId = new SimpleStringProperty();
        type = new SimpleStringProperty();
        owner = new SimpleObjectProperty<>();
        number = new SimpleIntegerProperty();
    }




    public String getAccountId(){
        return accountId.get();
    }

     void setAccountId(String accountId){
        this.accountId.set(accountId);
    }

    public StringProperty accountIdProperty(){
        return accountId;
    }







    public String getType(){
        return type.get();
    }

     void setType(String type){
        this.type.set(type);
    }

    public StringProperty typeProperty(){
        return type;
    }





    public double getBalance(){
        return balance.get();
    }

    public void setBalance(double balance){
        this.balance.set(balance);
    }




    public Customer getOwner(){
        return owner.get();
    }

    void setOwner(Customer owner){
        this.owner.set(owner);
    }




    int getNumber(){
        return number.get();
    }

    void setNumber(int number){
        this.number.set(number);
    }





    void deposit(double amount){
        setBalance(getBalance() + amount);

    }




    public void withdraw(double amount) throws OverdraftException{
        if(amount <= getBalance()){
            setBalance(getBalance() - amount);
        }else{
            throw new OverdraftException("insufficient funds", amount - getBalance());
        }
    }


}






