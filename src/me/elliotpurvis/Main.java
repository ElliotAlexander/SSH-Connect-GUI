package me.elliotpurvis;

import me.elliotpurvis.Jframe.CredentialsWindow;

import javax.swing.*;

/**
 * Created by elliot on 02/08/16.
 */
public class Main {

    private Main main;
    public static boolean verboseLogging;
    private  CredentialsWindow credentialsWindow;

    private Main(){

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                credentialsWindow = new CredentialsWindow(main);
            }
        });
    }

    public static void main(String[] args){
        for(int i = 0; i < args.length; i++){
            if(args[i].toString() == "-v" || args[i].toString() == "--verbose"){
                System.out.println("Running with Verbose logging enabled");
                verboseLogging = true;
            }
        }
        new Main();
    }

}
