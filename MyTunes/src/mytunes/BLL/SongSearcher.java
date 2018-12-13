/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.BLL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import mytunes.BLL.exception.MTBllException;
import mytunes.DAL.SongDAO;
import mytunes.DAL.exception.MTDalException;
import mytunes.be.Song;

/**
 *
 * @author bonde
 */
public class SongSearcher
{
    /**
     * Takes a list containing  all songs and checks each song 
     * if their title or artist matches a search word. If a song does match 
     * with the search then they get added to a new list
     * @param searchBase the list of songs getting searched
     * @param query the searched word which get matched with each of every songs
     * title and artist
     * @return a list of songs that has matched with the searched word
     * @throws MTBllException 
     */
    public List<Song> searchSongs(List<Song> searchBase, String query) throws MTBllException
    {
        SongDAO songdao;
        try
        {
            songdao = new SongDAO();
            List<Song> searchList = new ArrayList<>();
            List<Song> songList = songdao.getAllSongs();
            if (query.isEmpty())
            {
                searchList = songdao.getAllSongs();
            } else
            {
                for (Song song : songList)
                {
                    if (song.getTitle().toLowerCase().contains(query) || song.getArtist().toLowerCase().contains(query))
                    {
                        searchList.add(song);
                    }
                }
            }

            return searchList;
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not connect to the DAL layer.");
        } catch (IOException ex)
        {
            throw new MTBllException("Could not connect to the DAL layer");
        }

    }
}
