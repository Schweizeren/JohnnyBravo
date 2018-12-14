/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.GUI.Model;

import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.BLL.MyTunesManager;
import mytunes.BLL.exception.MTBllException;
import mytunes.be.Playlist;
import mytunes.be.Song;

/**
 *
 * @author J_Wan
 */
public class PlaylistSongModel {
    
    private MyTunesManager mtmanager;
    private ObservableList<Song> playlistSongList;
    
    /**
     * The constructor of the PlaylistSongModel class
     * Initializes an instance of the MyTunesManager class thats in the BLL
     * layer
     * Also initialize and ObersableArrayList of the songs on the different
     * playlist
     * @throws MTBllException 
     */
    public PlaylistSongModel() throws MTBllException
    {
        playlistSongList = FXCollections.observableArrayList();
        mtmanager = new MyTunesManager();
    }
    
    /**
     * Calls the method getPlaylistSongs from the MyTunesManager class and puts
     * all the songs from a specific playlist into a new list of songs. It does
     * this by using the id of the playlist. Then it goes through a for loop to
     * add all the songs in the new list into the Observable Arraylist which was
     * initialized in the contrusctor of this class. Then it returns this
     * Observable Arraylist. 
     * Everytime this method is called it also clears the
     * Observable Arraylist before it adds new songs from another playlist
     * @param id the id of playlist selected
     * @return a list of songs from the selected playlist
     * @throws MTBllException 
     */
    public ObservableList<Song> getPlaylistSongs(int id) throws MTBllException
    {
        try {
            playlistSongList.clear();
            List<Song> listofSongs = mtmanager.getPlaylistSongs(id);
            for (Song song : listofSongs)
            {
                playlistSongList.add(song);
            }
            
            return playlistSongList;
        } catch (MTBllException ex) {
            throw new MTBllException("Could not get songs in playlist");
        }
    }
    
    /**
     * Calls the method addToPlaylist from the MyTunesManagerClass
     * Takes a selected song and adds it to a selected playlist. Also adds the
     * song to the Observable Arraylist since it shows the songs of the current
     * selected playlist
     * @param playlist the selected playlist getting added to
     * @param song the selected song getting added to the selected playlist
     * @return the song which had been added to the playlist
     * @throws MTBllException 
     */
    public Song addToPlaylist(Playlist playlist, Song song) throws MTBllException
    {
        try {
            playlistSongList.add(song);
            return mtmanager.addToPlaylist(playlist, song);
        } catch (MTBllException ex) {
            throw new MTBllException("Could not add song to playlist");
        }
    }
    
    /**
     * Calls the method deleteFromPlaylist from the MyTunesManager class
     * Also clears the observable Arraylist from the songs of the selected
     * playlist since that playlist has just been deleted
     * @param playlist the playlist getting deleted from the PlaylistSong table
     * @throws MTBllException 
     */
    public void deleteFromPlaylist(Playlist playlist) throws MTBllException
    {
        try {
            playlistSongList.clear();
            mtmanager.deleteFromPlaylist(playlist);
        } catch (MTBllException ex) {
            throw new MTBllException("Could not delete playlist from PlaylistSongTable");
        }
    }
    
    /**
     * Calls the method removeSongFromPlaylist from the MyTunesManager class
     * Also removes the currently selected song from the Observable Arraylist
     * @param selectedPlaylist The playlist selected
     * @param selectedSong the song getting removed from the selected playlist
     * @throws MTBllException 
     */
    public void removeSongFromPlaylist(Playlist selectedPlaylist, Song selectedSong) throws MTBllException
    {
        try {
            playlistSongList.remove(selectedSong);
            mtmanager.removeSongFromPlaylist(selectedPlaylist, selectedSong);
        } catch (MTBllException ex) {
            throw new MTBllException("Could not delete song from playlist");
        }
    }
    
    /**
     * Calls the method deleteSongFromTable from the MyTunesManager class
     * @param song the song getting deleted from the Song table
     * @throws MTBllException 
     */
    public void deleteSongFromTable(Song song) throws MTBllException {
        try {
            mtmanager.deleteSongFromTable(song);
        } catch (MTBllException ex) {
            throw new MTBllException("Could not delete song from table");
        }
    }
    
    /**
     * Calls the method moveSong from the MyTunesManager class
     * It also swaps the two songs position in the Observable Arraylist so the
     * swapping of songs both happens in the list and database
     * 
     * @param songGettingMoved the song getting moved in the playlist
     * @param songAffected the song affected by the other songs movement
     * @param songGettingMovedLocation the index of the song getting moved in
     * the list
     * @param songAffectedLocation the index of the song affected in the list
     * @param playlistId the id of the playlist in which the movement occures
     * @throws MTBllException 
     */
    public void moveSong(Song songGettingMoved, Song songAffected, int songGettingMovedLocation, int songAffectedLocation, int playlistId) throws MTBllException {
        try
        {
            mtmanager.moveSong(songGettingMoved, songAffected, playlistId);
            Collections.swap(playlistSongList, songGettingMovedLocation, songAffectedLocation);
        } catch (MTBllException ex)
        {
            throw new MTBllException("Could not move song");
        }
    }
    
}

