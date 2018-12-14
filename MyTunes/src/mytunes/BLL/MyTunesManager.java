/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.BLL;

import java.io.IOException;
import java.util.List;
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

    /**
     * The constructor of the MyTunesManager class. Instanciates the different
     * DAL layer classes as objects
     * @throws MTBllException 
     */
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

    /**
     * Uses the getSongTitle method from the SongMetaData class to get the title
     * of a song
     * @param filepath the filepath of the mp3 file
     * @return a string containing the songs title
     * @throws MTBllException 
     */
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

    /**
     * Uses the getGenre method from the SongMetaData class
     * @param filepath filepath of the mp3 file
     * @return a string containing the genre of a song
     * @throws MTBllException 
     */
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

    /**
     * Uses the getAuthor method in the SongMetaData class to get the artist
     * of a song
     * 
     * @param filepath the filepath of the mp3 file
     * @return a string containing the artist of the song
     * @throws MTBllException 
     */
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

    /**
     * Uses the getDurationInSec method from the SongMetaData class
     * @param filepath the filepath of the mp3 file
     * @return returns an integer containing the songs duration in seconds
     * @throws MTBllException 
     */
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

    /**
     * Uses the createSong method from the SongDAO class
     * @param title the title of the song
     * @param duration the duration of the song in seconds
     * @param author the artist of the song
     * @param genre the genre of the song
     * @param filepath the filepath of the song
     * @return a song ojbect
     * @throws MTBllException 
     */
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

    /**
     * Uses the getAllSongs method from the SongDAO class
     * @return a list containing all songs from the Song table
     * @throws MTBllException 
     */
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

    
    /**
     * Uses the deleteSong method from the SongDAO class
     * @param song The song getting deleted
     * @throws MTBllException 
     */
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
    
    /**
     * Calls the updateSong method from the SongDAO Class
     * @param song the song being updated
     * @throws MTBllException 
     */
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

    /**
     * Calls the createPlaylist methods from the PlaylistDAO class
     * @param name the title of the playlist created
     * @return the created playlist
     * @throws MTBllException 
     */
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

    /**
     * Calls the getAllPlaylist method from the PlaylistDAO class
     * @return a list containing all playlists
     * @throws MTBllException 
     */
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

    /**
     * Calls the deletePlaylist method from the PlaylistDAO class
     * @param playlist the playlist getting deleted
     * @throws MTBllException 
     */
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

    /**
     * Calls the method
     * @param id
     * @return
     * @throws MTBllException 
     */
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

    /**
     * Calls the method updatePlaylist method from the PlaylistDAO class
     * @param playlist the playlist getting updated
     * @throws MTBllException 
     */
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
   
    /**
     * Calls the getPlaylistSongs method in the PlaylistSongDAO class
     * @param id the Id of a playlist
     * @return a list of songs in a specific playlist
     * @throws MTBllException 
     */
    public List<Song> getPlaylistSongs(int id) throws MTBllException
    {
        try {
            return psd.getPlaylistSongs(id);
        } catch (MTDalException ex) {
            throw new MTBllException("Could not get the songs in playlist");
        }
    }
    
    /**
     * Calls the addToPlaylist method in the PlaylistSongDAO class
     * @param playlist the playlist getting added to
     * @param song the song getting added to the playlist
     * @return an object of the song which have been added to the playlist
     * @throws MTBllException 
     */
    public Song addToPlaylist(Playlist playlist, Song song) throws MTBllException
    {
        try {
            return psd.addToPlaylist(playlist, song);
        } catch (MTDalException ex) {
            throw new MTBllException("Could not add song to playlist");
        }
    }
    
    /**
     * Calls the removeSongFromPlaylist method in the PlaylistSongDAO class
     * @param selectedPlaylist the selected playlist
     * @param selectedSong the song getting removed from the playlist
     * @throws MTBllException 
     */
    public void removeSongFromPlaylist(Playlist selectedPlaylist, Song selectedSong) throws MTBllException
    {
        try {
            psd.removeSongFromPlaylist(selectedPlaylist, selectedSong);
        } catch (MTDalException ex) {
            throw new MTBllException("Could not delete song from playlist");
        }
    }
    
    /**
     * Calls the deleteFromPlaylist method in the PlaylistSongDAO class
     * @param playlist the playlist getting deleted from the playlistSong table
     * @throws MTBllException 
     */
    public void deleteFromPlaylist(Playlist playlist) throws MTBllException
    {
        try {
            psd.deleteFromPlaylist(playlist);
        } catch (MTDalException ex) {
            throw new MTBllException("Could not delete all songs from SQL database");
        }
    }
    
    /**
     * calls the deleteSongFromTable method in the PlaylistSongDAO class
     * 
     * @param song the song which has been deleted from the Song table and now
     * getting deleted from the PlaylistSong table
     * @throws MTBllException 
     */
    public void deleteSongFromTable(Song song) throws MTBllException{
        try {
            psd.deleteSongFromTable(song);
        } catch (MTDalException ex) {
            throw new MTBllException("Could not delete song from table");
        }
    }
    
    /**
     * Calls the moveSong method from the PlaylistSongDAO class
     * @param locationGettingMoved The location of a song in a playlist getting 
     * moved
     * @param locationAffected a song which location is getting affected by the
     * other songs movement
     * @param playlistId the Id of the playlist where changes of a songs
     * locations occures
     * @throws MTBllException 
     */
    public void moveSong(int locationGettingMoved, int locationAffected, int playlistId) throws MTBllException {
        try
        {
            psd.moveSong(locationGettingMoved, locationAffected, playlistId);
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not move song");
        }
    }
    
}
