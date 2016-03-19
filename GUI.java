import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class GUI extends JFrame implements ActionListener {
    
    JButton test_btn;
    CheckButton cb;
    static JCheckBox key_cbx;
    static JRadioButton jrb1;
    static JRadioButton jrb2;
    static JRadioButton jrb3;
    
    public GUI(){
        
        setLayout(new GridLayout(6,1));
        add(new JLabel("TF2 parrot V1.0", SwingConstants.CENTER));
        add(new JLabel("http://www.github.com/ldom22", SwingConstants.CENTER));
        
        add(new JLabel("Number of repeats:"));
        JPanel jp = new JPanel();
        jp.setLayout(new GridLayout(1,3));
        jrb1 = new JRadioButton("1");
        jrb2 = new JRadioButton("2", true);
        jrb3 = new JRadioButton("3");
        ButtonGroup bg = new ButtonGroup();
        bg.add(jrb1);
        bg.add(jrb2);
        bg.add(jrb3);
        jp.add(jrb1);
        jp.add(jrb2);
        jp.add(jrb3);
        add(jp);
        
        key_cbx = new JCheckBox("Automatically press 'v' key");
        key_cbx.setSelected(true);
        add(key_cbx);
        
        test_btn = new JButton("Click and hold to test");
        test_btn.addActionListener(this);
        add(test_btn);
        
        cb = new CheckButton(this);
        cb.start();
        
        setTitle("TF2 Parrot - by Luis Olea");
        setSize(300,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public static void main(String args[]){
        new GUI();
        Capture c = new Capture();
        while(true){
            if(c.isSomeoneSpeaking()){
                try {
                    TF2parrot.startRecording();
                    while(c.isSomeoneSpeaking());
                    TF2parrot.stopRecording();
                    if(jrb1.isSelected()){
                        TF2parrot.mimic(1, key_cbx.isSelected());
                    } else if(jrb2.isSelected()){
                        TF2parrot.mimic(2, key_cbx.isSelected());
                    } if(jrb3.isSelected()){
                        TF2parrot.mimic(3, key_cbx.isSelected());
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void actionPerformed(ActionEvent e){
        
    }
}

class CheckButton extends Thread {
    GUI gui;
    
    public CheckButton(GUI gui){
        this.gui = gui;
    }
    
    public void run(){
        while(true){
            try {
                while(gui.test_btn.getModel().isPressed()==false){
                    sleep(10);
                }
                gui.test_btn.setText("Speak now");
                TF2parrot.startRecording();
                while(gui.test_btn.getModel().isPressed()==true){
                    sleep(10);
                }
                gui.test_btn.setEnabled(false);
                gui.test_btn.setText("Playing back...");
                TF2parrot.stopRecording();
                if(gui.jrb1.isSelected()){
                    TF2parrot.mimic(1, false);
                } else if(gui.jrb2.isSelected()){
                    TF2parrot.mimic(2, false);
                } if(gui.jrb3.isSelected()){
                    TF2parrot.mimic(3, false);
                }
                gui.test_btn.setText("Click and hold to test");
                gui.test_btn.setEnabled(true);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
