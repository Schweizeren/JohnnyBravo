/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.GUI.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.BLL.MyTunesManager;
import mytunes.BLL.exception.MTBllException;
import mytunes.be.Playlist;

/**
 *
 * @author Frederik Jensen
 */
public class PlaylistModel
{
    private ObservableList<Playlist> playlistList;
    private MyTunesManager mtmanager;
    
    /**
     * Constructor of the PlaylistModel class
     * Initializes instances of the MyTunesManager class in the BLL layer
     * Also initializes an ObservableArrayList that contains all playlists 
     * in the database
     * @throws MTBllException 
     */
    public PlaylistModel() throws MTBllException 
    {
        playlistList = FXCollections.observableArrayList();
        mtmanager = new MyTunesManager();
        playlistList.addAll(mtmanager.getAllPlaylist());
    }
    
    /**
     * Calls the createPlaylist method from the MyTunesManager class
     * @param name the title of the playlist getting created
     * @throws MTBllException 
     */
    public void createPlaylist(String name) throws MTBllException
    {
        Playlist playlist = mtmanager.createPlaylist(name);
        playlistList.add(playlist);
        
    }
    
    /**
     * Calls the deletePlaylist method from the MyTunesManager class
     * @param playlist the playlist getting deleted
     * @throws MTBllException 
     */
    public void deletePlaylist(Playlist playlist) throws MTBllException
    {
        mtmanager.deletePlaylist(playlist);
        playlistList.remove(playlist);
    }
    
    /**
     * Returns the Observable ArrayList containing all playlist
     * @return the arraylist containing all playlist
     */
    public ObservableList<Playlist> getAllPlaylist()
    {
        return playlistList;
    }
    
    /**
     * Updates an existing playlist in the Observable list and in the playlist
     * table. When it has been updates it also sets the updated playlist on its
     * index in the list
     * @param playlist the playlist getting updated
     * @param index the index of the playlist in the Observable list
     * @throws MTBllException 
     */
    public void updatePlaylist(Playlist playlist, int index) throws MTBllException
    {
        mtmanager.updatePlaylist(playlist);
        playlistList.set(index, playlist);
    }
    
    
    
    
    
    
    
    
    
    
    
}
