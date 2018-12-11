/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.GUI.Controller;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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
    public CreateSongController()
    {

    }

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

    @FXML
    private void handleCancelBtn(ActionEvent event) throws IOException
    {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

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

    @FXML
    private void titleEnterPressed(KeyEvent event)
    {
        
        if(event.getCode()==KeyCode.ENTER)
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
    }

    @FXML
    private void artistEnterPressed(KeyEvent event)
    {
        
        
        if(event.getCode()==KeyCode.ENTER)
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
    }

    @FXML
    private void comboBoxEnterPressed(KeyEvent event)
    {
        
        
        if(event.getCode()==KeyCode.ENTER)
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
    }

    @FXML
    private void otherCategoryEnterPressed(KeyEvent event)
    {
        
        
        if(event.getCode()==KeyCode.ENTER)
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
    }
}
