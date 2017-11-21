package domain;

import exceptions.ConflictedIdException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;



public class Customer implements Comparable<Customer>, Serializable
{
    private StringProperty firstName , lastName;
    private ObservableList<Account> accounts = null;
    private StringProperty id;
    private StringProperty password;



    public String getFirstName () {
        return firstName.get();
    }

    public void setFirstName(String firstName){
        this.firstName.set(firstName);
    }




    public String getLastName () {
        return lastName.get();
    }

    public void setLastName(String lastName){
        this.lastName.set(lastName);
    }






    public String getId(){
        return id.get();
    }

    public void setId(String id)throws ConflictedIdException{

        for(Customer customer : BankServer.getBankServer().getCustomers()){
            if(customer.id.get().equals(id)){
                throw new ConflictedIdException();
            }
        }
        this.id.set(id);
    }

    public StringProperty idProperty(){
        return id;
    }






    public String getPassword(){
        return password.get();
    }

    public void setPassword(String password){
        this.password.set(password);
    }


    @Override
    public int compareTo(Customer o) {
        if(this.lastName.get().compareTo(o.lastName.get()) > 0){
            return 1;
        }
        else if(this.lastName.get().compareTo(o.lastName.get()) < 0){
            return -1;
        }
        else {
            return 0;
        }
    }


    private void readObject (ObjectInputStream objIn)throws IOException, ClassNotFoundException{
        firstName = new SimpleStringProperty((String)objIn.readObject());
        lastName = new SimpleStringProperty((String)objIn.readObject());
        accounts = FXCollections.observableArrayList( (Account[])objIn.readObject());
        id = new SimpleStringProperty((String)objIn.readObject());
        password = new SimpleStringProperty((String)objIn.readObject());
    }
    private void writeObject (ObjectOutputStream objOut)throws IOException{
        objOut.writeObject(firstName.get());
        objOut.writeObject(lastName.get());
        objOut.writeObject(accounts.toArray(new Account[0]));
        objOut.writeObject(id.get());
        objOut.writeObject(password.get());
    }




    public void addAccount(Account account){
        accounts.add(account);
    }

    public void deleteAccount(Account account){
        accounts.remove(account);
    }

    public ObservableList<Account> getAccounts(){
        return accounts;
    }

    public Account getAccById(String accId){
        for(Account account : accounts){
            if(account.getAccountId().equals(accId)){
                return account;
            }
        }
        return null;
    }








    public Customer(String f , String l, String id, String pw)throws ConflictedIdException
    {
        for(Customer customer : BankServer.getBankServer().getCustomers())
        {
            if(customer.id.get().equals(id)){
                throw new ConflictedIdException();
            }
        }

        firstName = new SimpleStringProperty(f);
        lastName = new SimpleStringProperty(l);
        this.id = new SimpleStringProperty(id);
        password = new SimpleStringProperty(pw);
        accounts = FXCollections.observableArrayList();
    }
}
