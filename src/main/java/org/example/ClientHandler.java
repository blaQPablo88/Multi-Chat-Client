package org.example;

import java.io.*;
import java.net.Socket;
import java.security.PrivateKey;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    // keeps track of all active client handlers
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket; // socket from server class
    private BufferedReader bufferedReader; // read messages from server
    private BufferedWriter bufferedWriter; // send messages to client
    private String clientUserName; // stores connected users

    // ClientHandler constructor: initialise handler
    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUserName = bufferedReader.readLine();

            // add handler to list
            clientHandlers.add(this);

            // notify others when new user joins
            broadcastMessage("Server: " + clientUserName + " has entered tyhe chat!");
        } catch (IOException e) {
            closeEverything(socket, bufferedWriter, bufferedReader);
        }
    }

    @Override
    public void run() {
        String messageFromClient;

        // continuously listen to client messages
        while (socket.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine(); // read client message
                broadcastMessage(messageFromClient); // broadcast to everyone
            } catch (IOException e) {
                // if error occurs, close everything and break loop
                closeEverything(socket, bufferedWriter, bufferedReader);
                break;
            }
        }
    }

    // send message to everyone connect except the sender
    public void broadcastMessage(String messageToSend) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.clientUserName.equals(clientUserName)) {
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                // closes all resources if unable send message
                closeEverything(socket, bufferedWriter, bufferedReader);
            }
        }
    }

    // removes handler and notifies everyone
    public void removeClientHandler() {
        clientHandlers.remove(this);
        broadcastMessage("SERVER: " + clientUserName + " has left the chat.");
    }

    // closes everything associated to the client
    public void closeEverything(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        // removes from connected members
        removeClientHandler();
        try {
            if (socket != null) {
                socket.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace(); // logs errors if it dont close
        }
    }

 }