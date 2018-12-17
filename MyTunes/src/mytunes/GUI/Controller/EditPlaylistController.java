    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.GUI.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mytunes.BLL.exception.MTBllException;
import mytunes.GUI.Model.PlaylistModel;
import mytunes.be.Playlist;

/**
 * FXML Controller class
 *
 * @author Kristian Urup laptop
 */
public class EditPlaylistController implements Initializable
{

    PlaylistModel pm;
    Playlist oldPlaylist;
    int index;
    @FXML
    private Label lblPlaylistName;
    @FXML
    private TextField txtEditPlaylist;
    @FXML
    private AnchorPane rootPane;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
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
     * Takes the information in the textfield and edits the selected playlist
     * after this the view is closed
     * @param event when the save button is pressed
     */
    @FXML
    private void handleSaveBtn(ActionEvent event)
    {
        
        try
        {
            oldPlaylist.setName(txtEditPlaylist.getText());
            pm.updatePlaylist(oldPlaylist, index);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.close();
        } catch (MTBllException ex)
        {
            displayError(ex);
        }
    }

    /**
     * Closes the view of this controller
     * @param event when the cancel button is pressed
     */
    @FXML
    private void handleCancelBtn(ActionEvent event)
    {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Initializes this class' model object
     * @param playlistmodel the ojbect this class' model is initialized with
     */
    public void initializeModel(PlaylistModel playlistmodel) {
        this.pm = playlistmodel;
    }
    
    /**
     * Initializes the playlist getting updated and also the saves the index
     * of where the playlist were in the list
     * @param plist the playlist getting updated
     * @param index the index of the playlist in the list
     */
    public void initializePlaylist(Playlist plist, int index) {
        oldPlaylist = new Playlist(plist.getId(), plist.getName());
        this.index = index;
    }
    
    /**
     * Opens a pop up window that displays an error to the user
     * @param ex the exception getting shown to the user
     */
    private void displayError(Exception ex) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error dialog");
        alert.setHeaderText(null);
        alert.setContentText(ex.getMessage());

        alert.showAndWait();
    }
    
}
