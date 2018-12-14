/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.DAL;


import java.util.List;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import mytunes.DAL.exception.MTDalException;
import mytunes.be.Playlist;

/**
 *
 * @author J_Wan
 */
public class PlaylistDAO 
{
    private final ConnectionDAO cb;
    
    /**
     * The constructor of the PlaylistDAO class. Initialize the conntection to the database
     */
    public PlaylistDAO()
    {
        cb = new ConnectionDAO();
    }
    
    /**
     * Gets all rows from the Playlist table and uses a resultset to make objects
     * of each row
     * 
     * @return an arraylist containing all created playlist objects
     * @throws MTDalException 
     */
    public  List<Playlist> getAllPlaylist() throws MTDalException
    {
        List<Playlist> playlists = new ArrayList<>();
        
        try (Connection con = cb.getConnection())
        {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Playlist");
            while(rs.next())
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Playlist playlist = new Playlist(id,name);
                playlists.add(playlist);
                
            }
        } catch (SQLException ex)
        {
            throw new MTDalException("Could not read all playlists.", ex);
        }
        return playlists;
    }
    
    /**
     * Inserts a playlist into the playlist table. The Id is auto generated
     * 
     * @param name the title of the playlist
     * @return the created playlist as an object
     * @throws MTDalException 
     */
    public Playlist createPlaylist(String name) throws MTDalException
    {
        String sql = "INSERT INTO Playlist (name) VALUES(?);";
        
        try (Connection con = cb.getConnection())
        {
            PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            st.setString(1, name);
            
            int rowsAffected = st.executeUpdate();
            
            ResultSet rs = st.getGeneratedKeys();
            int id = 0;
            if (rs.next())
            {
                id = rs.getInt(1);
            }
            Playlist playlist = new Playlist(id,name);
            return playlist;
        } catch (SQLServerException ex)
        {
            throw new MTDalException("Could not connect to server", ex);
        } catch (SQLException ex)
        {
            throw new MTDalException("Could not create playlist.", ex);
        }
    }
    /**
     * Deletes a playlist from the Playlist table
     * Specifically uses the id of the playlist to delete the playlist
     * 
     * @param playlist the playlist getting deleted
     * @throws MTDalException 
     */
    public void deletePlaylist(Playlist playlist) throws MTDalException
    {
        try (Connection con = cb.getConnection())
        {
            Statement statement = con.createStatement();
            String sql = "DELETE FROM Playlist WHERE id = " + playlist.getId() + ";";
            statement.executeUpdate(sql);
        }
        catch (SQLException ex)
        {
            throw new MTDalException("Could not delete playlist.", ex);
        }
    }
    
    public Playlist getPlaylist(int id) throws MTDalException
    {
        try (Connection con = cb.getConnection())
        {
            Statement statement = con.createStatement();
            String sql = "SELECT FROM Playlist WHERE id = " + id + ";";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next())
            {
                int playlistId = rs.getInt("id");
                String name = rs.getString("name");
                Playlist playlist = new Playlist(playlistId, name);
                return playlist;
            }
        }
        catch (SQLException ex)
        {
            throw new MTDalException("Could not get specific playlist.", ex);
        }
        return null;
    }
    
   /**
    * Updates the information of an existing playlsit in the Playlist table
    * @param playlist the playlist getting updated
    * @throws MTDalException 
    */
    public void updatePlaylist(Playlist playlist) throws MTDalException
    {
        String name = playlist.getName();
        int id = playlist.getId();
        
        try (Connection con = cb.getConnection()) {
            Statement statement = con.createStatement();
            String sql = "UPDATE Playlist SET name = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, id);
            
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException ex)
        {
           throw new MTDalException("Could not update selected playlist", ex); 
        }
    }
    
}
