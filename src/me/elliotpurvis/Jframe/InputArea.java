package me.elliotpurvis.Jframe;

import me.elliotpurvis.TextPrompt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by elliot on 02/08/16.
 */
public class InputArea extends InputStream {

    byte[] contents;
    int pointer = 0;


    public InputArea(JTextField inputarea) {

        TextPrompt consoleTp = new TextPrompt(">> ", inputarea);
        consoleTp.setShow(TextPrompt.Show.FOCUS_LOST);
        inputarea.setPreferredSize(new Dimension(560, 40));
        inputarea.setVisible(true);
        // Fire listener for input area when enter key is pressed .
        inputarea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    contents = inputarea.getText().getBytes();
                    pointer = 0;
                    inputarea.setText("");
                }
                super.keyReleased(e);
            }
        });
    }

    @Override
    public int read() throws IOException {
        if (pointer >= contents.length) return -1;
        return this.contents[pointer++];
    }
}