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
    private int songCount;

    public Playlist(String name, int id, int songCount) {
        this.name = name;
        this.id = id;
        this.songCount = songCount;
    }

    public List<Song> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<Song> playlist) {
        this.playlist = playlist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getSongCount() {
        return songCount;
    }

    public void setSongCount(int songCount) {
        this.songCount = songCount;
    }

    @Override
    public String toString() {
        return "Playlist{" + "Name=" + name + ", Number of songs in playlist=" + songCount + '}';
    }

    
    
    
    
    
    
}
