/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.BLL.Model;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import mytunes.BLL.MyTunesManager;
import mytunes.BLL.SongSearcher;
import mytunes.BLL.exception.MTBllException;
import mytunes.be.Song;

/**
 *
 * @author Frederik Jensen
 */
public class SongModel {

    private MyTunesManager mtmanager;
    private SongSearcher ss;
    private ObservableList<Song> songList;
    private String trueTrueFilePath;

    public SongModel() throws MTBllException {
        mtmanager = new MyTunesManager();
        ss = new SongSearcher();
        songList = FXCollections.observableArrayList();
        songList.addAll(mtmanager.getAllSongs());

    }

    public void initializeFile() throws MTBllException {
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

        } else {
            throw new MTBllException("File was not chosen");
        }
    }

    public void deleteSong(Song song) {
        try {
            mtmanager.deleteSong(song);
            songList.remove(song);
        } catch (MTBllException ex) {
            Logger.getLogger(SongModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ObservableList<Song> searchSongs(List<Song> searchBase, String query) throws MTBllException {

        try {
            ObservableList<Song> searchedSongList = FXCollections.observableArrayList();
            searchedSongList.addAll(ss.searchSongs(searchBase, query));
            return searchedSongList;
        } catch (IOException ex) {
            throw new MTBllException("Could not connect to DAL layer");
        }

    }

    public String getSongTitle() throws MTBllException {
        try {
            String songTitle = mtmanager.getSongTitle(trueTrueFilePath);
            return songTitle;

        } catch (MTBllException ex) {
            throw new MTBllException("");
        }
    }

    public String getDuration() throws MTBllException {
        try {
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
        } catch (MTBllException ex) {
            throw new MTBllException("");
        }
    }

    public int getDurationInSec() throws MTBllException {
        try {
            return mtmanager.getDurationInSec(trueTrueFilePath);
        } catch (MTBllException ex) {
            throw new MTBllException("Could not get duration in seconds");
        }
    }

    public String getFilePath() {
        return trueTrueFilePath;
    }

    public String getArtist() throws MTBllException {
        try {
            String artist = mtmanager.getAuthor(trueTrueFilePath);
            return artist;
        } catch (MTBllException ex) {
            throw new MTBllException("Could not get artist of song");
        }
    }

    public void createSong(String title, int duration, String author, String genre, String filepath) throws MTBllException {
        try {
            Song song = mtmanager.createSong(title, duration, author, genre, filepath);
            songList.add(song);
        } catch (MTBllException ex) {
            throw new MTBllException("Could not create song");
        }
    }

    public ObservableList<Song> getSongs() {
        return songList;
    }

    public void updateSong(Song song) throws MTBllException {
        try {
            mtmanager.updateSong(song);
        } catch (MTBllException ex) {
            throw new MTBllException("Could not update song");
        }
    }
}
