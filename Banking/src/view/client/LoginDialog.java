package view.client;

import exceptions.ConflictedIdException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import domain.Client;
import domain.Customer;

import java.io.IOException;

public class LoginDialog{
    @FXML
    private TextField idField;
    @FXML
    private PasswordField pwField;
    @FXML
    private Label errorLabel;

    private Stage dialogStage;
    private Customer customer;
    private Client client = new Client();

    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     *
     */
    void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    @FXML
    private void handleLogin() throws  ConflictedIdException{
        String id = idField.getText();
        String pw = pwField.getText();
        try{
            customer = client.login(id,pw);
        }catch(Exception ex){
            ex.printStackTrace();
            errorLabel.setText("failed to login.");
        }

        if(customer != null){
            //login succeeded!
            launchClient();
        }else {
            errorLabel.setText("failed to login.");
        }


    }

    @FXML
    private void handleQuit() {
        dialogStage.close();
    }

    private void launchClient() throws ConflictedIdException{
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ClientGUI.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();

            ClientGUI clientGUI = fxmlLoader.getController();
            clientGUI.setCustomer(customer);
            clientGUI.setClient(client);

            dialogStage.setScene(new Scene(anchorPane));
            dialogStage.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
