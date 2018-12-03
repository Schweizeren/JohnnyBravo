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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mytunes.GUI.Model.MyTunesModel;

/**
 * FXML Controller class
 *
 * @author Kristian Urup laptop
 */
public class CreateSongController implements Initializable
{
    private MyTunesModel mtm;

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
    private ComboBox<String> comboBoxExtra;
    @FXML
    private Button moreBtn;
    
    
    /**
     * Initializes the controller class.
     */
    public CreateSongController() {
        try
        {
            mtm = new MyTunesModel();
        } catch (IOException ex)
        {
            Logger.getLogger(CreateSongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        txtDuration.setDisable(true);
        txtFile.setDisable(true);
        comboCategoryBox.setItems(FXCollections.observableArrayList("Blues","Hip Hop","Pop","Rap",
                "Rock","Techno"));
        comboCategoryBox.setVisibleRowCount(6);
        comboBoxExtra.setItems(FXCollections.observableArrayList("Blues","Hip Hop","Pop","Rap","Rock","Techno"));
        comboBoxExtra.setVisibleRowCount(3);
        comboBoxExtra.setVisible(false);
    }    

    @FXML
    private void handleMoreBtn(ActionEvent event)
    {
        comboBoxExtra.setVisible(true);
        moreBtn.setVisible(false);
    }

    @FXML
    private void handleChooseBtn(ActionEvent event)
    {
        mtm.initializeFile();
        txtDuration.setText(mtm.getDuration());
        txtTitleInput.setText(mtm.getSongTitle());
        txtFile.setText(mtm.getFilePath());
        txtArtistInput.setText(mtm.getArtist());
    }

    @FXML
    private void handleSaveBtn(ActionEvent event)
    {
        String title = txtTitleInput.getText();
        int duration = mtm.getDurationInSec();
        String filepath = txtFile.getText();
        String genre = comboCategoryBox.getSelectionModel().getSelectedItem();
        String artist = txtArtistInput.getText();
        mtm.createSong(title, duration, artist, genre, filepath);
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
            default: 
                throw new UnsupportedOperationException("Category not chosen");
        }
        return category;
    }

    @FXML
    private String handleComboBoxExtra(ActionEvent event)
    {
                String category2;
        
        int selectedIndex = comboCategoryBox.getSelectionModel().getSelectedIndex();
        
        switch(selectedIndex)
        {
            case 0:
                category2 = "blues";
                break;
            case 1:
                category2 = "hipHop";
                break;
            case 2:
                category2 = "pop";
                break;
            case 3:
                category2 = "rap";
                break;
            case 4:
                category2 = "rock";
                break;
            case 5:
                category2 = "techno";
                break;
            default: 
                throw new UnsupportedOperationException("Category not chosen");
        }
        return category2;
    }
    
    
    
    
}
