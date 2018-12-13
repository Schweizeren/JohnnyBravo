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
    /**
     * Takes an mp3 file and checks if the file either has an ID3v1 tag or
     * an ID3v2 tag. It then gets the title of the song from the files metadata
     * 
     * @param filepath the filepath of the mp3 file
     * @return a string containing the title of the song
     * @throws MTDalException 
     */
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
    
    /**
     * Checks if the mp3 file has an ID3v1 or ID3v2 tag. Then gets the artist
     * of the song from the files metadata
     * 
     * @param filepath the filepath of the mp3 file
     * @return a string containing the artist of the song
     * @throws MTDalException 
     */
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
    
    /**
     * Checks if the mp3 file has an ID3v1 or ID3v2 tag. Then gets the genre
     * of the song from the files metadata
     * 
     * @param filepath the filepath of the mp3 file
     * @return a string containing the genre of the song
     * @throws MTDalException 
     */
    public String getGenre(String filepath) throws MTDalException
    {
        File file = new File(filepath);
        MP3File mp3;
        try
        {
            mp3 = new MP3File(file);
            if (mp3.hasID3v1Tag()) 
            {
            ID3v1 tag = mp3.getID3v1Tag();
            String genre = tag.getSongGenre();
            return genre;
            }
            else if (mp3.hasID3v2Tag()) 
            {
            AbstractID3v2 tag = mp3.getID3v2Tag();
            String genre = tag.getSongGenre();
            return genre;
            }
        } 
        catch (IOException ex)
        {
            throw new MTDalException("Could not find MP3File", ex);
        } 
        catch (TagException ex)
        {
            throw new MTDalException("Could not find corresponding tag", ex);
        }
        return null;
    }
    
    /**
     * Takes an mp3 file and gets its duration in seconds
     * 
     * @param filepath the filepath of the mp3 file
     * @return an integer containing the mp3 files duration in seconds
     * @throws MTDalException 
     */
    public int getDurationInSec(String filepath) throws MTDalException
    {
        File file = new File(filepath);
        AudioFile audiofile;
        try
        {
            audiofile = AudioFileIO.read(file);
        } 
        catch (org.jaudiotagger.tag.TagException ex)
        {
            throw new MTDalException();
        } 
        catch (ReadOnlyFileException ex)
        {
            throw new MTDalException();
        }
        catch (InvalidAudioFrameException ex)
        {
            throw new MTDalException();
        } 
        catch (CannotReadException ex)
        {
            throw new MTDalException("Could not read the file.", ex);
        } 
        catch (IOException ex)
        {
            throw new MTDalException("Could not find MP3 file.", ex);
        }
        int duration = 0;
        try 
        {
         duration = audiofile.getAudioHeader().getTrackLength();
        } 
        catch (NullPointerException ex) {
            throw new MTDalException("The duration was not initiliazed.", ex);
        }
        return duration;
    }
}
