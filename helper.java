//libraries
import javax.sound.sampled.*;
import java.io.*;

public class helper extends Thread{
    
    TargetDataLine line;
    
    public helper(TargetDataLine line){
        this.line = line;
    }
    
    public void run(){
        try {
            //save to a file
            AudioInputStream ais = new AudioInputStream(line);
            AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File("rep.wav"));
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
