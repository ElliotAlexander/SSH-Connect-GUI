package me.elliotpurvis.Jframe;

import me.elliotpurvis.TextPrompt;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * Created by elliot on 02/08/16.
 */
public class ConsoleWindow extends JFrame{

    private OutputStream out;
    private PipedInputStream pIn;
    private PipedOutputStream pOut;

    public ConsoleWindow(){
        super("Console");


        //
        // Configure input area
        //
        JTextField inputTextField = new JTextField();
        TextPrompt consoleTp = new TextPrompt(">> ", inputTextField);
        consoleTp.setShow(TextPrompt.Show.FOCUS_LOST);
        inputTextField.setPreferredSize(new Dimension(560, 40));
        inputTextField.setVisible(true);
        add(inputTextField);


        pIn = new PipedInputStream();
        try {
            pOut = new PipedOutputStream(pIn);
        } catch(IOException e){
            e.printStackTrace();
        }

        new InputArea(inputTextField, pOut);



        //
        // Configure Window
        //
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(600,450));
        setSize(new Dimension(600, 450));
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(((int)screensize.getWidth() / 2)-300, ((int)screensize.getHeight()/2)-225);



        //
        // Configure console output area
        //
        TextOutputArea outputArea = new TextOutputArea();
        outputArea.setPreferredSize(new Dimension(560, 300));
        out = outputArea.getSshOutputStream();
        add(outputArea);
        setVisible(true);




        //
        // Bounds
        //
        layout.putConstraint(SpringLayout.NORTH, inputTextField, 340, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, inputTextField, 15, SpringLayout.WEST, this);

        layout.putConstraint(SpringLayout.NORTH, outputArea, 20, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, outputArea, 15, SpringLayout.WEST, this);


        // Take inputted channel and define output and input streams for it as initiallised above.

    }


    public PipedInputStream getpIn(){
        return pIn;
    }

    public OutputStream getOut(){
        return out;
    }
}
