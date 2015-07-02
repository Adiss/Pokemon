package hu.experiment_team;

import hu.experiment_team.models.Trainer;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the server of the Pokemon game
 * @author Jakab Ádám
 * */
public class Server {

    private Socket socket = null;
    private ServerSocket server = null;
    private static List<Socket> onlinePlayers = new ArrayList<>();

    /**
     * Here will be binded the port, and listen for new players
     * @param port This will be the port of the server
     * */
    public Server(int port) {
        try {
            System.out.println("Binding to port " + port + "...");
            this.server = new ServerSocket(port);
            System.out.println("Server started: " + server);
            while(true){
                try{
                    System.out.println("Waiting for players...");
                    this.socket = this.server.accept();
                    HandleClients hc = new HandleClients(this.socket);
                    hc.start();
                    onlinePlayers.add(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This class will handle the connected users. Every player will get its own thread
     * */
    private class HandleClients extends Thread {

        private ObjectOutputStream out = null;
        private ObjectInputStream in = null;
        private Socket socket = null;

        public HandleClients(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run(){
            Object inputObject;
            try {
                System.out.println("Client accepted: " + this.socket);
                this.out = new ObjectOutputStream(socket.getOutputStream());
                this.in = new ObjectInputStream(socket.getInputStream());
                System.out.println("Communication started!");

                while ((inputObject = this.in.readObject()) != null){
                    parseMessage(inputObject);
                }

                if(this.socket != null) this.socket.close();
                if(this.in != null) this.in.close();
            } catch(IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
        }

        /**
         * This method will parse the input messages.
         * @param inputObject This is the object which we get from the clients
         * @exception IOException
         * */
        private void parseMessage(Object inputObject) throws IOException {
            if(inputObject instanceof String){
                if(((String) inputObject).contains("!onlinePlayers")) {
                    System.out.println("'onlinePlayers' request from: " + socket.getPort() + " port.");
                    this.out.writeObject(onlinePlayers());
                    this.out.flush();
                    System.out.println("List of online players has sent to: '" + socket.getPort() + " port.");
                } else if(((String) inputObject).contains("!whisper")){
                    String[] msg = ((String) inputObject).split(" ");
                    String final_message = "";
                    for(int i = 3; i < msg.length; i++) final_message += msg[i];
                    sendWhisper(socket.getPort(), Integer.parseInt(msg[2]), final_message);
                } else if(((String) inputObject).contains("!getMyPort")){
                    getMyPort();
                } else if(((String) inputObject).contains("!getMyIp")){
                    getMyIp();
                } else if(((String) inputObject).contains("!login")) {
                    System.out.println("'login' request from: " + socket.getPort() + " port.");
                    List<String> loginData = new ArrayList<>();
                    for(String s : ((String) inputObject).split(" ")){
                        loginData.add(s);
                        System.out.println(s);
                    }
                    Trainer user = UserMethods.INSTANCE.login(loginData.get(2), loginData.get(3));
                    if(user != null){
                        System.out.println("Socket " + socket.getPort()+ " has logged in!");
                        this.out.writeObject("You are now logged in!");
                        this.out.flush();
                    } else {
                        System.out.println("Socket " + socket.getPort()+ " failed to log in!");
                    }
                } else {
                    System.out.println(inputObject + " | FROM: " + socket);
                    this.out.writeObject("Server got your message!");
                    this.out.flush();
                }
            }
        }

        /**
         * This method gets the online players from the onlinePlayers List.
         * */
        private Object onlinePlayers(){
            String list = "Ports of online players: ";
            for(Socket s : onlinePlayers)
                if(s.getPort() == socket.getPort()) list += "(Your port = " + s.getPort() + "),";
                else list += s.getPort() + ",";
            return list;
        }

        /**
         * This method searches a players by the given port
         * @param port This is the players port, which we searching for
         * */
        private Socket getPlayer(int port){
            Socket player = null;
            for(Socket s : onlinePlayers){
                if(s.getPort() == port){
                    player = s;
                    break;
                }
            }
            return player;
        }

        private void sendWhisper(int from, int to, String message){
            try {
                Object msg = "From " + from +  " to " + to + ": " + message;
                System.out.println(getPlayer(to));
                ObjectOutputStream whisperTo = new AppendingObjectOutputStream(getPlayer(to).getOutputStream());
                whisperTo.writeObject(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void getMyPort(){
            Object port = socket.getPort();
            try {
                this.out.writeObject(port);
                this.out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void getMyIp(){
            Object iAddress = socket.getInetAddress();
            try {
                this.out.writeObject(iAddress);
                this.out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public class AppendingObjectOutputStream extends ObjectOutputStream {
            public AppendingObjectOutputStream(OutputStream out) throws IOException {
                super(out);
            }
            @Override
            protected void writeStreamHeader() throws IOException {
                reset();
            }

        }

    }

}
