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
import javafx.event.EventType;
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
import mytunes.BLL.SongSearcher;
import mytunes.BLL.exception.MTBllException;
import mytunes.GUI.Model.PlaylistModel;
import mytunes.GUI.Model.PlaylistSongModel;
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

    private PlaylistSongModel psm;
    private PlaylistModel pm;
    private SongModel sm;
    private SongSearcher ss;
    private JFXPanel fxPanel;
    private MediaPlayer mediaPlayer;
    private Song oldSong;
    private boolean playing;
    @FXML
    private ListView<Playlist> listPlaylists;
    @FXML
    private ListView<Song> listSongs;
    @FXML
    private ListView<Song> listSongsOnPlaylist;
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
    @FXML
    private TextField writeSearch;
    @FXML
    private Slider sliderDuration;
    @FXML
    private Slider sliderVolume;

    public MyTunesViewController()
    {
        try
        {
            psm = new PlaylistSongModel();
            pm = new PlaylistModel();
            sm = new SongModel();
            ss = new SongSearcher();
            fxPanel = new JFXPanel();
        } catch (MTBllException ex)
        {
            displayError(ex);
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
        Parent root = (Parent) loader.load();

        CreatePlaylistController cpcontroller = loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void editPlaylist(ActionEvent event) throws IOException
    {
        Playlist playlist = listPlaylists.getSelectionModel().getSelectedItem();
        if (playlist == null)
        {
            displayNoPlaylistWindow();
        } else
        {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/GUI/EditPlaylist.fxml"));
        Parent root = (Parent)loader.load();
        
        EditPlaylistController epcontroller = loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        }
    }

    @FXML
    private void deletePlaylist(ActionEvent event)
    {
        Playlist playlist = listPlaylists.getSelectionModel().getSelectedItem();
          if (playlist == null) {
            
            
            }
            else 
            {
            try
            {
                pm.deletePlaylist(playlist);
                if (playlist == null)
                {
                    displayNoSongWindow();
                } else
                {
                    try
                    {
                        pm.deletePlaylist(playlist);
                    } catch (MTBllException ex)
                    {
                        displayError(ex);
                    }
                }   } catch (MTBllException ex)
            {
                displayError(ex);
            }
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
    private void deleteSongOnPlaylist(ActionEvent event) 
    {
        /*Song song = listSongsOnPlaylist.getSelectionModel().getSelectedItem();;
        if(song == null)
        {
            
        }
        else
        {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/GUI/NoPlaylistChosen.fxml"));
        Parent root = (Parent)loader.load();
        
        NoPlaylistChosenController npccontroller = loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        }*/
        Playlist playlist = listPlaylists.getSelectionModel().getSelectedItem();
        if (playlist == null) {
        displayNoPlaylistWindow();
        }else {
            try
            {
                pm.deletePlaylist(playlist);
            } catch (MTBllException ex)
            {
                displayError(ex);
            }
        }

    }

    @FXML
    private void newSong(ActionEvent event)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/GUI/CreateSong.fxml"));
            Parent root = (Parent) loader.load();

            CreateSongController cscontroller = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex)
        {
            displayError(ex);
        }
    }

    @FXML
    private void editSong(ActionEvent event)
    {
        Song song = listSongs.getSelectionModel().getSelectedItem();
        if (song == null)
        {
            displayNoSongWindow();
        } else
        {
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/GUI/EditSong.fxml"));
                Parent root = (Parent) loader.load();

                EditSongController escontroller = loader.getController();
                escontroller.initializeSong(song);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException ex)
            {
                displayError(ex);
            }
        }
    }

    @FXML
    private void deleteSong(ActionEvent event)
    {
        Song song = listSongs.getSelectionModel().getSelectedItem();
        if (song == null) {
            displayNoSongWindow();
        }else 
        {
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/GUI/AreYouSure.fxml"));
                Parent root = (Parent)loader.load();
                
                AreYouSureController aysController = loader.getController();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                //mtm.deleteSong(song);
                //testlbl.setText("")
                if (song == null)
                {
                    displayNoSongWindow();
                    
                } else
                {
                    sm.deleteSong(song);
                }   } catch (IOException ex)
            {
                displayError(ex);
            }
    }
    }

    @FXML
    private void endApplication(ActionEvent event)
    {
        System.exit(0);
    }


    @FXML
    private void searchSong(ActionEvent event)
    {
        try
        {
            listSongs.setItems(sm.searchSongs(sm.getSongs(), writeSearch.getText().toLowerCase()));
        } catch (IOException | MTBllException ex)
        {
            displayError(ex);
        }
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
        playing = true;
    }

    @FXML
    private void pauseMusic(ActionEvent event)
    {
        mediaPlayer.pause();
        playing = false;
        
    }

    @FXML
    private void stopMusic(ActionEvent event)
    {
        mediaPlayer.stop();
        playing = false;
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
        if (trueTrueFilePath != null)
        {
            Media media = new Media(trueTrueFilePath);
            mediaPlayer = new MediaPlayer(media);
            sliderVolume.setValue(mediaPlayer.getVolume() * 100);
            sliderVolume.valueProperty().addListener(new InvalidationListener()
            {
                @Override
                public void invalidated(Observable observable)
                {
                    mediaPlayer.setVolume(sliderVolume.getValue() / 100);
                }
            });

            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>()
            {
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue)
                {
                    sliderDuration.setValue(newValue.toSeconds());
                    sliderDuration.maxProperty().bind(Bindings.createDoubleBinding(() -> mediaPlayer.getTotalDuration().toSeconds(), mediaPlayer.totalDurationProperty()));
                }
            });
        }
    }

    @FXML
    public void addToPlaylist(ActionEvent event)
    {

    }
    @FXML
    public void deleteFromPlaylistSongsEverything(ActionEvent event)
    {

    }
    @FXML
    public void removeSongFromPlaylist(ActionEvent event)
    {

    }
    
    @FXML
    public void endApplication()
    {
        System.exit(0);
    }

    private void displayError(Exception ex)
    {
        //TODO vise fejl mere ordentlig
        System.out.println(ex.getMessage());
        ex.printStackTrace();
    }

    private void displayNoSongWindow()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/GUI/NoSongChosen.fxml"));
            Parent root = (Parent) loader.load();

            NoSongChosenController nsccontroller = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex)
        {
            displayError(ex);
        }
    }
    
    public void initializeSong(Song song)
    {
        oldSong = new Song(song.getId(), song.getTitle(), song.getLength(), song.getArtist(), song.getGenre(), song.getFilepath());
    }
    
    private void displayNoPlaylistWindow() {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/GUI/NoPlaylistChosen.fxml"));
            Parent root = (Parent) loader.load();
            
            NoPlaylistChosenController npccontroller = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex)
        {
            displayError(ex);
        }
    }
}
