package Socket2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    private static final int PORT = 12345;
    private static List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started. Listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private String username;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                out.println("Enter your username:");
                username = in.readLine();
                System.out.println("User " + username + " joined the chat.");

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println(username + ": " + inputLine);
                    sendMessageToAll(username + ": " + inputLine);
                }

                clients.remove(this);
                clientSocket.close();
                System.out.println("User " + username + " left the chat.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void sendMessageToAll(String message) {
            for (ClientHandler client : clients) {
                client.out.println(message);
            }
        }
    }
}
