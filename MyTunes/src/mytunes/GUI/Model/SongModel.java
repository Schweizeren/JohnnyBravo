/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.GUI.Model;

import java.io.File;
import java.io.IOException;
import java.util.List;
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
public class SongModel
{

    private MyTunesManager mtmanager;
    private SongSearcher ss;
    private ObservableList<Song> songList;
    private String trueTrueFilePath;

    public SongModel() throws MTBllException
    {
        mtmanager = new MyTunesManager();
        ss = new SongSearcher();
        songList = FXCollections.observableArrayList();
        songList.addAll(mtmanager.getAllSongs());

    }

    public void initializeFile() throws MTBllException
    {
        String filePath;
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a File (*.mp3)", "*.mp3");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        if (file != null)
        {
            filePath = file.toURI().toString();
            String trueFilePath = filePath.replaceFirst("file:/", "");
            trueTrueFilePath = trueFilePath.replace("%20", " ");

        } else
        {
            throw new MTBllException("File was not chosen");
        }
    }

    public void deleteSong(Song song) throws MTBllException
    {
        try
        {
            mtmanager.deleteSong(song);
            songList.remove(song);
        } catch (MTBllException ex)
        {
            throw new MTBllException("Could not delete song");
        }
    }

    public ObservableList<Song> searchSongs(List<Song> searchBase, String query) throws MTBllException, IOException
    {
        ObservableList<Song> searchedSongList = FXCollections.observableArrayList();
        searchedSongList.addAll(ss.searchSongs(searchBase, query));
        return searchedSongList;
    }

    public String getSongTitle() throws MTBllException
    {
        String songTitle = mtmanager.getSongTitle(trueTrueFilePath);
        return songTitle;
    }

    public String getDuration() throws MTBllException
    {
        int duration = mtmanager.getDurationInSec(trueTrueFilePath);
        int seconds = duration % 60;
        int minutes = (duration - seconds) / 60;

        String mp3Seconds;
        String mp3Minutes = "" + minutes;
        if (seconds < 10)
        {
            mp3Seconds = "0" + seconds;
        } else
        {
            mp3Seconds = "" + seconds;
        }

        String formattedTime = mp3Minutes + ":" + mp3Seconds;
        return formattedTime;
    }

    public int getDurationInSec() throws MTBllException
    {
        return mtmanager.getDurationInSec(trueTrueFilePath);
    }

    public String getFilePath()
    {
        return trueTrueFilePath;
    }

    public String getArtist() throws MTBllException
    {
        String artist = mtmanager.getAuthor(trueTrueFilePath);
        return artist;

    }

    public void createSong(String title, int duration, String author, String genre, String filepath) throws MTBllException
    {
        Song song = mtmanager.createSong(title, duration, author, genre, filepath);
        songList.add(song);
    }

    public ObservableList<Song> getSongs()
    {
        return songList;
    }

    public void updateSong(Song song, int index) throws MTBllException
    {
        mtmanager.updateSong(song);
        songList.set(index, song);
    }
}
