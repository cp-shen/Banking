package domain;


import java.io.Serializable;

/**
 * The type Telegraph.
 * Used between the client and server
 */
public class Telegraph implements Serializable{
    public enum Command implements Serializable {
        WITHDRAW, DEPOSIT
    }

    private String accId;
    private Command command;
    private double amt;
    private Boolean ifSuccess;

    public Telegraph (String accId, double amt, Command command){
        this.accId = accId;
        this.amt = amt;
        this.command = command;
        this.ifSuccess = false;
    }



    void setIfSuccess(Boolean ifSuccess){
        this.ifSuccess = ifSuccess;
    }




    String getAccId(){
        return accId;
    }

    Command getCommand(){
        return command;
    }

    double getAmt(){
        return amt;
    }

    Boolean getIfSuccess(){
        return ifSuccess;
    }
}
