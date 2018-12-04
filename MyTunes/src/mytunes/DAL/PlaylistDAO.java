/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.DAL;

import java.sql.Connection;
import java.io.IOException;
import java.util.List;
import mytunes.be.Song;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mytunes.be.Playlist;

/**
 *
 * @author J_Wan
 */
public class PlaylistDAO 
{
    private final ConnectionDAO cb;
    
    public PlaylistDAO()
    {
        cb = new ConnectionDAO();
    }
    
    public List<Playlist> getPlaylist()
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
            ex.printStackTrace();
        }
        return playlists;
    }
    
    public Playlist createPlaylist(String name) throws SQLException
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
        }
    }
}
