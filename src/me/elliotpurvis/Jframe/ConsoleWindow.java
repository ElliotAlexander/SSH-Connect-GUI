package me.elliotpurvis.Jframe;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import me.elliotpurvis.Main;

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

    private final String host, user;
    private final int port;
    private final char[] password;
    private final JSch jsch;
    private OutputStream out;
    private InputArea in;

    private PipedInputStream pIn;
    private PipedOutputStream pOut;


    public ConsoleWindow(int port, String host, String user, char[] password){
        super("Console");
        jsch = new JSch();


        //
        // Configure input area
        //
        JTextField inputTextField = new JTextField();
        add(inputTextField);


        pIn = new PipedInputStream();
        try {
            pOut = new PipedOutputStream(pIn);
            in = new InputArea(inputTextField, pOut);
        } catch(IOException e){
            e.printStackTrace();
        }


        this.password = password;
        this.port = port;
        this.host = host;
        this.user = user;

        //
        // Configure Window
        //
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        layout.putConstraint(SpringLayout.NORTH, inputTextField, 410, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, inputTextField, 20, SpringLayout.WEST, this);

        layout.putConstraint(SpringLayout.NORTH, outputArea, 20, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, outputArea, 20, SpringLayout.WEST, this);

        // Connect.
        SSHConnect();
    }

    private void SSHConnect(){
        try {
            Session session = jsch.getSession(user, host, port);


            // Disable key checking
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.setPassword(String.valueOf(password));
            System.out.println("Attempting to connect to " + host + " on port " + port + " ....");
            session.connect(3000);


            Channel channel = session.openChannel("shell");
            channel.setInputStream(pIn);
            channel.setOutputStream(out);
            channel.connect(3000);
            System.out.println("Connected.");
        } catch (JSchException e){
            System.out.println("Exception thrown with error " + e.getCause());
            e.printStackTrace();
        }
    }
}
