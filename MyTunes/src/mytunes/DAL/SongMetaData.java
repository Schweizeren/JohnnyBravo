/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.DAL;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.AbstractID3v2;
import org.farng.mp3.id3.ID3v1;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;

/**
 *
 * @author Frederik Jensen
 */
public class SongMetaData
{
    public String getSongTitle(String filepath) throws IOException, TagException {
        File file = new File(filepath);
        MP3File mp3 = new MP3File(file);
        if (mp3.hasID3v1Tag()) {
            ID3v1 tag = mp3.getID3v1Tag();
            String title = tag.getSongTitle();
            return title;
        }else if (mp3.hasID3v2Tag()) {
            AbstractID3v2 tag = mp3.getID3v2Tag();
            String title = tag.getSongTitle();
            return title;
        }
        return null;
    }
    public String getAuthor(String filepath) throws IOException, TagException {
        File file = new File(filepath);
        MP3File mp3 = new MP3File(file);
        if (mp3.hasID3v1Tag()) {
            ID3v1 tag = mp3.getID3v1Tag();
            String artist = tag.getArtist();
            return artist;
        }else if (mp3.hasID3v2Tag()) {
            AbstractID3v2 tag = mp3.getID3v2Tag();
            String artist = tag.getLeadArtist();
            return artist;
        }
        return null;
    }
    public String getGenre(String filepath) throws IOException, TagException {
        File file = new File(filepath);
        MP3File mp3 = new MP3File(file);
        if (mp3.hasID3v1Tag()) {
            ID3v1 tag = mp3.getID3v1Tag();
            String genre = tag.getSongGenre();
            return genre;
        }else if (mp3.hasID3v2Tag()) {
            AbstractID3v2 tag = mp3.getID3v2Tag();
            String genre = tag.getSongGenre();
            return genre;
        }
        return null;
    }
    public int getDurationInSec(String filepath) {
        File file = new File(filepath);
        AudioFile audiofile = null;
        try
        {
            audiofile = AudioFileIO.read(file);
        } catch (org.jaudiotagger.tag.TagException ex)
        {
            Logger.getLogger(SongMetaData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ReadOnlyFileException ex)
        {
            Logger.getLogger(SongMetaData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAudioFrameException ex)
        {
            Logger.getLogger(SongMetaData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CannotReadException ex)
        {
            Logger.getLogger(SongMetaData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(SongMetaData.class.getName()).log(Level.SEVERE, null, ex);
        }
        int duration = 0;
        try {
         duration = audiofile.getAudioHeader().getTrackLength();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return duration;
    }
}
