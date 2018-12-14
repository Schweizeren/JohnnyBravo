/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.DAL;

import java.io.IOException;
import java.util.List;
import mytunes.be.Song;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import mytunes.DAL.exception.MTDalException;

/**
 *
 * @author J
 */
public class SongDAO
{

    private final ConnectionDAO cb;

    /**
     * Constructor of the songDAO class. Instansiate the connection to the
     * mytunes database
     *
     * @throws IOException
     */
    public SongDAO() throws IOException
    {
        cb = new ConnectionDAO();
    }

    /**
     * Gets all rows from the song table and uses a resultset to make song ojbects
     * of each row
     *
     * @return an arraylist containing all of the created song objects
     * @throws MTDalException
     */
    public List<Song> getAllSongs() throws MTDalException
    {

        List<Song> songs = new ArrayList<>();

        try (Connection con = cb.getConnection())
        {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Song;");
            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                int duration = rs.getInt("duration");
                String author = rs.getString("author");
                String genre = rs.getString("genre");
                String filepath = rs.getString("filepath");
                Song song = new Song(id, title, duration, author, genre, filepath);
                songs.add(song);

            }
        } catch (SQLException ex)
        {
            throw new MTDalException("Could not read all songs.", ex);
        }
        return songs;

    }

    /**
     * Inserts a song into the Song table and uses the different paramteres as
     * the songs information
     * The Id of the song is auto genereated
     *
     * @param title the title of the song
     * @param duration the songs duration in seconds
     * @param author the artist of the song
     * @param genre the genre of the song
     * @param filepath the songs filepath on the specific computer it was
     * created on
     * @return a song object with the different parameters
     * @throws MTDalException
     */
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

    /**
     * Deletes a song from the Song table. Specifically uses the Id of the song
     * getting deleted
     *
     * @param song the song getting deleted
     * @throws MTDalException
     */
    public void deleteSong(Song song) throws MTDalException
    {
        try (Connection con = cb.getConnection())
        {
            Statement statement = con.createStatement();
            String sql = "DELETE FROM Song WHERE id = " + song.getId() + ";";
            statement.executeUpdate(sql);
        } catch (SQLException ex)
        {
            throw new MTDalException("Could not delete song.", ex);
        }
    }

    /**
     * Updates the information of an existing song in the Song table
     *
     * @param song The song getting updated
     * @throws MTDalException
     */
    public void updateSong(Song song) throws MTDalException
    {
        String title = song.getTitle();
        String author = song.getArtist();
        String genre = song.getGenre();
        int id = song.getId();
        try (Connection con = cb.getConnection())
        {
            String sql = "UPDATE Song SET title = ?, author = ?, genre = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, genre);
            ps.setInt(4, id);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex)
        {
            throw new MTDalException("Could not update song.", ex);
        }

    }
}
