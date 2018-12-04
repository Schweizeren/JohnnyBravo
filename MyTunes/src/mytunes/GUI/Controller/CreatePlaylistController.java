/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.GUI.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mytunes.GUI.Model.MyTunesModel;
import mytunes.be.Playlist;

/**
 * FXML Controller class
 *
 * @author Kristian Urup laptop
 */
public class CreatePlaylistController implements Initializable
{
    private MyTunesModel mtm;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField txtNameInput;

    public CreatePlaylistController()
    {
        try
        {
            mtm = new MyTunesModel();
        } catch (IOException ex)
        {
            Logger.getLogger(CreateSongController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void handleCreateBtn(ActionEvent event) throws SQLException
    {
        String name = txtNameInput.getText();
        mtm.createPlaylist(name);
        
    }

    @FXML
    private void handleCancelBtn(ActionEvent event) throws IOException
    {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
}
