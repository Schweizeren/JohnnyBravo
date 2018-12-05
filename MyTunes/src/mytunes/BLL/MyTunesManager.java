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
import mytunes.DAL.PlaylistDAO;
import mytunes.DAL.SongDAO;
import mytunes.DAL.SongMetaData;
import org.farng.mp3.TagException;

import mytunes.DAL.PlaylistDAO;
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

    private SongMetaData smd;
    private SongDAO sdao;
    private PlaylistDAO pdao;

    public MyTunesManager() throws MTBllException
    {
        try {
        smd = new SongMetaData();
        sdao = new SongDAO();
        pdao = new PlaylistDAO();
        }catch(IOException ex) {
            throw new MTBllException("Could not connect to the DAL layer.");
        }
    }

    public String getSongTitle(String filepath)
    {
        try
        {
            return smd.getSongTitle(filepath);
        } catch (MTDalException ex)
        {
            Logger.getLogger(MyTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getGenre(String filepath)
    {
        try
        {
            return smd.getGenre(filepath);
        } catch (MTDalException ex)
        {
            Logger.getLogger(MyTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getAuthor(String filepath)
    {
        try
        {
            return smd.getAuthor(filepath);
        } catch (MTDalException ex)
        {
            Logger.getLogger(MyTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int getDurationInSec(String filepath)
    {
        try
        {
            return smd.getDurationInSec(filepath);
        } catch (MTDalException ex)
        {
            Logger.getLogger(MyTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public Song createSong(String title, int duration, String author, String genre, String filepath)
    {
        try
        {
            return sdao.createSong(title, duration, author, genre, filepath);
        } catch (MTDalException ex)
        {
            Logger.getLogger(MyTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Song> getAllSongs() throws MTBllException
    {
        try
        {
            return sdao.getAllSongs();
        } catch (IOException ex)
        {
            throw new MTBllException("Could not read all songs.");
        }

    }

    public void deleteSong(Song song)
    {
        try
        {
            sdao.deleteSong(song);
        } catch (IOException ex)
        {
            Logger.getLogger(MyTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateSong(Song song)
    {
        try
        {
            sdao.updateSong(song);
        } catch (IOException ex)
        {
            Logger.getLogger(MyTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Song getSong(int id)
    {
        try
        {
            return sdao.getSong(id);
        } catch (IOException ex)
        {
            Logger.getLogger(MyTunesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Playlist createPlaylist(String name) throws SQLException
    {
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

    public void updatePlaylist(Playlist playlist)
    {
        pdao.updatePlaylist(playlist);
    }
}
