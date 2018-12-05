    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.util.List;
import mytunes.be.Song;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mytunes.DAL.exception.MTDalException;

/**
 *
 * @author J
 */
public class SongDAO
{   

    private final ConnectionDAO cb;
    
    public SongDAO() throws IOException {
        cb = new ConnectionDAO();
    }
    
    public List<Song> getAllSongs() throws IOException
    {
    
    List<Song> songs = new ArrayList<>();
    
    try (Connection con = cb.getConnection())
    {
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM Song;");
        while(rs.next())
        {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            int duration = rs.getInt("duration");
            String author = rs.getString("author");
            String genre = rs.getString("genre");
            String filepath = rs.getString("filepath");
            Song song = new Song(id,title,duration,author,genre,filepath);
            songs.add(song);
                    
        }
    }
    catch (SQLException ex)
    {
        ex.printStackTrace();
    }
    return songs;
        
    }
    
    public Song createSong(String title, int duration, String author, String genre, String filepath) throws MTDalException
    {
        String sql = "INSERT INTO Song (title,duration, author, genre, filepath) VALUES(?,?,?,?,?);";
        
        try (Connection con = cb.getConnection())
        {
            PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            st.setString(1, title);
            st.setInt(2, duration);
            st.setString(3, author);
            st.setString(4, genre);
            st.setString(5, filepath);
            
            int rowsAffected = st.executeUpdate();
            
            ResultSet rs = st.getGeneratedKeys();
            int id = 0;
            if (rs.next())
            {
                id = rs.getInt(1);
            }
            Song song = new Song(id, title, duration, author, genre, filepath);
            return song;
        } catch (SQLException ex)
        {
            throw new MTDalException("Could not create song.", ex);
        }
    }
    
    public void deleteSong(Song song) throws IOException
    {
        try(Connection con = cb.getConnection()) {
            Statement statement = con.createStatement();
            String sql = "DELETE FROM Song WHERE id = " + song.getId() + ";";
            statement.executeUpdate(sql);
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void updateSong(Song song) throws IOException
    {
        String title = song.getTitle();
        String author = song.getArtist();
        String genre = song.getGenre();
        int id = song.getId();
        try (Connection con = cb.getConnection()) {
            String sql = "UPDATE Song SET title = ?, author = ?, genre = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, genre);
            ps.setInt(4, id);
            
            ps.executeUpdate();
            ps.close();
            
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }
    
    public Song getSong(int id) throws IOException
    {
        try (Connection con = cb.getConnection()) {
            Statement statement = con.createStatement();
            String sql = "SELECT FROM Song WHERE id = " + id + ";";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()) {
                int songId = rs.getInt("id");
                String title = rs.getString("title");
                int duration = rs.getInt("duration");
                String artist = rs.getString("author");
                String genre = rs.getString("genre");
                String filepath = rs.getString("filepath");
                Song song = new Song(songId, title, duration, artist, genre, filepath);
                return song;
            }
            
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
