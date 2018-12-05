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
import javafx.collections.ObservableList;
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
    private MyTunesManager mtm;
    private String trueTrueFilePath;
    
    public MyTunesModel() throws IOException {
        playlistList = FXCollections.observableArrayList();
        songList = FXCollections.observableArrayList();
        ss = new SongSearcher();
        mtm = new MyTunesManager();
        songList.addAll(mtm.getAllSongs());
        playlistList.addAll(mtm.getPlaylist());
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
        mtm.deleteSong(song);
        songList.remove(song);
    }
    
    public ObservableList<Song> searchSongs(List<Song> searchBase, String query) {
        ObservableList<Song> searchedSongList = FXCollections.observableArrayList();
        searchedSongList.addAll(ss.searchSongs(searchBase, query));
        return searchedSongList;
    }
    
    public String getSongTitle() {
        String songTitle = mtm.getSongTitle(trueTrueFilePath);
        if (!songTitle.isEmpty() || !songTitle.equals(null)) {
            return songTitle;
        }else {
        return "This song has no current title";
        }
    }
    
    public String getDuration() {
        int duration = mtm.getDurationInSec(trueTrueFilePath);
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
        return mtm.getDurationInSec(trueTrueFilePath);
    }
    
    public String getFilePath() {
        return trueTrueFilePath;
    }
    
    public String getArtist() {
        String artist = mtm.getAuthor(trueTrueFilePath);
        return artist;
    }
    
    public void createSong(String title, int duration, String author, String genre, String filepath) {
        Song song = mtm.createSong(title, duration, author, genre, filepath);
        songList.add(song);
    }
    
    public void createPlaylist(String name) throws SQLException
    {
        Playlist playlist = mtm.createPlaylist(name);
        playlistList.add(playlist);
        
    }
    
    public ObservableList<Song> getSongs() {
        return songList;
    }
    
    public void updateSong(Song song) {
        mtm.updateSong(song);
    }
    
    public Song getSong(int id) {
        return mtm.getSong(id);
    }

    public Playlist getPlaylist(int id) throws SQLException
    {
        return mtm.getPlaylist(id);
    }
    
    public void deletePlaylist(Playlist playlist) throws SQLException
    {
        mtm.deletePlaylist(playlist);
        songList.remove(playlist);
    }

}
