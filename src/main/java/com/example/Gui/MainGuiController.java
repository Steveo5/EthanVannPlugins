package com.example.Gui;

import com.example.EthanApiPlugin.PathFinding.GlobalCollisionMap;
import com.example.StevesPlugin.StevesPlugin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainGuiController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void onAddScriptButtonClick(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(
                GlobalCollisionMap.class.getResource("ScriptListDialog.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("My modal window");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(
                ((Node)event.getSource()).getScene().getWindow() );
        stage.show();
    }

    @FXML
    public void onStartButtonClicked(ActionEvent event) {
        StevesPlugin.getInstance().setActiveScript(StevesPlugin.getInstance().getScript("StevesCombat"));
    }
}
