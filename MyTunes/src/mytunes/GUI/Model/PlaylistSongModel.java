/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.GUI.Model;

import java.sql.SQLException;
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
    
    public ObservableList<Song> getPlaylistSongs(int id)
    {
        playlistSongList.clear();
        List<Song> listofSongs = mtmanager.getPlaylistSongs(id);
        for (Song song : listofSongs)
        {
            playlistSongList.add(song);
        }
        
        return playlistSongList;
    }
    
    public Song addToPlaylist(Playlist playlist, Song song) throws SQLException
    {
        playlistSongList.add(song);
        return mtmanager.addToPlaylist(playlist, song);
    }
    
    public int getNewestSongInPlaylist(int id)
    {
        return mtmanager.getNewestSongInPlaylist(id);
    }
    
    public void deleteFromPlaylist(Playlist playlist) throws SQLException
    {
        mtmanager.deleteFromPlaylist(playlist);
    }
    
    public void removeSongFromPlaylist(Playlist selectedItem, Song selectedSong)
    {
        playlistSongList.remove(selectedSong);
        mtmanager.removeSongFromPlaylist(selectedItem, selectedSong);
    }
    
    
}

