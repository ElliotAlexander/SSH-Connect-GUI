package me.elliotpurvis;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import me.elliotpurvis.Jframe.ConsoleWindow;

/**
 * Created by Elliot on 05/08/2016.
 */
public class SSH {

    private final JSch jsch;

    // Called at program start.
    public SSH(){
        jsch = new JSch();
    }


    public void openNewSession(String user, String host, int port, char[] password, boolean hostKeyChecking, ConsoleWindow cw){
        try {
            Session session = jsch.getSession(user, host, port);


            if(!hostKeyChecking){
                // Disable key checking
                java.util.Properties config = new java.util.Properties();
                config.put("StrictHostKeyChecking", "no");
                session.setConfig(config);
            } else {

            }




            session.setPassword(String.valueOf(password));
            System.out.println("Attempting to connect to " + host + " on port " + port + " ....");
            session.connect(3000);
            Channel channel = session.openChannel("shell");

            channel.connect(3000);

            channel.setInputStream(cw.getpIn());
            channel.setOutputStream(cw.getOut());


        } catch (JSchException e){
            System.out.println("Exception thrown with error " + e.getCause());
            e.printStackTrace();
        }
    }
}
