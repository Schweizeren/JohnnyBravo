/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.BLL.Model;

import java.sql.SQLException;
import java.util.List;
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
            
    public PlaylistSongModel() throws MTBllException
    {
        mtmanager = new MyTunesManager();
    }
    
    public List<Song> getPlaylistSongs(int id)
    {
        return mtmanager.getPlaylistSongs(id);
    }
    
    public Song addToPlaylist(Playlist playlist, Song song) throws SQLException
    {
        return mtmanager.addToPlaylist(playlist, song);
    }
    
    public int getNewestSongInPlaylist(int id)
    {
        return mtmanager.getNewestSongInPlaylist(id);
    }
    
    public void deleteFromPlaylistSongsEverything(Song songToDelete)
    {
        mtmanager.deleteFromPlaylistSongsEverything(songToDelete);
    }
    
    public void deleteFromPlaylistSongsEverything(Playlist play)
    {
        mtmanager.deleteFromPlaylistSongsEverything(play);
    }
    
    public void removeSongFromPlaylist(Playlist selectedItem, Song selectedSong)
    {
        mtmanager.removeSongFromPlaylist(selectedItem, selectedSong);
    }

    public void addToPlaylist() 
    {
        
    }
}
