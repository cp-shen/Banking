package view.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerMain extends Application{

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("BankServerGUI.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();

            BankServerGUI bankServerGUI = fxmlLoader.getController();
            bankServerGUI.setPrimaryStage(primaryStage);
            primaryStage.setTitle("BJTU Bank Server");

            primaryStage.setScene(new Scene(anchorPane));
            primaryStage.show();

        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
