package me.elliotpurvis.Jframe;

import me.elliotpurvis.TextPrompt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PipedOutputStream;

/**
 * Created by elliot on 02/08/16.
 */
public class InputArea {


    public InputArea(final JTextField inputarea, final PipedOutputStream pOut) {

        TextPrompt consoleTp = new TextPrompt(">> ", inputarea);
        consoleTp.setShow(TextPrompt.Show.FOCUS_LOST);
        inputarea.setPreferredSize(new Dimension(560, 40));
        inputarea.setVisible(true);
        inputarea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    byte[] tmp = inputarea.getText().getBytes();
                    try {
                        pOut.write(tmp, 0, tmp.length);
                        pOut.write('\n');
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    inputarea.setText("");

                    super.keyReleased(e);
                }
            }
        });
    }
}