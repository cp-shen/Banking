package view.server;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import domain.Account;
import domain.CheckingAccount;
import domain.Customer;
import domain.SavingsAccount;

public class AccountEditDialog{
    @FXML
    private TextField idField;
    @FXML
    private TextField balanceField;
    @FXML
    private TextField overdraftPrtField;
    @FXML
    private TextField interestRateField;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private RadioButton checkingRadioButton;
    @FXML
    private RadioButton savingRadioButton;
    @FXML
    private Label errorLabel;

    private Stage dialogStage;
    private BankServerGUI bankServerGUI;
    private Account formerAccount;

    //Used as owner when formerAccount is null
    private Customer customer;

    void setCustomer(Customer customer){
        this.customer = customer;
    }

    void setFormerAccount(Account formerAccount){
        this.formerAccount = formerAccount;
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
        if(formerAccount != null){
            idField.setText(formerAccount.getAccountId());
            balanceField.setText(Double.toString(formerAccount.getBalance()));
            if(formerAccount instanceof CheckingAccount){
                overdraftPrtField.setText(Double.toString(((CheckingAccount)formerAccount).getOverdraftProtection()));
                interestRateField.setEditable(false);
                checkingRadioButton.fire();
                savingRadioButton.setDisable(true);
            }else if(formerAccount instanceof SavingsAccount){
                interestRateField.setText(Double.toString(((SavingsAccount)formerAccount).getInterestRate()));
                overdraftPrtField.setEditable(false);
                savingRadioButton.fire();
                checkingRadioButton.setDisable(true);
            }
        }
    }

    @FXML
    private void handleOK(){
        double balance, overdraftPrt, interestRate;

        //validate the input
        try{
            balance = Double.parseDouble(balanceField.getText());

            //new account
            if(formerAccount == null){
                if(toggleGroup.getSelectedToggle() == checkingRadioButton){
                    overdraftPrt = Double.parseDouble(overdraftPrtField.getText());
                    customer.addAccount(new CheckingAccount(balance,customer,overdraftPrt));

                }else if(toggleGroup.getSelectedToggle() == savingRadioButton){
                    interestRate = Double.parseDouble(interestRateField.getText());
                    customer.addAccount(new SavingsAccount(balance, customer, interestRate));
                }

            //modified account
            }else{
                if(formerAccount instanceof CheckingAccount){
                    formerAccount.setBalance(balance);
                    overdraftPrt = Double.parseDouble(overdraftPrtField.getText());
                    ((CheckingAccount)formerAccount).setOverdraftProtection(overdraftPrt);

                }else if(formerAccount instanceof SavingsAccount){
                    formerAccount.setBalance(balance);
                    interestRate = Double.parseDouble(interestRateField.getText());
                    ((SavingsAccount)formerAccount).setInterestRate(interestRate);
                }
                bankServerGUI.showAccDetails(formerAccount);
            }


            dialogStage.close();
        }catch(Exception ex){
            errorLabel.setText("invalid input!");
        }
    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }

    @FXML
    private void handleSavingAcc(){
        interestRateField.setEditable(true);
        overdraftPrtField.setText("");
        overdraftPrtField.setEditable(false);
    }
    @FXML
    private void handleCheckingAcc(){
        overdraftPrtField.setEditable(true);
        interestRateField.setText("");
        interestRateField.setEditable(false);
    }

}
