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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
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
public class MyTunesViewController implements Initializable {

    private PlaylistSongModel psm;
    private PlaylistModel pm;
    private SongModel sm;
    private MediaPlayer mediaPlayer;
    private boolean playing;
    private boolean paused;
    private int currentSongSelected;

    @FXML
    private Label lblMusicPlaying;
    @FXML
    private TextField writeSearch;
    @FXML
    private Slider sliderDuration;
    @FXML
    private Slider sliderVolume;
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

    /**
     * The constructor of the MyTunesViewController
     */
    public MyTunesViewController() {
        try {
            psm = new PlaylistSongModel();
            pm = new PlaylistModel();
            sm = new SongModel();
        } catch (MTBllException ex) {
            displayError(ex);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cellTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        cellArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        cellGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        cellTime.setCellValueFactory(new PropertyValueFactory<>("formattedLength"));
        cellSongPlaylistTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        cellPlaylistTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableSongList.setItems(sm.getSongs());
        tablePlaylist.setItems(pm.getAllPlaylist());
    }

    /**
     * When the button labelled "new..." over at the table view with playlist is
     * pressed this will with then run this method. This opens up a new view
     * where the user then can create a new playlist. This method also calls the
     * method initializeModel from inside the CreatePlaylistController. This
     * will make it so the PlaylistModel this class calls will also be called in
     * the CreatePlaylistController
     *
     * @param event The event that the button has
     */
    @FXML
    private void createPlaylist(ActionEvent event) {
        try {
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
        } catch (IOException ex) {
            displayError(ex);
        }
    }

    /**
     * When the button labelled "edit..." is pressed over at the table view
     * containing playlists then this method will run. This will open up a new
     * view where the user can then edit a playlist. This method also checks if
     * a playlist has been clicked before it opens up the new view. This method
     * also calls the method initializeModel from the EditPlaylistController
     * This makes it so the playlistmodel in this controller also gets called in
     * the EditPlaylistController
     *
     * @param event the event when the button is pressed
     */
    @FXML
    private void editPlaylist(ActionEvent event) {
        Playlist playlist = tablePlaylist.getSelectionModel().getSelectedItem();
        int index = tablePlaylist.getSelectionModel().getSelectedIndex();
        if (playlist == null) {
            displayNoPlaylistWindow();
        } else {
            try {
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
            } catch (IOException ex) {
                displayError(ex);
            }
        }
    }

    /**
     * When the button labelled "delete" over at the tableview containing
     * playlist then this method wil run. This method opens up a pop up window
     * asking the user if they are sure they want to delete a playlist. If the
     * press "yes" then this will run the deletePlaylist method from the
     * PlaylistModel class and the deleteFromPlaylist method from the
     * PlaylistSongModel class. this will delete the playlist and its
     * information from the Playlist and PlaylistSong table
     *
     * @param event the event when the button is pressed
     */
    @FXML
    private void deletePlaylist(ActionEvent event) {
        Playlist playlist = tablePlaylist.getSelectionModel().getSelectedItem();
        if (playlist == null) {
            displayNoPlaylistWindow();
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation dialog");
            alert.setContentText("Are you sure you want to delete: " + playlist);

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/mytunes/GUI/Dialogs.css").toExternalForm());
            dialogPane.getStyleClass().add("dialog-pane");

            Image icon = new Image(this.getClass().getResourceAsStream("/mytunes/GUI/newicon.png"));
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(icon);

            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeYes) {
                try {
                    pm.deletePlaylist(playlist);
                    psm.deleteFromPlaylist(playlist);
                    tablePlaylist.getSelectionModel().clearSelection();
                } catch (MTBllException ex) {
                    displayError(ex);
                }
            }
        }
    }

    /**
     * When the button containing the up arrow over at the tabelview containing
     * songs from a playlis then this method will run. This method will try to
     * call the moveSong method from the PlaylistSongModel and then move the
     * song one up from its current position
     *
     * @param event the event when the button is pressed
     */
    @FXML
    private void moveUp(ActionEvent event) {
        try {
            currentSongSelected = tableSongOnPlaylist.getSelectionModel().getSelectedIndex();
            Song songGettingMoved = tableSongOnPlaylist.getItems().get(currentSongSelected);
            Song songAffected = tableSongOnPlaylist.getItems().get(currentSongSelected - 1);
            int songGettingMovedLocation = tableSongOnPlaylist.getSelectionModel().getSelectedIndex();
            int songAffectedLocation = songGettingMovedLocation - 1;
            int playlistId = tablePlaylist.getSelectionModel().getSelectedItem().getId();
            psm.moveSong(songGettingMoved, songAffected, songGettingMovedLocation, songAffectedLocation, playlistId);
        } catch (MTBllException | IndexOutOfBoundsException | NullPointerException ex) {
            displayError(ex);
        }

    }

    /**
     * This method does the same as the moveUp method only difference is that
     * this method will try to move the song one down from its current position
     *
     * @param event the event when the button is pressed
     */
    @FXML
    private void moveDown(ActionEvent event) {
        try {
            currentSongSelected = tableSongOnPlaylist.getSelectionModel().getSelectedIndex();
            Song songGettingMoved = tableSongOnPlaylist.getItems().get(currentSongSelected);
            Song songAffected = tableSongOnPlaylist.getItems().get(currentSongSelected + 1);
            int songGettingMovedLocation = tableSongOnPlaylist.getSelectionModel().getSelectedIndex();
            int songAffectedLocation = songGettingMovedLocation + 1;
            int playlistId = tablePlaylist.getSelectionModel().getSelectedItem().getId();
            psm.moveSong(songGettingMoved, songAffected, songGettingMovedLocation, songAffectedLocation, playlistId);
        } catch (MTBllException | IndexOutOfBoundsException | NullPointerException ex) {
            displayError(ex);
        }
    }

    /**
     * When the button "delete" over at the tableview containing songs from
     * playlists is pressed this method will run. When a playlist and a song
     * from the playlist is selected this method will run the method
     * removeSongFromPlaylist from the PlaylistSongModel class. This will then
     * delete the song from the playlist
     *
     * @param event
     */
    @FXML
    private void deleteSongOnPlaylist(ActionEvent event) {
        Playlist playlist = tablePlaylist.getSelectionModel().getSelectedItem();
        Song song = tableSongOnPlaylist.getSelectionModel().getSelectedItem();
        if (playlist == null) {
            displayNoPlaylistWindow();
        } else if (song == null) {
            displayNoSongWindow();
        } else {
            try {
                psm.removeSongFromPlaylist(playlist, song);
            } catch (Exception ex) {
                displayError(ex);
            }
        }

    }

    /**
     * When the button labelled "new..." over at the tableview that contains all
     * songs then this method will run. This method opens up a new view where
     * the user can add a new song to the tableview. Before the view is open the
     * method initializeModel in the CreateSongController will run. This method
     * makes it so the instance of the SongModel in this class will also be
     * called in the CreateSongController
     *
     * @param event the event when button is pressed
     * @throws SQLException
     */
    @FXML
    private void newSong(ActionEvent event) {
        try {
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
        } catch (IOException ex) {
            displayError(ex);
        }
    }

    /**
     * When the button labelled "edit..." is pressed over at the tableview that
     * contains all songs is pressed then this method will run. When a song is
     * pressed on the tableview then a new view will appear where the user then
     * can edit an alreaady existing song. Beofre the view appears the two
     * methods initializeModel and initializeSong will be called from the
     * EditSongController class. The initializeModel method makes it so the
     * SongModel used in this class will also be called in the
     * EditSongController. The initializeSong method takes the selected song and
     * its index in the table view and sends its information to the
     * EditSongController.
     *
     * @param event the event when the button is pressed
     */
    @FXML
    private void editSong(ActionEvent event) {
        Song song = tableSongList.getSelectionModel().getSelectedItem();
        int index = tableSongList.getSelectionModel().getSelectedIndex();
        if (song == null) {
            displayNoEditWindow();
        } else {

            try {
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
            } catch (IOException ex) {
                displayError(ex);
            }
        }
    }

    /**
     * When the button labelled "delete" over at the tableview containing all
     * songs is pressed then this method will run. This makes a popup window
     * appear asking the user if they want to delete the selected song. If the
     * user presses yes then the song will be deleted from the tabelview and the
     * database. This is done by this method making calls to the method
     * deleteSong from the SongModel and the method deleteSongFromTable from the
     * PlaylistSongModel
     *
     * @param event the event when the button is pressed
     */
    @FXML
    private void deleteSong(ActionEvent event) {
        Song song = tableSongList.getSelectionModel().getSelectedItem();
        if (song == null) {
            displayNoSongWindow();
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation dialog");
            alert.setContentText("Are you sure you want to delete: " + song.getTitle());

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/mytunes/GUI/Dialogs.css").toExternalForm());
            dialogPane.getStyleClass().add("dialog-pane");

            Image icon = new Image(this.getClass().getResourceAsStream("/mytunes/GUI/newicon.png"));
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(icon);

            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeYes) {
                try {
                    sm.deleteSong(song);
                    psm.deleteSongFromTable(song);
                } catch (MTBllException ex) {
                    displayError(ex);
                }
            }
        }
    }

    /**
     * The button labbelled "close" is pressed this close the program
     *
     * @param event the event when the button is pressed
     */
    @FXML
    private void endApplication(ActionEvent event) {
        System.exit(0);
    }

    /**
     * This method checks if the mediaplayer is currently playing a song. If it
     * does it will skip to the song before the one currently playing. If the
     * song is at index 0 in the list it will skip to the song at the last index
     *
     * @param event the event when the button right next to the stop button is
     * pressed
     */
    @FXML
    private void previousSong(ActionEvent event) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            paused = false;
            playing = true;
            if (tableSongList.getSelectionModel().getSelectedItem() != null) {
                if (currentSongSelected == 0) {
                    currentSongSelected = tableSongList.getItems().size() - 1;
                } else {
                    currentSongSelected--;
                }
                play();
            } else if (tableSongOnPlaylist.getSelectionModel().getSelectedItem() != null) {
                if (currentSongSelected == 0) {
                    currentSongSelected = tableSongOnPlaylist.getItems().size() - 1;
                } else {
                    currentSongSelected--;
                }
                play();
            }
        }
    }

    /**
     * Checks if the mediaplayer is playing a song. If it does this method will
     * skip to the song next to the one currently playing and play that song
     * instead. If the song playing is at the last index in the list then it
     * will skip to the song at index 0 aka the first song in the list
     *
     * @param event the event when the button next to the pause bause button is
     * pressed
     */
    @FXML
    private void nextSong(ActionEvent event) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            paused = false;
            playing = true;
            if (tableSongList.getSelectionModel().getSelectedItem() != null) {
                if (currentSongSelected == tableSongList.getItems().size() - 1) {
                    currentSongSelected = 0;
                } else {
                    currentSongSelected++;
                }
            } else if (tableSongOnPlaylist.getSelectionModel().getSelectedItem() != null) {
                if (currentSongSelected == tableSongOnPlaylist.getItems().size() - 1) {
                    currentSongSelected = 0;
                } else {
                    currentSongSelected++;
                }
            }
            play();

        }
    }

    /**
     * This method checks the whether is mediaplayer is null, is currently
     * playing or is paused. If the mediaplayer is playing something and the
     * play button is pressed then it will stop the song and play the currently
     * selected song. If the mediaplayer is paused and the play button is
     * pressed then it will unpause and play the song from where it was. If the
     * mediaplayer is null and a song is selected it will play that song If the
     * mediaplayer is null and no song i selected a popup window will appear
     * asking the use to select a song first When this method is called it will
     * also call the controlSound method from this controller
     *
     * @param event th event when the play button is pressed
     */
    @FXML
    private void playMusic(ActionEvent event) {
        if (mediaPlayer == null && tableSongList.getSelectionModel().getSelectedItem() == null
                && tableSongOnPlaylist.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information dialog");
            alert.setHeaderText("You have not chosen a song to play");
            alert.setContentText("Please select a song");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/mytunes/GUI/Dialogs.css").toExternalForm());
            dialogPane.getStyleClass().add("dialog-pane");

            Image icon = new Image(this.getClass().getResourceAsStream("/mytunes/GUI/newicon.png"));
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(icon);

            alert.showAndWait();
        } else {
            if (tableSongList.getSelectionModel().getSelectedItem() != null) {
                if (playing) {
                    mediaPlayer.stop();
                    currentSongSelected = tableSongList.getSelectionModel().getSelectedIndex();
                    play();
                } else if (paused) {
                    mediaPlayer.play();
                } else {
                    currentSongSelected = tableSongList.getSelectionModel().getSelectedIndex();
                    play();
                }
            } else {
                if (playing) {
                    mediaPlayer.stop();
                    currentSongSelected = tableSongOnPlaylist.getSelectionModel().getSelectedIndex();
                    play();
                } else if (paused) {
                    mediaPlayer.play();
                } else {
                    currentSongSelected = tableSongOnPlaylist.getSelectionModel().getSelectedIndex();
                    play();
                }
            }
            playing = true;
            paused = false;
            controlSound();

        }
    }

    /**
     * When this method is called it will pause the currently playing song. If a
     * song isnt playing the method will do nothing
     *
     * @param event the event when the pause button is pressed
     */
    @FXML
    private void pauseMusic(ActionEvent event) {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            playing = false;
            paused = true;
        }
    }

    /**
     * When the stop button is pressed this methol will be called. Thie method
     * stops the currently selected song and also clears the selection from both
     * table views containing songs
     *
     * @param event
     */
    @FXML
    private void stopMusic(ActionEvent event) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            tableSongList.getSelectionModel().clearSelection();
            tableSongOnPlaylist.getSelectionModel().clearSelection();
            mediaPlayer = null;
            playing = false;
            paused = false;
            lblMusicPlaying.setText("Nothing is playing");
        }
    }

    /**
     * This method calls the method searchSongs from the songModel class
     * Everytime you press a key in the search textfield it will get the text
     * from that field. The method searchSongs will then use that text as a
     * parameter and also the list containing all songs. This will then set the
     * items to the tableview to show songs that only contains the text from the
     * textfield in their title or artist.
     *
     * @param event the event everytime a key on the keyboard is pressed on the
     * textfield. So everytime a key is pressed on that field this method is
     * called
     */
    @FXML
    private void writeSearch(KeyEvent event) {
        try {
            tableSongList.setItems(sm.searchSongs(sm.getSongs(), writeSearch.getText().toLowerCase()));
        } catch (IOException | MTBllException ex) {
            displayError(ex);
        }

    }

    /**
     * When the slider is dragged it gets its value on where its position is and
     * uses that to set the song currently playing to a specific time
     *
     * @param event
     */
    @FXML
    private void sliderDrag(MouseEvent event) {
        mediaPlayer.seek(Duration.seconds(sliderDuration.getValue()));
    }

    /**
     * Opens a popup window displaying an exception
     *
     * @param ex the exception being displayed
     */
    private void displayError(Exception ex) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Error dialog");
        alert.setHeaderText(null);
        alert.setContentText(ex.getMessage());

        alert.showAndWait();
    }

    /**
     * A pop-up window that tells the user they havent chosen a song to delete
     */
    private void displayNoSongWindow() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information dialog");
        alert.setHeaderText("You have not chosen a song to delete");
        alert.setContentText("Please select a song");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/mytunes/GUI/Dialogs.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");

        Image icon = new Image(this.getClass().getResourceAsStream("/mytunes/GUI/newicon.png"));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);

        alert.showAndWait();
    }

    /**
     * A pop-up window that tells the user they havent chosen a song to edit
     */
    private void displayNoEditWindow() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information dialog");
        alert.setHeaderText("You have not chosen a song to edit");
        alert.setContentText("Please select a song");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/mytunes/GUI/Dialogs.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");

        Image icon = new Image(this.getClass().getResourceAsStream("/mytunes/GUI/newicon.png"));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);

        alert.showAndWait();
    }

    /**
     * A pop window that shows the user they havent selected a playlist
     */
    private void displayNoPlaylistWindow() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information dialog");
        alert.setHeaderText("You have not selected a playlist");
        alert.setContentText("Please select a playlist");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/mytunes/GUI/Dialogs.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");

        Image icon = new Image(this.getClass().getResourceAsStream("/mytunes/GUI/newicon.png"));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);

        alert.showAndWait();
    }

    /**
     * Adds a selected song to a selected playlist by calling the method
     * addToPlaylist from the PlaylistSongModel. If a song or a playlist hasnt
     * been selected a popup window will appear
     *
     * @param event The arrow button between the two tabelviews containing songs
     */
    @FXML
    private void mouseClickedArrow(MouseEvent event) {
        Playlist playlist = tablePlaylist.getSelectionModel().getSelectedItem();
        Song song = tableSongList.getSelectionModel().getSelectedItem();
        if (playlist == null) {
            displayNoPlaylistWindow();
        } else if (song == null) {
            displayNoSongWindow();
        } else {
            try {
                psm.addToPlaylist(playlist, song);
            } catch (MTBllException ex) {
                displayError(ex);
            }
        }
    }

    /**
     * Gets the filepath by the currently selected song in a tableview. The
     * songs filepath is gotten by using the integer currentSongSelected. This
     * instance variable is set to the index of the song in the list. This is
     * done in the playMusic method. This integer is then used here to get the
     * filepath of the song on that index in the list. After that the filepath
     * of the song is then converted so the mediaplayer accepts the filepath in
     * order to play the song. The mediaplayer then plays the song.
     *
     * The slider that shows the time of the song and where the song is
     * currently at is also set in this method
     *
     * The method also checks if a song has enden and then uses a runnable to
     * then adds one to the currentSongSelected variable or sets it at 0 if the
     * last song in the index has just finished playing. After this it runs this
     * method again to keep playing the next song until its done
     */
    private void play() {
        String filePath;
        if (tableSongList.getSelectionModel().getSelectedItem() != null) {
            filePath = tableSongList.getItems().get(currentSongSelected).getFilepath();
            tableSongList.getSelectionModel().clearAndSelect(currentSongSelected);
            lblMusicPlaying.setText(tableSongList.getItems().get(currentSongSelected).getTitle() + " is now playing");
        } else {
            filePath = tableSongOnPlaylist.getItems().get(currentSongSelected).getFilepath();
            tableSongOnPlaylist.getSelectionModel().clearAndSelect(currentSongSelected);
            lblMusicPlaying.setText(tableSongOnPlaylist.getItems().get(currentSongSelected).getTitle() + " is now playing");
        }
        String trueFilePath = "file:/" + filePath;
        String trueTrueFilePath = trueFilePath.replace(" ", "%20");
        try {
            Media media = new Media(trueTrueFilePath);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        } catch (Exception ex) {
            displayError(ex);
            lblMusicPlaying.setText("Nothing is playing");
        }
        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                sliderDuration.setValue(newValue.toSeconds());
                sliderDuration.maxProperty().bind(Bindings.createDoubleBinding(() -> mediaPlayer.getTotalDuration().toSeconds(), mediaPlayer.totalDurationProperty()));
            }
        });
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                if (tableSongList.getSelectionModel().getSelectedItem() != null) {
                    if (currentSongSelected == tableSongList.getItems().size() - 1) {
                        currentSongSelected = 0;
                    } else {
                        currentSongSelected++;
                    }
                    play();
                } else {
                    if (currentSongSelected == tableSongOnPlaylist.getItems().size() - 1) {
                        currentSongSelected = 0;
                    } else {
                        currentSongSelected++;
                    }
                    play();
                }
            }

        });
        playing = true;
    }

    /**
     * Sets the volume of the mediaplayer to the value of the slider
     * sliderVolume. This method also listens to changes in the slider so it
     * changes the volume of the mediaplayer correspondingly
     */
    private void controlSound() {
        sliderVolume.setValue(mediaPlayer.getVolume() * 100);
        sliderVolume.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(sliderVolume.getValue() / 100);
            }
        });
    }

    /**
     * When a song is clicked on in the tableSongList tableview then it will
     * clear the selection over in the tableSongOnPlaylist tableview so two
     * songs cant be selected at once
     *
     * @param event When the tablelist is clicked on
     */
    @FXML
    private void clickedOnSongs(MouseEvent event) {
        tableSongOnPlaylist.getSelectionModel().clearSelection();
        paused = false;
    }

    /**
     * Does the same as the clickedOnSongs method but clears the selection in
     * the tabelSongList tableview if the opposite tableview has been clicked on
     *
     * @param event When the tablelist is clicked on
     */
    @FXML
    private void clickedOnSongPlaylist(MouseEvent event) {
        tableSongList.getSelectionModel().clearSelection();
        paused = false;
    }

    /**
     * Takes a selected playlists id and uses it to get all available songs in
     * that playlist. It does so by calling the method getPlaylistSongs in the
     * PlaylistSongModel class. Everytime a new playlist is clicked on it
     * removes all songs from the tableview before adding new ones from the new
     * selected playlist
     *
     * @param event when a playlist is clicked on in the playlist tableview
     */
    @FXML
    private void tablePlaylistClicked(MouseEvent event) {
        try {
            if (tablePlaylist.getSelectionModel().getSelectedItem() != null) {
                Playlist playlist = tablePlaylist.getSelectionModel().getSelectedItem();
                int id = playlist.getId();
                tableSongOnPlaylist.setItems(psm.getPlaylistSongs(id));
            }
        } catch (MTBllException ex) {
            displayError(ex);
        }
    }

    /**
     * If a song is clicked on twice it will get played
     * @param event the song getting clicked on
     */
    @FXML
    private void pressedOnSongs(MouseEvent event) {
        if (tableSongList.getSelectionModel().getSelectedItem() != null) {
            if (event.getClickCount() == 2) {
                playing = true;
                currentSongSelected = tableSongList.getSelectionModel().getSelectedIndex();
                play();
            }
        } else if (tableSongOnPlaylist.getSelectionModel().getSelectedItem() != null) {
            if (event.getClickCount() == 2) {
                playing = true;
                currentSongSelected = tableSongOnPlaylist.getSelectionModel().getSelectedIndex();
                play();
            }

        }

    }
}
