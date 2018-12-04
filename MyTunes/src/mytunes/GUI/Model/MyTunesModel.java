/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.GUI.Model;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import mytunes.BLL.MyTunesManager;
import mytunes.BLL.SongSearcher;
import mytunes.be.Playlist;
import mytunes.be.Song;

/**
 *
 * @author bonde
 */
public class MyTunesModel
{
    private ObservableList<Playlist> playlistList;

    private ObservableList<Song> songList;
    private final SongSearcher ss;
    private MyTunesManager mtmanager;
    private String trueTrueFilePath;
    
    public MyTunesModel() throws IOException {
        playlistList = FXCollections.observableArrayList();
        songList = FXCollections.observableArrayList();
        ss = new SongSearcher();
        mtmanager = new MyTunesManager();
        songList.addAll(mtmanager.getAllSongs());
        playlistList.addAll(mtmanager.getAllPlaylist());
    }
    
    
    public void initializeFile()
    {
        String filePath;
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a File (*.mp3)", "*.mp3");
            fileChooser.getExtensionFilters().add(filter);
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                filePath = file.toURI().toString();
                System.out.print(trueTrueFilePath);
                String trueFilePath = filePath.replaceFirst("file:/", "");
                trueTrueFilePath = trueFilePath.replace("%20", " ");
                
            }else {
                System.out.println("No file chosen");
                
            }
    }
    
    public void endApplication()
    {
        System.exit(0);
    }
    
    public void deleteSong(Song song)
    {
        mtmanager.deleteSong(song);
        songList.remove(song);
    }
    
    public ObservableList<Song> searchSongs(List<Song> searchBase, String query) {
        ObservableList<Song> searchedSongList = FXCollections.observableArrayList();
        searchedSongList.addAll(ss.searchSongs(searchBase, query));
        return searchedSongList;
    }
    
    public String getSongTitle() {
        String songTitle = mtmanager.getSongTitle(trueTrueFilePath);
        if (!songTitle.isEmpty() || !songTitle.equals(null)) {
            return songTitle;
        }else {
        return "This song has no current title";
        }
    }
    
    public String getDuration() {
        int duration = mtmanager.getDurationInSec(trueTrueFilePath);
        int seconds = duration % 60;
        int minutes = (duration - seconds) / 60;
        
        String mp3Seconds;
        String mp3Minutes = "" + minutes;
        if (seconds < 10) {
            mp3Seconds = "0" + seconds;
        } else {
            mp3Seconds = "" + seconds;
        }
        
        String formattedTime = mp3Minutes + ":" + mp3Seconds;
        return formattedTime;
    }
    
    public int getDurationInSec() {
        return mtmanager.getDurationInSec(trueTrueFilePath);
    }
    
    public String getFilePath() {
        return trueTrueFilePath;
    }
    
    public String getArtist() {
        String artist = mtmanager.getAuthor(trueTrueFilePath);
        return artist;
    }
    
    public void createSong(String title, int duration, String author, String genre, String filepath) {
        Song song = mtmanager.createSong(title, duration, author, genre, filepath);
        songList.add(song);
    }
    
    public void createPlaylist(String name) throws SQLException
    {
        Playlist playlist = mtmanager.createPlaylist(name);
        playlistList.add(playlist);
        
    }
    
    public ObservableList<Song> getSongs() {
        return songList;
    }
    
    public void updateSong(Song song) {
        mtmanager.updateSong(song);
    }
    
    public Song getSong(int id) {
        return mtmanager.getSong(id);
    }
    
    public Playlist getPlaylist(int id) throws SQLException
    {
        return mtmanager.getPlaylist(id);
    }
    
    public void deletePlaylist(Playlist playlist) throws SQLException
    {
        mtmanager.deletePlaylist(playlist);
        songList.remove(playlist);
    }
    
    public ObservableList<Playlist> getAllPlaylist()
    {
        return playlistList;
    }
}
