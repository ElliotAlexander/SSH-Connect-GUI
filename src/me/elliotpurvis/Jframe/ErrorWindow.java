package me.elliotpurvis.Jframe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created by elliot on 02/08/16.
 */
public class ErrorWindow extends JFrame implements ActionListener{

    private final JTextPane errorLabel;
    private static final String SYSTEM_FONT = new JLabel().getFont().getFontName();
    private final JButton closeButton;




    public ErrorWindow(String title, String errorMessage){

        this.setName(title);

        FlowLayout layout = new FlowLayout();
        setLayout(layout);
        setSize(600, 125);
        setVisible(true);


        errorLabel = new JTextPane();
        // Order of arguements = new Insets(Top Inset, left Inset, bottom Inset, right);
        errorLabel.setMargin(new Insets(10, 40, 10, 40));
        errorLabel.setText(errorMessage);
        errorLabel.setSize(400, 50);
        add(errorLabel);

        errorLabel.setEditable(false);
        errorLabel.setCursor(null);
        errorLabel.setOpaque(false);

        closeButton = new JButton("Close");
        closeButton.addActionListener(this);
        closeButton.setVisible(true);
        //closeButton.setMargin(new Insets(0,0,20,0));
        add(closeButton);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setResizable(false);

        // Define size of the display we're running on
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        // Centre the window on the display
        setLocation((int) dim.getWidth() / 2 - (getWidth() / 2), (int) dim.getHeight() / 2 - (getHeight() / 2));

    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == closeButton){
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }
}

