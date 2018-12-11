/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.GUI.Controller;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private boolean playing;
    private boolean paused;
    private int currentSongSelected;

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
    private Label lblMusicPlaying;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField writeSearch;
    @FXML
    private Slider sliderDuration;
    @FXML
    private Slider sliderVolume;
    @FXML
    private ImageView arrowView;
    @FXML
    private ImageView volumeView;
    @FXML
    private TableColumn<Song, String> cellTitle;
    @FXML
    private TableColumn<Song, String> cellArtist;
    @FXML
    private TableColumn<Song, String> cellGenre;
    @FXML
    private TableColumn<Song, String> cellTime;
    @FXML
    private TableView<Song> tableSongList;
    @FXML
    private TableView<Song> tableSongOnPlaylist;
    @FXML
    private TableColumn<Song, String> cellSongPlaylistTitle;
    @FXML
    private TableView<Playlist> tablePlaylist;
    @FXML
    private TableColumn<Playlist, String> cellPlaylistTitle;

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
        cellTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        cellArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        cellGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        cellTime.setCellValueFactory(new PropertyValueFactory<>("formattedLength"));
        cellSongPlaylistTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        cellPlaylistTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableSongList.setItems(sm.getSongs());
        tablePlaylist.setItems(pm.getAllPlaylist());
    }

    @FXML
    private void createPlaylist(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/GUI/CreatePlaylist.fxml"));
        Parent root = (Parent) loader.load();

        CreatePlaylistController cpcontroller = loader.getController();
        cpcontroller.initializeModel(pm);
        Stage stage = new Stage();

        Image icon = new Image(getClass().getResourceAsStream("/mytunes/GUI/newicon.png"));
        stage.getIcons().add(icon);
        stage.setTitle("MyTunes");
        
        stage.setScene(new Scene(root));
        stage.show();
    }
    

    @FXML
    private void editPlaylist(ActionEvent event) throws IOException
    {
        Playlist playlist = tablePlaylist.getSelectionModel().getSelectedItem();
        int index = tablePlaylist.getSelectionModel().getSelectedIndex();
        if (playlist == null)
        {
            displayNoPlaylistWindow();
        } else
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/GUI/EditPlaylist.fxml"));
            Parent root = (Parent) loader.load();

            EditPlaylistController epcontroller = loader.getController();
            epcontroller.initializeModel(pm);
            epcontroller.initializePlaylist(playlist, index);
            Stage stage = new Stage();
            
            Image icon = new Image(getClass().getResourceAsStream("/mytunes/GUI/newicon.png"));
            stage.getIcons().add(icon);
            stage.setTitle("MyTunes");
            
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    @FXML
    private void deletePlaylist(ActionEvent event)
    {
        Playlist playlist = tablePlaylist.getSelectionModel().getSelectedItem();
        if (playlist == null)
        {
            displayNoPlaylistWindow();
        } else
        {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation dialog");
            alert.setContentText("Are you sure you want to delete: " + playlist);

            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeYes)
            {
                try
                {
                    pm.deletePlaylist(playlist);
                    psm.deleteFromPlaylist(playlist);
                    tablePlaylist.getSelectionModel().clearSelection();
                } catch (MTBllException ex)
                {
                    displayError(ex);
                }
            }
        }
    }

    @FXML
    private void moveUp(ActionEvent event)
    {
        try
        {
            int locationGettingMoved = tableSongOnPlaylist.getSelectionModel().getSelectedIndex() + 1;
            int locationGettingAffected = locationGettingMoved - 1;
            int playlistId = tablePlaylist.getSelectionModel().getSelectedItem().getId();
            psm.moveSong(locationGettingMoved, locationGettingAffected, playlistId);
        } catch (MTBllException ex)
        {
            displayError(ex);
        }

    }

    @FXML
    private void moveDown(ActionEvent event)
    {
        try
        {
            int locationGettingMoved = tableSongOnPlaylist.getSelectionModel().getSelectedIndex() + 1;
            int locationGettingAffected = locationGettingMoved + 1;
            int playlistId = tablePlaylist.getSelectionModel().getSelectedItem().getId();
            psm.moveSong(locationGettingMoved, locationGettingAffected, playlistId);
        } catch (MTBllException ex)
        {
            displayError(ex);
        }
    }

    @FXML
    private void deleteSongOnPlaylist(ActionEvent event)
    {
        Playlist playlist = tablePlaylist.getSelectionModel().getSelectedItem();
        Song song = tableSongOnPlaylist.getSelectionModel().getSelectedItem();
        if (playlist == null)
        {
            displayNoPlaylistWindow();
        } else if (song == null)
        {
            displayNoSongWindow();
        } else
        {
            try
            {
                psm.removeSongFromPlaylist(playlist, song);
            } catch (Exception ex)
            {
                displayError(ex);
            }
        }

    }

    @FXML
    private void newSong(ActionEvent event) throws SQLException
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/GUI/CreateSong.fxml"));
            Parent root = (Parent) loader.load();

            CreateSongController cscontroller = loader.getController();
            cscontroller.initializeModel(sm);
            Stage stage = new Stage();
            
            Image icon = new Image(getClass().getResourceAsStream("/mytunes/GUI/newicon.png"));
            stage.getIcons().add(icon);
            stage.setTitle("MyTunes");
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
        Song song = tableSongList.getSelectionModel().getSelectedItem();
        int index = tableSongList.getSelectionModel().getSelectedIndex();
        if (song == null)
        {
            displayNoEditWindow();
        } else
        {

            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/GUI/EditSong.fxml"));
                Parent root = (Parent) loader.load();

                EditSongController escontroller = loader.getController();
                escontroller.initializeModel(sm);
                escontroller.initializeSong(song, index);
                Stage stage = new Stage();
                
                Image icon = new Image(getClass().getResourceAsStream("/mytunes/GUI/newicon.png"));
                stage.getIcons().add(icon);
                stage.setTitle("MyTunes");
        
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
        Song song = tableSongList.getSelectionModel().getSelectedItem();
        if (song == null)
        {
            displayNoSongWindow();
        } else
        {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation dialog");
            alert.setContentText("Are you sure you want to delete: " + song.getTitle());

            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeYes)
            {
                try
                {
                    sm.deleteSong(song);
                    psm.deleteSongFromTable(song);
                } catch (MTBllException ex)
                {
                    displayError(ex);
                }
            }
        }
    }

    @FXML
    private void endApplication(ActionEvent event)
    {
        System.exit(0);
    }

    private void searchSong(ActionEvent event)
    {
        try
        {
            tableSongList.setItems(sm.searchSongs(sm.getSongs(), writeSearch.getText().toLowerCase()));
        } catch (IOException | MTBllException ex)
        {
            displayError(ex);
        }
    }

    @FXML
    private void previousSong(ActionEvent event)
    {
        if (mediaPlayer != null)
        {
            mediaPlayer.stop();
            paused = false;
            playing = true;
            if (tableSongList.getSelectionModel().getSelectedItem() != null)
            {
                if (currentSongSelected == 0)
                {
                    currentSongSelected = tableSongList.getItems().size() - 1;
                } else
                {
                    currentSongSelected--;
                }
                play();
            } else if (tableSongOnPlaylist.getSelectionModel().getSelectedItem() != null)
            {
                if (currentSongSelected == 0)
                {
                    currentSongSelected = tableSongOnPlaylist.getItems().size() - 1;
                } else
                {
                    currentSongSelected--;
                }
                play();
            }
        }
    }

    @FXML
    private void nextSong(ActionEvent event)
    {
        if (mediaPlayer != null)
        {
            mediaPlayer.stop();
            paused = false;
            playing = true;
            if (tableSongList.getSelectionModel().getSelectedItem() != null)
            {
                if (currentSongSelected == tableSongList.getItems().size() - 1)
                {
                    currentSongSelected = 0;
                } else
                {
                    currentSongSelected++;
                }
            } else if (tableSongOnPlaylist.getSelectionModel().getSelectedItem() != null)
            {
                if (currentSongSelected == tableSongOnPlaylist.getItems().size() - 1)
                {
                    currentSongSelected = 0;
                } else
                {
                    currentSongSelected++;
                }
            }
            play();

        }
    }

    @FXML
    private void playMusic(ActionEvent event)
    {
        if (mediaPlayer == null && tableSongList.getSelectionModel().getSelectedItem() == null
                && tableSongOnPlaylist.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information dialog");
            alert.setHeaderText("You have not chosen a song to play");
            alert.setContentText("Please select a song");

            alert.showAndWait();
        } else
        {
            if (tableSongList.getSelectionModel().getSelectedItem() != null)
            {
                if (playing)
                {
                    mediaPlayer.stop();
                    currentSongSelected = tableSongList.getSelectionModel().getSelectedIndex();
                    play();
                } else if (paused)
                {
                    mediaPlayer.play();
                } else
                {
                    currentSongSelected = tableSongList.getSelectionModel().getSelectedIndex();
                    play();
                }
            } else
            {
                if (playing)
                {
                    mediaPlayer.stop();
                    currentSongSelected = tableSongOnPlaylist.getSelectionModel().getSelectedIndex();
                    play();
                } else if (paused)
                {
                    mediaPlayer.play();
                } else
                {
                    currentSongSelected = tableSongOnPlaylist.getSelectionModel().getSelectedIndex();
                    play();
                }
            }
            playing = true;
            paused = false;
            controlSound();

        }
    }

    @FXML
    private void pauseMusic(ActionEvent event)
    {
        if (mediaPlayer != null)
        {
            mediaPlayer.pause();
            playing = false;
            paused = true;
        }
    }

    @FXML
    private void stopMusic(ActionEvent event)
    {
        if (mediaPlayer != null)
        {
            mediaPlayer.stop();
            tableSongList.getSelectionModel().clearSelection();
            tableSongOnPlaylist.getSelectionModel().clearSelection();
            mediaPlayer = null;
            playing = false;
            paused = false;
            lblMusicPlaying.setText("Nothing is playing");
        }
    }

    @FXML
    private void writeSearch(KeyEvent event)
    {
        try
        {
            tableSongList.setItems(sm.searchSongs(sm.getSongs(), writeSearch.getText().toLowerCase()));
        } catch (IOException | MTBllException ex)
        {
            displayError(ex);
        }

    }

    @FXML
    private void sliderDrag(MouseEvent event)
    {
        mediaPlayer.seek(Duration.seconds(sliderDuration.getValue()));
    }

    private void songPressed(MouseEvent event)
    {
        paused = false;
        tableSongOnPlaylist.getSelectionModel().clearSelection();
    }

    private void addToPlaylist(ActionEvent event)
    {
        try
        {
            Playlist playlist = tablePlaylist.getSelectionModel().getSelectedItem();
            Song song = tableSongList.getSelectionModel().getSelectedItem();
            psm.addToPlaylist(playlist, song);
        } catch (MTBllException ex)
        {
            displayError(ex);
        }
    }

    private void deleteFromPlaylistSongsEverything(ActionEvent event)
    {
    }

    private void removeSongFromPlaylist(ActionEvent event)
    {
    }

    private void endApplication()
    {
        System.exit(0);
    }

    private void displayError(Exception ex)
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Error dialog");
        alert.setHeaderText(null);
        alert.setContentText(ex.getMessage());

        alert.showAndWait();
    }

    private void displayNoSongWindow()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information dialog");
        alert.setHeaderText("You have not chosen a song to delete");
        alert.setContentText("Please select a song");

        alert.showAndWait();
    }
    
    private void displayNoEditWindow() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information dialog");
        alert.setHeaderText("You have not chosen a song to delete");
        alert.setContentText("Please select a song");

        alert.showAndWait();
    }

    private void displayNoPlaylistWindow()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information dialog");
        alert.setHeaderText("You have not selected a playlist");
        alert.setContentText("Please select a playlist to delete");

        alert.showAndWait();
    }



    @FXML
    private void mouseClickedSearch(MouseEvent event)
    {
        try
        {

            tableSongList.setItems(sm.searchSongs(sm.getSongs(), writeSearch.getText().toLowerCase()));
        } catch (IOException | MTBllException ex)
        {
            displayError(ex);
        }
    }

    @FXML
    private void mouseClickedArrow(MouseEvent event)
    {
        Playlist playlist = tablePlaylist.getSelectionModel().getSelectedItem();
        Song song = tableSongList.getSelectionModel().getSelectedItem();
        if (playlist == null)
        {
            displayNoPlaylistWindow();
        } else if (song == null)
        {
            displayNoSongWindow();
        } else
        {
            try
            {
                psm.addToPlaylist(playlist, song);
            } catch (MTBllException ex)
            {
                displayError(ex);
            }
        }
    }

    private void play()
    {
        String filePath;
        if (tableSongList.getSelectionModel().getSelectedItem() != null)
        {
            filePath = tableSongList.getItems().get(currentSongSelected).getFilepath();
            tableSongList.getSelectionModel().clearAndSelect(currentSongSelected);
            lblMusicPlaying.setText(tableSongList.getItems().get(currentSongSelected).getTitle() + " is now playing");
        } else
        {
            filePath = tableSongOnPlaylist.getItems().get(currentSongSelected).getFilepath();
            tableSongOnPlaylist.getSelectionModel().clearAndSelect(currentSongSelected);
            lblMusicPlaying.setText(tableSongOnPlaylist.getItems().get(currentSongSelected).getTitle() + " is now playing");
        }
        String trueFilePath = "file:/" + filePath;
        String trueTrueFilePath = trueFilePath.replace(" ", "%20");
        try
        {
            Media media = new Media(trueTrueFilePath);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        } catch (Exception ex)
        {
            displayError(ex);
            lblMusicPlaying.setText("Nothing is playing");
        }
        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>()
        {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue)
            {
                sliderDuration.setValue(newValue.toSeconds());
                sliderDuration.maxProperty().bind(Bindings.createDoubleBinding(() -> mediaPlayer.getTotalDuration().toSeconds(), mediaPlayer.totalDurationProperty()));
            }
        });
        mediaPlayer.setOnEndOfMedia(new Runnable()
        {
            @Override
            public void run()
            {
                if (tableSongList.getSelectionModel().getSelectedItem() != null)
                {
                    if (currentSongSelected == tableSongList.getItems().size() - 1)
                    {
                        currentSongSelected = 0;
                    } else
                    {
                        currentSongSelected++;
                    }
                    play();
                } else
                {
                    if (currentSongSelected == tableSongOnPlaylist.getItems().size() - 1)
                    {
                        currentSongSelected = 0;
                    } else
                    {
                        currentSongSelected++;
                    }
                    play();
                }
            }

        });
        playing = true;
    }

    private void controlSound()
    {
        sliderVolume.setValue(mediaPlayer.getVolume() * 100);
        sliderVolume.valueProperty().addListener(new InvalidationListener()
        {
            @Override
            public void invalidated(Observable observable)
            {
                mediaPlayer.setVolume(sliderVolume.getValue() / 100);
            }
        });
    }

    private void getSongsInPlayList(MouseEvent event)
    {
        try
        {
            Playlist playlist = tablePlaylist.getSelectionModel().getSelectedItem();
            int id = playlist.getId();
            tableSongOnPlaylist.getItems().removeAll();
            tableSongOnPlaylist.setItems(psm.getPlaylistSongs(id));
        } catch (MTBllException ex)
        {
            displayError(ex);
        }
    }

    @FXML
    private void clickedOnSongs(MouseEvent event)
    {
        tableSongOnPlaylist.getSelectionModel().clearSelection();
        paused = false;
    }

    @FXML
    private void clickedOnSongPlaylist(MouseEvent event)
    {
        tableSongList.getSelectionModel().clearSelection();
        paused = false;
    }

    @FXML
    private void tablePlaylistClicked(MouseEvent event)
    {
        try
        {
            if (tablePlaylist.getSelectionModel().getSelectedItem() != null)
            {
                Playlist playlist = tablePlaylist.getSelectionModel().getSelectedItem();
                int id = playlist.getId();
                tableSongOnPlaylist.setItems(psm.getPlaylistSongs(id));
            }
        } catch (MTBllException ex)
        {
            displayError(ex);
        }
    }
     
    
}
