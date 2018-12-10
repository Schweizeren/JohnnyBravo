/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.util.List;
import mytunes.be.Song;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mytunes.DAL.exception.MTDalException;
import mytunes.be.Playlist;

/**
 *
 * @author J_Wan
 */
public class PlaylistSongDAO {

    private final ConnectionDAO cb;

    public PlaylistSongDAO() 
    {
        cb = new ConnectionDAO();
    }

    public List<Song> getPlaylistSongs(int id) throws MTDalException
    {
        List<Song> playlistsongs = new ArrayList<>();
        try (Connection con = cb.getConnection()) {
            String query = "SELECT * FROM PlaylistSong INNER JOIN Song ON SongID = Song.id WHERE PlaylistSong.PlaylistID = ? ORDER by LocationInListID asc";
            PreparedStatement preparedSt = con.prepareStatement(query);
            preparedSt.setInt(1, id);
            ResultSet rs = preparedSt.executeQuery();
            while (rs.next()) {
                Song song = new Song(rs.getInt("id"), rs.getString("title"), rs.getInt("duration"), rs.getString("author"), rs.getString("genre"), rs.getString("filepath"));
                song.setLocationInList(rs.getInt("LocationInListID"));
                playlistsongs.add(song);
            }
            return playlistsongs;
        } catch (SQLServerException ex) {
            throw new MTDalException("Could not connect to the SQL server.", ex);
        } catch (SQLException ex) {
            throw new MTDalException("Could not get songs in playlist.", ex);
        }
    }

    public Song addToPlaylist(Playlist playlist, Song song) throws MTDalException 
    {
        String sql = "INSERT INTO PlaylistSong(PlaylistID,SongID,LocationInListID) VALUES (?,?,?)";
        int Id;
        try (Connection con = cb.getConnection()) {
            PreparedStatement preparedst = con.prepareStatement(sql);
            Id = getNewestIdInPlaylist(playlist.getId());
            preparedst.setInt(1, playlist.getId());
            preparedst.setInt(2, song.getId());
            preparedst.setInt(3, Id);
            preparedst.addBatch();
            preparedst.executeBatch();
            song.setLocationInList(Id);
            return song;
        } catch (SQLServerException ex) {
            throw new MTDalException("Could not connect to the SQL server.", ex);
        } catch (SQLException ex) {
            throw new MTDalException("Could not add a song to the playlist.", ex);
        }
    }

    public int getNewestIdInPlaylist(int id) throws MTDalException
    {
        int newestID = 0;
        try (Connection con = cb.getConnection()) {
            String query = "SELECT TOP(1) * FROM PlaylistSong WHERE PlaylistID = ? ORDER by locationInListID desc";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, id);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()) {
                newestID = rs.getInt("locationInListID");
            }
            System.out.println(newestID);
            return newestID + 1;
        } catch (SQLServerException ex) {
            throw new MTDalException("Could not connect to the SQL server.", ex);
        } catch (SQLException ex) {
            throw new MTDalException("Could not get the newest ID in playlist.", ex);
        }
    }

    
    public void deleteFromPlaylist(Playlist playlist) throws MTDalException
    {
        try (Connection con = cb.getConnection())
        {
            String query = "DELETE from PlaylistSong WHERE PlaylistID = ?";
            PreparedStatement preparedSt = con.prepareStatement(query);
            preparedSt.setInt(1, playlist.getId());
            preparedSt.execute();
        } catch (SQLServerException ex) {
            throw new MTDalException("Could not connect to the SQL server.", ex);
        } catch (SQLException ex) {
            throw new MTDalException("Could delete song from playlist.", ex);
        }
    }

    public void removeSongFromPlaylist(Playlist selectedItem, Song selectedSong) throws MTDalException
    {
        try (Connection con = cb.getConnection()) {
            String query = "DELETE from PlaylistSong WHERE PlaylistID = ? AND SongID = ? AND locationInListID = ?";
            PreparedStatement preparedSt = con.prepareStatement(query);
            preparedSt.setInt(1, selectedItem.getId());
            preparedSt.setInt(2, selectedSong.getId());
            preparedSt.setInt(3, selectedSong.getLocationInList());
            preparedSt.execute();
        } catch (SQLServerException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
    }
    
    public void insertSongToPlaylist (int songID, int playlistID) throws MTDalException
    {
        try (Connection con = cb.getConnection())
        {
            String query = "INSERT INTO PlaylistSong(PlaylistID,SongID,locationInListID) VALUES (?,?,?)";
            PreparedStatement preparedst = con.prepareStatement(query);
            preparedst.setInt(1, playlistID);
            preparedst.setInt(2, songID);
        } catch (SQLServerException ex) {
            throw new MTDalException("Could not connect to the SQL server.", ex);
        } catch (SQLException ex) {
            throw new MTDalException("Could not insert song to playlistSong table.", ex);
        }
    }
    
    public void deleteSongFromTable(Song song) {
        
    }

}
