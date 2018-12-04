/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.BLL;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mytunes.DAL.PlaylistDAO;
import mytunes.DAL.SongDAO;
import mytunes.DAL.SongMetaData;
import org.farng.mp3.TagException;

import mytunes.DAL.PlaylistDAO;
import mytunes.DAL.SongMetaData;
import mytunes.be.Playlist;
import mytunes.be.Song;

/**
 *
 * @author bonde
 */
public class MyTunesManager
{
    private SongMetaData smd;
    private SongDAO sdao;
    private PlaylistDAO pdao;
    public MyTunesManager() {
        smd = new SongMetaData();
        sdao = new SongDAO();
        pdao = new PlaylistDAO();
        
    }
    
    public String getSongTitle(String filepath) {
        try
        {
            return smd.getSongTitle(filepath);
        } catch (IOException ex)
        {
            Logger.getLogger(MyTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TagException ex)
        {
            Logger.getLogger(MyTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String getGenre(String filepath) {
        try
        {
            return smd.getGenre(filepath);
        } catch (IOException ex)
        {
            Logger.getLogger(MyTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TagException ex)
        {
            Logger.getLogger(MyTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String getAuthor(String filepath) {
        try
        {
            return smd.getAuthor(filepath);
        } catch (IOException ex)
        {
            Logger.getLogger(MyTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TagException ex)
        {
            Logger.getLogger(MyTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    

    public int getDurationInSec(String filepath) {
        return smd.getDurationInSec(filepath);
    }
    
    public Song createSong(String title, int duration, String author, String genre, String filepath) {
        try
        {
            return sdao.createSong(title, duration, author, genre, filepath);
        } catch (SQLException ex)
        {
            Logger.getLogger(MyTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<Song> getAllSongs() throws IOException {
        try
        {
            return sdao.getAllSongs();
        } catch (IOException ex)
        {
            throw new IOException("Could not show all movies cause: " + ex.getMessage());
        }
        
    }
    
    public void deleteSong(Song song) {
        sdao.deleteSong(song);
    }
    
    public void updateSong(Song song) {
        sdao.updateSong(song);
    }
    
    public Song getSong(int id) {
        return sdao.getSong(id);
    }
    
    public Playlist createPlaylist(String name) throws SQLException {
    try
        {
            return pdao.createPlaylist(name);
        } catch (SQLException ex)
        {
            Logger.getLogger(MyTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<Playlist> getAllPlaylist()
    {
        return pdao.getAllPlaylist();
    }
    
    public void deletePlaylist(Playlist playlist) throws SQLException
    {
        pdao.deletePlaylist(playlist);
    }
    public Playlist getPlaylist(int id) throws SQLException
    {
        return pdao.getPlaylist(id);
    }
}

