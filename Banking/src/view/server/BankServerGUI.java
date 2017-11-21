package view.server;


import domain.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class BankServerGUI{
    private Stage primaryStage;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label balanceLabel;
    @FXML
    private Label overdraftPrtLabel;
    @FXML
    private Label interestRateLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableView<Account> accountTable;
    @FXML
    private TableColumn<Customer,String> cusIdColumn;
    @FXML
    private TableColumn<Account,String> accIdColumn;
    @FXML
    private TableColumn<Account,String> accTypeColumn;


    void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
        customerTable.setItems(BankServer.getBankServer().getCustomers());

    }

    @FXML
    private void initialize(){

        new Thread(() -> {
            try{
                BankServer.getBankServer().service();
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }).start();

        cusIdColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().idProperty());
        accIdColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().accountIdProperty());
        accTypeColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().typeProperty());


        customerTable.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> showCusDetails(newValue));
        accountTable.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> showAccDetails(newValue));


    }

    void showAccDetails(Account account){
        if(account != null){
            if(account instanceof CheckingAccount){
                balanceLabel.setText(Double.toString(account.getBalance()));
                overdraftPrtLabel.setText(Double.toString( ((CheckingAccount)account).getOverdraftProtection()));
                interestRateLabel.setText("");
            }else if(account instanceof SavingsAccount){
                balanceLabel.setText(Double.toString(account.getBalance()));
                interestRateLabel.setText(Double.toString( ((SavingsAccount)account).getInterestRate()));
                overdraftPrtLabel.setText("");
            }
        }else {
            balanceLabel.setText("");
            overdraftPrtLabel.setText("");
            interestRateLabel.setText("");
        }
    }

     void showCusDetails(Customer customer){
        if(customer != null){
            accountTable.setItems(customer.getAccounts());

            firstNameLabel.setText(customer.getFirstName());
            lastNameLabel.setText(customer.getLastName());
            passwordLabel.setText(customer.getPassword());
        }else {
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            passwordLabel.setText("");
        }
    }

    private void showCusEditDialog(Customer customer){
        try{
            Stage cusEditStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("CustomerEditDialog.fxml"));
            AnchorPane cusEditPane = fxmlLoader.load();

            CustomerEditDialog customerEditDialog = fxmlLoader.getController();
            customerEditDialog.setFormerCustomer(customer);
            customerEditDialog.setDialogStage(cusEditStage);
            customerEditDialog.setBankServerGUI(this);

            cusEditStage.setScene(new Scene(cusEditPane));
            cusEditStage.initOwner(primaryStage);
            cusEditStage.initModality(Modality.WINDOW_MODAL);
            cusEditStage.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    private void showAccEditDialog(Account account, Customer customer){
        try{
            Stage accEditStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("AccountEditDialog.fxml"));
            AnchorPane accEditPane = fxmlLoader.load();

            AccountEditDialog accountEditDialog = fxmlLoader.getController();
            accountEditDialog.setFormerAccount(account);
            accountEditDialog.setDialogStage(accEditStage);
            accountEditDialog.setCustomer(customer);
            accountEditDialog.setBankServerGUI(this);

            accEditStage.setScene(new Scene(accEditPane));
            accEditStage.initOwner(primaryStage);
            accEditStage.initModality(Modality.WINDOW_MODAL);
            accEditStage.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }





    @FXML
    private void handleNewCus(){
        showCusEditDialog(null);
    }

    @FXML
    private void handleEditCus(){
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        if(customer != null){
            showCusEditDialog(customer);
        }
    }

    @FXML
    private void handleDeleteCus(){
        Customer customer = customerTable.getSelectionModel().selectedItemProperty().getValue();
        if(customer != null){
            BankServer.getBankServer().deleteCustomer(customer);
        }
    }






    @FXML
    private void handleNewAcc(){
        Customer customer = customerTable.getSelectionModel().selectedItemProperty().getValue();
        if(customer != null){
            showAccEditDialog(null,customer);
        }

    }

    @FXML
    private void handleEditAcc(){
        Account account = accountTable.getSelectionModel().selectedItemProperty().getValue();
        if(account != null){
            showAccEditDialog(account,account.getOwner());
        }
    }

    @FXML
    private void handleDeleteAcc(){
        Account account = accountTable.getSelectionModel().selectedItemProperty().getValue();
        if(account != null){
            account.getOwner().deleteAccount(account);
        }
    }

    @FXML
    private void handleRefreshAcc(){
        Account currentAcc = accountTable.getSelectionModel().selectedItemProperty().getValue();
        if(currentAcc != null){
            showAccDetails(currentAcc);
        }
    }

    @FXML
    private void handleSave(){
        BankServer.getBankServer().saveToFile();
        errorLabel.setText("saving succeed");
    }
}
