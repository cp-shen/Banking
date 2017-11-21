package view.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientMain extends Application{

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("loginDialog.fxml"));
            AnchorPane anchorPane =  fxmlLoader.load();
            primaryStage.setScene(new Scene(anchorPane));
            primaryStage.setTitle("BJTU Bank Client");
            primaryStage.show();

            LoginDialog loginDialog = fxmlLoader.getController();
            loginDialog.setDialogStage(primaryStage);
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
