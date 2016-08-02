package me.elliotpurvis.Jframe;

import me.elliotpurvis.Main;
import me.elliotpurvis.TextPrompt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by elliot on 02/08/16.
 */
public class CredentialsWindow extends JFrame {

    private final Main main;

    private final JTextField hostname,user;
    private final JPasswordField password;
    private final JButton connect, reset;

    public CredentialsWindow(Main main) {
        super("SSH - Connect");
        this.main = main;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Centre the winddow, regardless of screen resolution.  Must be called after setVisible.
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(((int)screensize.getWidth() / 2)-200, ((int)screensize.getHeight() /2)-135);

        SpringLayout layout = new SpringLayout();

        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException e) {
            System.err.print("Unsupported look and feel, using default L&F settings (Metal)");
        }
        catch (ClassNotFoundException e) {
            System.err.println("Specified look and feel not found, using default L&F (Metal)");
        }
        catch (InstantiationException e) {
            System.err.println("Failure initiating specified Look and Feel, using default L&F (Metal)");
        }
        catch (IllegalAccessException e) {
            System.err.println("Unable to access specified Look and Feel, using default L&F (Metal)");
        }
        catch (Exception e){
            System.err.println("Unknown error applying specified Look and Feel, using default L&F (Metal)");
            e.printStackTrace();
        }


        setLayout(layout);
        setSize(210, 400);
        setPreferredSize(new Dimension(400,270));
        setVisible(true);


        // Standard size of an input box.
        Dimension inputBoxDims = new Dimension(150,40);



        //
        //  Hostname Field
        //
        hostname = new JTextField();
        hostname.setText(null);
        hostname.setVisible(true);
        hostname.setPreferredSize(inputBoxDims);
        add(hostname);

        //
        // User field
        //
        user = new JTextField();
        user.setText(null);
        user.setVisible(true);
        user.setPreferredSize(inputBoxDims);
        add(user);


        //
        // Password Field
        //
        password = new JPasswordField();
        password.setVisible(true);
        password.setPreferredSize(inputBoxDims);
        password.setText(null);
        add(password);



        // Connect Button
        connect = new JButton();
        connect.setText("<html><b>Connect</b></html>");
        connect.setPreferredSize(inputBoxDims);
        connect.setVisible(true);
        add(connect);

        // Reset Button
        reset = new JButton();
        reset.setText("<html><b>Reset</b><html>");
        reset.setPreferredSize(inputBoxDims);
        reset.setVisible(true);
        add(reset);


        connect.addActionListener(buttonListener);
        reset.addActionListener(buttonListener);

        resetTextPrompts();


        layout.putConstraint(SpringLayout.WEST, hostname, 30, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, hostname,25,SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, user,30, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, user, 85, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, password, 30, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, password, 150, SpringLayout.WEST, this);

        layout.putConstraint(SpringLayout.WEST, connect, 30, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, connect, 215, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, reset, 30, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, reset, 280, SpringLayout.NORTH, this);
    }

    private void resetTextPrompts(){

        user.setText("");
        password.setText("");
        hostname.setText("");

        TextPrompt tpUser = new TextPrompt("Username", user);
        tpUser.setShow(TextPrompt.Show.FOCUS_LOST);


        TextPrompt tpPassword = new TextPrompt("Password", password);
        tpPassword.setShow(TextPrompt.Show.FOCUS_LOST);


        TextPrompt tpHost = new TextPrompt("Hostname:port", hostname);
        tpHost.setShow(TextPrompt.Show.FOCUS_LOST);
    }


    ActionListener buttonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource() == connect) {
                if (hostname.getText().length() == 0 || password.getPassword().length == 0 || user.getText().length() == 0) {
                    new ErrorWindow("Invalid Credentials!", "Enter valid login credentials.");

                } else {
                    if (hostname.getText().contains(":")) {
                        String[] hostInputArray = hostname.getText().split(":");
                        String parsedHost = hostInputArray[0];
                        int port = Integer.parseInt(hostInputArray[1]);


                        new ConsoleWindow(port, parsedHost, user.getText(), password.getPassword());
                    } else {
                        new ConsoleWindow(22, hostname.getText(), user.getText(), password.getPassword());
                    }
                }
            } else if (actionEvent.getSource() == reset) {
                    resetTextPrompts();
            }
        }
    };
}
