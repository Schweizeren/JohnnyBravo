/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.GUI.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mytunes.BLL.exception.MTBllException;
import mytunes.GUI.Model.SongModel;
import mytunes.be.Song;

/**
 * FXML Controller class
 *
 * @author Kristian Urup laptop
 */
public class AreYouSureController implements Initializable
{
    
    private Song selectedSong;
    private SongModel sm;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label lblNameOfDelete;
    private MyTunesViewController mtvcontroller;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void handleYesBtn(ActionEvent event) throws IOException, MTBllException
    {
        sm.deleteSong(selectedSong);
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleNoBtn(ActionEvent event)
    {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    
    public void initializeSong(Song song) {
        this.selectedSong = song;
    }
    
    public void initializeModel(SongModel songmodel) {
        this.sm = songmodel;
    }
}
