/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.BLL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import mytunes.DAL.SongDAO;
import mytunes.be.Song;

/**
 *
 * @author bonde
 */
public class SongSearcher
{
    public List<Song> searchSongs(List<Song> searchBase ,String query) {
        SongDAO songdao = new SongDAO();
        List<Song> searchList = new ArrayList<>();
        try {
            List<Song> songList = songdao.getAllSongs();
            if (query.isEmpty()) {
                searchList = songdao.getAllSongs();
            } else {
            for (Song song : songList) {
                if (song.getTitle().toLowerCase().contains(query) || song.getArtist().toLowerCase().contains(query)) {
                    searchList.add(song);
                }
            }
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException("Could not find song");
        }
        return searchList;
    }
}