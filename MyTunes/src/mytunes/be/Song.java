        /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

/**
 *
 * @author bonde
 */
public class Song
{
    private int id;
    private String title;
    private int length;
    private String artist;
    private String genre;
    private String filepath;
    private int locationInList;
    private String formattedLength;
    
    /**
     * The constructor of the Song class
     * @param id id of the song
     * @param title songs title
     * @param duration the songs duration in seconds
     * @param author artist of the song
     * @param genre the songs genre
     * @param filepath the songs filedestination
     */
    public Song (int id, String title, int duration, String author, String genre, String filepath) {
        this.id = id;
        this.title = title;
        this.length = duration;
        this.artist = author;
        this.genre = genre;
        this.filepath = filepath;
        
    }
    
    /**
     * Gets the songs id
     * @return the id of the song
     */
    public int getId() {
        return id;
    }
    
    /**
     * Get the songs title
     * @return the title of the song
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Sets the songs title
     * @param title the title getting assigned to a song
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Gets the songs duration in seconds
     * @return the songs duration
     */
    public int getLength() {
        return length;
    }
    
    /**
     * Gets the artist of the song
     * @return the songs artist
     */
    public String getArtist() {
        return artist;
    }
    
    /**
     * Sets the songs artist
     * @param artist the artist getting assigned to a song
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }
    
    /**
     * Gets the genre of the song
     * @return the songs genre
     */
    public String getGenre() {
        return genre;
    }
    
    /**
     * Sets the songs genre
     * @param genre the genre getting assigned to a song
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    /**
     * Gets the songs file destination
     * @return the filepath
     */
    public String getFilepath()
    {
        return filepath;
    }
    
    /**
     * Sets a songs file destination
     * @param filepath the filepath getting assigned to a song
     */
    public void setFilepath(String filepath)
    {
        this.filepath = filepath;
    }
    
    /**
     * Gets a songs location in a playlist
     * @return the songs location in a playlist
     */
    public int getLocationInList() {
        return locationInList;
    }
    
    /**
     * Sets a songs location in a playlist
     * @param locationInList the location getting assigned to a song
     */
    public void setLocationInList(int locationInList) {
        this.locationInList = locationInList;
    }
    
    /**
     * Gets a songs duration formatted so it is displayed in both minutes and
     * seconds
     * @return the songs formatted duration as a string
     */
    public String getFormattedLength() {
        int seconds = length % 60;
        int minutes = (length - seconds) / 60;

        String mp3Seconds;
        String mp3Minutes = "" + minutes;
        if (seconds < 10)
        {
            mp3Seconds = "0" + seconds;
        } else
        {
            mp3Seconds = "" + seconds;
        }

        formattedLength = mp3Minutes + ":" + mp3Seconds;
        return formattedLength;
    }
}
