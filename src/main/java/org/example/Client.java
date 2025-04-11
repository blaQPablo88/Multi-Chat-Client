package org.example;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    private Socket socket; // socket to connect server
    private BufferedReader bufferedReader; // reads messages from server
    private BufferedWriter bufferedWriter; // writes messages to server
    String userName; // for clients username

    // Client constructors: initialises client resources
    public Client(Socket socket, String userName) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.userName = userName;
        } catch (Exception e) {
            closeEverything(socket, bufferedWriter, bufferedReader);
        }
    }

    // method to send message to server
    public void sendMessage() {
        try {
            // send username first (to group messages by user), to server
            bufferedWriter.write(userName);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            // read messages from users input and send to server
            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(userName + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            closeEverything(socket, bufferedWriter, bufferedReader);
        }
    }

    // listen for messages from the server
    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;

                while(socket.isConnected()) {
                    try {
                        // print messages from server to console
                        msgFromGroupChat = bufferedReader.readLine();
                        System.out.println(msgFromGroupChat);
                    } catch (IOException e) {
                        closeEverything(socket, bufferedWriter, bufferedReader);
                    }
                }
            }
        }).start();
    }

    // closes all resources
    public void closeEverything(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
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
            e.printStackTrace();
        }
    }

    // main method to start the client
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your username for the group chat: ");
        String username = scanner.nextLine();

        // connect to localhost on port 4999
        Socket socket = new Socket("localhost", 4999);

        // create a new client and start the chat
        Client client = new Client(socket, username);
        client.listenForMessage(); // listen for messages from server
        client.sendMessage(); // send messages to server
    }

}
