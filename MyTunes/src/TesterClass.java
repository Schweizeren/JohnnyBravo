
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import mytunes.DAL.SongMetaData;
import mytunes.DAL.exception.MTDalException;
import org.farng.mp3.TagException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author bonde
 */
public class TesterClass {

    public static void main(String[] args) throws MTDalException {

    }

    public static void playMusic() {
        final JFXPanel fxPanel = new JFXPanel();
        String filepath = "data/music/bitch lasagna.mp3";
        Media hit = new Media(new File(filepath).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
    }

    private static String hjælpJannick(String... words) {
        String rowOfWords = "";
        for (int i = 0; i < words.length; i++) {
            rowOfWords = rowOfWords + words[i];
        }
        String combinedWords = rowOfWords.replaceAll(" ", "");
        return combinedWords;

    }
}
