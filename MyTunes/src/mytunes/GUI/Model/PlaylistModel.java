/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.GUI.Model;

import java.io.IOException;
import java.sql.SQLException;
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
    
    public PlaylistModel() throws MTBllException 
    {
        playlistList = FXCollections.observableArrayList();
        mtmanager = new MyTunesManager();
        playlistList.addAll(mtmanager.getAllPlaylist());
    }
    
    public void createPlaylist(String name) throws MTBllException
    {
        Playlist playlist = mtmanager.createPlaylist(name);
        playlistList.add(playlist);
        
    }
    
    public Playlist getPlaylist(int id) throws MTBllException
    {
        return mtmanager.getPlaylist(id);
    }
    
    public void deletePlaylist(Playlist playlist) throws MTBllException
    {
        mtmanager.deletePlaylist(playlist);
        playlistList.remove(playlist);
    }
    
    public ObservableList<Playlist> getAllPlaylist()
    {
        return playlistList;
    }
    
    public void updatePlaylist(Playlist playlist) throws MTBllException
    {
        mtmanager.updatePlaylist(playlist);
    }
    
    
    
    
    
    
    
    
    
    
    
}
