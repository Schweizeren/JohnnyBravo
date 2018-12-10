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
import mytunes.BLL.exception.MTBllException;
import mytunes.DAL.SongDAO;
import mytunes.DAL.PlaylistDAO;
import mytunes.DAL.PlaylistSongDAO;
import mytunes.DAL.SongMetaData;
import mytunes.DAL.exception.MTDalException;
import mytunes.be.Playlist;
import mytunes.be.Song;

/**
 *
 * @author bonde
 */
public class MyTunesManager
{
    private PlaylistSongDAO psd;
    private SongMetaData smd;
    private SongDAO sdao;
    private PlaylistDAO pdao;

    public MyTunesManager() throws MTBllException
    {
        try {
        psd = new PlaylistSongDAO();
        smd = new SongMetaData();
        sdao = new SongDAO();
        pdao = new PlaylistDAO();
        }catch(IOException ex) {
            throw new MTBllException("Could not connect to the DAL layer.");
        }
    }

    public String getSongTitle(String filepath) throws MTBllException
    {
        try
        {
            return smd.getSongTitle(filepath);
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not get the title of the song. " + ex.getMessage());
        }
    }

    public String getGenre(String filepath) throws MTBllException
    {
        try
        {
            return smd.getGenre(filepath);
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not get genre of the song. " + ex.getMessage());
        }
    }

    public String getAuthor(String filepath) throws MTBllException
    {
        try
        {
            return smd.getAuthor(filepath);
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not get artist of song. " + ex.getMessage());
        }
        
    }

    public int getDurationInSec(String filepath) throws MTBllException
    {
        try
        {
            return smd.getDurationInSec(filepath);
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not get the duration in seconds. " + ex.getMessage());
        }
    }

    public Song createSong(String title, int duration, String author, String genre, String filepath) throws MTBllException
    {
        try
        {
            return sdao.createSong(title, duration, author, genre, filepath);
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not creat song. " + ex.getMessage());
        }
    }

    public List<Song> getAllSongs() throws MTBllException
    {
        try
        {
            return sdao.getAllSongs();
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not read all songs. " + ex.getMessage());
        }

    }

    public void deleteSong(Song song) throws MTBllException
    {
        try
        {
            
            sdao.deleteSong(song);
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not delete song." + ex.getMessage());
        }
    }

    public void updateSong(Song song) throws MTBllException
    {
        try
        {
            sdao.updateSong(song);
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not update song. " + ex.getMessage());
        }
    }

    public Song getSong(int id) throws MTBllException
    {
        try
        {
            return sdao.getSong(id);
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not update song. " + ex.getMessage());
        }
    }

    public Playlist createPlaylist(String name) throws MTBllException
    {
        try
        {
            return pdao.createPlaylist(name);
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not create playlist");
        }
    }

    public List<Playlist> getAllPlaylist() throws MTBllException
    {
        try
        {
            return pdao.getAllPlaylist();
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not read all playlists");
        }
    }

    public void deletePlaylist(Playlist playlist) throws MTBllException
    {
        try
        {
            pdao.deletePlaylist(playlist);
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not delete playlist");
        }
    }

    public Playlist getPlaylist(int id) throws MTBllException
    {
        try
        {
            return pdao.getPlaylist(id);
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not get selected playlist");
        }
    }

    public void updatePlaylist(Playlist playlist) throws MTBllException
    {
        try
        {
            pdao.updatePlaylist(playlist);
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not update playlist");
        }
    }
    
    public List<Song> getPlaylistSongs(int id) throws MTBllException
    {
        try {
            return psd.getPlaylistSongs(id);
        } catch (MTDalException ex) {
            throw new MTBllException("Could not get the songs in playlist");
        }
    }
    
    public Song addToPlaylist(Playlist playlist, Song song) throws MTBllException
    {
        try {
            return psd.addToPlaylist(playlist, song);
        } catch (MTDalException ex) {
            throw new MTBllException("Could not add song to playlist");
        }
    }
    
    public int getNewestSongInPlaylist(int id) throws MTBllException
    {
        try {
            return psd.getNewestIdInPlaylist(id);
        } catch (MTDalException ex) {
            throw new MTBllException("Could not get newest ID in playlist");
        }
    }
    
    public void removeSongFromPlaylist(Playlist selectedItem, Song selectedSong) throws MTBllException
    {
        try {
            psd.removeSongFromPlaylist(selectedItem, selectedSong);
        } catch (MTDalException ex) {
            throw new MTBllException("Could not delete song from playlist");
        }
    }
    
    public void deleteFromPlaylist(Playlist playlist) throws MTBllException
    {
        try {
            psd.deleteFromPlaylist(playlist);
        } catch (MTDalException ex) {
            throw new MTBllException("Could not delete all songs from SQL database");
        }
    }
    
    public void insertSongToPlaylist ()
    {
        
    }
    
    public void deleteSongFromTable(Song song) throws MTBllException{
        try {
            psd.deleteSongFromTable(song);
        } catch (MTDalException ex) {
            throw new MTBllException("Could not delete song from table");
        }
    }

    
}
