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

    public List<Song> getPlaylistSongs(int id) 
    {
        List<Song> playlistsongs = new ArrayList<>();
        try (Connection con = cb.getConnection()) {
            String query = "SELECT * FROM PlaylistSong INNER JOIN Song ON PlaylistSongID = Song.id WHERE PlaylistSong.PlaylistID = ? ORDER by LocationInListID desc";
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
            System.out.println(ex);
            return null;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    public Song addToPlaylist(Playlist playlist, Song song) throws SQLException 
    {
        String sql = "INSERT INTO PlaylistSong(PlaylistID,SongID,LocationInListID) VALUES (?,?,?)";
        int Id = -1;
        try (Connection con = cb.getConnection()) {
            PreparedStatement preparedst = con.prepareStatement(sql);
            Id = getNewestSongInPlaylist(playlist.getId()) + 1;
            preparedst.setInt(1, playlist.getId());
            preparedst.setInt(2, song.getId());
            preparedst.setInt(3, Id);
            preparedst.addBatch();
            preparedst.executeBatch();
            song.setLocationInList(Id);
            return song;
        } catch (SQLServerException ex) {
            System.out.println(ex);
            return null;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    public int getNewestSongInPlaylist(int id)
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
            return newestID;
        } catch (SQLServerException ex) {
            System.out.println(ex);
            return newestID;
        } catch (SQLException ex) {
            System.out.println(ex);
            return newestID;
        }
    }

    public void deleteFromPlaylistSongsEverything(Song songToDelete)
    {
        try (Connection con = cb.getConnection()) {
            String query = "DELETE from PlaylistSong WHERE SongID = ?";
            PreparedStatement preparedSt = con.prepareStatement(query);
            preparedSt.setInt(1, songToDelete.getId());
            preparedSt.execute();
        } catch (SQLServerException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void deleteFromPlaylistSongsEverything(Playlist play) 
    {
        try (Connection con = cb.getConnection()) {
            String query = "DELETE from PlaylistSong WHERE PlaylistID = ?";
            PreparedStatement preparedSt = con.prepareStatement(query);
            preparedSt.setInt(1, play.getId());
            preparedSt.execute();
        } catch (SQLServerException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void removeSongFromPlaylist(Playlist selectedItem, Song selectedSong) 
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
    
    public void insertSongToPlaylist (int songID, int playlistID) throws SQLServerException, SQLException
    {
        try (Connection con = cb.getConnection())
        {
            String query = "INSERT INTO PlaylistSong(PlaylistID,SongID,locationInListID) VALUES (?,?,?)";
            PreparedStatement preparedst = con.prepareStatement(query);
            preparedst.setInt(1, playlistID);
            preparedst.setInt(2, songID);
            
            
        }
    }

}
