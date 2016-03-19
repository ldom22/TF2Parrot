import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.Robot;
import java.io.*;
import javax.imageio.*;

public class Capture {
    
    Rectangle screenRect;
    BufferedImage capture;
    static Robot r;
    int coordX, coordY;
    
    public Capture(){
        try {
            r = new Robot();
            screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            double width = screenRect.width;
            width /= 1366;
            coordX = (int)(1142 * width);
            double height = screenRect.height;
            height /= 768;
            coordY = (int)(627 * height);
        } catch(Exception e){
            
        }
    }
    
    public boolean isSomeoneSpeaking(){
        try {
            screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            capture = r.createScreenCapture(screenRect);
            int coordX, coordY;
            coordX = 1142;
            coordY = 627;
            int a = capture.getRGB(coordX, coordY);
            int b = capture.getRGB(coordX+1, coordY);
            int c = capture.getRGB(coordX-1, coordY);
            int d = capture.getRGB(coordX, coordY+1);
            int e = capture.getRGB(coordX, coordY+1);
            if((a==-1)&&(a==b)&&(b==c)&&(c==d)&&(d==e)){
                return true;
            }
        } catch(Exception e){
            
        }
        return false;
    }
    
    public static void press(){
        r.keyPress(KeyEvent.VK_V);
    }
    
    public static void release(){
        r.keyRelease(KeyEvent.VK_V);
    }
    
    //unit test
    public static void main(String args[]){
        Capture c = new Capture();
        System.out.println(c.coordX+","+c.coordY);
    }
}
