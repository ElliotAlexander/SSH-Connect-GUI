package me.elliotpurvis.Jframe;

import me.elliotpurvis.SSH;
import me.elliotpurvis.TextPrompt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by elliot on 02/08/16.
 */
public class CredentialsWindow extends JFrame {




    private final JTextField hostname,user;
    private final JPasswordField password;
    private final JButton connect, reset;
    private final JCheckBox strictHostCheckbox, newWindowCheckbox;
    private final JLabel hostKeyCheckingText, newWindowText;
    private boolean hostKeyChecking, openInNewWindow;
    private ConsoleWindow cw;
    private SSH ssh;

    public CredentialsWindow() {
        super("SSH - Connect");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Centre the winddow, regardless of screen resolution.  Must be called after setVisible.
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(((int)screensize.getWidth() / 2)-200, ((int)screensize.getHeight() /2)-135);

        SpringLayout layout = new SpringLayout();
        ssh = new SSH();

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
        setSize(400, 400);
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

        // Strict host key checking options
        strictHostCheckbox = new JCheckBox();
        strictHostCheckbox.setVisible(true);
        strictHostCheckbox.setSelected(true);
        add(strictHostCheckbox);

        // Strict host checking text
        hostKeyCheckingText = new JLabel("Strict host key checking");
        hostKeyCheckingText.setVisible(true);
        add(hostKeyCheckingText);

        // New window Checkbox
        newWindowCheckbox= new JCheckBox();
        newWindowCheckbox.setVisible(true);
        newWindowCheckbox.setSelected(true);
        add(newWindowCheckbox);

        // New window text
        newWindowText = new JLabel("Open in new Console window");
        newWindowText.setVisible(true);
        add(newWindowText);


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

        layout.putConstraint(SpringLayout.WEST, strictHostCheckbox, 200, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, strictHostCheckbox, 30, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, hostKeyCheckingText, 222,  SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, hostKeyCheckingText, 33, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, newWindowCheckbox, 200, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, newWindowCheckbox, 50, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, newWindowText, 222, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, newWindowText, 53, SpringLayout.NORTH, this);


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
                    if(strictHostCheckbox.isSelected()){
                        hostKeyChecking = true;
                    } else {
                        hostKeyChecking = false;
                    }

                    String parsedHost = hostname.getText();
                    int port;

                    if (hostname.getText().contains(":")) {
                        String[] hostInputArray = hostname.getText().split(":");
                        parsedHost = hostInputArray[0];
                        port = Integer.parseInt(hostInputArray[1]);
                    } else {
                        port = 22;
                    }
                    openConsoleWindow(parsedHost, port);

                }
            } else if (actionEvent.getSource() == reset) {
                    resetTextPrompts();
            }
        }
    };


    private void openConsoleWindow(String host, int port){
        if(cw == null){
            cw = new ConsoleWindow();

            ssh.openNewSession(user.getText(), host, port, password.getPassword(), hostKeyChecking, cw);
        } else {
            // TODO Add tabbed support for existing Console window implementation.
        }
    }
}
