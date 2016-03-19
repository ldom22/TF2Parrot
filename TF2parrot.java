//libraries
import javax.sound.sampled.*;
import sun.audio.*;
import java.io.*;
import java.util.*;

//main class
public class TF2parrot {
    
    static TargetDataLine line;
    static DataLine.Info info;
    static long time;
    
    public static void startRecording() throws Exception{
        //open mic access
        AudioFormat format = new AudioFormat(16000,16,2,true,true);
        info = new DataLine.Info(TargetDataLine.class, format);
        line = (TargetDataLine) AudioSystem.getLine(info);
        line.open(format);
        
        //start recording
        helper h = new helper(line);
        h.start();
        line.start();
        time = System.nanoTime();
    }
    
    public static void stopRecording(){
        line.stop();
        line.close();
        time = (long)((System.nanoTime()-time)/1e6);
    }
    
    public static void mimic(int num, boolean key) throws Exception{
        //open
        File srcFile = new File("rep.wav");
        File dstFile = new File("rep2.wav");
        FileInputStream in = new FileInputStream(srcFile);
        FileOutputStream out = new FileOutputStream(dstFile);
        byte[] buf = new byte[44];
        in.read(buf);
        out.write(buf, 0, 44);
        buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            byte[] nd = new byte[len/2];
            for(int i=0; i<len; i+=4){
                nd[i/2] = buf[i];
                nd[i/2+1] = buf[i+1];
            }
            out.write(nd, 0, len/2);
        }
        in.close();
        out.close();
        
        //play back modified
        if(key){
            Capture.press();
        }
        for(int i=0; i<num; i++){
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File("rep2.wav"));
            info = new DataLine.Info(Clip.class, new AudioFormat(16000,16,2,true,true));
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
            Thread.sleep(time/2);
            clip.stop();
        }
        if(key){
            Capture.release();
        }

    }
    
    //unit test
    public static void main(String args[]) throws Exception{
        startRecording();
        Thread.sleep(1000);
        stopRecording();
    }
}
