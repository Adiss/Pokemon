/**
 * Created by Jakab on 2015.06.28..
 */
package hu.experiment_team;

import java.io.*;
import java.net.*;

public class Client {

    private Socket client = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;

    public Client(String serverIp, int serverPort){
        try{
            client = new Socket(serverIp, serverPort);
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());
            System.out.println("Connected to " + serverIp + ".");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Client start(){
        System.out.println("Communication started!");
        Runnable read = () -> {
            try {
                Object serverIn;
                while (true) {
                    if((serverIn = this.in.readObject()) != null){
                        if(serverIn instanceof String)
                            System.out.println((String)serverIn);
                        else
                            System.out.println(serverIn);
                    }
                    else break;
                }
                if (this.in != null) this.in.close();
                if (this.client != null) this.client.close();
            } catch(IOException e){
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        };
        Runnable write = () -> {
            try {
                String userIn;
                BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
                while (((userIn = console.readLine()) != null)) {
                    this.out.writeObject(parseMessage(userIn));
                    this.out.flush();
                }
                if (this.out != null) this.out.close();
                if (this.client != null) this.client.close();
            } catch(IOException e){
                e.printStackTrace();
            }
        };
        new Thread(read).start();
        new Thread(write).start();
        return this;
    }

    private Object parseMessage(String command){
        Object message = null;
        if(command.contains("!onlinePlayers")) message = command;
        else message = "USER_MESSAGE_TO_SERVER " + command;
        return message;
    }

}

