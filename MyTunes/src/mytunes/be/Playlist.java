/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

import java.util.List;

/**
 *
 * @author bonde
 */
public class Playlist
{
    private List<Song> playlist;
    private String name;
    private int id;

    /**
     * Constructor of the Playlist class
     * @param id the id of the playlist
     * @param name the playlists title
     */
    public Playlist(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Returns the list of songs a playlist contains
     * @return a list of songs
     */
    public List<Song> getPlaylist() {
        return playlist;
    }

    /**
     * Sets a list of songs to the playlist
     * @param playlist the list getting assigned to the playlist
     */
    public void setPlaylist(List<Song> playlist) {
        this.playlist = playlist;
    }

    /**
     * Gets the title of the playlist
     * @return the title of the playlist
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the title of the playlist
     * @param name the title gettting assigned to the playlist
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the id of the playlist
     * @return the playlists id
     */
    public int getId() {
        return id;
    }
    
}