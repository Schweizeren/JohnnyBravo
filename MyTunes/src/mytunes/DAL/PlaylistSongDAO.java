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
public class PlaylistSongDAO
{

    private final ConnectionDAO cb;

    public PlaylistSongDAO()
    {
        cb = new ConnectionDAO();
    }

    public List<Song> getPlaylistSongs(int id)
    {
        List<Song> playlistsongs = new ArrayList<>();
        try (Connection con = cb.getConnection())
        {
            String query = "SELECT * FROM PlaylistSong INNER JOIN Song ON PlaylistSongID = Song.id WHERE PlaylistSong.PlaylistID = ? ORDER by LocationInListID desc";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, id);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next())
            {
                Song song = new Song(rs.getInt("id"), rs.getString("title"), rs.getInt("duration"), rs.getString("author"), rs.getString("genre"), rs.getString("filepath"));
                song.setLocationInList (rs.getInt("LocationInListID"));
                playlistsongs.add(song);
            }
            return playlistsongs;   
        }
        catch (SQLServerException ex) 
        {
            System.out.println(ex);
            return null;
        } 
        catch (SQLException ex) 
        {
            System.out.println(ex);
            return null;
        }
    }
    
    
    

}
