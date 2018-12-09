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
import mytunes.DAL.exception.MTDalException;
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
    public String getSongTitle(String filepath) throws MTDalException
    {
        File file = new File(filepath);
        MP3File mp3;
        try
        {
            mp3 = new MP3File(file);
            if (mp3.hasID3v1Tag()) {
            ID3v1 tag = mp3.getID3v1Tag();
            String title = tag.getSongTitle();
            return title;
        }else if (mp3.hasID3v2Tag()) {
            AbstractID3v2 tag = mp3.getID3v2Tag();
            String title = tag.getSongTitle();
            return title;
        }
        } catch (IOException ex)
        {
            throw new MTDalException("Could not find MP3 file.", ex);
        } catch (TagException ex)
        {
            throw new MTDalException("Could not find corresponding tag.", ex);
        }
        return null;
    }
    
    public String getAuthor(String filepath) throws MTDalException{
        File file = new File(filepath);
        MP3File mp3;
        try
        {
            mp3 = new MP3File(file);
            if (mp3.hasID3v1Tag()) {
            ID3v1 tag = mp3.getID3v1Tag();
            String artist = tag.getArtist();
            return artist;
        }else if (mp3.hasID3v2Tag()) {
            AbstractID3v2 tag = mp3.getID3v2Tag();
            String artist = tag.getLeadArtist();
            return artist;
        }
        } catch (IOException ex)
        {
            throw new MTDalException("Could not find MP3 file.", ex);
        } catch (TagException ex)
        {
            throw new MTDalException("Could not find corresponding tag", ex);
        }
        return null;
    }
    
    public String getGenre(String filepath) throws MTDalException
    {
        File file = new File(filepath);
        MP3File mp3;
        try
        {
            mp3 = new MP3File(file);
            if (mp3.hasID3v1Tag()) {
            ID3v1 tag = mp3.getID3v1Tag();
            String genre = tag.getSongGenre();
            return genre;
            }else if (mp3.hasID3v2Tag()) {
            AbstractID3v2 tag = mp3.getID3v2Tag();
            String genre = tag.getSongGenre();
            return genre;
        }
        } catch (IOException ex)
        {
            throw new MTDalException("Could not find MP3File", ex);
        } catch (TagException ex)
        {
            throw new MTDalException("Could not find corresponding tag", ex);
        }
        return null;
    }
    public int getDurationInSec(String filepath) throws MTDalException
    {
        File file = new File(filepath);
        AudioFile audiofile = null;
        try
        {
            audiofile = AudioFileIO.read(file);
        } catch (org.jaudiotagger.tag.TagException ex)
        {
            throw new MTDalException();
        } catch (ReadOnlyFileException ex)
        {
            throw new MTDalException();
        } catch (InvalidAudioFrameException ex)
        {
            throw new MTDalException();
        } catch (CannotReadException ex)
        {
            throw new MTDalException("Could not read the file.", ex);
        } catch (IOException ex)
        {
            throw new MTDalException("Could not find MP3 file.", ex);
        }
        int duration = 0;
        try {
         duration = audiofile.getAudioHeader().getTrackLength();
        } catch (NullPointerException ex) {
            throw new MTDalException("The duration was not initiliazed.", ex);
        }
        return duration;
    }
}
