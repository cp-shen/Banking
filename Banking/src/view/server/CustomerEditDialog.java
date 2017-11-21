package view.server;

import exceptions.ConflictedIdException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import domain.BankServer;
import domain.Customer;

public class CustomerEditDialog{
    private Customer formerCustomer;
    private Stage dialogStage;
    private BankServerGUI bankServerGUI;

    @FXML
    private TextField cusIdField, fnField, lnFiled, pwField;
    @FXML
    private Label errorLabel;


    void setFormerCustomer(Customer formerCustomer){
        this.formerCustomer = formerCustomer;
    }

    void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    void setBankServerGUI(BankServerGUI bankServerGUI){
        this.bankServerGUI = bankServerGUI;
    }

    @FXML
    private void initialize(){
        Platform.runLater(this::showDetails);
    }

    private void showDetails(){
        if(formerCustomer != null){
            cusIdField.setEditable(false);

            cusIdField.setText(formerCustomer.getId());
            fnField.setText(formerCustomer.getFirstName());
            lnFiled.setText(formerCustomer.getLastName());
            pwField.setText(formerCustomer.getPassword());

        //prepare to create a new formerCustomer
        }else {
            cusIdField.setEditable(true);

            cusIdField.setText("");
            fnField.setText("");
            lnFiled.setText("");
            pwField.setText("");
        }
    }

    @FXML
    private void handleOK(){
        String id, fn, ln, pw;

        try{
            id = cusIdField.getText();
            fn = fnField.getText();
            ln = lnFiled.getText();
            pw = pwField.getText();

            //input validation
            if(id == null || id .equals("")
                    || fn == null || fn .equals("")
                    || ln == null || ln .equals("")
                    || pw == null || pw .equals("")){

                errorLabel.setText("invalid input");
                return;
            }

            //create a new customer
            if(formerCustomer == null){
                BankServer.getBankServer().addCustomer(new Customer(fn,ln,id,pw));

            //modify the former customer
            }else {
                formerCustomer.setFirstName(fn);
                formerCustomer.setLastName(ln);
                formerCustomer.setPassword(pw);

                bankServerGUI.showCusDetails(formerCustomer);
            }

            dialogStage.close();
        }catch(ConflictedIdException ex){
            errorLabel.setText("id conflicted");
        }
    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }

}
