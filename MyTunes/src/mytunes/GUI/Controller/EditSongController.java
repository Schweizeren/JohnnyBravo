/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.GUI.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mytunes.BLL.exception.MTBllException;
import mytunes.BLL.Model.SongModel;
import mytunes.be.Song;


/**
 * FXML Controller class
 *
 * @author Kristian Urup laptop
 */
public class EditSongController implements Initializable
{  
    private SongModel sm;

    private Song oldSong;

    @FXML
    private AnchorPane rootPane;
    @FXML
    public TextField txtTitleInput;
    @FXML
    public TextField txtArtistInput;
    @FXML
    public TextField txtDuration;
    @FXML
    private ComboBox<String> comboEditSong;
    @FXML
    public TextField txtFile;
    @FXML
    private TextField txtOtherCategory;


    public EditSongController() throws MTBllException {
        try
        {
            sm = new SongModel();
        } catch (MTBllException ex)
        {
            //TODO
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        comboEditSong.setItems(FXCollections.observableArrayList("Blues","Hip Hop","Pop","Rap",
                "Rock","Techno","Other"));
        comboEditSong.setVisibleRowCount(6);
        txtOtherCategory.setVisible(false);
        
    }    

    @FXML
    private String handleComboES(ActionEvent event)
    {
        String category;
        
        int selectedIndex = comboEditSong.getSelectionModel().getSelectedIndex();
        
        switch(selectedIndex)
        {
            case 0:
                category = "blues";
                break;
            case 1:
                category = "hipHop";
                break;
            case 2:
                category = "pop";
                break;
            case 3:
                category = "rap";
                break;
            case 4:
                category = "rock";
                break;
            case 5:
                category = "techno";
                break;
            case 6: 
                txtOtherCategory.setVisible(true);
                category = txtOtherCategory.getText();
                break;
            default: 
                throw new UnsupportedOperationException("Category not chosen");
        }
        return category;
    }


    @FXML
    private void handleCancelBtn(ActionEvent event)
    {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleSaveBtn(ActionEvent event)
    {
        try {
            oldSong.setArtist(txtTitleInput.getText());
            oldSong.setTitle(txtArtistInput.getText());
            oldSong.setFilepath(txtFile.getText());
            oldSong.setGenre(comboEditSong.getSelectionModel().getSelectedItem());
            sm.updateSong(oldSong);
        } catch (MTBllException ex) {
            //TODO
        }
    }
    
    public void initializeSong(Song song) {
        txtTitleInput.setText(song.getTitle());
        txtArtistInput.setText(song.getArtist());
        txtDuration.setText(Integer.toString(song.getLength()));
        txtFile.setText(song.getFilepath());
        oldSong = new Song(song.getId(), song.getTitle(), song.getLength(), song.getArtist(), song.getGenre(), song.getFilepath());
    }
    
    
}

