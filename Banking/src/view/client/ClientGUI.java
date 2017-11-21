package view.client;

import domain.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


public class ClientGUI{
    @FXML
    private TableView<Account> accountTable;
    @FXML
    private TableColumn<Account,String> accountIdColumn;
    @FXML
    private TableColumn<Account,String> typeColumn;
    @FXML
    private TextField balanceField;
    @FXML
    private TextField overdraftPrtField;
    @FXML
    private TextField interestRateField;
    @FXML
    private TextField amountField;
    @FXML
    private Label errorLabel;

    private Customer customer;
    private Client client;

    void setClient(Client client){
        this.client = client;
    }

    void setCustomer(Customer customer){
        this.customer = customer;
        accountTable.setItems(customer.getAccounts());
    }

    @FXML
    private void initialize(){
        accountIdColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().accountIdProperty() );
        typeColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().typeProperty());
        accountTable.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> showAccountDetails(newValue));
    }


    private void showAccountDetails(Account account){
        if(account != null){
            balanceField.setText(Double.toString(account.getBalance()));
            if(account instanceof  CheckingAccount){
                overdraftPrtField.setText(Double.toString( ((CheckingAccount)account).getOverdraftProtection() ));
            }else {
                overdraftPrtField.setText("");
            }
            if(account instanceof SavingsAccount){
                interestRateField.setText(Double.toString( ((SavingsAccount)account).getInterestRate() ));
            }else {
                interestRateField.setText("");
            }
        }else {
            balanceField.setText("");
            overdraftPrtField.setText("");
            interestRateField.setText("");
        }
    }

    @FXML
    private void handleWithdrawButton(){
        Account currentAccount = accountTable.getSelectionModel().selectedItemProperty().get();

        try{
            if(currentAccount != null){
                double amount = Double.parseDouble(amountField.getText());
                String currentAccountId = currentAccount.getAccountId();

                Customer updatedCustomer =
                        client.sendRequest(new Telegraph(currentAccountId, amount, Telegraph.Command.WITHDRAW));
                if(updatedCustomer != null){
                    customer = updatedCustomer;
                }
                showAccountDetails(customer.getAccById(currentAccountId));
                errorLabel.setText("succeed to withdraw");
            }
        }catch(Exception ex){
            ex.printStackTrace();
            errorLabel.setText("failed to withdraw");
        }
    }

    @FXML
    private void handleDepositButton(){
        Account currentAccount = accountTable.getSelectionModel().selectedItemProperty().get();

        try{
            if(currentAccount != null){
                double amount = Double.parseDouble(amountField.getText());
                String currentAccountId = currentAccount.getAccountId();

                Customer updatedCustomer =
                        client.sendRequest(new Telegraph(currentAccountId, amount, Telegraph.Command.DEPOSIT));
                if(updatedCustomer != null){
                    customer = updatedCustomer;
                }
                showAccountDetails(customer.getAccById(currentAccountId));
                errorLabel.setText("succeed to deposit");
            }
        }catch(Exception ex){
            ex.printStackTrace();
            errorLabel.setText("failed to deposit");
        }
    }

    /**
     * re-login
     */
    @FXML
    private void handleRefresh(){

        String id = customer.getId();
        String pw = customer.getPassword();
        Customer refreshedCustomer;
        try{
            refreshedCustomer = client.login(id,pw);

            if(refreshedCustomer != null){
                //refreshing succeeded!
                customer = refreshedCustomer;
                accountTable.setItems(customer.getAccounts());
                showAccountDetails(null);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}

