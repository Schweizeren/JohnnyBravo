/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.GUI.Controller;

import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mytunes.BLL.exception.MTBllException;
import mytunes.GUI.Model.PlaylistModel;

/**
 * FXML Controller class
 *
 * @author Kristian Urup laptop
 */
public class CreatePlaylistController implements Initializable
{
    private PlaylistModel pm;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField txtNameInput;

    public CreatePlaylistController()
    {
        try
        {
            pm = new PlaylistModel();
        } catch (MTBllException ex)
        {
            displayError(ex);
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void handleCreateBtn(ActionEvent event)
    {

        try
        {


                String name = txtNameInput.getText();
                pm.createPlaylist(name);
            
                Stage stage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();
                stage.close();

        } catch (MTBllException ex)
        {
        displayError(ex);
        }
    }

    @FXML
    private void handleCancelBtn(ActionEvent event) 
    {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    
    private void displayError(Exception ex) {
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error dialog");
        alert.setHeaderText(null);
        alert.setContentText(ex.getMessage());
        alert.showAndWait();
    }
    
    public void initializeModel(PlaylistModel playlistmodel) {
        this.pm = playlistmodel;
    }
    
}
