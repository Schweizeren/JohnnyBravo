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
    
    public PlaylistSongModel() throws MTBllException
    {
        playlistSongList = FXCollections.observableArrayList();
        mtmanager = new MyTunesManager();
    }
    
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
            throw new MTBllException("Could not get songs in playl");
        }
    }
    
    public Song addToPlaylist(Playlist playlist, Song song) throws MTBllException
    {
        try {
            playlistSongList.add(song);
            return mtmanager.addToPlaylist(playlist, song);
        } catch (MTBllException ex) {
            throw new MTBllException("Could not add song to playlist");
        }
    }
    
    public int getNewestSongInPlaylist(int id) throws MTBllException
    {
        try {
            return mtmanager.getNewestSongInPlaylist(id);
        } catch (MTBllException ex) {
            throw new MTBllException("Could not get newest ID in playlist");
        }
    }
    
    
    public void deleteFromPlaylist(Playlist playlist) throws MTBllException
    {
        try {
            playlistSongList.clear();
            mtmanager.deleteFromPlaylist(playlist);
        } catch (MTBllException ex) {
            throw new MTBllException("Could not delete all songs from SQL database");
        }
    }
    
    public void removeSongFromPlaylist(Playlist selectedItem, Song selectedSong) throws MTBllException
    {
        try {
            playlistSongList.remove(selectedSong);
            mtmanager.removeSongFromPlaylist(selectedItem, selectedSong);
        } catch (MTBllException ex) {
            throw new MTBllException("Could not delete song from playlist");
        }
    }
    
    public void deleteSongFromTable(Song song) throws MTBllException {
        try {
            mtmanager.deleteSongFromTable(song);
        } catch (MTBllException ex) {
            throw new MTBllException("Could not delete song from table");
        }
    }
    
    public void moveSong(int locationGettingMoved, int locationAffected, int playlistId) throws MTBllException {
        try
        {
            mtmanager.moveSong(locationGettingMoved, locationAffected, playlistId);
            Collections.swap(playlistSongList, locationGettingMoved - 1, locationAffected - 1);
        } catch (MTBllException ex)
        {
            throw new MTBllException("Could not move song");
        }
    }
    
}

