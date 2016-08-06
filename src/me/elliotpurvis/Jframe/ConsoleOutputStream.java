package me.elliotpurvis.Jframe;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

public class ConsoleOutputStream extends OutputStream {

    private final JTextArea textArea;
    private final StringBuilder sb = new StringBuilder();
    private String title;

    public ConsoleOutputStream(final JTextArea textArea, String title) {
        this.textArea = textArea;
        this.title = title;
        sb.append(title + "> ");
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() {
    }

    @Override
    public void write(int b) throws IOException {

        if (b == '\r')
            return;

        if (b == '\n') {

            // Filter out VT100 terminal codes. (ASCII)
            String tmp = sb.toString() + "\n";
            tmp = tmp.replaceAll("\u001B\\[[\\d;]*[^\\d;]","");
            final String text = tmp;



            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    textArea.append(text);
                }
            });
            sb.setLength(0);
            sb.append(title + "> ");
            return;
        }


        sb.append((char) b);
    }
}