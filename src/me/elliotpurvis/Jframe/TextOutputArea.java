package me.elliotpurvis.Jframe;

import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;

/**
 * Created by elliot on 02/08/16.
 */


public class TextOutputArea extends JPanel {

    private JTextArea textArea = new JTextArea(15, 30);
    private ConsoleOutputStream sshOutputStream = new ConsoleOutputStream(
            textArea, "");

    public TextOutputArea() {
        setLayout(new BorderLayout());
        add(new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        PrintStream outputStream = new PrintStream(sshOutputStream);
        System.setOut(outputStream);
        System.setErr(outputStream);
    }

    public ConsoleOutputStream getSshOutputStream(){
        return sshOutputStream;
    }
}