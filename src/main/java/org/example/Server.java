package org.example;

import java.io.*;
import java.net.*;

public class Server {

    // listens for incoming client connections
    private ServerSocket serverSocket;

    // Server constructor: initialize the server with a specific ServerSocket
    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    // method to start server and accept connections
    public void startServer() {
        try {
            while (!serverSocket.isClosed()) {

                // accept incoming client connection
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                // create a new ClientHandler for the connected client
                ClientHandler clientHandler = new ClientHandler(socket);

                // start the client handler in a new thread
                Thread thread = new Thread(clientHandler);
                thread.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // method to close the server socket safely
    public void closeServerSocke() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // main method to run the server
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4999);
        Server server = new Server(serverSocket); // create and start the server
        server.startServer();

    }

}