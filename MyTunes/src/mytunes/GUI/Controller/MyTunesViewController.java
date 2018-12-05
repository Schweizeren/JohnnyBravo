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
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import mytunes.GUI.Model.MyTunesModel;
import mytunes.BLL.SongSearcher;
import mytunes.GUI.Model.PlaylistModel;
import mytunes.GUI.Model.SongModel;
import mytunes.be.Playlist;
import mytunes.be.Song;

/**
 * FXML Controller class
 *
 * @author bonde
 */
public class MyTunesViewController implements Initializable
{
    private SongModel sm;
    private PlaylistModel pm;
    private SongSearcher ss;
    
    @FXML
    private ListView<Playlist> listPlaylists;
    @FXML
    private ListView<Song> listSongs;
    @FXML
    private ListView<?> listSongsOnPlaylist;
    @FXML
    private Button btnNewPlaylist;
    @FXML
    private Button btnEditPlaylist;
    @FXML
    private Button btnDeletePlaylist;
    @FXML
    private Button btnMoveSongUp;
    @FXML
    private Button btnMoveSongDown;
    @FXML
    private Button btnDeleteSongsOnPlaylist;
    @FXML
    private Button btnNewSong;
    @FXML
    private Button btnEditSong;
    @FXML
    private Button btnDeleteSong;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnMoveSongLeft;
    @FXML
    private Button btnSearch;
    @FXML
    private Label lblMusicPlaying;
    @FXML
    private AnchorPane rootPane;
    
    final JFXPanel fxPanel = new JFXPanel();
    private MediaPlayer mediaPlayer;
    private ListView<Song> listSearch;
    @FXML
    private TextField writeSearch;
    @FXML
    private Label testlbl;
    @FXML
    private Slider sliderDuration;
    @FXML
    private Slider sliderVolume;
    
    public MyTunesViewController() {
        try
        {
            sm = new SongModel();
            ss = new SongSearcher();
        } catch (IOException ex)
        {
            Logger.getLogger(MyTunesViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        listSongs.setItems(sm.getSongs());
        listPlaylists.setItems(pm.getAllPlaylist());
        
    }    

    @FXML
    private void createPlaylist(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/GUI/CreatePlaylist.fxml"));
        Parent root = (Parent)loader.load();
        
        CreatePlaylistController cpcontroller = loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void editPlaylist(ActionEvent event) throws IOException
    {
        Playlist playlist = listPlaylists.getSelectionModel().getSelectedItem();
        if(playlist == null)
        {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/GUI/NoPlaylistChosen.fxml"));
        Parent root = (Parent)loader.load();
        
        NoPlaylistChosenController npccontroller = loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        }
        else
        {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/GUI/CreatePlaylist.fxml"));
        Parent root = (Parent)loader.load();
        
        CreatePlaylistController cpcontroller = loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        }
    }

    @FXML
    private void deletePlaylist(ActionEvent event) throws SQLException, IOException
    {
        Playlist playlist = listPlaylists.getSelectionModel().getSelectedItem();
          if (playlist == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/GUI/NoSongChosen.fxml"));
            Parent root = (Parent)loader.load();
            
            NoSongChosenController nsccontroller = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            
            }else {
            pm.deletePlaylist(playlist);
            }
    }

    @FXML
    private void moveUp(ActionEvent event)
    {
    }

    @FXML
    private void moveDown(ActionEvent event)
    {
    }

    @FXML
    private void deleteSongOnPlaylist(ActionEvent event) throws IOException, SQLException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/GUI/NoPlaylistChosen.fxml"));
        Parent root = (Parent)loader.load();
        
        NoPlaylistChosenController npccontroller = loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();  
    }

    @FXML
    private void newSong(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/GUI/CreateSong.fxml"));
        Parent root = (Parent)loader.load();
        
        CreateSongController cscontroller = loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void editSong(ActionEvent event) throws IOException
    {
        Song song = listSongs.getSelectionModel().getSelectedItem();
        if (song == null) 
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/GUI/NoSongChosen.fxml"));
            Parent root = (Parent)loader.load();
            
            NoSongChosenController nsccontroller = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } 
        else 
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/GUI/EditSong.fxml"));
            Parent root = (Parent)loader.load();
        
            EditSongController escontroller = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();  
        }
    }

    @FXML
    private void deleteSong(ActionEvent event) throws IOException
    {
        Song song = listSongs.getSelectionModel().getSelectedItem();
        if (song == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/GUI/NoSongChosen.fxml"));
            Parent root = (Parent)loader.load();
            
            NoSongChosenController nsccontroller = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }else {
        sm.deleteSong(song);
        testlbl.setText("");
        }
    }

    @FXML
    private void endApplication(ActionEvent event)
    {
        System.exit(0);
    }

    @FXML
    private void moveSong(ActionEvent event)
    {
    }

    @FXML
    private void searchSong(ActionEvent event)
    {
        listSongs.setItems(sm.searchSongs(sm.getSongs(), writeSearch.getText().toLowerCase()));
    }

    @FXML
    private void previousSong(ActionEvent event)
    {
    }

    @FXML
    private void nextSong(ActionEvent event)
    {
    }

    @FXML
    private void playMusic(ActionEvent event)
    {      
        mediaPlayer.play();
    }

    @FXML
    private void pauseMusic(ActionEvent event)
    {
        mediaPlayer.pause();
    }

    @FXML
    private void stopMusic(ActionEvent event)
    {
        mediaPlayer.stop();
    }

    @FXML
    private void writeSearch(KeyEvent event)
    {
        
    }

    @FXML
    private void sliderDrag(MouseEvent event)
    {
        mediaPlayer.seek(Duration.seconds(sliderDuration.getValue()));
    }

    @FXML
    private void songPressed(MouseEvent event)
    {
        Song song = listSongs.getSelectionModel().getSelectedItem();
        String filePath = song.getFilepath();
        String trueFilePath = "file:/" + filePath;
        String trueTrueFilePath = trueFilePath.replace(" ", "%20");
        System.out.println(trueTrueFilePath);
        if(trueTrueFilePath != null)
        {
            Media media = new Media(trueTrueFilePath);
            mediaPlayer = new MediaPlayer(media);
            sliderVolume.setValue(mediaPlayer.getVolume() * 100);
            sliderVolume.valueProperty().addListener(new InvalidationListener() 
            {
                @Override
                public void invalidated(Observable observable)
                {
                    mediaPlayer.setVolume(sliderVolume.getValue()/100);
                }
            });
            
            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>()
            {
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                sliderDuration.setValue(newValue.toSeconds());
                sliderDuration.maxProperty().bind(Bindings.createDoubleBinding(() -> mediaPlayer.getTotalDuration().toSeconds(), mediaPlayer.totalDurationProperty()));
                }
            });
        }
    }
    
    public void endApplication()
    {
        System.exit(0);
    }
    
}

