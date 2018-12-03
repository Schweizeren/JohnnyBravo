/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.GUI.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kristian Urup laptop
 */
public class EditSongController implements Initializable
{

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField txtTitleInput;
    @FXML
    private TextField txtArtistInput;
    @FXML
    private TextField txtDuration;
    @FXML
    private ComboBox<String> comboEditSong;
    @FXML
    private TextField txtFile;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        comboEditSong.setItems(FXCollections.observableArrayList("Blues","Hip Hop","Pop","Rap",
                "Rock","Techno"));
        comboEditSong.setVisibleRowCount(6);
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
            default: 
                throw new UnsupportedOperationException("Category not chosen");
        }
        return category;
    }

    @FXML
    private void handleChooseBtn(ActionEvent event)
    {
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
    }
    
}
