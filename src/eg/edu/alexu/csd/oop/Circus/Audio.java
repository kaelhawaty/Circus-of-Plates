package eg.edu.alexu.csd.oop.Circus;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Audio {
    Clip clip;
    public void playMusic(String audioLocation){
        try {
            File musicPath=new File(audioLocation);
            if(musicPath.exists()){
                AudioInputStream input= AudioSystem.getAudioInputStream(musicPath);
                clip=AudioSystem.getClip();
                clip.open(input);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            else
                System.out.println("can't find the file");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void stop(){ clip.stop();}
    public void resume(){
        clip.start();
    clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
