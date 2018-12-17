/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.GUI.Controller;

import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mytunes.BLL.exception.MTBllException;
import mytunes.GUI.Model.SongModel;

/**
 * FXML Controller class
 *
 * @author Kristian Urup laptop
 */
public class CreateSongController implements Initializable
{

    private SongModel sm;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField txtTitleInput;
    @FXML
    private TextField txtArtistInput;
    @FXML
    private ComboBox<String> comboCategoryBox;
    @FXML
    private TextField txtDuration;
    @FXML
    private TextField txtFile;
    @FXML
    private TextField txtOtherCategory;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        txtDuration.setDisable(true);
        txtFile.setDisable(true);
        comboCategoryBox.setItems(FXCollections.observableArrayList("Blues", "Hip Hop", "Pop", "Rap",
                "Rock", "Techno", "Other"));
        comboCategoryBox.setVisibleRowCount(6);
        txtOtherCategory.setVisible(false);
    }

    /**
     * Opens up a filechooser. And when an mp3 file is chosen the different
     * textfields will get filled out with the mp3 files metadata or nothing
     * it it doesnt have some specific data
     * @param event when the choose button is pressed
     */
    @FXML
    private void handleChooseBtn(ActionEvent event)
    {
        try
        {
            sm.initializeFile();
            txtDuration.setText(sm.getDuration());
            txtTitleInput.setText(sm.getSongTitle());
            txtFile.setText(sm.getFilePath());
            txtArtistInput.setText(sm.getArtist());
        } catch (MTBllException ex)
        {
            displayError(ex);
        }
    }

    /**
     * Takes the information in the different textfields and also the combobox
     * and then creates a song. Afteer this the view is closed
     * @param event when the save button is pressed
     */
    @FXML
    private void handleSaveBtn(ActionEvent event)
    {
        String title;
        int duration;
        String filepath;
        String genre;
        String artist;
        try
        {
            
                title = txtTitleInput.getText();
                duration = sm.getDurationInSec();
                filepath = txtFile.getText();
                if (comboCategoryBox.getSelectionModel().getSelectedItem().equals("Other")) {
                genre = txtOtherCategory.getText();
                } else {
                genre = comboCategoryBox.getSelectionModel().getSelectedItem();
                }
                artist = txtArtistInput.getText();
                sm.createSong(title, duration, artist, genre, filepath);
            

            Stage stage = (Stage) ((Node) ((EventObject) event).getSource()).getScene().getWindow();
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
     * A combobox with different music genres as selections. If category 6 is
     * chosen a textfield will appear where the user can type a music genre
     * @param event when a category is chosen on the combobox
     * @return a string containing the category chosen
     */
    @FXML
    private String handleComboCategory(ActionEvent event)
    {
        String category;

        int selectedIndex = comboCategoryBox.getSelectionModel().getSelectedIndex();

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

    /**
     * A popup window that displays the error that occured
     * @param ex the exception getting showened to the user
     */
    private void displayError(Exception ex)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error dialog");
        alert.setHeaderText(null);
        alert.setContentText(ex.getMessage());

        alert.showAndWait();
    }

    /**
     * Initializes this class' songmodel object
     * @param songmodel the songmodel this class' songmodel is getting
     * initialized with
     */
    public void initializeModel(SongModel songmodel)
    {
        this.sm = songmodel;
    }

    @FXML
    private void titleEnterPressed(KeyEvent event)
    {
    }

    @FXML
    private void artistEnterPressed(KeyEvent event)
    {
    }

    @FXML
    private void comboBoxEnterPressed(KeyEvent event)
    {
    }

    @FXML
    private void otherCategoryEnterPressed(KeyEvent event)
    {
    }
}
