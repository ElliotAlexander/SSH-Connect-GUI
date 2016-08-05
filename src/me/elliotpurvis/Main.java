package me.elliotpurvis;

import me.elliotpurvis.Jframe.CredentialsWindow;

import javax.swing.*;

/**
 * Created by elliot on 02/08/16.
 */

public class Main {


    private Main(){

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
               new CredentialsWindow();
            }
        });
    }

    public static void main(String[] args){
        new Main();
    }

}
