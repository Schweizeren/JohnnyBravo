/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.GUI.Model;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
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

    /**
     * The constructor of the SongModel class
     * Initializes instace of the classes in the BLL layer
     * Also initializes an ObservableArrayList that contains all songs in
     * the database
     * @throws MTBllException 
     */
    public SongModel() throws MTBllException
    {
        mtmanager = new MyTunesManager();
        ss = new SongSearcher();
        songList = FXCollections.observableArrayList();
        songList.addAll(mtmanager.getAllSongs());

    }

    /**
     * Opens up af filechooser. This filechooser can only select mp3 files. It
     * then takes the abseloute pathfile of the selected mp3 files and converts
     * it into a String. Then that String is taken and converted again. Then its
     * set so the instance variable trueTrueFilePath now contains the filepath
     * @throws MTBllException 
     */
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

    /**
     * Calls the method deleteSong from the MyTunesManager class. Also removes
     * the song from the ObservableArrayList songList
     * @param song the song getting removed
     * @throws MTBllException 
     */
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

    /**
     * Makes a new ObservableList which contains song ojbects. Adds all songs
     * which title and artist contains the query String into the new
     * ObservableList. After this the ObservableList is returned with all songs
     * matching the query
     * @param searchBase the list of songs getting searched
     * @param query the searched word
     * @return a list containing songs containing the searched word
     * @throws MTBllException
     * @throws IOException 
     */
    public ObservableList<Song> searchSongs(List<Song> searchBase, String query) throws MTBllException, IOException
    {
        ObservableList<Song> searchedSongList = FXCollections.observableArrayList();
        searchedSongList.addAll(ss.searchSongs(searchBase, query));
        return searchedSongList;
    }

    /**
     * Calls the method getSongTitle from the MyTunesManager class
     * @return a string containing the title of the song
     * @throws MTBllException 
     */
    public String getSongTitle() throws MTBllException
    {
        String songTitle = mtmanager.getSongTitle(trueTrueFilePath);
        return songTitle;
    }

    /**
     * Calls the getDurationInSec method in the MyTunesManager class. The it
     * takes the duration that is in seconds and turns it into a string
     * where it shows the songs length in minutes and seconds. So the duration
     * is formatted into a time on a digital clock
     * @return the string containing the duration in minutes and seconds
     * @throws MTBllException 
     */
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

    /**
     * Calls the method getDurationInSec in the MyTunesManager class
     * @return the duration of a song in seconds
     * @throws MTBllException 
     */
    public int getDurationInSec() throws MTBllException {
        try
        {
            return mtmanager.getDurationInSec(trueTrueFilePath);
        } catch (MTBllException ex)
        {
            throw new MTBllException("Could not get duration in seconds");
        }
    }
    /**
     * Returns the filepath that is in the instance variable trueTrueFilePath
     * @return the instance variable trueTrueFilePath
     */
    public String getFilePath()
    {
        return trueTrueFilePath;
    }

    /**
     * Calls the method getAuthor in the MyTunesManager class
     * @return a string containing the artist of the song
     * @throws MTBllException 
     */
    public String getArtist() throws MTBllException
    {
        String artist = mtmanager.getAuthor(trueTrueFilePath);
        return artist;

    }

    /**
     * Calls the method createSong in the MyTunesManager class
     * @param title title of the song
     * @param duration duration in seconds of the song
     * @param author artist of the song
     * @param genre genre of the song
     * @param filepath filepath of the song
     * @throws MTBllException 
     */
    public void createSong(String title, int duration, String author, String genre, String filepath) throws MTBllException
    {
        Song song = mtmanager.createSong(title, duration, author, genre, filepath);
        songList.add(song);
    }

    /**
     * Returns the list containing all songs from the database
     * @return the ObservableList containing all songs
     */
    public ObservableList<Song> getSongs()
    {
        return songList;
    }

    /**
     * Calls the updateSong class from the myTunesManager class
     * When the song has been updated then it also updates the songList by
     * setting the updated song on its index
     * @param song the song getting updated
     * @param index the index of the song in the ObservableList
     * @throws MTBllException 
     */
    public void updateSong(Song song, int index) throws MTBllException
    {
        mtmanager.updateSong(song);
        songList.set(index, song);
    }
}
