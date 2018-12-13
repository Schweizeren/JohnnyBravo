/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.GUI.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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
public class EditSongController implements Initializable
{

    private SongModel sm;

    private Song oldSong;
    
    private int index;

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

    public EditSongController()
    {

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        txtDuration.setDisable(true);
        txtFile.setDisable(true);
        comboEditSong.setItems(FXCollections.observableArrayList("Blues", "Hip Hop", "Pop", "Rap",
                "Rock", "Techno", "Other"));
        comboEditSong.setVisibleRowCount(6);
        txtOtherCategory.setVisible(false);

    }

    @FXML
    private String handleComboES(ActionEvent event)
    {
        String category;

        int selectedIndex = comboEditSong.getSelectionModel().getSelectedIndex();

        switch (selectedIndex)
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

        try
        {
                oldSong.setArtist(txtArtistInput.getText());
                oldSong.setTitle(txtTitleInput.getText());
                oldSong.setFilepath(txtFile.getText());
                if (comboEditSong.getSelectionModel().getSelectedItem().equals("Other")) 
                {
                oldSong.setGenre(txtOtherCategory.getText());
                } else
                {
                oldSong.setGenre(comboEditSong.getSelectionModel().getSelectedItem());
                }
                sm.updateSong(oldSong, index);
                Stage stage = (Stage) rootPane.getScene().getWindow();
                stage.close();
           
        } catch (MTBllException ex)
        {
            displayError(ex);
        }
    }

    public void initializeSong(Song song, int index)
    {
        txtTitleInput.setText(song.getTitle());
        txtArtistInput.setText(song.getArtist());
        txtDuration.setText(song.getFormattedLength());
        txtFile.setText(song.getFilepath());
        oldSong = new Song(song.getId(), song.getTitle(), song.getLength(), song.getArtist(), song.getGenre(), song.getFilepath());
        this.index = index;
    }

    private void displayError(Exception ex)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error dialog");
        alert.setHeaderText(null);
        alert.setContentText(ex.getMessage());

        alert.showAndWait();
    }

    public void initializeModel(SongModel songmodel)
    {
        this.sm = songmodel;
    }
}
